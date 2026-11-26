package io.github.sadellie.sukko.core.importexport

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import org.junit.Test

class SafeArchiveTest {
  private fun archive(vararg entries: Pair<String, String>): ByteArrayInputStream {
    val bytes = ByteArrayOutputStream()
    ZipOutputStream(bytes).use { zip ->
      entries.forEach { (name, value) ->
        zip.putNextEntry(ZipEntry(name))
        zip.write(value.toByteArray())
        zip.closeEntry()
      }
    }
    return ByteArrayInputStream(bytes.toByteArray())
  }

  private fun temporary(block: (File) -> Unit) {
    val root = Files.createTempDirectory("sukko-test").toFile()
    try {
      block(root)
    } finally {
      root.deleteRecursively()
    }
  }

  @Test
  fun rejectsDirectoryPayload() = temporary { root ->
    assertFailsWith<IllegalArgumentException> {
      extractPresetArchive(archive("directory/" to "hidden payload"), root)
    }
    assertFalse(root.exists())
  }

  @Test
  fun extractsDirectoriesAndAcceptsMissingPreview() = temporary { root ->
    extractPresetArchive(archive("iconPacks/" to "", "iconPacks/a.svg" to "icon"), root)
    assertEquals("icon", File(root, "iconPacks/a.svg").readText())
    assertNull(optionalPreview(root))
  }

  @Test
  fun rejectsUnsafePathsAndCleansPartialExtraction() {
    listOf("../escape", "/absolute", "C:/escape", "a\\b", "a/../../escape", "./a").forEach { path ->
      temporary { root ->
        val destination = File(root, "import").apply { mkdir() }
        assertFailsWith<IllegalArgumentException> {
          extractPresetArchive(archive("good" to "ok", path to "bad"), destination)
        }
        assertFalse(File(root, "escape").exists())
        assertFalse(destination.exists())
      }
    }
  }

  @Test
  fun enforcesActualEntryByteLimit() = temporary { root ->
    assertFailsWith<IllegalArgumentException> {
      extractPresetArchive(archive("large" to "a".repeat(10000)), root, maxEntryBytes = 20)
    }
    assertFalse(root.exists())
  }

  @Test
  fun enforcesTotalByteLimit() = temporary { root ->
    assertFailsWith<IllegalArgumentException> {
      extractPresetArchive(archive("a" to "123", "b" to "456"), root, maxTotalBytes = 5)
    }
  }

  @Test
  fun enforcesEntryCount() = temporary { root ->
    assertFailsWith<IllegalArgumentException> {
      extractPresetArchive(archive("a" to "1", "b" to "2"), root, maxEntries = 1)
    }
  }

  @Test
  fun rejectsInvalidArchive() = temporary { root ->
    assertFailsWith<IllegalArgumentException> {
      extractPresetArchive(ByteArrayInputStream("not a zip".toByteArray()), root)
    }
  }

  @Test
  fun refusesNonemptyDestinationWithoutDeletingIt() = temporary { root ->
    File(root, "existing").writeText("keep")
    assertFailsWith<IllegalArgumentException> { extractPresetArchive(archive("a" to "1"), root) }
    assertEquals("keep", File(root, "existing").readText())
  }

  @Test
  fun rejectsUnsafeResourceNames() {
    listOf("../a.svg", "/a.ttf", "a/b.svg", "a\\b.svg", ".", "..").forEach { name ->
      assertFailsWith<IllegalArgumentException> { requireResourceName(name) }
    }
  }
}

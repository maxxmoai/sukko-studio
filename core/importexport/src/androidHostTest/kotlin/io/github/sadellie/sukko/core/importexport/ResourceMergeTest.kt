package io.github.sadellie.sukko.core.importexport

import java.io.File
import java.nio.file.Files
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test

class ResourceMergeTest {
  @Test
  fun reusesIdenticalFilesAndRenamesDifferentContent() {
    val root = Files.createTempDirectory("merge-test").toFile()
    try {
      val source = File(root, "source").apply { mkdir() }
      val destination = File(root, "destination").apply { mkdir() }
      File(source, "same.svg").writeText("same")
      File(destination, "same.svg").writeText("same")
      File(source, "conflict.svg").writeText("new")
      File(destination, "conflict.svg").writeText("old")
      File(destination, "conflict_1.svg").writeText("also old")
      val merge =
        prepareResourceMerge(source.listFiles()!!.toList(), destination, File(root, "stage"))
      assertEquals("same.svg", merge.names["same.svg"])
      assertEquals("conflict_2.svg", merge.names["conflict.svg"])
      assertEquals(listOf("conflict_2.svg"), merge.files.map { it.name })
      assertEquals("old", File(destination, "conflict.svg").readText())
      merge.files.forEach { it.copyTo(File(destination, it.name)) }
      val second =
        prepareResourceMerge(source.listFiles()!!.toList(), destination, File(root, "second"))
      assertEquals(merge.names, second.names)
      assertTrue(second.files.isEmpty())
      assertEquals("new", File(source, "conflict.svg").readText())
    } finally {
      root.deleteRecursively()
    }
  }
}

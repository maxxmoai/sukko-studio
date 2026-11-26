package io.github.sadellie.sukko.core.importexport

import java.io.File
import java.io.InputStream
import java.util.zip.ZipInputStream

private const val MAX_ENTRY_BYTES = 16L * 1024 * 1024
private const val MAX_TOTAL_BYTES = 64L * 1024 * 1024
private const val MAX_ENTRIES = 2048

/** Limits are enforced on bytes actually read, never trusted ZIP metadata. */
internal fun extractPresetArchive(
  input: InputStream,
  destination: File,
  maxEntryBytes: Long = MAX_ENTRY_BYTES,
  maxTotalBytes: Long = MAX_TOTAL_BYTES,
  maxEntries: Int = MAX_ENTRIES,
) {
  input.use { source ->
    require(destination.isDirectory && destination.list()?.isEmpty() == true) {
      "Import directory must be empty"
    }
    try {
      extractEntries(source, destination, maxEntryBytes, maxTotalBytes, maxEntries)
    } catch (failure: Exception) {
      destination.deleteRecursively()
      throw failure
    }
  }
}

private fun extractEntries(
  input: InputStream,
  destination: File,
  maxEntryBytes: Long,
  maxTotalBytes: Long,
  maxEntries: Int,
) {
  var total = 0L
  var entries = 0
  val seen = mutableSetOf<String>()
  ZipInputStream(input.buffered()).use { zip ->
    var entry = zip.nextEntry
    while (entry != null) {
      require(++entries <= maxEntries) { "Too many archive entries" }
      val target = archiveTarget(destination, entry.name.removeSuffix("/"))
      require(seen.add(target.path)) { "Duplicate archive entry" }
      if (entry.isDirectory) {
        require(zip.read() == -1) { "Directory entry contains data" }
        require(target.mkdirs() || target.isDirectory) { "Cannot create archive directory" }
      } else {
        total += copyEntry(zip, target, minOf(maxEntryBytes, maxTotalBytes - total))
      }
      zip.closeEntry()
      entry = zip.nextEntry
    }
  }
  require(entries > 0) { "Empty or invalid ZIP archive" }
}

private fun archiveTarget(destination: File, name: String): File {
  require(
    name.isNotEmpty() &&
      !name.startsWith("/") &&
      name.none { it == '\\' || it == ':' || it == '\u0000' } &&
      name.split('/').none { it == ".." || it == "." || it.isEmpty() }
  ) {
    "Unsafe archive path"
  }
  return File(destination, name).canonicalFile.also { target ->
    require(target.path.startsWith(destination.canonicalPath + File.separator)) {
      "Archive path escapes import directory"
    }
  }
}

private fun copyEntry(zip: ZipInputStream, target: File, limit: Long): Long {
  require(target.parentFile.mkdirs() || target.parentFile.isDirectory)
  require(target.createNewFile()) { "Archive entry already exists" }
  var size = 0L
  target.outputStream().buffered().use { output ->
    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
    var count = zip.read(buffer)
    while (count >= 0) {
      size += count
      require(size <= limit) { "Archive exceeds import size limits" }
      output.write(buffer, 0, count)
      count = zip.read(buffer)
    }
  }
  return size
}

internal fun requireResourceName(name: String) {
  require(
    name.isNotBlank() &&
      name != "." &&
      name != ".." &&
      name.none { it == '/' || it == '\\' || it == ':' || it == '\u0000' }
  ) {
    "Invalid resource filename"
  }
}

internal fun optionalPreview(directory: File): File? =
  File(directory, "preview.png").takeIf { it.isFile }

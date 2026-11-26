package io.github.sadellie.sukko.core.importexport

import java.io.File
import java.security.MessageDigest

internal data class ResourceMerge(val names: Map<String, String>, val files: List<File>)

/** Prepare copies separately; installed resources and source files remain unchanged. */
internal fun prepareResourceMerge(
  sources: List<File>,
  destination: File,
  staging: File,
): ResourceMerge {
  require(staging.mkdirs() || staging.isDirectory)
  require(staging.list()?.isEmpty() == true)
  val names = mutableMapOf<String, String>()
  val files = mutableListOf<File>()
  for (source in sources) {
    require(source.isFile) { "Missing imported resource" }
    requireResourceName(source.name)
    require(source.name !in names) { "Duplicate resource name" }
    val name = availableResourceName(source, destination, staging)
    if (!File(destination, name).exists() && !File(staging, name).exists()) {
      files += source.copyTo(File(staging, name))
    }
    names[source.name] = name
  }
  return ResourceMerge(names, files)
}

private fun availableResourceName(source: File, destination: File, staging: File): String {
  val digest = source.sha256()
  var name = source.name
  var suffix = 0
  while (!canReuseName(name, destination, staging, digest)) {
    suffix++
    val extension = source.extension.let { if (it.isEmpty()) "" else ".$it" }
    name = "${source.nameWithoutExtension}_$suffix$extension"
  }
  return name
}

private fun canReuseName(
  name: String,
  destination: File,
  staging: File,
  digest: ByteArray,
): Boolean {
  val staged = File(staging, name)
  val occupied = if (staged.exists()) staged else File(destination, name)
  if (!occupied.exists()) return true
  require(occupied.isFile) { "Resource path is not a file" }
  return occupied.sha256().contentEquals(digest)
}

private fun File.sha256(): ByteArray {
  val digest = MessageDigest.getInstance("SHA-256")
  inputStream().buffered().use { input ->
    val buffer = ByteArray(8192)
    while (true) {
      val count = input.read(buffer)
      if (count < 0) break
      digest.update(buffer, 0, count)
    }
  }
  return digest.digest()
}

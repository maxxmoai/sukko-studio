package io.github.sadellie.sukko.core.importexport

import io.github.sadellie.sukko.core.iconfiles.IconFile
import io.github.sadellie.sukko.core.iconfiles.IconPack
import io.github.sadellie.sukko.core.model.Globals
import io.github.sadellie.sukko.core.model.WidgetDataPreset
import io.github.sadellie.sukko.core.model.basic.ImageUriSource
import io.github.sadellie.sukko.core.model.layer.ColdImageLayer
import kotlin.test.assertEquals
import org.junit.Test

class IconReferenceMergeTest {
  @Test
  fun remappingDoesNotCascadeWhenPackIdsOverlap() {
    val first = IconPack.Custom(1, "First")
    val second = IconPack.Custom(2, "Second")
    val third = IconPack.Custom(3, "Third")
    val original =
      WidgetDataPreset.Custom(
        1,
        "Test",
        listOf(
          ColdImageLayer(
            id = 1,
            imageUriSource = ImageUriSource.IconPack(IconFile("a.svg", first)),
          ),
          ColdImageLayer(
            id = 2,
            imageUriSource = ImageUriSource.IconPack(IconFile("b.svg", second)),
          ),
        ),
        Globals(),
      )
    val step = replaceIconPack(first, second, original)
    val result = replaceIconPack(second, third, step, originalPreset = original)
    assertEquals(step.layers[0], result.layers[0])
    assertEquals(
      ImageUriSource.IconPack(IconFile("b.svg", third)),
      (result.layers[1] as ColdImageLayer).imageUriSource,
    )
  }

  @Test
  fun updatesPackAndFilenameWithoutChangingOtherPacks() {
    val old = IconPack.Custom(1, "Studio Glass")
    val destination = IconPack.Custom(2, "Installed")
    val other = IconPack.Custom(3, "Other")
    val layer =
      ColdImageLayer(id = 1, imageUriSource = ImageUriSource.IconPack(IconFile("a.svg", old)))
    val untouched =
      ColdImageLayer(id = 2, imageUriSource = ImageUriSource.IconPack(IconFile("a.svg", other)))
    val preset =
      WidgetDataPreset.Custom(
        presetId = 1,
        name = "Test",
        layers = listOf(layer, untouched),
        globals = Globals(),
      )
    val result = replaceIconPack(old, destination, preset, mapOf("a.svg" to "a_1.svg"))
    assertEquals(
      layer.copy(imageUriSource = ImageUriSource.IconPack(IconFile("a_1.svg", destination))),
      result.layers[0],
    )
    assertEquals(untouched, result.layers[1])
  }
}

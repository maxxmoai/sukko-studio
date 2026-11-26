package io.github.sadellie.sukko.core.data

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.github.sadellie.sukko.core.common.toHex
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DynamicColorSchemeProviderImplTest {
  @Test
  fun generateColorSchemeFromImage_validPng() = runTest {
    withContext(Dispatchers.IO) {
      val imageBitmap =
        InstrumentationRegistry.getInstrumentation().context.assets.open("file.png").use {
          BitmapFactory.decodeStream(it).asImageBitmap()
        }
      val colorScheme = generateColorSchemeFromImage(imageBitmap)
      val expectedPrimary = Color(0xFF696000)
      val actualPrimary = colorScheme.primary
      assertEquals(
        expectedPrimary,
        actualPrimary,
        "Expected: ${expectedPrimary.toHex()} Actual: ${actualPrimary.toHex()}",
      )
    }
  }
}

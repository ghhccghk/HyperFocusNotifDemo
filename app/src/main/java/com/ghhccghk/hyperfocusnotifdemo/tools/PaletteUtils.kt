package com.ghhccghk.hyperfocusnotifdemo.tools

import android.graphics.Bitmap
import androidx.palette.graphics.Palette

object PaletteUtils {
    private const val BLACK_MAX_LIGHTNESS = 0.08f
    private const val WHITE_MIN_LIGHTNESS = 0.9f
    private const val POPULATION_FRACTION_FOR_WHITE_OR_BLACK = 2.5f
    private const val RESIZE_BITMAP_AREA = 22500

    fun findBackgroundSwatch(bitmap: Bitmap): Palette.Swatch {
        return findBackgroundSwatch(generateArtworkPaletteBuilder(bitmap).generate())
    }

    fun findBackgroundSwatch(palette: Palette): Palette.Swatch {
        val dominant = palette.dominantSwatch ?: return Palette.Swatch(-1, 100)

        if (!isWhiteOrBlack(dominant.hsl)) {
            return dominant
        }

        var maxPopulation = -1f
        var bestSwatch: Palette.Swatch? = null

        for (swatch in palette.swatches) {
            if (swatch != dominant && !isWhiteOrBlack(swatch.hsl)) {
                if (swatch.population > maxPopulation) {
                    maxPopulation = swatch.population.toFloat()
                    bestSwatch = swatch
                }
            }
        }

        return if (bestSwatch != null && dominant.population / maxPopulation <= POPULATION_FRACTION_FOR_WHITE_OR_BLACK) {
            bestSwatch
        } else {
            dominant
        }
    }

    fun generateArtworkPaletteBuilder(bitmap: Bitmap): Palette.Builder {
        return Palette.from(bitmap)
            .setRegion(0, 0, bitmap.width, bitmap.height)
            .clearFilters()
            .resizeBitmapArea(RESIZE_BITMAP_AREA)
    }

    private fun isBlack(hsl: FloatArray): Boolean {
        return hsl[2] <= BLACK_MAX_LIGHTNESS
    }

    private fun isWhite(hsl: FloatArray): Boolean {
        return hsl[2] >= WHITE_MIN_LIGHTNESS
    }

    fun isWhiteOrBlack(hsl: FloatArray): Boolean {
        return isBlack(hsl) || isWhite(hsl)
    }
}

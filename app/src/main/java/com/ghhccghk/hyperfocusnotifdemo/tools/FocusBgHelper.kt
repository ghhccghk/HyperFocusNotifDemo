package com.ghhccghk.hyperfocusnotifdemo.tools

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Icon
import android.widget.RemoteViews
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import com.ghhccghk.hyperfocusnotifdemo.R
import androidx.core.graphics.scale
import androidx.core.graphics.createBitmap


object FocusBgHelper {

    private const val COLOR_ALPHA = 160  // 根据你的实际透明度设定

    /**
     * 入口方法：生成焦点通知背景图并设置到 RemoteViews
     */
    fun createPartBg(
        context: Context,
        icon: Icon?,
        remoteViews: RemoteViews
    ) {
        val imageViewId = R.id.focus_notify_bg_image
        remoteViews.setImageViewBitmap(imageViewId, null)

        val drawable = icon?.loadDrawable(context)
        val baseBitmap = drawable?.let {
            createBitmap(it.intrinsicWidth, it.intrinsicHeight)
        }

        val baseCanvas = baseBitmap?.let { Canvas(it) }

        baseCanvas?.let {
            drawable.setBounds(0, 0, it.width, it.height)
            drawable.draw(it)
        }

        val croppedBitmap = baseBitmap?.let { resizeAndCrop(context, it) }

        val a = croppedBitmap?.let { addCurveShadow(it) }

        val partBitmap = a?.let {
            it.config?.let { config -> createBitmap(it.width, it.height, config) }
        }

        val swatch = findBackgroundSwatch(baseBitmap)
        val bgColor = darkenColor(
            ColorUtils.setAlphaComponent(swatch.rgb, COLOR_ALPHA),
            0.3f,
            0.0f
        )

        baseCanvas?.drawColor(bgColor)
        remoteViews.setImageViewBitmap(imageViewId, baseBitmap)

        val partCanvas = partBitmap?.let { Canvas(it) }
        partCanvas?.drawBitmap(croppedBitmap, 0f, 0f, null)

        val paint = Paint().apply {
            shader = croppedBitmap?.let {
                LinearGradient(
                    0f, 0f, it.width.toFloat(), 0f,
                    bgColor,
                    darkenColor(
                        ColorUtils.setAlphaComponent(swatch.rgb, COLOR_ALPHA),
                        0.3f,
                        0.5f
                    ),
                    Shader.TileMode.CLAMP
                )
            }
        }

        croppedBitmap?.let {
            partCanvas?.drawRect(0f, 0f, it.width.toFloat(), it.height.toFloat(), paint)
        }
    }

    /**
     * 缩放并居中裁剪 Bitmap
     */
    private fun resizeAndCrop(context: Context, bitmap: Bitmap): Bitmap {
        val targetWidth = context.resources.getDimension(R.dimen.focus_notify_part_bg_width).toInt()
        val targetHeight = context.resources.getDimension(R.dimen.focus_notify_extend_height).toInt()

        return if (bitmap.width < bitmap.height) {
            val scaled = bitmap.scale(
                targetWidth,
                (bitmap.height * (targetWidth.toFloat() / bitmap.width)).toInt()
            )
            Bitmap.createBitmap(
                scaled,
                0,
                (scaled.height - targetHeight) / 2,
                targetWidth,
                targetHeight
            )
        } else {
            val scaled = bitmap.scale(
                (bitmap.width * (targetHeight.toFloat() / bitmap.height)).toInt(),
                targetHeight
            )
            Bitmap.createBitmap(
                scaled,
                (scaled.width - targetWidth) / 2,
                0,
                targetWidth,
                targetHeight
            )
        }
    }

    // ✅ 添加左侧弯曲阴影（保持裁切部分不受影响）
    private fun addCurveShadow(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)

        // 绘制原图
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val path = Path()

        // 阴影渐变色（左黑右透明）
        paint.shader = LinearGradient(
            0f, 0f, width * 0.3f, 0f,
            Color.parseColor("#80000000"), // 深灰黑
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )

        // 构造带弯曲轮廓的左边遮罩路径
        path.moveTo(0f, 0f)
        path.lineTo(width * 0.25f, 0f)
        path.quadTo(width * 0.15f, height / 2f, width * 0.25f, height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()

        canvas.drawPath(path, paint)

        return result
    }

    /**
     * 提取主色调
     */
    private fun findBackgroundSwatch(bitmap: Bitmap?): Palette.Swatch {
        val palette = Palette.from(bitmap!!).generate()
        return palette.dominantSwatch ?: Palette.Swatch(Color.GRAY, 1)
    }

    /**
     * 颜色加深工具
     */
    private fun darkenColor(color: Int, darkenRatio: Float, fallbackRatio: Float = 0f): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= (1f - darkenRatio).coerceAtLeast(fallbackRatio)
        return Color.HSVToColor(Color.alpha(color), hsv)
    }
}

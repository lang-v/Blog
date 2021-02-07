package sl.app.module_markdown.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

import android.text.style.ReplacementSpan


class RoundBackgroundColorSpan(private val bgColor: Int, private val textColor: Int) :
    ReplacementSpan() {
    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return paint.measureText(text, start, end).toInt() + 60
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val color1: Int = paint.color
        paint.color = bgColor
        canvas.drawRoundRect(
            RectF(
                x,
                (top + 1).toFloat(),
                x + (paint.measureText(text, start, end).toInt() + 40),
                (bottom - 1).toFloat()
            ), 15f, 15f, paint
        )
        paint.color = textColor
        text?.let { canvas.drawText(it, start, end, x + 20, y.toFloat(), paint) }
        paint.color = color1
    }
}
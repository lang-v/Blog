package sl.app.module_markdown.component

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.component.base.MarkDownView

/**
 * 引言
 */
class IntroductionTextView(context: ViewGroup, element: Element) : MarkDownView(context, element) {
    override fun createView(parent: ViewGroup): View {
        return TextView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(10)
            textSize = 18f
            text = element.content
            setBackgroundColor(Color.GRAY)
        }
    }

    override fun bindView(view: View) {
    }

    override fun getType(): Class<*> {
        return IntroductionTextView::class.java
    }
}
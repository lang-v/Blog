package sl.app.module_markdown.component

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.component.base.MarkDownView

/**
 * 内置代码块
 */
class InlineCodeTextView(context: ViewGroup, element: Element) : MarkDownView(context, element) {
    override fun createView(parent: ViewGroup): View {
        return TextView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundColor(Color.parseColor("fuchsia"))
            text = element.content
            setPadding(2)
            textSize=18f
        }
    }

    override fun bindView(view: View) {
    }

    override fun getType(): Class<*> {
        return InlineCodeTextView::class.java
    }
}
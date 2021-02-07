package sl.app.module_markdown.component

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.toSpannable
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.bean.TextElement
import sl.app.module_markdown.component.base.MarkDownView

/**
 * 正常文本
 */
class CommonTextView(context:ViewGroup,element: Element) : MarkDownView(context,element) {
    override fun createView(parent: ViewGroup): View {
        return TextView(parent.context).apply {
            text = element.content
            setPadding(3)
            setBackgroundColor(Color.WHITE)
            textSize = 18f
        }
    }

    override fun bindView(view: View) {

    }

    override fun getType(): Class<*> {
        return CommonTextView::class.java
    }

}
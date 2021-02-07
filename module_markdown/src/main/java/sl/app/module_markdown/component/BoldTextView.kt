package sl.app.module_markdown.component

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.component.base.MarkDownView

/**
 * 粗体文字
 */
class BoldTextView(context:ViewGroup,element: Element) : MarkDownView(context,element)  {
    override fun createView(parent: ViewGroup): View {
        return TextView(parent.context).apply {
            text = element.content
            textSize = 18f
            setPadding(3)
            setTypeface(null,Typeface.BOLD)
            setBackgroundColor(Color.WHITE)
        }
    }

    override fun bindView(view: View) {

    }

    override fun getType(): Class<*> {
        return BoldTextView::class.java
    }
}
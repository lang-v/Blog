package sl.app.module_markdown.component

import android.graphics.Color
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.LeadingMarginSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import sl.app.module_markdown.R
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.component.base.MarkDownView

/**
 * 代码块
 */
class CodeView(context: ViewGroup, element: Element) : MarkDownView(context, element) {
    override fun createView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.code_view_layout,parent,false).apply {
            findViewById<TextView>(R.id.codeText).apply {
                textSize = 18f
                text = buildLeading(element.content)
            }
            setPadding(3)
            setBackgroundColor(Color.parseColor("#f8f8f8"))
        }
    }

    override fun bindView(view: View) {

    }

    private fun buildLeading(str:String):SpannableString{
        return SpannableString(str).apply {
            setSpan(LeadingMarginSpan.Standard(40),0,length,SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    override fun getType(): Class<*> {
        return CodeView::class.java
    }
}
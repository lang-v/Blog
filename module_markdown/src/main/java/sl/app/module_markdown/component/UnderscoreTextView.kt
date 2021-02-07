package sl.app.module_markdown.component

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.UnderlineSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.component.base.MarkDownView

class UnderscoreTextView(context: ViewGroup, element: Element) : MarkDownView(context, element) {
    override fun createView(parent: ViewGroup): View {
        return TextView(parent.context).apply {
            text = buildUnderscoreString()
            textSize = 18f
            setPadding(3)
            setBackgroundColor(Color.WHITE)
        }
    }

    override fun bindView(view: View) {

    }

    private fun buildUnderscoreString():SpannableString{
        val underlineSpan = UnderlineSpan()
        val string = SpannableString(element.content)
        string.setSpan(underlineSpan,0,string.length,SpannableString.SPAN_EXCLUSIVE_INCLUSIVE)
        return string
    }

    override fun getType(): Class<*> {
        return UnderscoreTextView::class.java
    }
}
package sl.app.module_markdown.component

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.component.base.MarkDownView

//分割线
class SplitLineView(context: ViewGroup, element: Element) : MarkDownView(context, element) {
    override fun createView(parent: ViewGroup): View {
        return View(parent.context).apply {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,3)
            setBackgroundColor(Color.GRAY)
        }
    }

    override fun bindView(view: View) {

    }

    override fun getType(): Class<*> {
        return SplitLineView::class.java
    }
}
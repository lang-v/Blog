package sl.app.module_markdown.component

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.component.base.MarkDownView

/**
 * 标题
 * 考虑到手机显示不方便，将所有的标题都设置为相同的大小
 */
class TitleTextView(context: ViewGroup, element: Element) : MarkDownView(context, element) {

    override fun loadView(parent: ViewGroup){
        //装载之前先对内容格式化处理
        contentFormat()
        super.loadView(parent)
    }
    //删除多余的格式
    private fun contentFormat(){
        element.content.replace("`","")
        element.content.replace("*","")
    }

    override fun createView(parent: ViewGroup): View {
        return TextView(parent.context).apply {
            //居中
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
            text = element.content
            setTypeface(null,Typeface.BOLD)
            setPadding(3)
            textSize = 25f
        }
    }

    override fun bindView(view: View) {
    }

    override fun getType(): Class<*> {
        return TitleTextView::class.java
    }
}
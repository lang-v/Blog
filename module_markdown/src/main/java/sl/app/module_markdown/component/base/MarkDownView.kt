package sl.app.module_markdown.component.base

import android.view.View
import android.view.ViewGroup
import sl.app.module_markdown.bean.Element
import java.lang.ref.WeakReference

/**
 * 正常文本 size = 18sp
 * 标题(#+) size = 25sp
 * 加粗显示（**加粗**）18sp bold
 * 斜体（*斜体*） 18sp bold
 * 下划线（<u>下划线<u/>）18sp u
 * 删除线 (~~删除线~~)
 * 引言(> )
 * 有序序列（1. ）
 * 无序序列（- ）
 * 代码块(```\n code ```\n)
 * 图片（![](htpp..)）
 * 链接（[](http...)）
 * 傻了 好多啊
 */
abstract class MarkDownView {
    protected var viewReference:WeakReference<View> = WeakReference<View>(null)
    protected val element:Element
    constructor(context: ViewGroup):this(context,"")
    constructor(context: ViewGroup,str: String):this(context,Element(str))
    constructor(context: ViewGroup,element: Element){
        this.element=element
        loadView(context)
    }

    /**
     * 初始化阶段会自动装载View
     */
    protected open fun loadView(parent: ViewGroup){
        viewReference = WeakReference(createView(parent))
        viewReference.get()?.let { bindView(it) }
    }

    //创建元素View
    protected abstract fun createView(parent:ViewGroup):View

    protected abstract fun bindView(view: View)

    fun getView():View?{
        return viewReference.get()
    }

    //返回viewType
    abstract fun getType():Class<*>

    override fun toString(): String {
        return element.toString()
    }
}
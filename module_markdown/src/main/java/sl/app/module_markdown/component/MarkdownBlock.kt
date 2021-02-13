package sl.app.module_markdown.component

import android.content.Context
import android.graphics.Canvas
import android.opengl.Visibility
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import androidx.lifecycle.MutableLiveData
import org.w3c.dom.Text
import sl.app.module_markdown.bean.Block
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.bean.ImageElement
import sl.app.module_markdown.bean.TableElement
import sl.app.module_markdown.extensions.toImageElement
import java.lang.ref.WeakReference

/**
 * 将文本内容分块
 * 每次编辑即可编辑块内容
 */
class MarkdownBlock constructor(
    context: Context, private var block: Block
) : LinearLayout(context, null, 0) {
    init {
        orientation = VERTICAL
        setPadding(5,10,5,10)
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            topMargin = 5
            bottomMargin = 5
        }
        loadView()
    }

    var isEdit = false
        set(value) {
            if (value != field) {
                if (value) {
                    //进入编辑模式
                    (editView as TextView).text = block.content
                } else {
                    //退出编辑模式
                    block.content = editView.text.toString()
                }
            }
            field = value
        }

    private val editView: EditText by lazy {
        EditText(context).apply {
            (this as TextView).text = block.content
        }
    }

    @MainThread
    private fun loadView() {
        //判断块内元素类型
        block.content.let {str->
            val it = str.trimStart()
            Log.e("block",str)
            when {
                //代码段
                it.startsWith("```") -> {
                    //为什么用\n来锁定位置，因为代码块的开头``` kotlin\n 是这样的格式
                    addView(CodeView(this, Element(it.substringAfter("\n").substringBeforeLast("\n"))).getView()!!)
                }
                //图片
                it.startsWith("!") -> {
                    addView(ImageView(this,it.toImageElement()).getView()!!)
                }
                //表格
                it.startsWith("|") -> {
                    addView(TableView(this, TableElement(it.split('\n').toTypedArray())).getView()!!)
                }
                //分割线
                it == "***" ->{
                    addView(SplitLineView(this,Element("")).getView())
                }
                //文本
                else -> {
                    val picture = Regex(pattern = "[ |]*!\\[[^]^\\[^\\s]*]\\([^]^\\[^)^(^\\s]*\\)")
                    //将文本中内嵌的图片给提取出来
                    picture.findAll(str).takeIf { it -> it.count() != 0 }?.apply {
                        val list = toList()
                        var start = 0
                        for (i in list.indices) {
                            if (list[i].range.first!=0)
                            addView(
                                TextView(
                                    this@MarkdownBlock,
                                    Element(str.substring(start, list[i].range.first))
                                ).getView()!!
                            )
                            addView(ImageView(this@MarkdownBlock, list[i].value.toImageElement()).getView()!!)
                            start = list[i].range.last
                        }
                        if (start != it.length-1){
                            addView(TextView(this@MarkdownBlock, Element(str.substring(start))).getView())
                        }
                        return@let
                    }
                    //没有找到图片
                    addView(TextView(this,Element(str)).getView()!!)
                }
            }
        }
    }

    //换个思路，活用spannableString
//    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        if (!changed)return
//        //减去padding值得到真实
//        val right = r-paddingRight
//        val top = t+paddingTop
//        val bottom = b-paddingBottom
//        val left = l+paddingLeft
//        var currentLeft = left
//        var currentRight = right
//        var currentTop = top
//        var currentBottom = bottom
//
//        //记录上一个view的高度，便于切到下一行
//        var lastHeight = 0
//        //遍历所有view，按行添加到容器中（超出宽度就自动下一行），就像textView一样排版
//        for (i in 0 until childCount){
//            val view:View? = getChildAt(i)
//            if (view != null){
//                val measureWidth = view.measuredWidth
//                val measureHeight = view.measuredHeight
//                if (currentRight-currentLeft>=measureWidth){
//                    view.layout(currentLeft,currentTop,currentLeft+measureWidth,currentTop+measureHeight)
//                }else {
//                    if (right-left < measureWidth)
//                        view.layout(currentLeft,currentTop,currentLeft+measureWidth,currentTop+measureHeight)
//                    else{
//                        currentTop+=lastHeight
//                        currentLeft=left
//                        view.layout(currentLeft,currentTop,currentLeft+measureWidth,currentTop+measureHeight)
//                    }
//                }
//                lastHeight = measureHeight
//            }
//        }
//    }

}
package sl.app.module_markdown.component

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.MutableLiveData
import sl.app.module_markdown.bean.Block
import sl.app.module_markdown.bean.Content
import java.lang.StringBuilder

/**
 * markdown 容器
 * 承载markdown view
 */
class MarkDownContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }
    var isEdit = MutableLiveData<Boolean>()

    private val content = Content("")

    private val list = ArrayList<Block>()

    /**
     * 不建议二次调用此方法
     * 容器内分割了许多块，重新设置的话就需要将所有块重新设置
     */
    fun setContent(str: String) {
        content.content = str
    }

    /**
     * 此方法将分块内容整合
     */
    fun getContent(): Content {
        val sb = StringBuilder()
        list.forEach {
            sb.append(it.content)
        }
        return Content(sb.toString())
    }

    //初始化，将内容切割分块
    fun init() {
        //按行处理
        val array = content.content.split('\n')
        var i = -1
        val sb = StringBuilder()
        while (++i<array.size){
            val str = array[i].trimStart()
            if(i==array.size-2){
                val a = i
                sb.append("")
            }
            if (str.isBlank())
                continue
            //表头
            if (str.startsWith('|')){
                val pair = verifyTable(array,i)
                if (pair!=null){//如果后面有匹配的数据就将这一块数据封装
                    verifyCommonData(sb)
                    list.add(Block(pair.first))
                    i = pair.second
                    continue
                }else{
                    sb.append(str)
                    sb.append("\n")
                    continue
                }
            }
            //代码块
            if (str.startsWith("```")){
                val pair = verifyCode(array,i)
                if (pair!=null){//如果后面有匹配的数据就将这一块数据封装
                    verifyCommonData(sb)
                    list.add(Block(pair.first))
                    i = pair.second
                    continue
                }else{
                    sb.append(str)
                    sb.append("\n")
                    continue
                }
            }
            if (str.startsWith('!')){
                val pic = Regex("!\\[[\\s\\S]*]\\([\\s\\S]*\\)")
                if(pic.containsMatchIn(str)){
                    verifyCommonData(sb)
                    list.add(Block(str))
                    continue
                }else{
                    sb.append(str)
                    sb.append("\n")
                    continue
                }
            }
            sb.append(str)
            sb.append("\n")
            continue
        }
        //循环结束后检查还有没有数据
        verifyCommonData(sb)
    }
    //发现特殊数据后将累计的普通文本，封装到块中
    private fun verifyCommonData(sb:StringBuilder){
        if (sb.isNotEmpty()){
            list.add(Block(sb.toString()))
            sb.clear()
        }
    }

    //验证代码块数据
    private fun verifyCode(array: List<String>, index: Int):Pair<String,Int>?{
        val sb = StringBuilder(array[index])
        sb.append("\n")
        var end = -1
        for (i in index+1 until array.size){
            if (array[i].trimStart().contains("```")){
                sb.append(array[i])
                end=i
                break
            }
            if (i == array.size-1){
                return null
            }
            sb.append(array[i])
            sb.append("\n")
        }
        return sb.toString() to end
    }

    //验证表格数据
    private fun verifyTable(array: List<String>, index:Int): Pair<String, Int>? {
        val sb = StringBuilder(array[index])
        sb.append("\n")
        var end = -1
        for (i in index+1 until array.size){
            if (!array[i].trimStart().startsWith('|')){
                end=i
                break
            }
            if (i == array.size-1){
                return null
            }
            sb.append(array[i])
            sb.append("\n")
        }
        return sb.toString() to end
    }

    /**
     * 在init之后调用
     * 装载view
     */
    fun loadView() {
        list.forEach {
            addView(MarkdownBlock(context, it))
        }
    }

}
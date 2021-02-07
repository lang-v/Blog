package sl.app.module_markdown.component

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import sl.app.module_markdown.R
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.component.base.MarkDownView
import sl.app.module_markdown.extensions.getContent
import sl.app.module_markdown.extensions.getUrl
import sl.app.module_markdown.extensions.startWithSpaceNumber
import sl.app.module_markdown.span.RoundBackgroundColorSpan

class TextView(val context: ViewGroup, element: Element) : MarkDownView(context, element) {
    private val builder = buildSpannableString()
    override fun createView(parent: ViewGroup): View {
        return TextView(parent.context)
    }

    override fun bindView(view: View) {
        view.post {
            view as TextView
            view.text = builder
            view.linksClickable = true
            view.movementMethod = LinkMovementMethod.getInstance()
            view.textSize = 18f
            view.setPadding(10)
        }
    }

    private fun buildSpannableString(): SpannableStringBuilder {
        val builder = SpannableStringBuilder()
        val array = element.content.split("\n")//通过换行符切割字符串
        //这一类可以先判断，只需要判断开始字符就能得到结果
        val title = Regex("#+ [\\s\\S]*$")//匹配标题
        val orderList = Regex("\\d. [\\S\\s]*$")
        val unOrderList = Regex("- [\\s\\S]*$")
        val introduction = Regex(">[\\s\\S]*$")

        for (i in array.indices) {
            val str = array[i] + "\n"
            if (str.isBlank()) continue
            when {
                title.matches(str) -> {
                    title.find(str)?.let {
                        isTitle(builder, str)
                    }
                }
                introduction.matches(str) -> {
                    introduction.find(str)?.let {
                        isIntroduction(builder, str)
                    }
                }
                orderList.matches(str) -> {
                    orderList.find(str)?.let {
                        isOrderList(builder, str)
                    }
                }
                unOrderList.matches(str) -> {
                    unOrderList.find(str)?.let {
                        isUnOrderList(builder, str)
                    }
                }
                else -> {
                    //以上全部未匹配那么说明是普通文本
                    builder.append(buildLine(SpannableStringBuilder(str)))
                }
            }
        }
        return builder
    }

    //对行文本进行格式化处理
    private fun buildLine(string: SpannableStringBuilder): SpannableStringBuilder {
        //粗体
        val bold = Regex("\\*\\*\\S*\\*\\*")
        checkRegexp(string, bold, ::isBold)
        //斜体
        val italic = Regex("\\*\\S*\\*")
        checkRegexp(string, italic, ::isItalic)
        //下划线
        val underLine = Regex("<u>[\\s\\S]*</u>")
        checkRegexp(string, underLine, ::isUnderLine)
        //删除线
        val strikeThrough = Regex("~~[\\s\\S]*~~")
        checkRegexp(string, strikeThrough, ::isStrikeThrough)
        //内嵌代码
        val inlineCode = Regex("`[\\s\\S]*`")
        checkRegexp(string, inlineCode, ::isInlineCode)
        //链接,这里把图片和普通链接一起处理
        val link = Regex("(!|)\\[[\\s\\S]*]\\(http[\\S]*\\)")
        checkRegexp(string, link, ::isLink)
        return string
    }

    //简化函数
    private inline fun checkRegexp(
        string: SpannableStringBuilder,
        regex: Regex,
        func: (String) -> SpannableString
    ) {
        if (string.isEmpty()) return
        regex.findAll(string).let {
            it.iterator().forEach { it ->
                string.replace(it.range.first, it.range.last + 1, func.invoke(it.value))
            }
        }
    }

    private fun isBold(string: String): SpannableString {
        if (string.length < 4) return SpannableString("")
        return SpannableString(string.substring(2, string.length - 2)).apply {
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun isItalic(string: String): SpannableString {
        if (string.length < 2) return SpannableString("")
        return SpannableString(string.substring(1, string.length - 1)).apply {
            setSpan(
                StyleSpan(Typeface.ITALIC),
                0,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun isUnderLine(string: String): SpannableString {
        if (string.length < 7) return SpannableString("")
        return SpannableString(string.substring(3, string.length - 4)).apply {
            setSpan(UnderlineSpan(), 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun isStrikeThrough(string: String): SpannableString {
        if (string.length < 4) return SpannableString("")
        return SpannableString(string.substring(2, string.length - 2)).apply {
            setSpan(StrikethroughSpan(), 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun isInlineCode(string: String): SpannableString {
        if (string.length < 2) return SpannableString("")
        return SpannableString(string.substring(1, string.length - 1)).apply {
            setSpan(
                RoundBackgroundColorSpan(Color.parseColor("#f8f8f8"), Color.WHITE),
                0,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(RelativeSizeSpan(0.8f), 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun isLink(string: String): SpannableString {
        if (string.length < 4) return SpannableString("")
        val url = string.getUrl()
        val content = string.getContent()
        return SpannableString(if (content.isEmpty()) url else content).apply {
            setSpan(URLSpan(url), 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    //通过imageGetter从网络加载图片会导致卡死
//    private fun isPicture(string: String):SpannableString{
//        val url = getUrl(string)
//        val content = getContent(string)
//        if (string.length<5)return SpannableString("")
//        return Html.fromHtml()
//    }

    private fun isTitle(builder: SpannableStringBuilder, string: String) {
        val span = StyleSpan(Typeface.BOLD)
        val newStr = string.substring(string.indexOfFirst { it == ' ' })
        builder.append(buildLine(SpannableStringBuilder(newStr)).apply {
            setSpan(span, 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(
                RelativeSizeSpan(1.5f),
                0,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        })
    }

    private fun isIntroduction(builder: SpannableStringBuilder, string: String) {
        builder.append(
            buildLine(
                SpannableStringBuilder(
                    if (string.length == 1) " " else string.substring(
                        1
                    )
                )
            ).apply {
                setSpan(ContextCompat.getDrawable(context.context, R.drawable.ic_square)?.let {
                    DrawableMarginSpan(
                        it
                    )
                }, 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            })
    }

    private fun isOrderList(builder: SpannableStringBuilder, string: String) {
        val leadingOffset = 20
        val spaceNum = string.startWithSpaceNumber()
        val newStr = string.trimStart()
        val offset = string.indexOfFirst { it == ' ' }
        val span = LeadingMarginSpan.Standard(leadingOffset * spaceNum, 0)
        builder.append(buildLine(SpannableStringBuilder(newStr)).apply {
            setSpan(span, 0, offset + 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(
                ForegroundColorSpan(Color.BLACK),
                0,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                offset + 1,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                BackgroundColorSpan(Color.parseColor("#f8f8f8")),
                0,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        })
    }

    private fun isUnOrderList(builder: SpannableStringBuilder, string: String) {
        val offset = 10
        val spaceNum = string.startWithSpaceNumber()
        val newStr = string.substringAfter("- ")
        val span = LeadingMarginSpan.Standard(offset * spaceNum, 0)
        builder.append(buildLine(SpannableStringBuilder(newStr)).apply {
            setSpan(ContextCompat.getDrawable(context.context, R.drawable.ic_dot)?.let {
                DrawableMarginSpan(it)
            }, 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(
                BackgroundColorSpan(Color.parseColor("#f8f8f8")),
                0,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(span, 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        })
    }

    override fun getType(): Class<*> {
        return sl.app.module_markdown.component.TextView::class.java
    }
}
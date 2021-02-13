package sl.app.module_markdown.extensions

import sl.app.module_markdown.bean.Content
import sl.app.module_markdown.bean.Block
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.bean.ImageElement

//对总体的文本内容进行格式化处理
fun Content.format(){

}

//对块内容格式化处理
fun Block.format(){

}

/**
 * 对元素内容格式化处理
 * 这里只有Text会用到，因为Text中存在格式
 */
fun Element.format():Array<String>{
    /**
     * 特殊字符有：* ** <u> <u/> #
     */

    return arrayOf()
}

fun String.startWithSpaceNumber():Int{
    var sum = 0
    for (i in this.indices){
        if (get(i)==' ')
            sum++
        else break
    }
    return sum
}

fun String.toImageElement():ImageElement{
    return ImageElement(getUrl(),getContent())
}

fun String.getContent():String{
    return substringAfter('[').substringBefore(']')
}

fun String.getUrl():String{
    return substringAfter('(').substringBefore(')')
}
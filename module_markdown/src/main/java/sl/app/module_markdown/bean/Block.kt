package sl.app.module_markdown.bean


/**
 * 不只是一行文本,根据具体文本内容将文本分块
 * 块内容包括：可以包括多个任意元素
 * @see Element
 */
data class Block(var content:String)

package sl.app.module_markdown.bean

/**
 * @param link 将要跳转的链接
 */
class ImageElement(val imgUrl: String, content: String, val link:String=""): Element(content)

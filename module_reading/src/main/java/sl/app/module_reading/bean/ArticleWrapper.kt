package sl.app.module_reading.bean

import sl.app.lib_common.bean.BaseResult
import java.io.Serializable

data class ArticleWrapper(val title: String, val content: String, val date: String) : Serializable

package sl.app.module_reading.service

import retrofit2.Call
import retrofit2.http.GET
import sl.app.lib_common.bean.BaseResult
import sl.app.module_reading.bean.ArticleWrapper

interface ApiService {
    @GET("url..")
    fun getArticle():Call<BaseResult<ArticleWrapper>>
}
package sl.app.lib_common.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiGenerator {

    //默认
    private val default = Retrofit.Builder()
        .baseUrl("https://")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //默认使用
    fun <T> getService(service: Class<T>): T =
        default.create(service)

    //自定义retrofit
    fun <T> getService(custom: Retrofit, service: Class<T>): T = custom.create(service)

}
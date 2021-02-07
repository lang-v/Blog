package sl.app.lib_common.service

import com.alibaba.android.arouter.launcher.ARouter

object ServiceManager {
    fun <T> getService(clazz: Class<T>): T = ARouter.getInstance().navigation(clazz)

    @Suppress("UNCHECKED_CAST")
    fun <T> getService(path: String): T = ARouter.getInstance().build(path).navigation() as T
}
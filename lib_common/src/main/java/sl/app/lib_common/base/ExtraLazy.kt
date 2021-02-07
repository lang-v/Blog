@file:Suppress("UNCHECKED_CAST")

package sl.app.lib_common.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlin.reflect.KProperty

/**
 * intent数据代理类
 */
@Deprecated("接入阿里路由框架，可以直接使用框架自带的注解{[@Autowired]}")
class ExtraLazy<T>(private val name: String, private val default: T) {
    private var value: T? = null
    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        value=getExtra(thisRef)
        return value?:default
    }

    operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        value=getExtra(thisRef)
        return value?:default
    }

    private fun getExtra(thisRef: AppCompatActivity): T? =
        value ?: thisRef.intent.extras?.get(name) as T

    private fun getExtra(thisRef: Fragment): T? = value ?: thisRef.arguments?.get(name) as T
}
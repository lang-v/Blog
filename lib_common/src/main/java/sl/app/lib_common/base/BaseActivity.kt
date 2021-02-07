package sl.app.lib_common.base

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import kotlinx.coroutines.*

/**
 * 由MainScope代理
 * 将会创建一个协程作用域
 * 通过cancel取消所有任务
 *
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
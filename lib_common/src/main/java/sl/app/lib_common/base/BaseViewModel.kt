package sl.app.lib_common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * 内置协程作用域
 */
abstract class BaseViewModel: ViewModel(),CoroutineScope by MainScope() {
    override fun onCleared() {
        super.onCleared()
        cancel()
    }
}
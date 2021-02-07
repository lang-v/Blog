package sl.app.module_reading.viewmodel

import sl.app.lib_common.base.BaseViewModel

class BrowseViewModel : BaseViewModel() {
    var title: String = ""
    var content:Array<String> = arrayOf()
//    var comment:Array<*>
    /**
     * 从url加载数据
     * 文章标题、内容
     * 作者信息
     * 创作时间
     * 评论
     */
    fun loadArticleWithUrl(url: String) {
        //...
//        launch {
//            Log.e("test1", "running")
//            delay(10000)
//            Log.e("test1","still running")
//        }.invokeOnCompletion {
//            if (it is CancellationException){
//                Log.e("test1","coroutine was canceled")
//            }
//        }

    }

    fun loadComment(){}

    fun submitComment(){}

    fun submitZan(){}

    fun collect(){}

    fun cancelCollect(){}
}
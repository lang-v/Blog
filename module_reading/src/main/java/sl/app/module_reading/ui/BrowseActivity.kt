package sl.app.module_reading.ui

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import sl.app.lib_common.base.BaseActivity
import sl.app.lib_common.config.BLOG_PAGE
import sl.app.lib_common.config.BLOG_PAGE_URL
import sl.app.module_reading.R
import sl.app.module_reading.viewmodel.BrowseViewModel

/**
 * 博文阅读页面
 */
@Route(path = BLOG_PAGE)
class BrowseActivity : BaseActivity() {
    //博文地址
    @Autowired(name = BLOG_PAGE_URL)
    lateinit var url: String
    private val viewModel:BrowseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        findViewById<Button>(R.id.searchBtn).setOnClickListener {
            viewModel.loadArticleWithUrl("")
        }
    }
}
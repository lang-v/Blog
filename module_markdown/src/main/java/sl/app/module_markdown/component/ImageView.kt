package sl.app.module_markdown.component

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import sl.app.module_markdown.R
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.bean.ImageElement
import sl.app.module_markdown.component.base.MarkDownView

/**
 * 图片
 */
class ImageView(context: ViewGroup,val elements: ImageElement) : MarkDownView(context, elements) {
    override fun createView(parent: ViewGroup): View {
        return android.widget.ImageView(parent.context)
    }

    override fun bindView(view: View) {
        view.post {
            Glide.with(view.context)
                .load(elements.url)
                .error(R.mipmap.ic_launcher)
                .into(view as android.widget.ImageView)
        }
    }

    override fun getType(): Class<*> {
        return ImageView::class.java
    }
}
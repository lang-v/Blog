package sl.app.module_markdown.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import sl.app.module_markdown.R
import sl.app.module_markdown.bean.ImageElement
import sl.app.module_markdown.component.base.MarkDownView

class InlineImageView(context: ViewGroup, val elements: ImageElement) :
    MarkDownView(context, elements) {
    override fun createView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.inline_image_view_layout, parent, false)
    }

    override fun bindView(view: View) {
        view.post {
            view.findViewById<TextView>(R.id.content).text = element.content
            if (elements.imgUrl.startsWith("http")) {
                val img = view.findViewById<ImageView>(R.id.picture)
                Glide.with(view)
                    .load(elements.imgUrl)
                    .into(img)
            }
        }
    }

    override fun getType(): Class<*> {
        return InlineImageView::class.java
    }
}
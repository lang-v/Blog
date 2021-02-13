package sl.app.module_markdown.component

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import sl.app.module_markdown.R
import sl.app.module_markdown.bean.ImageElement
import sl.app.module_markdown.component.base.MarkDownView

/**
 * 图片
 */
class ImageView(context: ViewGroup, private val elements: ImageElement) : MarkDownView(context, elements) {
    override fun createView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.image_view_layout,parent,false)
    }

    override fun bindView(view: View) {
        view.post {
            val img = view.findViewById<android.widget.ImageView>(R.id.img)
            Glide.with(view.context)
                .load(elements.imgUrl)
                .error(R.drawable.ic_not_found)
                .placeholder(R.drawable.ic_loading)
                .listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        view.findViewById<TextView>(R.id.tips).text = elements.content
                        //点击重新加载
                        img.setOnClickListener {
                            Glide.with(img)
                                .load(elements.imgUrl)
                                .error(R.drawable.ic_not_found)
                                .placeholder(R.drawable.ic_loading)
                                .listener(object :RequestListener<Drawable>{
                                    override fun onLoadFailed(
                                        e: GlideException?,
                                        model: Any?,
                                        target: Target<Drawable>?,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        return false
                                    }

                                    override fun onResourceReady(
                                        resource: Drawable?,
                                        model: Any?,
                                        target: Target<Drawable>?,
                                        dataSource: DataSource?,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        img.setOnClickListener(null)
                                        return false
                                    }
                                })
                                .into(img)
                        }
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(img)

        }
    }

    override fun getType(): Class<*> {
        return ImageView::class.java
    }
}
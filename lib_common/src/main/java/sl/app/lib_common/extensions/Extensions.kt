package sl.app.lib_common.extensions

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

fun String.loadInto(view:ImageView?){
    if (view == null || isNullOrEmpty())return
    Glide.with(view.context)
        .load(this)
        .into(view)
}

fun ImageView.loadWith(url:String?){
    if (url.isNullOrEmpty())return
    Glide.with(context)
        .load(url)
        .into(this)
}

//格式化日期
//todo 等接口出来再写吧
@SuppressLint("SimpleDateFormat")
fun String.dataFormat():String{
    val sf = SimpleDateFormat("yy.MM.dd hh:mm")
    return this
}
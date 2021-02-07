package sl.app.lib_common.bean

import java.io.Serializable

open class BaseResult<T>(val msg:String,val code:Int,val data:T):Serializable

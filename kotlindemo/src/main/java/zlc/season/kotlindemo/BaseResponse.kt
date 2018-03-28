package zlc.season.kotlindemo

data class BaseResponse(
        var code: Int = 0,
        var message: String = "",
        var data: Any? = null)

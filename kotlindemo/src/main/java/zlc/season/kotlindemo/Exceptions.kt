package zlc.season.kotlindemo

interface Exceptions {
    class TokenExpired : RuntimeException("Token 过期")

    class Offline : RuntimeException("没有网络连接")

    class TimeOut : RuntimeException("连接超时")
}

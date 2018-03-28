package zlc.season.javademo;

public interface Exceptions {
    class TokenExpired extends RuntimeException {
        public TokenExpired() {
            super("Token 过期");
        }
    }

    class Offline extends RuntimeException {
        public Offline() {
            super("没有网络连接");
        }
    }

    class TimeOut extends RuntimeException {
        public TimeOut() {
            super("连接超时");
        }
    }
}

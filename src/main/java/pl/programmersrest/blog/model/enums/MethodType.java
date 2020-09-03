package pl.programmersrest.blog.model.enums;

public enum  MethodType {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE"),
    PUT("PUT");
    String value;

    MethodType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

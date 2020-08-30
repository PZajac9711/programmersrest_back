package pl.programmersrest.blog.model.enums;

public enum AuthorityEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private String authority;

    AuthorityEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}

package pl.programmersrest.blog.model.enums;

/**
 * Defines available roles in system.
 * @author Patryk
 */
public enum AuthorityEnum {
    /**
     * Defines admin, user with this authority can create/delete/update post and more
     */
    ADMIN("ADMIN"),
    /**
     * Default authority after creating new account.
     */
    USER("USER");

    private String authority;

    AuthorityEnum(String authority) {
        this.authority = authority;
    }

    /**
     * Get authority.
     * @return authority in String form
     */
    public String getAuthority() {
        return authority;
    }
}

package backend.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    WORK_CLASS_1("WORK_CLASS_1"),
    WORK_CLASS_2("WORK_CLASS_2"),
    WORK_CLASS_3("WORK_CLASS_3");

    private String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}

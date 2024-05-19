package userService.security.models;

import org.springframework.security.core.GrantedAuthority;
import userService.models.Role;

public class CustomGrantAuthority implements GrantedAuthority {
    private String authority;

    public CustomGrantAuthority(Role role) {
        this.authority = role.getValue();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}

package userService.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import userService.models.Role;
@JsonDeserialize
@NoArgsConstructor
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

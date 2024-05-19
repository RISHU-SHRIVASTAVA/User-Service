package userService.security.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import userService.models.Role;
import userService.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//This CustomUserDetails class will act like a User Class for Spring Security
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<CustomGrantAuthority> grantAuthorities;

    public CustomUserDetails(User user){
        this.username = user.getEmail();
        this.password = user.getHashedPassword();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;

        //In the granted authorities we need to add roles;
        this.grantAuthorities = new ArrayList<>();
        for(Role role:user.getRoles()){
            grantAuthorities.add(new CustomGrantAuthority(role));
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

package userService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModels {
    private String name;
    private String email;
    private String hashedPassword;
    @ManyToMany
    private List<Role> roles;
    private boolean isEmailVerified;
}

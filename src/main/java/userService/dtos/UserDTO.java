package userService.dtos;

import lombok.Getter;
import lombok.Setter;
import userService.models.Role;
import userService.models.User;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private List<Role> roles;

    public static UserDTO from(User user){
        if(user==null){
            return null;
        }

        UserDTO userDTO=new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setRoles(user.getRoles());

        return userDTO;
    }
}

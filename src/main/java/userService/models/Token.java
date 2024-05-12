package userService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Token extends BaseModels{
    private String value;
    @ManyToOne
    private User user;
    private Date expiryAt;
}

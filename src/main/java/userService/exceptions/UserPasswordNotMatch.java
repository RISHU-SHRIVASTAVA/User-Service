package userService.exceptions;

public class UserPasswordNotMatch extends RuntimeException{
    public UserPasswordNotMatch(String message){
        super(message);
    }
}

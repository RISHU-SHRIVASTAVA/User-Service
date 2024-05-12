package userService.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import userService.exceptions.UserNotFoundException;
import userService.exceptions.UserPasswordNotMatch;
import userService.models.Token;
import userService.models.User;
import userService.repositories.TokenRepository;
import userService.repositories.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRepository userRepository,
                       TokenRepository tokenRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository=userRepository;
        this.tokenRepository=tokenRepository;
    }

    public User signUp(String email,
                       String name,
                       String password){
        User user=new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        user.setEmailVerified(true);

        //save the user object to the DB.

        return userRepository.save(user);
    }
    public Token login(String email,String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User with email :"+email+" doesn't exit in our system");
        }
        User user=optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())){
            //throw exception
            throw new UserPasswordNotMatch("Password Not Match");
        }

        Token token=generateToken(user);
        Token savedToken=tokenRepository.save(token);
        return savedToken;
    }

    private Token generateToken(User user){
        LocalDate currentDate= LocalDate.now();
        LocalDate thirtyDaysLater = currentDate.plusDays(30);

        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpiryAt(expiryDate);

        //128 character alphanumeric string.
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);
        return token;


    }
    public void logout(String tokenValue){
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(tokenValue, false);
        if(optionalToken.isEmpty()){
            //Throw new Exception
            return;
        }
        Token token=optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);
    }
    public User validateToken(String token){

        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(token, false,new Date());

        if(optionalToken.isEmpty()){
            //throw an exception
            return null;
        }
        return optionalToken.get().getUser() ;

    }
}

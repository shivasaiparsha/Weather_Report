package com.Dice.Weather.Services;


import com.Dice.Weather.Dtos.AuthRequestDto;
import com.Dice.Weather.SecurityFilterImpl.UserDetailsServiceImp;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@ComponentScan
@Component
public class AuthService {


    final String saltedkey="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMN1234567890OPQRSTUVWXYZ";
    final String saltedPassword="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMN1234567890OPQRSTUVWXYZ@#4%^&*?";

    public static HashMap<String, String> map=new HashMap<>();
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public boolean Authenticate(AuthRequestDto authRequestDto) throws BadCredentialsException, UsernameNotFoundException
    {
        try {
            String username = authRequestDto.getUsername();
            String password = authRequestDto.getPassword();
            String encryptedPassword=passwordEncoder.encode(password);
            UserDetails user = new UserDetailsServiceImp().loadUserByUsername(username);

            if(user==null) throw new  UsernameNotFoundException(username+" not found");// if user  not found
            //passwordEncoder.matches(encryptedPassword, passwordEncoder.encode(user.getPassword()))
            if(user.getPassword().equals(password)) return true ; // if password matches return true
            else throw new BadCredentialsException("password incorrect");

        }
        catch (UsernameNotFoundException e)
        {
            throw new UsernameNotFoundException(e.getMessage());
        }
        catch (Exception e){

            throw new BadCredentialsException(e.getMessage());
        }

    }



    public String[] getCredentials() {
        StringBuilder randomUserString=new StringBuilder();
        StringBuilder randomPasswordString=new StringBuilder();
        for(int i=0; i<10; i++)
        {
            int randomIndex=(int)Math.floor(Math.random()*saltedkey.length());

            randomUserString.append(saltedkey.charAt(randomIndex));
        }

        for(int i=0; i<14; i++)
        {
            int randomIndex=(int)Math.floor(Math.random()*saltedPassword.length());
            randomPasswordString.append(saltedPassword.charAt(randomIndex));
        }

     String user=randomUserString.toString();
        String password=randomPasswordString.toString();
        map.put(user, password);
        return new String[]{user, password};
    }
}

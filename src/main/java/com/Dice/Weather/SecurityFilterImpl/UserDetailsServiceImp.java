package com.Dice.Weather.SecurityFilterImpl;

import com.Dice.Weather.Pojo.User;
import com.Dice.Weather.Services.AuthService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(authService.map.size());
        if (!authService.map.containsKey(username)) {
            log.error("user not found");
            throw new UsernameNotFoundException("user not found exception");
        } else{
            String password = authService.map.get(username);

        UserDetails user = new User().getUser(username, password);


        // write a logic here to get the details from the database to authenticate the use

        return user;
       }
    }
}

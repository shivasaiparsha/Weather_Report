package com.Dice.Weather.Controller;

import com.Dice.Weather.Dtos.AuthRequestDto;
import com.Dice.Weather.JwtFilter.JwtService;
import com.Dice.Weather.SecurityFilterImpl.UserDetailsServiceImp;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Builder
public class AuthController {



    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsServiceImp userDetailsServiceImp;


    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    @PostMapping("/auth/login")
    public ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO){

        System.out.println(authRequestDTO.getUsername()+" "+authRequestDTO.getPassword());
        try {
            if(Authenticate(authRequestDTO)) {
                String accessToken = jwtService.GenerateToken(authRequestDTO.getUsername());

                return new ResponseEntity<>(accessToken, HttpStatus.OK);
            }
            else {
                throw new UsernameNotFoundException("invalid user request..!!");
            }
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>("user not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (LockedException e) {
            return new ResponseEntity<>("User account is locked", HttpStatus.NOT_FOUND);
        } catch (DisabledException e) {
            return new ResponseEntity<>("User account is disabled", HttpStatus.CONFLICT);
        } catch (UsernameNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/welcome")
    public ResponseEntity<?> getUser()
    {
        String username="user123";
        String password="password123";
        AuthRequestDto authRequestDto=new AuthRequestDto(username, password);
        return new ResponseEntity<>(authRequestDto, HttpStatus.OK);
    }



    // Autnticate user
    public boolean Authenticate(AuthRequestDto authRequestDto) throws BadCredentialsException,UsernameNotFoundException
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



}

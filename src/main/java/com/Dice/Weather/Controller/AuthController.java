package com.Dice.Weather.Controller;

import com.Dice.Weather.Dtos.AuthRequestDto;
import com.Dice.Weather.JwtFilter.JwtService;
import com.Dice.Weather.SecurityFilterImpl.UserDetailsServiceImp;
import com.Dice.Weather.Services.AuthService;
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

import java.util.HashMap;

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




    @Autowired
    private AuthService authService;


    @PostMapping("/auth/login")
    public ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO){

        System.out.println(authRequestDTO.getUsername()+" "+authRequestDTO.getPassword());
        try {
            if(authService.Authenticate(authRequestDTO)) {
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
        // hard corded credentials
        String credentialArray[]=authService.getCredentials(); // this method used to generate valid credentials to logni
        String username=credentialArray[0]; // the first string of array is username
        String password=credentialArray[1]; // the second index string of array is password
        AuthRequestDto authRequestDto=new AuthRequestDto(username, password);
        return new ResponseEntity<>(authRequestDto, HttpStatus.OK);
    }

}

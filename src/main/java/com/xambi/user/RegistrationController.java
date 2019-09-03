/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xambi.user;

import com.xambi.account.AccountDTO;
import com.xambi.user.OTPBundle;
import com.xambi.user.UserDTO;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/registration")
@CrossOrigin
public class RegistrationController {
    static String SECRET_KEY = "xambi";

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping(path = "/initialize")
    public @ResponseBody
    ResponseEntity<?> registerUser(@RequestBody UserDTO userRegistration) {
        boolean isValid = userRegistration.getPhone() != null
                && !userRegistration.getPhone().isEmpty()
                && userRegistration.getPhone().matches("^\\d{3}$");
        if (isValid) {
            User user = userRegistration.prepareUser();
            user = userRepository.save(user);
            String otp = generateOTP(user);
            userRegistration.setId(user.getId());
            userRegistration.setOtp(otp);
            return ResponseEntity.ok(userRegistration);
        }
        return ResponseEntity.badRequest().body("Error validating user.");
    }

    @PostMapping(path = "/confirm-otp")
    public @ResponseBody
    ResponseEntity<?> confirmOtp(@RequestBody UserDTO userRegistration) {
        return ResponseEntity.ok("Sucess");
    }

    @PostMapping(path = "/complete")
    public @ResponseBody
    ResponseEntity<?> completeInformation(@RequestBody UserDTO userRegistration) {
        boolean isValid = true;
        if (isValid) {
            ResponseEntity response;
            Optional<User> userResult = userRepository.findById(userRegistration.getId());
            if (!userResult.isPresent()) {
                response = ResponseEntity.badRequest().body("Error saving username. User dont exists.");
            } else if (userRepository.existsUserByUsername(userRegistration.getUsername())) {
                response = ResponseEntity.badRequest().body("Username is used.");
            } else {
                User user = userResult.get();
                user.setUsername(userRegistration.getUsername());
                userRepository.save(user);
                response = ResponseEntity.ok("Username saved successfully");
            }      
            return response;
        }
        return ResponseEntity.badRequest().body("Error validating user.");
    }
    
    @PostMapping(path = "/finish")
    public @ResponseBody
    ResponseEntity<?> finishRegistration(@RequestBody UserDTO userRegistration) {
        ResponseEntity response = null;
        Optional<User> userResult = userRepository.findById(userRegistration.getId());
        if (userResult.isPresent()) {
            User user = userResult.get();
            user.setStatus(User.COMPLETE_STATUS);
            userRepository.save(user);
            response = ResponseEntity.ok("User registered successfully");
        } else {
            response = ResponseEntity.badRequest().body("Error saving username. User dont exists.");
        }
        return response;
    }

    protected static String generateOTP(User user) {
        // Using numeric values 
        String numbers = "0123456789";

        // Using random method 
        Random randomGenerator = new Random();
        char[] otp = new char[7];
        for (int i = 0; i < otp.length; i++) {
            otp[i] = numbers.charAt(randomGenerator.nextInt(numbers.length()));
        }
        return new String(otp);
    }
}

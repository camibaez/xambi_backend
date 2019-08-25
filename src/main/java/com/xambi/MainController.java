/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xambi;

import com.xambi.dto.AccountRegistration;
import com.xambi.dto.OTPBundle;
import com.xambi.dto.UserRegistration;
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
@RequestMapping("/")
@CrossOrigin
public class MainController {

    static String SECRET_KEY = "xambi";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(path = "/register")
    public @ResponseBody
    ResponseEntity<?> registerUser(@RequestBody UserRegistration userRegistration) {
        ResponseEntity response = null;
        if (!isValid(userRegistration)) {
            return ResponseEntity.badRequest().body("Error validating user.");
        }
        User user = null;
        switch (userRegistration.getContext()) {
            case UserRegistration.INIT_REGISTRATION:
                response = initRegistration(userRegistration);
                break;
            case UserRegistration.COMPLETE_REGISTRATION_DATA:
                response = completeRegistrationData(userRegistration);
                break;
            case UserRegistration.FINISH_REGISTRATION:
                response = finishRegistration(userRegistration);
                break;
        }

        return response;

    }

    protected ResponseEntity<UserRegistration> initRegistration(UserRegistration registrationData) {
        User user = registrationData.prepareUser();
        user = userRepository.save(user);
        String otp = generateOTP(user);
        registrationData.setId(user.getId());
        registrationData.setOtp(otp);
        return ResponseEntity.ok(registrationData);
    }

    protected ResponseEntity completeRegistrationData(UserRegistration registrationData) {
        ResponseEntity response = null;
        Optional<User> userResult = userRepository.findById(registrationData.getId());
        if (!userResult.isPresent()) {
            response = ResponseEntity.badRequest().body("Error saving username. User dont exists.");
        } else if (!userRepository.existsUserByUsername(registrationData.getUsername())) {
            User user = userResult.get();
            user.setUsername(registrationData.getUsername());
            userRepository.save(user);
            response = ResponseEntity.ok("Username saved successfully");
        } else {
            response = ResponseEntity.badRequest().body("Username is used.");
        }

        return response;
    }

    protected ResponseEntity finishRegistration(UserRegistration registrationData) {
        ResponseEntity response = null;
        Optional<User> userResult = userRepository.findById(registrationData.getId());
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

    @PostMapping(path = "/confirm-otp")
    public @ResponseBody
    ResponseEntity<?> confirmOtp(@RequestBody UserRegistration userRegistration) {
        return ResponseEntity.ok("Sucess");
    }

    @PostMapping(path = "/account")
    public @ResponseBody
    ResponseEntity<?> registerAccount(@RequestBody AccountRegistration accountRegistration) {
        if (accountRegistration.isValid()) {
            Account account = accountRegistration.prepareAccount();
            accountRepository.save(account);
            return ResponseEntity.ok("Account registered successfully!");
        }

        return ResponseEntity.badRequest().body("Error registering account.");
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
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

    private boolean isValid(UserRegistration userRegistration) {
        boolean isValid = false;
        switch (userRegistration.getContext()) {
            case UserRegistration.INIT_REGISTRATION:
                isValid = userRegistration.getPhone() != null
                        && !userRegistration.getPhone().isEmpty()
                        && userRegistration.getPhone().matches("^\\d{3}$");
                break;
            case UserRegistration.COMPLETE_REGISTRATION_DATA:
                isValid = true;
        }

        return isValid;
    }
}

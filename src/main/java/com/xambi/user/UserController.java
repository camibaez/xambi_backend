/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xambi.user;

import com.xambi.account.Account;
import com.xambi.account.AccountRepository;
import java.util.Optional;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRespository;

    @GetMapping(path = "/get/{id}")
    public @ResponseBody
    UserDTO get(@PathVariable String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        UserDTO userDto = new UserDTO();
        userDto.loadFrom(user);
        return userDto;
    }

    @PostMapping(path = "/update/{id}")
    public @ResponseBody
    UserDTO update(@PathVariable String id, @RequestBody UserDTO userData) {
       User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
       userData.loadTo(user);
       userRepository.save(user);
       
       return userData;
    }

    @GetMapping(path = "/accounts-list/{id}")
    public @ResponseBody
    Iterable<Account> getAccounts(@PathVariable String id) {
        return accountRespository.findByUserId(id);
    }
    
    
}

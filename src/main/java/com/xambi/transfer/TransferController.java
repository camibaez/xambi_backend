/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xambi.transfer;

import com.xambi.account.AccountRepository;
import com.xambi.user.User;
import com.xambi.user.UserDTO;
import com.xambi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRespository;
    
    @PostMapping(path = "transfer")
    public @ResponseBody
    UserDTO transferMoney(@RequestBody TransferDTO transferDto) {
        return null;
    }
    
}

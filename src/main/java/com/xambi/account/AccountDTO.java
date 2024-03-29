/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xambi.account;

import com.xambi.user.User;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class AccountDTO {
    private String accountNumber;
    private String userId;
    private short type;

    
     public Account prepareAccount(){
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setUserId(userId);
        account.setType((short)type);
        account.setId(UUID.randomUUID().toString());
        
        return account;
    }
    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the type
     */
    public short getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(short type) {
        this.type = type;
    }
    
    public boolean isValid(){
        return true;
    }
    
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xambi.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class UserDTO {
    public final static int INIT_REGISTRATION = 1;
    public final static int CONFIRM_OTP = 2;
    public final static int COMPLETE_REGISTRATION_DATA = 3;
    public final static int FINISH_REGISTRATION = 4;
    
    private int context;
    private String username;
    private String password;
    private String repeatPassword;
    private String phone;
    private String email;
    private String otp;
    private String id;
    private String tag;

    public void loadFrom(User user){
        username = user.getUsername();
        phone = user.getPhone();
        email = user.getEmail();
    }
    
    public void loadTo(User user){
        user.setUsername(username);
        user.setPhone(phone);
        user.setEmail(email);
    }
    
    public boolean validate(){
        return true;
    }
    
    public User prepareUser(){
        User user = null;
        user = new User();
        user.setPhone(phone);
        user.setId(generateId());
        user.setStatus(User.INIT_STATUS);
        user.setPassHash("hash");
        return user;
    }
    
    
    public String generatePassowdHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String passHash = new String(encodedhash);
        return passHash;
    }
    
    public String generateId(){
        String id = UUID.randomUUID().toString();
        return id;
    }
    
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the repeatPassword
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * @param repeatPassword the repeatPassword to set
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the context
     */
    public int getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(int context) {
        this.context = context;
    }

    /**
     * @return the otp
     */
    public String getOtp() {
        return otp;
    }

    /**
     * @param otp the otp to set
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}

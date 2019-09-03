/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xambi.user;

import com.xambi.user.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author User
 */
public interface UserRepository extends CrudRepository<User, String> {
    boolean existsUserByUsername(String username);
}

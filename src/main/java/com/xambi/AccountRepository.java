/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xambi;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author User
 */
public interface AccountRepository extends CrudRepository<Account, String> {
    
}

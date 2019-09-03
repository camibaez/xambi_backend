/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xambi.account;

import com.xambi.account.Account;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author User
 */
public interface AccountRepository extends CrudRepository<Account, String> {
    public List<Account> findByUserId(String userId);
}

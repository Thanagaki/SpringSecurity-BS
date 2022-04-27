package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    public UserDetailsServiceImpl (UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userDao.findByUsername(username);
         if(user == null) {
             throw new UsernameNotFoundException(String.format("User '%s' not found", username));
         }
         return user;
    }


}

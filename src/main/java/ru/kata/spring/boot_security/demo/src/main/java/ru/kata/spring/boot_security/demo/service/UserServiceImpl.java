package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;

    public UserServiceImpl (UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }


    @Override
    public List<User> getAllUsers() {
      return userDao.findAll();
    }

    @Override
    public User getById(Long id) {
       return userDao.findById(id).orElse(null);
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDb = userDao.findByUsername(user.getUsername());
        if(userFromDb != null) {
            return false;
        }
        userDao.save(user);
        return true;
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);

    }

    @Override
    public void edit(User user) {
        userDao.save(user);
    }

    public User findByUserName(String username) {
        return userDao.findByUsername(username);
    }
}

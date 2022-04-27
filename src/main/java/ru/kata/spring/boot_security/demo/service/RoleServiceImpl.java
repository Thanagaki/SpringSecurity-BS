package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl (RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public Role getById(Long id){
        return roleDao.findById(id).orElse(null);
    }
}

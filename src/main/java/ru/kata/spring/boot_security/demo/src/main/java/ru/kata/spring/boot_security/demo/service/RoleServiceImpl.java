package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl (RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @PersistenceContext
    EntityManager entitymanager;


    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public Role getById(Long id){
        return roleDao.findById(id).orElse(null);
    }

    @Override
    public Set<Role> getByName(String[] roles) {
        return entitymanager.createQuery("select r FROM Role r where r.name in (:name)", Role.class).
                setParameter("name", Arrays.asList(roles)).getResultList().stream().collect(Collectors.toSet());
    }
}

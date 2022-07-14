package com.bf.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bf.app.entities.Authority;
import com.bf.app.repository.AuthorityRepository;
import com.bf.app.util.Trees;

@Service
public class AuthorityService {
    
    private AuthorityRepository authorityRepository;

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }
    
    public Set<Authority> getDescendentAuthTree(long parentId) {
        List<Authority> list = authorityRepository.findAll();
        return Trees.list2tree(new HashSet<>(list), parentId);
    }
    
    public List<Authority> findAllByRoleId(int roleId) {
        return authorityRepository.findAllByRoleId(roleId);
    }
    
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

}

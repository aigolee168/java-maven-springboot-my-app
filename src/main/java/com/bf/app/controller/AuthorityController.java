package com.bf.app.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bf.app.entities.Authority;
import com.bf.app.service.AuthorityService;

@RestController
@RequestMapping("authority")
public class AuthorityController {
    
    private AuthorityService authorityService;
    
    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping
    public Set<Authority> getAuthTree(long parentId) {
        return authorityService.getDescendentAuthTree(parentId);
    }

}

package com.bf.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bf.app.entities.Authority;
import com.bf.app.entities.Role;
import com.bf.app.entities.User;
import com.bf.app.repository.UserRepository;
import com.bf.app.util.Trees;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	private RoleService roleService;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public Set<Authority> getAuthTree(int userId) {
		List<Role> roles = roleService.findAllByUserId(userId);
		Set<Authority> list = new HashSet<>();
		for (Role role : roles) {
			Set<Authority> roleAuthTree = roleService.getAuthTree(role.getId());
			list.addAll(Trees.tree2list(roleAuthTree));
		}
		return Trees.list2tree(list, -1);
	}
	
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

}

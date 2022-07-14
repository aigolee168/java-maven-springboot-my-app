package com.bf.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.bf.app.entities.Authority;
import com.bf.app.entities.Role;
import com.bf.app.entities.User;
import com.bf.app.service.AuthorityService;
import com.bf.app.service.RoleService;
import com.bf.app.service.UserService;
import com.bf.app.util.Executors;
import com.bf.app.util.Trees;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class ServicesTests {
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@Order(2)
	@Test
	void authorityServiceTest() {
		Set<Authority> allTree = authorityService.getDescendentAuthTree(-1);
		Authority subTree = Trees.findSubTree(allTree, Arrays.asList("sso"));
		assertThat(subTree).isNotNull();
		
		Trees.iterate(subTree, (auth, depth) -> {
			Executors.repeating(depth, () -> System.out.print("\t"));
			System.out.println(auth);
		});
		
		List<Authority> allList = authorityService.findAllByRoleId(0);
		assertThat(allList).isEmpty();
	}
	
	@Order(1)
	@Test
	void roleServiceTest(
			@Autowired RoleRepository roleDao, 
			@Autowired AuthorityRepository authDao) {
		List<Role> roles = roleService.findAllByUserId(0);
		assertThat(roles).isEmpty();
		
		Role role = new Role("admin");
		roleDao.save(role);
		
		List<Authority> allAuthList = authorityService.findAll();
		for (Authority auth : allAuthList) {
			authDao.saveRoleAuthority(role.getId(), auth.getId());
		}
		
		Set<Authority> tree = roleService.getAuthTree(role.getId());
		assertThat(tree).isNotEmpty();
	}
	
	@Order(3)
	@Test
	void userServiceTest(
			@Autowired RoleRepository roleDao, 
			@Autowired AuthorityRepository authDao,
			@Autowired UserRepository userDao) {
		Set<Authority> tree = userService.getAuthTree(0);
		assertThat(tree).isEmpty();
		
		Role role = new Role("admin");
		roleDao.save(role);
		
		List<Authority> allAuthList = authorityService.findAll();
		for (Authority auth : allAuthList) {
			authDao.saveRoleAuthority(role.getId(), auth.getId());
		}
		
		User user = new User("lily", "123456", "Hello Kitty");
		userDao.save(user);
		
		roleDao.saveUserRole(user.getId(), role.getId());
		
		tree = userService.getAuthTree(user.getId());
		assertThat(tree).isNotEmpty();
		
		Trees.printTree(tree);
	}

}

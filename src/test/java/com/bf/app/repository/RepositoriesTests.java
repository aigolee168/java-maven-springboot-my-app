package com.bf.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.bf.app.entities.Authority;
import com.bf.app.entities.Role;
import com.bf.app.entities.User;

@ActiveProfiles("test")
@ImportAutoConfiguration(classes = MybatisAutoConfiguration.class)
@DataJpaTest(properties = {
        "spring.jpa.defer-datasource-initialization=true",
        "spring.jpa.generate-ddl=false",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.sql.init.schema-locations=classpath:schema-test.sql",
        "spring.sql.init.data-locations=classpath:data-test.sql"
})
class RepositoriesTests {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Order(1)
    @Test
    void authorityRepositoryTest() {
        List<Authority> allAuthority = authorityRepository.findAll();
        assertThat(allAuthority).isNotEmpty();
    }

    @Order(2)
    @Test
    void roleRepository() {
        Role role = roleRepository.save(new Role("管理员"));
        List<Authority> allAuthority = authorityRepository.findAll();
        assertThat(allAuthority).isNotEmpty();
        for (Authority auth : allAuthority) {
            authorityRepository.saveRoleAuthority(role.getId(), auth.getId());
        }
        List<Authority> authorityList = authorityRepository.findAllByRoleId(role.getId());
        assertThat(authorityList).isNotEmpty();
    }

    @Order(3)
    @Test
    void userRepositoryTest() {
        User user = new User("lily", "123456", "Hello Kitty");
        userRepository.save(user);
        List<User> users = userRepository.findAll();
        assertThat(users).isNotEmpty();
    }

}

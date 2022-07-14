package com.bf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bf.app.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	@Query(value = 
			  "select t1.* "
			+ "from role t1 inner join user_role t2 on t2.role_id = t1.id "
			+ "where t2.user_id = ?1", 
			nativeQuery = true)
	List<Role> findAllByUserId(int userId);
	
	@Modifying
    @Query(value = "insert into user_role(user_id,role_id) values(?1,?2)", nativeQuery = true)
	void saveUserRole(int userId, int roleId);

}

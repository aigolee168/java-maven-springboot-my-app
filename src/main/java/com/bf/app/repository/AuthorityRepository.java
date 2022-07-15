package com.bf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bf.app.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query(value = "select t1.* "
            + "from authority t1 "
            + "inner join role_authority t2 on t2.authority_id = t1.id "
            + "where t2.role_id = ?1", nativeQuery = true)
    List<Authority> findAllByRoleId(int roleId);

    @Modifying
    @Query(value = "insert into role_authority(role_id, authority_id) values(?1, ?2)", nativeQuery = true)
    void saveRoleAuthority(int roleId, long authorityId);

    @Modifying
    @Query("update Authority set id = ?1 where id = ?2")
    int updateId(long newId, long id);

}

package com.bf.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bf.app.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}

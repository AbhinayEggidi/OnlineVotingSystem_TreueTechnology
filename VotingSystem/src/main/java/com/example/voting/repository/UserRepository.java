package com.example.voting.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.voting.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

	   

}
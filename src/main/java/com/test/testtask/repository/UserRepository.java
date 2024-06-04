package com.test.testtask.repository;

import com.test.testtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByName(String name);

}

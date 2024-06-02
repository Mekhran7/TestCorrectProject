package com.test.testtask.repository;

import com.test.testtask.entity.House;
import com.test.testtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House,Integer> {

}

package com.test.testtask.service;

import com.test.testtask.entity.House;
import com.test.testtask.entity.User;
import com.test.testtask.repository.HouseRepository;
import com.test.testtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService{

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private UserRepository userRepository;
    @Transactional
    @Override
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }
    @Transactional
    @Override
    public House getHouseById(int id) {
        return houseRepository.findById(id).orElseThrow(()->new IllegalArgumentException("house not found"));
    }
    @Transactional
    @Override
    public House createHouse(House house) {
        return houseRepository.save(house);
    }
    @Transactional
    @Override
    public House updateHouse(int id, House houseDetails) {
        House house = houseRepository.findById(id).orElseThrow(()->new IllegalArgumentException("house not found"));
            house.setAddress(houseDetails.getAddress());
            return houseRepository.save(house);
    }
    @Transactional
    @Override
    public void deleteHouse(int id) {
        houseRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void addResident(int houseId, int userId) {
        House house = houseRepository.findById(houseId).orElseThrow(()->new IllegalArgumentException("house not found"));
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("user not found"));
        house.addUserToHouse(user);
        houseRepository.save(house);
    }
}

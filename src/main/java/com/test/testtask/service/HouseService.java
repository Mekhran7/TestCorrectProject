package com.test.testtask.service;

import com.test.testtask.entity.House;

import java.util.List;

public interface HouseService {
    public List<House> getAllHouses();
    public House getHouseById(int id);
    public House createHouse(House house);
    public House updateHouse(int id, House houseDetails);
    public void deleteHouse(int id);
    public void addResident(int houseId, int userId,int requesterId);
}

package com.test.testtask.service;

import com.test.testtask.entity.House;
import com.test.testtask.entity.User;
import com.test.testtask.repository.HouseRepository;
import com.test.testtask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HouseServiceImplTest {
    @InjectMocks
    private HouseServiceImpl houseServiceImpl;
    @Mock
    private HouseRepository houseRepository;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp(){MockitoAnnotations.openMocks(this);}

    @Test
    void getAllHouses() {
        House house1=new House("A-5",1);
        House house2=new House("B-1",2);
        List<House> houses= Arrays.asList(house1,house2);
        when(houseRepository.findAll()).thenReturn(houses);
        List<House> result=houseServiceImpl.getAllHouses();
        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals("A-5",result.get(0).getAddress());
        assertEquals("B-1",result.get(1).getAddress());
        assertEquals(1,result.get(0).getOwnerId());
        assertEquals(2,result.get(1).getOwnerId());
    }

    @Test
    void getHouseById() {
        House house=new House("A-5",1);
        when(houseRepository.findById(1)).thenReturn(Optional.of(house));
        House result=houseServiceImpl.getHouseById(1);
        assertNotNull(result);
        assertEquals("A-5",result.getAddress());
        assertEquals(1,result.getOwnerId());
    }

    @Test
    void createHouse() {
        House house=new House("A-5",1);
        when(houseRepository.save(house)).thenReturn(house);
        House result=houseServiceImpl.createHouse(house);
        assertNotNull(result);
        assertEquals("A-5",result.getAddress());
        assertEquals(1,result.getOwnerId());
    }

    @Test
    void updateHouse() {
        House house=new House("A-5",1);
        house.setOwnerId(5);
        House updateDetails=new House("B-1",2);
        when(houseRepository.findById(5)).thenReturn(Optional.of(house));
        when(houseRepository.save(house)).thenReturn(house);
        House result=houseServiceImpl.updateHouse(5,updateDetails);
        assertNotNull(result);
        assertEquals("B-1",result.getAddress());
        assertEquals(5,result.getOwnerId());
    }

    @Test
    void deleteHouse() {
        doNothing().when(houseRepository).deleteById(1);
        houseServiceImpl.deleteHouse(1);
        verify(houseRepository,times(1)).deleteById(1);
    }

    @Test
    void addResident() {
        House house=new House("A-5",1);
        User user=new User("Dima",25,"password");
        when(houseRepository.findById(1)).thenReturn(Optional.of(house));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        houseServiceImpl.addResident(1,1);
        verify(houseRepository,times(1)).save(house);
        assertTrue(house.getUsers().contains(user));
    }
}
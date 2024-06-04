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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class HouseServiceImplTest {
    @InjectMocks// это озночает что нужно создать объект HouseServiceImpl и внедрить моки houseRepository и userRepository
    // в его зависимости
    private HouseServiceImpl houseServiceImpl;
    @Mock //озночат что надо создать моки для замены реальных объектов
    private HouseRepository houseRepository;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp(){MockitoAnnotations.openMocks(this);}//инициализирует моки перед выполнением каждого теста

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
    void testAddResident_HouseNotFound() {
        given(houseRepository.findById(1)).willReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            houseServiceImpl.addResident(1, 1, 1);
        });

        assertEquals("house is not fount", exception.getMessage());
    }
    @Test
    void testAddResident_UserNotFound() {
        House house = new House();
        house.setHouseId(1);
        house.setOwnerId(1);

        given(houseRepository.findById(1)).willReturn(Optional.of(house));
        given(userRepository.findById(1)).willReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            houseServiceImpl.addResident(1, 1, 1);
        });

        assertEquals("user is not found", exception.getMessage());
    }
    @Test
    void testAddResident_RequesterIsNotOwner() {
        House house = new House();
        house.setHouseId(1);
        house.setOwnerId(2);

        User user = new User();
        user.setId(1);

        given(houseRepository.findById(1)).willReturn(Optional.of(house));
        given(userRepository.findById(1)).willReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            houseServiceImpl.addResident(1, 1, 1);
        });

        assertEquals("only owner can add users to house", exception.getMessage());
    }
    @Test
    void testAddResident_Success() {
        House house = new House();
        house.setHouseId(1);
        house.setOwnerId(1);

        User user = new User();
        user.setId(1);
        // задаем поведение то есть настраиваем поведение мок-объектов при вызове определенных методов
        given(houseRepository.findById(1)).willReturn(Optional.of(house));
        given(userRepository.findById(1)).willReturn(Optional.of(user));

        houseServiceImpl.addResident(1, 1, 1);

        verify(houseRepository, times(1)).save(house);
}


}
/*
использование моков позволяет изолировать тестируемый метод от
его зависимостей (houseRepository и userRepository). путем создания замещающих объектов(моков)
эти методы имитируют поведение реальных объектов, но дают возможность контролировать их состояние и поведение
Это означает, что тесты проверяют только логику метода,
а не взаимодействие с базой данных или другие внешние системы
 */
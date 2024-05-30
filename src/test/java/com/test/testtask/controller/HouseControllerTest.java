package com.test.testtask.controller;

import com.test.testtask.entity.House;
import com.test.testtask.entity.User;
import com.test.testtask.service.HouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HouseControllerTest {
    private MockMvc mockMvc;
    @Mock
    private HouseService houseService;
    @InjectMocks
    private HouseController houseController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(houseController).build();
    }
    @Test
    void getAllHouses() throws Exception {
        List<House> houses= Arrays.asList(new House("A-5",1),new House("B-5",2));
        given(houseService.getAllHouses()).willReturn(houses);
        mockMvc.perform(get("/api/houses/getAllHouses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].address",is("A-5")))
                .andExpect(jsonPath("$[1].address",is("B-5")))
                .andExpect(jsonPath("$[0].ownerId",is(1)))
                .andExpect(jsonPath("$[1].ownerId",is(2)));
    }

    @Test
    void getHouseById() throws Exception {
        House house=new House(1,"A-5",2);
        given(houseService.getHouseById(anyInt())).willReturn(house);
        mockMvc.perform(get("/api/houses/getHouse/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address",is("A-5")))
                .andExpect(jsonPath("$.ownerId",is(2)));
    }

    @Test
    void createHouse() throws Exception {
        House house = new House( "A-5", 1);

        given(houseService.createHouse(any(House.class))).willReturn(house);

        mockMvc.perform(post("/api/houses/createHouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"A-5\", \"ownerId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address", is("A-5")));
    }

    @Test
    void updateHouse() throws Exception {
        House house=new House(1,"A-5",2);
        given(houseService.updateHouse(anyInt(),any(House.class))).willReturn(house);
        mockMvc.perform(put("/api/houses/updateHouse/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"A-5\", \"ownerId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerId",is(house.getOwnerId())));
    }

    @Test
    void deleteHouse() throws Exception {
        mockMvc.perform(delete("/api/houses/deleteHouse/1"))
                .andExpect(status().isOk());
    }

    @Test
    void addResident() throws Exception {
        House house=new House(1,"A-5",2);
        User user=new User(3,"Dima",20,"pass123");
        mockMvc.perform(post("/api/houses/1/residents/3"))
                .andExpect(status().isOk());
    }
}

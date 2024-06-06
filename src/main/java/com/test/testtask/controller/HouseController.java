package com.test.testtask.controller;

import com.test.testtask.entity.House;
import com.test.testtask.security.JwtUtil;
import com.test.testtask.service.HouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
@Tag(name = "house controller", description = "изменение,удаление,добавление,поиск домов и добавление пользователей в дом")
public class HouseController {

    @Autowired
    private HouseService houseService;
    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/getAllHouses")
    public List<House> getAllHouses() {
        return houseService.getAllHouses();
    }

    @Operation(summary = "получение дома", description = "позволяет получать дом по ID")
    @GetMapping("/{id}")
    public ResponseEntity<House> getHouseById(@PathVariable int id) {
        House house = houseService.getHouseById(id);
        return house != null ? ResponseEntity.ok(house) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "создание дома", description = "позволяет создавать дома")
    @PostMapping("/createHouse")
    public House createHouse(@RequestBody House house) {
        return houseService.createHouse(house);
    }

    @Operation(summary = "изменение дома", description = "позволяет изменять дом по ID")
    @PutMapping("/{id}")
    public ResponseEntity<House> updateHouse(@PathVariable int id, @RequestBody House houseDetails) {
        House updatedHouse = houseService.updateHouse(id, houseDetails);
        return updatedHouse != null ? ResponseEntity.ok(updatedHouse) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "удаление дома", description = "позволяет удалять дома по ID")
    @DeleteMapping("/{id}")
    public void deleteHouse(@PathVariable int id) {
        houseService.deleteHouse(id);
    }

    @Operation(summary = "добавление пользователя в дом", description = "позволяет домавлять пользователей в дом")
    @PostMapping("{houseId}/residents/{userId}")
    public ResponseEntity<Void> addResident(@PathVariable int houseId, @PathVariable int userId,
                                            @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        int requesterId = jwtUtil.extractUserId(token);
        houseService.addResident(houseId, userId, requesterId);
        return ResponseEntity.ok().build();
    }
}

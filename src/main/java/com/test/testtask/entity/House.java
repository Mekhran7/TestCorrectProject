package com.test.testtask.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private int houseId;
    @Column(name = "address")
    private String address;
    @Column(name = "owner_id")
    private int ownerId;
    @OneToMany(mappedBy = "house", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST
            , CascadeType.REFRESH})
    @JsonManagedReference //должно быть сериализовано(управляемая сторона)
    private List<User> users;

    public void addUserToHouse(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        user.setHouse(this);
    }

    public House(String address, int ownerId) {
        this.address = address;
        this.ownerId = ownerId;
    }

    public House(int houseId, String address, int ownerId) {//как и в классе user этот конструктор нужен для тестирования
        this.houseId = houseId; //контроллера где нужно работать с ID
        this.address = address;
        this.ownerId = ownerId;
    }
}

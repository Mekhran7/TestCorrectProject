package com.test.testtask.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "password")
    private String password;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST
            ,CascadeType.REFRESH})
    @JoinColumn(name = "house_id")
    @JsonBackReference //не должно быть серилизовано(обратная сторона)
    @JsonInclude
    private House house;

    public User(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public User(int id, String name, int age, String password) { //надо для тестирования контроллера где нужен ID
        this.id = id;
        this.name = name;
        this.age = age;
        this.password = password;
    }
}

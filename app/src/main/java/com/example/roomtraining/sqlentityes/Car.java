package com.example.roomtraining.sqlentityes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Car {

    @PrimaryKey
    public long id;

    public String model;

    public int year;
}

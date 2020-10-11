package com.example.roomtraining.sqlentityes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Department {

    @PrimaryKey
    public int id;

    public String name;
}

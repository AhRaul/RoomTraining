package com.example.roomtraining.sqldatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.roomtraining.sqldao.CarDao;
import com.example.roomtraining.sqldao.EmployeeDao;
import com.example.roomtraining.sqlentityes.Car;
import com.example.roomtraining.sqlentityes.Employee;

/**
 * Аннотацией Database помечаем основной класс по работе с базой данных. Этот класс должен быть
 * абстрактным и наследовать RoomDatabase.
 *
 * В параметрах аннотации Database указываем, какие Entity будут использоваться, и версию базы.
 * Для каждого Entity класса из списка entities будет создана таблица.
 *
 * В Database классе необходимо описать абстрактные методы для получения Dao объектов,
 * которые вам понадобятся.
 */
@Database(entities = {Employee.class, Car.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {
    public abstract EmployeeDao employeeDao();
    public abstract CarDao carDao();
}

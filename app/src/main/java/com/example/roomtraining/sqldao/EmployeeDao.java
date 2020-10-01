package com.example.roomtraining.sqldao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomtraining.sqlentityes.Employee;

import java.util.List;

/**
 * В объекте Dao мы будем описывать методы для работы с базой данных. Нам нужны будут методы
 * для получения списка сотрудников и для добавления/изменения/удаления сотрудников.
 *
 * Описываем их в интерфейсе с аннотацией Dao.
 */
@Dao
public interface EmployeeDao {

    /**
     * Методы getAll и getById позволяют получить полный список сотрудников или конкретного
     * сотрудника по id. В аннотации Query нам необходимо прописать соответствующие SQL-запросы,
     * которые будут использованы для получения данных.
     *
     * Обратите внимание, что в качестве имени таблицы мы используем employee. Напомню,
     * что имя таблицы равно имени Entity класса, т.е. Employee, но в SQLite не важен регистр
     * в именах таблиц, поэтому можем писать employee.
     *
     * @return
     */
    @Query("SELECT * FROM employee")
    List<Employee> getAll();

    @Query("SELECT * FROM employee WHERE id = :id")
    Employee getByID(long id);

    /**
     * Для вставки/обновления/удаления используются методы insert/update/delete с соответствующими
     * аннотациями. Тут никакие запросы указывать не нужно. Названия методов могут быть любыми.
     * Главное - аннотации.
     * @param employee
     */
    @Insert
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);
}

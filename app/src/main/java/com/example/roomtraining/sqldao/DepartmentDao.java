package com.example.roomtraining.sqldao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.roomtraining.sqlentityes.Department;
import com.example.roomtraining.sqlentityes.DepartmentWithEmployees;

import java.util.List;

@Dao
public interface DepartmentDao {

    /**
     * Relation
     * Это простой запрос, который вытащит необходимые данные по отделу. А запрос по сотрудникам
     * для каждого отдела сделает за нас Room.
     * @return
     */
    @Query("SELECT id, name from department")
    List<DepartmentWithEmployees> getDepartmentWithEmployees();
}

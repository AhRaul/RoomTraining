package com.example.roomtraining.sqlentityes;

import androidx.room.ColumnInfo;

/**
 * Обратите внимание на тип объектов, который мы будем получать от этого метода. Это
 * EmployeeDepartment. Нам нужно создать этот объект, и указать в нем все поля, которые мы ожидаем
 * получить от запроса.
 * Это не Entity объект, а обычный класс. Поля этого класса должны совпадать с полями результата,
 * который вернет запрос. Room конвертирует результаты запроса в список этих объектов, и мы получим
 * то, что хотели.
 */
public class EmployeeDepartment {

    public String name;

    public int salary;

    @ColumnInfo(name = "department_name")
    public String departmentName;
}

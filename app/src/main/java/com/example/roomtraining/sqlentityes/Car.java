package com.example.roomtraining.sqlentityes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Внешний ключ
 *
 * Внешние ключи позволяют связывать таблицы между собой.
 * Каждая машина должна быть прикреплена к какому-либо сотруднику.
 *
 * Используем параметр foreignKeys для создания внешнего ключа. Указываем, что значения поля
 * employee_id (параметр childColumns) должно обязательно быть равно какому-либо значению
 * поля id (параметр parentColumns) в таблице сотрудников Employee (параметр entity).
 *
 * Т.е. если у нас есть три сотрудника с id 1,2 и 3, мы не сможем добавить в базу данных машину
 * с employee_id = 4. Потому что в базе нет такого родительского ключа, т.е. сотрудника с id = 4.
 *
 * Или, если вы попытаетесь удалить родительский ключ, т.е. сотрудника, к которому прикреплена
 * какая-либо машина, то база выдаст вам ошибку. Потому что после удаления сотрудника, у машины
 * в поле employee_id будет находиться значение, которого нет в поле id таблицы сотрудников.
 *
 * Для подобных случаев удаления или изменения родительского ключа, вы можете настроить поведение
 * базы данных. По умолчанию она возвращает ошибку, но это можно поменять с помощью параметров
 * onDelete и onUpdate в аннотации ForeignKey. (см. Внешний ключ в классе Employee)
 */
@Entity(foreignKeys = @ForeignKey(entity = Employee.class, parentColumns = "id" , childColumns = "employee_id"))
public class Car {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String model;

    public int year;

    //В поле employee_id будет храниться id сотрудника, к которому прикреплена эта машина.
    @ColumnInfo(name = "employee_id")
    public long employeeId;
}

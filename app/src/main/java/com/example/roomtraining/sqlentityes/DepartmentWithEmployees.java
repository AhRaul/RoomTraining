package com.example.roomtraining.sqlentityes;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * Relation
 * Аннотация Relation также позволяет делать запросы из нескольких таблиц, но структура результата
 * будет немного другой. И нам самим не придется писать сложные запросы. Room все сделает за нас.
 * Давайте представим, что нам надо получить список отделов. И к каждому отделу должен прилагаться
 * список сотрудников.
 *
 * Это не Entity, а обычный класс. В полях id и name будут данные отдела.
 *
 * В employees будет список сотрудников этого отдела. Для этого мы помечаем список аннотацией
 * Relation, и Room сам заполнит его для нас. Давайте разбираться, как именно Room поймет, что он
 * должен поместить в этот список. Откуда он будет брать данные и по какому условию?
 *
 * Тип данных списка - это Employee. Это Entity объект, для него в базе данных создана таблица. Из
 * этой таблицы Room и будет читать данные по сотрудникам. В параметрах parentColumn и entityColumn
 * указываем названия полей, которые участвуют в условии выборки данных. В результате, Room будет
 * искать сотрудников, у которых entityColumn (т.е. department_id) равен parentColumn (т.е. id)
 * отдела. Все найденные сотрудники окажутся в employees.
 */
public class DepartmentWithEmployees {

//    public int id;
//    public String name;
    /**
     * В классе DepartmentWithEmployees мы используем поля id и name для данных по отделу. Но класс
     * Department имеет точно такую же структуру - id и name. Поэтому мы в DepartmentWithEmployees
     * можем заменить эти поля на одно поле с типом Department и аннотацией Embedded:
     */
    @Embedded
    public Department department;

    //По требованиям Room, тип employees должен быть List или Set.
//    @Relation(parentColumn = "id", entityColumn = "department_id")
//    public List<Employee> employees;

    /**
     * Предположим, что нам нужны не все данные по сотрудникам, а только некоторые поля. Например,
     * name и salary. Создаем под них класс: EmployeeNameAndSalary
     * И используем его, как тип в Relation-списке
     * А чтобы Room знал, откуда брать данные по сотрудникам, указываем Entity класс Employee в
     * параметре entity.
     */
    @Relation(parentColumn = "id", entityColumn = "department_id", entity = Employee.class)
    public List<EmployeeNameAndSalary> employees;
}

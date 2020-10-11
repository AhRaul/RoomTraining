package com.example.roomtraining.sqlentityes;

/**
 * Предположим, что нам нужны не все данные по сотрудникам, а только некоторые поля. Например, name
 * и salary. Создаем под них класс:
 *
 * Relation может быть вложенным. Т.е. в нашем примере класс EmployeeNameAndSalary также может
 * содержать в себе Relation, который будет для каждого сотрудника собирать, например, список
 * техники, записанной на него.
 *
 * Relation не может быть использован в Entity классах, только в обычных. Relation поле не может
 * задаваться через конструктор. Оно должно быть public или иметь public set-метод.
 */
public class EmployeeNameAndSalary {

    public String name;

    public int salary;
}

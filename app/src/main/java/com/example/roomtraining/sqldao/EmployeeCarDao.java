package com.example.roomtraining.sqldao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Transaction;

import com.example.roomtraining.sqlentityes.Car;
import com.example.roomtraining.sqlentityes.Employee;

/**
 * Транзакции
 * Аннотация @Transaction позволяет выполнять несколько методов в рамках одной транзакции.
 *
 * Рассмотрим пример, когда нам нужно добавить объекты Car и Employee:
 *
 * EmployeeCarDao - отдельный Dao объект для работы с комбинацией Car и Employee.
 * В нем описываем методы для вставки объектов по отдельности, а затем оба этих метода вызываем
 * в одном методе с аннотацией Transaction. В итоге вставятся либо оба объекта, либо, в случае
 * возникновения ошибки, ни один из них.
 *
 * Обратите внимание, что в этом случае Dao - не интерфейс, а абстрактный класс.
 */
@Dao
public abstract class EmployeeCarDao {

    @Insert
    public abstract void insertEmployee(Employee employee);

    @Insert
    public abstract void insertCar(Car car);

    @Transaction
    public void insertCarAndEmployee(Car car, Employee employee) {
        insertCar(car);
        insertEmployee(employee);
    }
}

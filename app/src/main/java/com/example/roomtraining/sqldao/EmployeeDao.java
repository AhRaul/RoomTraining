package com.example.roomtraining.sqldao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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
//    @Insert
//    void insert(Employee employee);

    /**
     * Вставка нескольких объектов
     * Мы можем передавать в метод не один, а несколько объектов, используя varargs
     * @param employees
     */
    @Insert
    void insert(Employee... employees);

    /**
     * Также, это может быть список
     * @param employees
     */
    @Insert
    void insert(List<Employee> employees);

    /**
     * Или это вообще может быть любой Iterable
     * При вызове этого метода вы можете использовать массив или коллекцию.
     * @param employees
     */
    @Insert
    void insert(Iterable<Employee> employees);

    /**
     * Получение id
     * При вставке метод Insert может возвращать id только что добавленной записи.
     * Для этого надо описать метод так, чтобы он возвращал long.
     * @param employee
     * @return Если в Employee есть числовой первичный ключ, то именно его значение вы и получите.
     */
//    @Insert
//    long insert(Employee employee);

    /**
     * В случае добавления нескольких записей, необходимо использовать long[]
     * @param employees
     * @return
     */
//    @Insert
//    long[] insert(List<Employee> employees);

    /**
     * или List<Long>
     */
//    @Insert
//    List <Long> insert(List<Employee> employees);

    /**
     * Режимы вставки
     * Рассмотрим ситуацию, когда мы вставляем в таблицу запись, но обнаруживается,
     * что запись с таким ключом там уже есть. По умолчанию мы получим ошибку:
     * SQLiteConstraintException: UNIQUE constraint failed. И ничего в базу не запишется.
     *
     * Но это можно поменять с помощью параметра onConflict.
     *
     * В режиме REPLACE старая запись будет заменена новой. Этот режим хорошо подходит, если вам
     * надо вставить запись, если ее еще нет в таблице или обновить запись, если она уже есть.
     *
     * Также есть режим IGNORE. В этом режиме будет оставлена старая запись и операция вставки
     * не будет выполнена.
     * @param employee
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Employee employee);

    /**
     * Update
     * Эта аннотация аналогична Insert, но она не вставляет, а обновляет объекты в бд.
     *
     * Так же, как и с Insert мы можем использовать коллекции и varargs, чтобы обновлять
     * несколько объектов сразу.
     *
     * Update ищет в бд запись по ключу. Если не найдет, то ничего не произойдет.
     * Если найдет, то обновит все поля, а не только те, которые мы заполнили в Entity объекте.
     *
     * Как и Insert, Update поддерживает параметр onConflict.
     * @param employee
     */
    @Update
    void update(Employee employee);

    /**
     * Мы можем получить количество обновленных записей. Для этого опишите метод так,
     * чтобы он возвращал int.
     * @param employee
     */
    @Update
    int update(List<Employee> employee);

    /**
     * Delete
     * Методы с аннотацией Delete будут удалять объекты.
     *
     * В Delete методах мы также можем использовать коллекции и varargs,
     * чтобы удалять несколько объектов сразу.
     *
     * Delete ищет в бд запись по ключу.
     * @param employee
     */
    @Delete
    void delete(Employee employee);

    /**
     * Мы можем получить количество удаленных записей.
     * Для этого необходимо описать метод так, чтобы он возвращал int.
     * @param employee
     * @return
     */
    @Delete
    int delete(List<Employee> employee);
}

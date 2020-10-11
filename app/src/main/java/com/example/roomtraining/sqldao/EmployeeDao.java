package com.example.roomtraining.sqldao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.roomtraining.sqlentityes.DepartmentWithEmployees;
import com.example.roomtraining.sqlentityes.Employee;
import com.example.roomtraining.sqlentityes.EmployeeDepartment;
import com.example.roomtraining.sqlentityes.Name;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

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
//    @Query("SELECT * FROM employee")
//    List<Employee> getAll();

    /**
     * Room умеет возвращать данные в LiveData обертке.
     * @return
     */
    @Query("SELECT * FROM employee")
    LiveData<List<Employee>> getAllLifeData();

    /**
     * RxJava
     *В Dao указываем для метода выходной тип Flowable
     * @return
     */
    @Query("SELECT * FROM employee")
    Flowable<List<Employee>> getAll();

    /**
     * RxJava
     * Если при запросе нескольких записей,
     * вместо Flowable<List<Employee>> использовать Flowable<Employee>:
     * то мы получим только первую запись из всего результата.
     * @return
     */
//    @Query("SELECT * FROM employee")
//    Flowable<Employee> getAll();

    /**
     * Вместо List, мы также можем использовать массив:
     * @return
     */
//    @Query("SELECT * FROM employee")
//    Employee[] getAll();

    /**
     * И даже Cursor, если это необходимо по каким-то причинам:
     * @return
     */
//    @Query("SELECT * FROM employee")
//    Cursor getAll();

    @Query("SELECT * FROM employee WHERE id = :id")
    Employee getByIDnoRX(long id);

    /**
     * RxJava
     * Если же мы составляем запрос для получения только одной записи,
     * то Flowable<Employee> вполне подойдет.
     *
     * Метод в Dao
     * @return
     */
    @Query("SELECT * FROM employee WHERE id = :id")
    Flowable<Employee> getByID(long id);

    /**
     * У вышеописанного примера есть минус. Если записи нет в базе, то Flowable вообще ничего нам
     * не пришлет. Т.е. это будет выглядеть так, как будто он все еще выполняет запрос.
     * Это можно исправить следующим образом:
     * Хоть мы и ожидаем всего одну запись, но используем не Flowable<Employee>,
     * а Flowable<List<Employee>>. И если записи нет, то мы хотя бы получим пустой лист вместо
     * полной тишины.
     * @param id
     * @return
     */
//    @Query("SELECT * FROM employee WHERE id = :id")
//    Flowable<List<Employee>> getByID(long id);

    /**
     * Single
     * Рассмотрим тот же пример с запросом одной записи, но с использованием Single. Напомню,
     * что в Single может прийти только один onNext, либо OnError. После этого Single считается
     * завершенным.
     */
    @Query("SELECT * FROM employee WHERE id = :id")
    Single<Employee> getByIdSingle(long id);

    /**
     * Поиск сотрудников с зарплатой больше заданного значения
     * @return
     */
    @Query("SELECT * FROM employee WHERE salary > :minSalary")
    List<Employee> getAllWithSalaryMoreThan(int minSalary);

    /**
     * Поиск сотрудников с зарплатой в заданном диапазоне
     * @return
     */
    @Query("SELECT * FROM employee WHERE salary BETWEEN :minSalary AND :maxSalary")
    List<Employee> getAllWithSalaryBetween(int minSalary, int maxSalary);

    /**
     *Поиск сотрудников по имени или фамилии
     * @return
     */
//    @Query("SELECT * FROM employee WHERE first_Name Like :search OR last_name Like :search")
//    List<Employee> getAllWithNameLike(String search);

    /**
     * Поиск сотрудников по списку id.
     * @return
     */
    @Query("SELECT * FROM employee WHERE id IN (:idList)")
    List<Employee> getByIdList(List<Long> idList);

    /**
     * Maybe
     * Рассмотрим тот же пример с запросом одной записи, но с использованием Maybe. Напомню,
     * что в Maybe может прийти либо один onNext, либо onComplete, либо OnError. После этого Maybe
     * считается завершенным.
     */
    @Query("SELECT * FROM employee WHERE id IN (:id)")
    Maybe<Employee> getByIdMaybe(long id);

    /**
     * Subsets
     * Часто при запросе данных нам нужно получить из таблицы не все поля, а только некоторые.
     * Такие запросы быстрее и легче, чем тянуть все поля.
     *
     * Допустим нам надо получать только имя и фамилию сотрудника. Если сделать так:
     *
     * @Query("SELECT first_name, last_name FROM employee")
     * List<Employee> getNames();
     *
     * то уже при компиляции получим ошибку: The columns returned by the query does not have
     * the fields [id,salary] in Employee even though they are annotated as non-null or primitive.
     * Columns returned by the query: [first_name,last_name].
     *
     * Room сообщает, что в данных, которые вернет этот запрос, не хватает полей, чтобы заполнить
     * все поля объекта Employee.
     *
     * В этом случае мы можем использовать отдельный объект. (см. класс Name)
     */
//    @Query("SELECT first_name, last_name FROM employee")
//    List<Name> getNames();

    /**
     * insert, update и delete запросы
     * Аннотации Insert, Update и Delete позволяют нам модифицировать данные, но их возможности
     * слишком ограниченны. Часто возникает необходимость обновить только некоторые поля или
     * удалить записи по определенному условию. Это можно сделать запросами с помощью Query.
     *
     * Давайте рассмотрим пару примеров.
     *
     * Обновление зарплат у сотрудников по списку id.
     *
     * Опционально метод может возвращать int значение, в котором мы получим количество обновленных
     * строк. Если вам это не нужно, то делайте метод void.
     * (Вызов метода: см метод updateSalaryByIdList() в Main классе)
     */
    @Query("UPDATE employee SET salary =:newSalary WHERE id IN (:idList)")
    int updateSalaryByIdList(List<Long> idList, int newSalary);

    /**
     * Удаление сотрудников по списку id
     *
     * Запросы удаления также могут возвращать int значение, в котором мы получим количество
     * удаленных строк.
     * (Вызов метода: см метод deleteByIdList() в Main классе)
     */
    @Query("DELETE from employee WHERE id IN (:idList)")
    int deleteByIdList(List<Long> idList);

    /**
     * Мы хотим получить список работников, в котором будет следующая информация: имя работника,
     * его зарплата, наименование его отдела. Для этого нам надо будет написать запрос, который
     * вытащит данные из двух таблиц.
     *
     * Т.к. поле name есть в обоих таблицах, то для отдела переименовываем его в department_name
     * @return
     */
//    @Query("SELECT employee.name, employee.salary, department.name AS department_name " +
//            "FROM employee, department " +
//            "WHERE department.id == employee.department_id")
//    public List<EmployeeDepartment> getEmployeeWithDepartment();

    /**
     * Relation+Transaction
     * При использовании Relation, Room выполняет несколько запросов, чтобы собрать все данные.
     * Имеет смысл выполнять все эти запросы в одной транзакции, чтобы получить корректные данные.
     * Для этого можно использовать аннотацию Transaction
     */
    @Transaction
    @Query("SELECT id, name from department")
    List<DepartmentWithEmployees> getDepartmentWithEmployees();

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

package com.example.roomtraining;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roomtraining.app.App;
import com.example.roomtraining.sqldao.CarDao;
import com.example.roomtraining.sqldatabase.AppDatabase;
import com.example.roomtraining.sqldao.EmployeeDao;
import com.example.roomtraining.sqlentityes.Employee;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    EmployeeDao employeeDao;
    CarDao carDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //получение базы из синглтон класса
        db = App.getInstance().getDatabase();
        //получение доступа к Dao командам через db
        employeeDao = db.employeeDao();
        carDao = db.carDao();
    }

    /**
     * Теперь мы можем работать с Employee объектами.
     * Но эти операции должны выполняться не в UI потоке. Иначе мы получим Exception.
     *
     * Добавление нового сотрудника в базу будет выглядеть так:
     */
    public void createNewEmployee() {
        Employee employee = new Employee();
        employee.id = 1;
        employee.name = "John Smith";
        employee.salary = 10000;

        employeeDao.insert(employee);
    }

    /**
     * Метод getAll вернет нам всех сотрудников в List<Employee>
     *     (должны выполняться не в UI потоке.)
     * @return
     */
    public List<Employee> getEmployeeList() {
        return employeeDao.getAll();
    }

    /**
     * Получение сотрудника по id:
     * Employee employee = employeeDao.getById(1);
     * (должны выполняться не в UI потоке.)
     * @param id
     * @return
     */
    public Employee getEmployeeByID(long id) {
        return employeeDao.getByID(id);
    }

    /**
     * Обновление данных по сотруднику.
     *Room будет искать в таблице запись по ключевому полю, т.е. по id.
     * Если в объекте employee не заполнено поле id, то по умолчанию в нашем примере оно будет равно
     * нулю и Room просто не найдет такого сотрудника (если, конечно, у вас нет записи с id = 0).
     * (должны выполняться не в UI потоке.)
     * @param employee
     */
    public void updateEmployee(Employee employee) {
        employee.salary = 20000;
        employeeDao.update(employee);
    }

    /**
     * Удаление сотрудника
     * Аналогично обновлению, Room будет искать запись по ключевому полю, т.е. по id
     * (должны выполняться не в UI потоке.)
     * @param employee
     */
    public void deleteEmployee(Employee employee) {
        employeeDao.delete(employee);
    }
}

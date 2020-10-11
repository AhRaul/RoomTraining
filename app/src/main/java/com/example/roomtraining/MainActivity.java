package com.example.roomtraining;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import com.example.roomtraining.app.App;
import com.example.roomtraining.sqldao.CarDao;
import com.example.roomtraining.sqldatabase.AppDatabase;
import com.example.roomtraining.sqldao.EmployeeDao;
import com.example.roomtraining.sqlentityes.Employee;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
public static final String TAG = "MainActivity";

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

        //Получение данных в коде Activity выглядит так:
        //Получаем LiveData и подписываемся на него.
        //
        // Использование LiveData имеет огромное преимущество перед использование списка или массива.
        // Подписавшись на LiveData, вы будете получать свежие данные при их изменении в базе.
        // Т.е. при добавлении новых, удалении старых или обновлении текущих данных в таблице
        // employee, Room снова выполнит ваш Query запрос, и вы получите в onChanged методе
        // актуальные данные с учетом последних изменений. Вам больше не надо самим запрашивать
        // эти данные каждый раз. И все это будет приходить вам в UI поток.
        LiveData<List<Employee>> employeesLiveData = employeeDao.getAllLifeData();

        employeesLiveData.observe(this, new Observer<List<Employee>>() {
           @Override
           public void onChanged(@Nullable List<Employee> employees) {
               Log.d(TAG, "onChanged: " + employees);
           }
        });
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
        employee.firstName = "John Smith";
        employee.salary = 10000;

        employeeDao.insert(employee);
    }

    /**
     * Метод getAll вернет нам всех сотрудников в List<Employee>
     *     (должны выполняться не в UI потоке.)
     * @return
     */
//    public List<Employee> getEmployeeList() {
//        return employeeDao.getAll();
//    }

    /**
     * Получение сотрудника по id:
     * Employee employee = employeeDao.getById(1);
     * (должны выполняться не в UI потоке.)
     * @param id
     * @return
     */
    public Employee getEmployeeByID(long id) {
        return employeeDao.getByIDnoRX(id);
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

    /**
     * Опциональный Update query метод, пример вызова
     * @return количество обновленных строк
     */
    public int updateSalaryByIdList() {
        int updatedCount = db.employeeDao().updateSalaryByIdList(Arrays.asList(1L, 3L, 4L), 10000);
        return updatedCount;
    }

    /**
     * Опциональный delete query метод, пример вызова
     * @return количество удаленных строк
     */
    public int deleteByIdList() {
        int deletedCount = db.employeeDao().deleteByIdList(Arrays.asList(1L, 3L, 4L));
        return deletedCount;
    }
}

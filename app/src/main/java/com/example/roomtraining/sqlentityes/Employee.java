package com.example.roomtraining.sqlentityes;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ColumnInfo.TEXT;
import static androidx.room.ForeignKey.CASCADE;

/**
 * Аннотацией Entity нам необходимо пометить объект, который мы хотим хранить в базе данных.
 * Для этого создаем класс Employee, который будет представлять собой данные сотрудника:
 * id, имя, зарплата:
 *
 * Класс помечается аннотацией Entity. Объекты класса Employee будут использоваться при работе
 * с базой данных.
 * Например, мы будем получать их от базы при запросах данных и отправлять их в базу при вставке
 * данных.
 *
 * Этот же класс Employee будет использован для создания таблицы в базе.
 * В качестве имени таблицы будет использовано имя класса. А поля таблицы будут созданы
 * в соответствии с полями класса.
 *
 * Аннотацией PrimaryKey мы помечаем поле, которое будет ключом в таблице.
 */
//@Entity(tableName = "employees")      //По умолчанию в качестве имени таблицы используется
                                        // имя этого класса. Но мы можем указать свое имя,
                                        // используя параметр tableName.
@Entity
public class Employee {

    // Первичный ключ

    // Каждый Entity класс должен содержать хотя бы одно @PrimaryKey поле.
    // Даже если в классе всего одно поле.
    // У PrimaryKey есть параметр autoGenerate. Он позволяет включить для поля режим autoincrement,
    // в котором база данных сама будет генерировать значение, если вы его не укажете.
    // Теперь при создании Entity объекта вы можете не заполнять поле id.
    // База сама найдет ближайшее свободное значение и использует его.
 @PrimaryKey(autoGenerate = true)
//    @PrimaryKey
    public long id;

    // По умолчанию в качестве имени полей в таблице используются имена полей Entity класса.
    // Но мы можем указать свое имя, используя параметр name в аннотации ColumnInfo.
//    @ColumnInfo(name = "full_name")
    public String name;

    // По умолчанию Room определяет тип данных для поля в таблице по типу данных поля в
    // Entity классе. Но мы можем явно указать свой тип.
//    @ColumnInfo(typeAffinity = TEXT)
    public int salary;
}

//__________________________________________________________________________________________________
/**
 * Модификаторы доступа
 *
// Чтобы Room мог добраться до полей класса Entity, мы делаем их public.
// Но есть возможность использовать private поля. Для этого надо добавить set/get методы.
// Все поля - private. Но каждое имеет set/get методы.
//
// В Android Studio эти методы добавляются парой кликов.
// Жмете в коде ALT+INSERT, выбираете пункт Getter and Setter,
// затем выбираете поля, для которых надо создать методы.
 */

//@Entity
//public class Employee {
//
//    @PrimaryKey
//    private long id;
//
//    private String name;
//
//    private int salary;
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getSalary() {
//        return salary;
//    }
//
//    public void setSalary(int salary) {
//        this.salary = salary;
//    }
//}

//__________________________________________________________________________________________________
/**
// Вместо set-методов мы также можем использовать конструктор. Поле id здесь - private и имеет
// get-метод. А вместо set-метода, Room будет использовать конструктор.
// Параметр конструктора должен иметь тот же тип и имя, что и поле Entity класса.
// Вы можете использовать конструктор для всех полей или только для некоторых, как в примере выше.
 */

//@Entity
//public class Employee {
//
//    public Employee(long id) {
//        this.id = id;
//    }
//
//    @PrimaryKey
//    private long id;
//
//    public String name;
//
//    public int salary;
//
//
//    public long getId() {
//        return id;
//    }
//}

//__________________________________________________________________________________________________
/**
 * Первичный ключ
 *
 // Чтобы создать составной ключ, используйте параметр primaryKeys.
 */
//@Entity(primaryKeys = {"key1", "key2"})
//public class Employee {
//    public long key1;
//    public long key2;
//
//    //...
//
//}

//__________________________________________________________________________________________________
/**
 * Внешний ключ
 *
 * Его значение = CASCADE. Это означает, что при удалении родительского ключа, будут удалены,
 * связанные с ним дочерние ключи. Т.е. при удалении сотрудника, удалится и его машина.
 *
 * Список возможных значений для параметра onDelete можно посмотреть здесь.
 * А подробнее почитать о них на русском здесь.
 *
 * Еще один параметр аннотации ForeignKey - это deferred, имеющий по умолчанию значение false.
 * Если задать этому параметру значение true, то внешний ключ станет отложенным. Это может быть
 * полезно при вставке данных в разные таблицы в рамках одной транзакции. Вы сможете внести все
 * необходимые изменения, и проверка на корректность внешних ключей будет выполнена в самом конце,
 * при выполнении commit.
 */
//@Entity(foreignKeys = @ForeignKey(entity = Employee.class, parentColumns = "id",
// childColumns = "employee_id", onDelete = CASCADE))

//__________________________________________________________________________________________________
/**
 *Индекс
 *
 * Индексы могут повысить производительность вашей таблицы.
 * В аннотации Entity есть параметр indicies, который позволяет задавать индексы.
 *
 * Создаем два индекса: один по полю salary, а другой по двум полям first_name и last_name.
 */
//@Entity(indices = {
//                @Index("salary"),
//                @Index(value = {"first_name", "last_name"})
//            }
//        )
//// Индекс дает возможность установить для его полей проверку на уникальность.
//// Это делается параметром unique = true.
//// В этом случае база будет следить, чтобы в этой таблице не было записи с повторящейся парой
//// значений first_name и last_name.
////@Entity(indices = {@Index(value = {"first_name", "last_name"}, unique = true)})
//public class Employee {
//
//    @PrimaryKey(autoGenerate = true)
//    public long id;
//
//    @ColumnInfo(name = "first_name")
//    public String first_name;
//
//    @ColumnInfo(name = "last_name")
//    public String last_name;
//
//    // Индекс для одного поля также может быть настроен через параметр index аннотации ColumnInfo.
//    // Будет создан индекс для поля salary.
////    @ColumnInfo(index = true)
//    public int salary;
//}

//__________________________________________________________________________________________________
/**
 * Вложенные объекты
 *
 * Пусть у нас есть класс Address, с данными о адресе. Это обычный класс, не Entity.
 * И мы хотим использовать его в Entity классе Employee
 *
 * Embedded объекты могут включать в себя другие Embedded объекты.
 */
/**
 * Неправильное решение
 * Если мы сделаем так, то Room будет ругаться, т.к. он не знает, как сохранить такой объект в базу:
 * Cannot figure out how to save this field into database. You can consider adding a type converter for it.
 */
//@Entity()
//public class Employee {
//
//    @PrimaryKey(autoGenerate = true)
//    public long id;
//
//    public String name;
//
//    public int salary;
//
//    public Address address;
//}

/**
 * Правильное решение
 * Но есть простое решение - использовать аннотацию Embedded.
 * Embedded подскажет Room, что надо просто взять поля из Address и считать их полями таблицы Employee.
 * Т.е. в базе будет создана таблица Employee с полями id, name, salary, city, street, number.
 */
//@Entity()
//public class Employee {
//
//    @PrimaryKey(autoGenerate = true)
//    public long id;
//
//    public String name;
//
//    public int salary;
//
//    /**
//     *  Если у вас получается так, что совпадают имена каких-то полей в основном объекте и в Embedded
//     *  объекте, то используйте префикс для Embedded объекта.
//     *  В этом случае к именам полей Embedded объекта в таблице будет добавлен указанный префикс.
//     */
//    @Embedded(prefix = "address")
//    public Address address;
//}

//Добавление новой записи будет выглядеть так:

//Employee employee = new Employee();
//employee.id = 1;
//employee.name = "John Smith";
//employee.salary = 10000;
//Address address = new Address();
//address.city = "London";
//address.street = "Baker Street";
//address.number = 221;
//employee.address = address;
//
//db.employeeDao().insert(employee);
//Мы создаем вложенный объект Address, но Room разберется,
// и запишет все в таблицу, как плоскую структуру.

//__________________________________________________________________________________________________
/**
 *Ignore
 *
 * Аннотация Ignore позволяет подсказать Room, что это поле не должно записываться в базу или
 * читаться из нее. Нам не нужно хранить Bitmap в базе, поэтому добавляем Ignore к этому полю.
 */
//@Entity()
//public class Employee {
//
//    @PrimaryKey(autoGenerate = true)
//    public long id;
//
//    public String name;
//
//    public int salary;
//
//    @Ignore
//    public Bitmap avatar;
//}
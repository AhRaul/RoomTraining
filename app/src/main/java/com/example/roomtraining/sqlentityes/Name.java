package com.example.roomtraining.sqlentityes;

import androidx.room.ColumnInfo;

/**
 * Обратите внимание, что он не Entity. Это обычный класс. С помощью ColumnInfo мы настраиваем
 * имена полей, чтобы они совпадали с полями таблицы.
 *
 * Вы также можете в этих не Entity классах использовать вложенные классы с аннотацией @Embedded.
 * (см. урок 6)
 */
public class Name {

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;
}

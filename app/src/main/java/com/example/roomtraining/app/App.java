package com.example.roomtraining.app;

import android.app.Application;

import androidx.room.Room;

import com.example.roomtraining.sqldatabase.AppDatabase;

/**
 * Все необходимые для работы объекты созданы. Давайте посмотрим, как использовать их для работы
 * с базой данных.
 */
public class App extends Application {

    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //Database объект - это стартовая точка. Его создание выглядит так:
        //Используем Application Context, а также указываем AppDatabase класс и имя файла для базы.

        // * Учитывайте, что при вызове этого кода Room каждый раз будет создавать новый экземпляр AppDatabase.
        // * Эти экземпляры очень тяжелые и рекомендуется использовать один экземпляр для всех ваших операций.
        // * Поэтому вам необходимо позаботиться о синглтоне для этого объекта.
        // * Это можно сделать с помощью Dagger, например.
        // *
        // * Если вы не используете Dagger (или другой DI механизм), то можно использовать Application
        // * класс для создания и хранения AppDatabase.
        // * (В данном случае использован этот подход.
        // * Для этого в манифест добавлен @@ android:name=".app.App" @@).
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
        //если раскомментировать, появится возможность обрабатывать БД запросы в одном
        // UI потоке, но появятся ощутимые тормоза
                //.allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    /**
     * В коде получение базы будет выглядеть так:
     * AppDatabase db = App.getInstance().getDatabase();
     * @return
     */
    public AppDatabase getDatabase() {
        return database;
    }
}

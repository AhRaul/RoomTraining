package com.example.roomtraining;

import com.example.roomtraining.app.App;
import com.example.roomtraining.sqldao.EmployeeDao;
import com.example.roomtraining.sqldatabase.AppDatabase;
import com.example.roomtraining.sqlentityes.Employee;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * В каком случае что лучше использовать?
 * Flowable подходит, если вы запрашиваете данные и далее планируете автоматически получать их
 * обновления.
 *
 * Single и Maybe подходят для однократного получения данных. Разница между ними в том, что Single
 * логичнее использовать, если запись должна быть в базе. Если ее нет, вам придет ошибка.
 * А Maybe допускает, что записи может и не быть.
 */
public class MainPresenter {

    private AppDatabase db = App.getInstance().getDatabase();
    private final EmployeeDao employeeDao = db.employeeDao();
    private Disposable disposable = null;

    /**
     * В коде подписываемся и получаем данные
     *
     * subscribeOn в случае с Flowable не нужен. Запрос в базу будет выполнен не в UI потоке.
     * А вот, чтобы результат пришел в UI поток, используем observeOn
     *
     * Теперь при любом изменении данных в базе, мы будем получать свежие данные в методе accept.
     * И нам не надо будет каждый раз их снова запрашивать самим.
     */
    public void getAll() {
        db.employeeDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Employee>>() {
                    @Override
                    public void accept(List<Employee> employees) throws Exception {
                        //...
                    }
                });
    }

    /**
     * В коде подписываемся на Flowable
     * Итак, мы запрашиваем из базы запись по id. И тут возможны варианты.
     *
     * Если запись есть в базе, то она придет в accept сразу же после подписки. И при каждом
     * последующем обновлении этой записи в базе данных, она также будет приходить в accept.
     *
     * Если записи нет, то сразу после подписки ничего не придет. А вот если она позже появится,
     * то она придет в accept.
     *
     * У данного примера есть минус. Если записи нет в базе, то Flowable вообще ничего нам
     * не пришлет. Т.е. это будет выглядеть так, как будто он все еще выполняет запрос.
     *
     * Это можно исправить следующим образом:
     *
     * @Query("SELECT * FROM employee WHERE id = :id")
     * Flowable<List<Employee>> getById(long id);
     *
     * Хоть мы и ожидаем всего одну запись, но используем не Flowable<Employee>, а
     * Flowable<List<Employee>>. И если записи нет, то мы хотя бы получим пустой лист вместо полной
     * тишины.
     * @param id
     */
    public void getByID(long id) {
        db.employeeDao().getByID(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Employee>() {

                    @Override
                    public void accept(Employee employee) throws Exception {
                        //...
                    }
                });
    }

    /**
     * В отличие от Flowable, с Single необходимо использовать onSubscribe, чтобы задать поток для
     * выполнения запроса. Иначе в onError придет ошибка: java.lang.IllegalStateException: Cannot
     * access database on the main thread since it may potentially lock the UI for a long period of
     * time.
     *
     * Снова рассматриваем варианты наличия требуемой записи в базе.
     *
     * Если такая запись в базе есть, то она придет в onSuccess. После этого Single будет считаться
     * завершенным и при последующих обновлениях этой записи ничего приходить уже не будет.
     *
     * Если такой записи в базе нет, то мы в onError получим ошибку: android.arch.persistence.room.
     * EmptyResultSetException: Query returned empty result set: SELECT * FROM employee WHERE id = ?.
     * После этого Single будет считаться завершенным, и даже, если такая запись появится в базе,
     * то нам ничего приходить уже не будет.
     */
    public void getByIdSingle(long id) {
        db.employeeDao().getByIdSingle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Employee>() {
                    @Override
                    public void onSuccess(Employee employee) {
                        //...
                    }

                    @Override
                    public void onError(Throwable e) {
                        //...
                    }
                });
    }

    /**
     * Maybe
     * Рассмотрим тот же пример с запросом одной записи, но с использованием Maybe. Напомню, что в
     * Maybe может прийти либо один onNext, либо onComplete, либо OnError. После этого Maybe
     * считается завершенным.
     *
     * С Maybe также необходимо использовать onSubscribe, чтобы задать поток для выполнения запроса.
     *
     * Рассматриваем варианты наличия требуемой записи в базе.
     *
     * Если такая запись в базе есть, то она придет в onSuccess. После этого Maybe будет считаться
     * завершенным и при последующих обновлениях этой записи ничего приходить уже не будет.
     *
     * Если такой записи в базе нет, то мы получим onComplete. После этого Maybe будет считаться
     * завершенным, и даже, если такая запись появится в базе, то нам ничего приходить уже не будет.
     */
    public void getByIdMaybe(long id) {
        db.employeeDao().getByIdMaybe(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<Employee>() {
                    @Override
                    public void onSuccess(Employee employee) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void insertEmployee(Employee employee) {
        disposable = Completable.fromAction(() -> employeeDao.insert(employee))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        () -> {
                            System.out.println("NOTE SAVED");
                        }, Throwable::printStackTrace
                );
    }

    public void updateEmployee(Employee employee) {
        disposable = Completable.fromAction(() -> employeeDao.insert(employee))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        () -> {
                            System.out.println("NOTE UPDATED");
                        }, Throwable::printStackTrace
                );
    }

    public void deleteEmployee(Employee employee) {
        disposable = Completable.fromAction(() -> employeeDao.delete(employee))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> {
                            System.out.println("NOTE DELETED");
                        },
                        Throwable::printStackTrace
                );
    }
}

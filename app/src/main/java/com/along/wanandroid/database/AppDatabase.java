package com.along.wanandroid.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Signature.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase sInstance;

    //自定义的 database 类最好使用单例模式，创建多个无意义且消耗内存
    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "wanandroid_database").build();
                }
            }
        }
        return sInstance;
    }

    //定义抽象方法， room 会实例化该接口
    public abstract UserDao userDao();

    public abstract SignatureDao signatureDao();

}

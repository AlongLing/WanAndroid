package com.along.wanandroid.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {

    // onConflict = OnConflictStrategy.REPLACE 表示插入有冲突时，覆盖掉旧值。
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM user_table")
    User findUser();

    @Query("DELETE FROM user_table")
    void deleteUser();

}

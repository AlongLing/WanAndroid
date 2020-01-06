package com.along.wanandroid.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SignatureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSignature(Signature signature);

    @Query("SELECT * FROM signature_table")
    Signature findSignature();

    @Update
    void updateSignature(Signature signature);

}

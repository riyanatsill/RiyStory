package com.example.riystory.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.riystory.entity.PaginationEntity

@Dao
interface PaginationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PaginationEntity>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): PaginationEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKeys()
}
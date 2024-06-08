package com.example.riystory.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class PaginationEntity(

    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
package com.example.medicinechest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Checklist")
data class Checklist(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo (name = "name") val name: String
)

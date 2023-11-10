package com.example.medicinechest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
// Сущность таблицы Medicine
@Entity(tableName = "Medicine",
)
data class Medicine(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "composition") val composition: String,
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "symptoms") val symptoms: String,
    @ColumnInfo(name = "contraindications") val contraindications: String,
    @ColumnInfo(name = "storageTemperature") val storageTemperature: String,
    @ColumnInfo(name = "sideEffects") val sideEffects: String,
    @ColumnInfo(name = "instruction") val instruction: String
)
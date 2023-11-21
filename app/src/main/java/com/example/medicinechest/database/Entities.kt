package com.example.medicinechest.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

// Сущность таблицы Medicine
@Entity(tableName = "Medicine")
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

@Entity(tableName = "Lists")
data class Lists(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "display_name") val name: String
)

@Entity(tableName = "ListToMedicine")
data class ListToMedicine(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "list") val list: Int,
    @ColumnInfo(name = "medicine") val medicine: Int
)

data class ListMedicinePair(
    @Embedded
    var list: Lists,
    @Relation(
        parentColumn = "id",
        entity = Medicine::class,
        entityColumn = "id",
        associateBy = Junction(
            value = ListToMedicine::class,
            parentColumn = "list",
            entityColumn = "medicine"
        )
    )
    var medicine: List<Medicine>
)


@Entity(tableName = "User")
data class User(
    @ColumnInfo (name = "name") val name: String,
    @ColumnInfo (name = "password") val password: String,
    @PrimaryKey(autoGenerate = true) val id: Int
)
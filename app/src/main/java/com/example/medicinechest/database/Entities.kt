package com.example.medicinechest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "checklist")
data class Checklist(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "name") val name: String?
)

@Entity(tableName = "Medicine",
    foreignKeys = [
        ForeignKey(
            entity = Checklist::class,
            parentColumns = ["id"],
            childColumns = ["checklist"]
        )]
)
data class Medicine(
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "checklist") val checklist: Int?,
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "sideEffects") val sideEffects: String?,
    @ColumnInfo(name = "instruction") val instruction: String?
)
package com.example.medicinechest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Medicine",
    foreignKeys = [
        ForeignKey(
            entity = Checklist::class,
            parentColumns = ["id"],
            childColumns = ["checklist"]
        )]
)
data class Medicine(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "instruction") val instruction: String?,
    @ColumnInfo(name = "sideEffects") val sideEffects: String?,
    @ColumnInfo(name = "checklist") val checklist: Int?
)
package com.example.medicinechest

import androidx.room.ColumnInfo

data class MedicineInfoTuple(
    val id: Int,
    val name: String,
    @ColumnInfo(name = "validUntil") val result: String,
    val instruction: String?,
    val sideEffects: String?,
    val checklist: Int?
)
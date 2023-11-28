package com.example.medicinechest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

@Serializable
data class MedicineSerialized(
    val name: String,
    val symptom: String,
    val description: Description
) {
    val composition: String
        get() = description.composition
    val contraindications: String
        get() = description.contraindications
    val storageConditions: String
        get() = description.storageConditions
    val sideEffects: String
        get() = description.sideEffects
}
@Serializable
data class MedicineListWrapper(val medicines: List<MedicineSerialized>)
@Serializable
data class Description(
    val contraindications: String,
    val composition: String,
    val sideEffects: String,
    val storageConditions: String,
)
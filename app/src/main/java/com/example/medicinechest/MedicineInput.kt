package com.example.medicinechest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.medicinechest.database.Dependencies
import com.example.medicinechest.database.MainVM

class MedicineInput : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainVM = MainVM(Dependencies.medicineRepository)
        setContent {
            var name = remember { mutableStateOf(" ") }
            var composition = remember { mutableStateOf(" ") }
            var symptoms = remember { mutableStateOf(" ") }
            var instruction = remember { mutableStateOf(" ") }
            var contraindications = remember { mutableStateOf(" ") }
            var sideEffects = remember { mutableStateOf(" ") }
            var storageTemperature = remember { mutableStateOf(" ") }
            var iconClickable by remember { mutableStateOf(true) }
            Scaffold(
                topBar = {
                    TopAppBar {
                        IconButton(onClick = {
                            finish()
                        }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Spacer(Modifier.weight(1f, true))
                        if (iconClickable) {
                            IconButton(onClick = {
                                viewModel.insertMedicine(
                                    name = name.value,
                                    composition = composition.value,
                                    symptoms = symptoms.value,
                                    contraindications = contraindications.value,
                                    storageTemperature = storageTemperature.value,
                                    sideEffects = sideEffects.value,
                                    instruction = instruction.value
                                )
                                Toast.makeText(
                                    this@MedicineInput,
                                    "Лекарство было добавлено.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                iconClickable = false
                            }) {
                                Icon(
                                    Icons.Filled.Check,
                                    contentDescription = "Save"
                                )
                            }
                        }
                    }
                }

            ) {
                Column {
                    EditableTextField(value = name, "Название")
                    EditableTextField(value = composition, "Состав")
                    EditableTextField(value = symptoms, "Симптомы")
                    EditableTextField(value = instruction, "Инструкция по применению")
                    EditableTextField(value = contraindications, "Противопоказания")
                    EditableTextField(value = sideEffects, "Побочные эффекты")
                    EditableTextField(value = storageTemperature, "Условия хранения")
                }
            }
        }
    }
}

@Composable
fun EditableTextField(value: MutableState<String>, field: String) {
    OutlinedTextField(
        value = value.value,
        onValueChange = { newValue ->
            value.value = newValue
        },
        label = { Text(field) },
        modifier = Modifier.fillMaxWidth(),
    )
}
package com.example.medicinechest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medicinechest.database.Dependencies
import com.example.medicinechest.database.MainVM
import com.example.medicinechest.database.Medicine

class MedicineInput : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var name: String = ""
        var composition: String = ""
        var symptoms: String = ""
        var instruction: String = ""
        var contraindications: String = ""
        var sideEffects: String = ""
        var storageConditions: String = ""
        val viewModel: MainVM = MainVM(Dependencies.medicineRepository)
        setContent {
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
                        IconButton(onClick = {
                            //val medicine = Medicine(name = name, composition = composition, symptoms = symptoms, contraindications = contraindications, storageTemperature = storageConditions, sideEffects = sideEffects, instruction = instruction)
                        }) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = "Save"
                            )
                        }
                    }
                }

            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Введите информацию о медицинском препарате")

                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { androidx.compose.material.Text("Название") },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    TextField(
                        value = composition,
                        onValueChange = { composition = it },
                        label = { androidx.compose.material.Text("Состав") },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    TextField(
                        value = symptoms,
                        onValueChange = { symptoms = it },
                        label = { androidx.compose.material.Text("Симптомы") },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                    TextField(
                        value = instruction,
                        onValueChange = { instruction = it },
                        label = { androidx.compose.material.Text("Применение") },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                    TextField(
                        value = contraindications,
                        onValueChange = { contraindications = it },
                        label = { androidx.compose.material.Text("Противопоказания") },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                    TextField(
                        value = sideEffects,
                        onValueChange = { sideEffects = it },
                        label = { androidx.compose.material.Text("Побочные эффекты") },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                    TextField(
                        value = storageConditions,
                        onValueChange = { storageConditions = it },
                        label = { androidx.compose.material.Text("Условия хранения") },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                }
            }
        }
    }
}
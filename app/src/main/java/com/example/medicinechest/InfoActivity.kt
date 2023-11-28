package com.example.medicinechest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicinechest.database.Dependencies
import com.example.medicinechest.database.Description
import com.example.medicinechest.database.InfoViewModel
import com.example.medicinechest.database.MainVM
import com.example.medicinechest.database.Medicine
import com.example.medicinechest.database.MedicineSerialized

class InfoActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val viewModel = InfoViewModel(Dependencies.medicineRepository)
        val viewModel = MainVM(Dependencies.medicineRepository, Dependencies.httpRepository)
        setContent {
            val arguments = this.intent.extras
            val name = arguments?.getString("name")
            //var medicine by remember { mutableStateOf(Medicine("Loading...","Loading...",0,"Loading...","Loading...","Loading...","Loading...","Loading..." ))}
            /*
            if (id != null) {
                viewModel.getById(id)
            }
            viewModel.medicine.observe(this) {
                medicine = it
            }
*/
            var medicine by remember {
                mutableStateOf(MedicineSerialized("работай", "string", Description("string", "string", "string", "string")))
            }
            viewModel.apcthi.observe(this){
                medicine = it
            }
            if (name != null) {
                viewModel.getMedicineByName(name)
            }


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
                    }
                }

            ) {
                Column {
                    ListItem("Название лекарства", medicine.name)
                    ListItem("Состав", medicine.description.composition)
                    ListItem("Симптомы", medicine.symptom)
                    ListItem("Инструкция по применению", "Нету. Не добавили :<")
                    ListItem("Противопоказания", medicine.description.contraindications)
                    ListItem("Побочные эффекты", medicine.description.sideEffects)
                    ListItem("Условия хранения", medicine.description.storageConditions)
                }
            }
        }
    }
}

@Composable
fun Chapter(heading: String, description: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,//разметка
        verticalArrangement = Arrangement.Center) {
        Text(
            text = heading,
            color = Color.Gray,
            fontSize = 18.sp
        )
        Text(
            text = description,
            fontSize = 21.sp
        )
    }
}

@Composable
private fun ListItem(name: String, desc: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 40.dp)
            .padding(3.dp),
        border = BorderStroke(0.5.dp, Color.LightGray),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Box {
            Chapter(heading = name, description = desc)
        }
    }
}
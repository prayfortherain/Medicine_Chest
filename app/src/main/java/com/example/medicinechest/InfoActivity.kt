package com.example.medicinechest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import com.example.medicinechest.database.InfoViewModel
import com.example.medicinechest.database.Medicine

class InfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: InfoViewModel = InfoViewModel(Dependencies.medicineRepository)
        setContent {
            val arguments = this.intent.extras
            val id = arguments?.getInt("id")
            var medicine by remember {
                mutableStateOf(Medicine("Loading...", 0, 0, "Loading...", "Loading..."))
            }
            if (id != null) {
                viewModel.getById(id)
            }
            viewModel.medicine.observe(this) {
                medicine = it
            }
            Column {
                ListItem("Название лекарства", medicine.name!!)
                ListItem("Инструкция по применению:", medicine.instruction!!)
                ListItem("Побочные эффекты", medicine.sideEffects!!)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        finish()
                    }) {
                        Text(text = "вернуться")
                    }

                }
            }
        }
    }
}

@Composable
fun Chapter(heading: String, description: String) {
    Text(
        text = heading,
        color = Color.Gray,
        fontSize = 20.sp
    )
    Text(
        text = description,
        fontSize = 23.sp
    )
}


@Composable
private fun ListItem(name: String, desc: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 40.dp)
            .padding(3.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Box {
            Column(verticalArrangement = Arrangement.Center) {
                Chapter(heading = name, description = desc)
            }
        }
    }
}
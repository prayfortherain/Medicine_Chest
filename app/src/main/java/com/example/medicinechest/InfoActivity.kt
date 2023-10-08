package com.example.medicinechest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

class InfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val arguments = intent.extras
            val name = arguments?.getString("name")
            val date = arguments?.getString("date")
            Column {
                Text(text = "Название лекарства",
                    color = Color.Gray,
                    fontSize = 20.sp)
                Text(text = name!!,
                    fontSize = 23.sp)

                Text(text = "Годен до: ",
                    color = Color.Gray,
                    fontSize = 20.sp)
                Text(text = date!!,
                    fontSize = 23.sp)

                Text(text = "Инструкция по применению: ",
                    color = Color.Gray,
                    fontSize = 20.sp)
                Text(text = "Гениальная инфа для применения препарата, а следующий раздел это побочки",
                    fontSize = 23.sp)
            }
        }
    }
}


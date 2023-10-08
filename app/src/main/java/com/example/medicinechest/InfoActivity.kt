package com.example.medicinechest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                chapter(heading = "Название лекарства", description = name!!)
                chapter(heading = "Годен до:", description = date!!)
                chapter(
                    heading = "Инструкция по применению:",
                    description = "Гениальная инфа для применения препарата, а следующий раздел это побочки"
                )
                chapter(
                    heading = "Побочные эффекты",
                    description = "Как и просили побочки.. У всего есть побочки, побочки, побочки. В концовке будут три точки, три точки. УУУУУ ВСЕГО ЕСТЬ ПОБОЧКИ"
                )
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
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "вернуться")
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "обновить")
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "удалить")
                    }
                }
            }
        }
    }
}

@Composable
fun chapter(heading : String, description : String){
    Text(text = heading,
        color = Color.Gray,
        fontSize = 20.sp)
    Text(text = description,
        fontSize = 23.sp)
}

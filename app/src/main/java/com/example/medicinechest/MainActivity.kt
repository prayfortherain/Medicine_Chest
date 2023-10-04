package com.example.medicinechest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medicinechest.ui.theme.MedicineChestTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            var topBarTitle = remember {
                mutableStateOf("Полный список")
            }
            MedicineChestTheme {
                Row {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            MainTopBar(
                                title = topBarTitle.value,
                                scaffoldState
                            )
                        },
                        drawerContent = {
                            DrawerMenu { event ->
                                when (event) {
                                    is DrawerEvents.OnItemClick -> {
                                        topBarTitle.value = event.title
                                    }
                                }
                                coroutineScope.launch {
                                    scaffoldState.drawerState.close()
                                }
                            }
                        }
                    ) {
                        it.calculateBottomPadding()
                    }
                    /*Row{
                        ListItem(name = "Default", valueUntil = "23/10/05")
                        ListItem(name = "Afobazol", valueUntil = "25/12/23") //не работает
                    }*/
                }
            }
        }
    }
}

@Composable
private fun ListItem(name : String, valueUntil : String){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
    shape = RoundedCornerShape(15.dp),
    elevation = 5.dp){
        Box{
            Row(verticalAlignment = Alignment.CenterVertically){
                Column {
                    Text(text = name)
                    Text(text = valueUntil)
                }
            }
        }
    }
}

package com.example.medicinechest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
                    LazyColumn(modifier = Modifier.fillMaxSize()){
                        itemsIndexed(listOf<String>("Афобазол", "АЦЦ", "Глицин", "Отривин")
                        ){ index, item ->
                            ListItem(name = item)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun ListItem(name : String, valueUntil : String = "23/10/44"){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
    shape = RoundedCornerShape(15.dp),
    elevation = 5.dp){
        Box{
            Row(verticalAlignment = Alignment.CenterVertically
            ){
                Column( modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = name)
                    Text(text = valueUntil)
                }
            }
        }
    }
}

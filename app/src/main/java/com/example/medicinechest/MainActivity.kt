package com.example.medicinechest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val medicine = listOf("Афобазол", "АЦЦ", "Глицин", "Отривин")
        setContent {
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            var topBarTitle = remember {
                mutableStateOf("Полный список")
            }
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
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ){
                    Button(onClick = { /*TODO*/ },
                        shape = CircleShape
                        ) {
                        Text(text = "+")
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(
                        medicine
                    ) { _, item ->
                        ListItem(item)

                    }
                }
            }
        }
    }
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun ListItem(name: String, validUntil: String = "23/10/44") {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp,
            onClick = {
                val intent = Intent(this, InfoActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("date", validUntil)
                startActivity(intent)
            }
        ) {
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = name)
                        Text(text = validUntil)
                    }
                }
            }
        }
    }
}
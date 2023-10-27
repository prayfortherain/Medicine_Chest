package com.example.medicinechest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.room.*
import androidx.room.Room.databaseBuilder
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var appDatabase : AppDatabase = AppDatabase.getDatabase(context = applicationContext)
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
                Column(modifier = Modifier.fillMaxSize()){
                coroutineScope.launch{
                    appDatabase.medicineDao()?.getAll()
                }
                }
            }
                /*LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed( med
                    ) { index, item ->
                        ListItem(item!!.name!!, index, context = this@MainActivity, dao = medDatabase!!.medicineDao()!!)
                    }
                }*/
            }
        }
    }
//}
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun ListItem(name: String, index: Int) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp,
            onClick = {
                //val intent = Intent(context, InfoActivity::class.java)
                //intent.putExtra("name", name)
                //intent.putExtra("date", validUntil)
                //context.startActivity(intent)
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
                        //dao.getById(index)?.let { Text(text = it.name!!) }
                        //Text(text = validUntil)
                    }
                }
            }
        }
    }
package com.example.medicinechest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.*
import com.example.medicinechest.database.Dependencies
import com.example.medicinechest.database.MainVM
import com.example.medicinechest.database.Medicine
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dependencies.init(applicationContext)
        val viewModel: MainVM = MainVM(Dependencies.medicineRepository)
        setContent {
            var openDialog by remember { mutableStateOf(false) }
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            var topBarTitle = remember { mutableStateOf("Список лекарств") }
            var checklists by remember { mutableStateOf(listOf("Loading...")) }


            var deleteID by remember {
                mutableStateOf(0)
            }
            viewModel.getLists()
            viewModel.temp_list1.observe(this) {
                checklists = it
            }

            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar {
                        MainTopBar(
                            title = topBarTitle.value,
                            scaffoldState
                        )
                    }
                },
                drawerContent = {
                    DrawerMenu(checklists, onEvent = { event ->
                        when (event) {
                            is DrawerEvents.OnItemClick -> {
                                topBarTitle.value = event.title
                            }
                        }
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }, context = this@MainActivity)
                },
                floatingActionButton = {
                    FloatingActionButton(
                        content = { Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                        onClick = {
                            val intent = Intent(this@MainActivity, MedicineInput::class.java)
                            startActivity(intent)
                            viewModel.getLists()
                        }
                    )
                },
                floatingActionButtonPosition = FabPosition.End,
                isFloatingActionButtonDocked = true,
            ) { it ->
                it.calculateBottomPadding()
                viewModel.getMedicinesList(
                    intent.getStringExtra("listName") ?: "Аллергия"
                )
                var list by remember {
                    mutableStateOf(
                        listOf(
                            Medicine(
                                "Loading...",
                                "Loading...",
                                1,
                                "Loading...",
                                "Loading...",
                                "Loading...",
                                "Loading...",
                                "Loading..."
                            )
                        )
                    )
                }
                viewModel.medslist.observe(this) {
                    list = it
                }
                if(openDialog){
                    AlertDialog(onDismissRequest = {
                        openDialog = false
                    },
                        title = { Text(text = "Вы действительно хотите удалить?") },
                        buttons = {
                            Button(modifier = Modifier.fillMaxWidth(),
                                onClick = { openDialog = false }
                            ) {
                                Text("Отмена", fontSize = 12.sp)
                            }
                            Button(modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Лекарство было удалено.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    viewModel.deleteMedicine(deleteID)
                                    openDialog = false

                                }
                            ) {
                                Text("Удалить", fontSize = 12.sp)
                            }
                        })
                }
                LazyColumn {
                    items(list) {med ->
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 50.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        openDialog = true
                                        deleteID = med.id
                                    }
                                )
                            }
                            .padding(3.dp),
                            shape = RoundedCornerShape(10.dp),
                            elevation = 5.dp,
                            onClick = {
                                val intent =
                                    Intent(this@MainActivity, InfoActivity::class.java)
                                intent.putExtra("id", med.id)
                                startActivity(intent)
                            }
                        ) {
                            Box(contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth(),

                                ) {
                                Text(text = "${med.name}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerMenu(list: List<String>, onEvent: (DrawerEvents) -> Unit, context: Context) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Header()
            Body(list, onEvent, context = context)
        }
    }
}

@Composable
fun Header() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Домашняя аптечка",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Body(list: List<String>, onEvent: (DrawerEvents) -> Unit, context: Context) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(list) { index, title ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                onClick = {
                    var intent = Intent(context, MainActivity::class.java)
                    intent.putExtra("listName", title)
                    context.startActivity(intent)
                }
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .wrapContentWidth(),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MainTopBar(title: String, scaffoldState: ScaffoldState) {
    val coroutine = rememberCoroutineScope()
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = {
                coroutine.launch {
                    scaffoldState.drawerState.open()
                }
            })
            { Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu") }
        }
    )
}

sealed class DrawerEvents {
    data class OnItemClick(val title: String, val index: Int) : DrawerEvents()
}
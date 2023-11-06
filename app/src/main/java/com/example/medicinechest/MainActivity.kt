package com.example.medicinechest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dependencies.init(applicationContext)
        val viewModel: MainVM = MainVM(Dependencies.medicineRepository)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            var topBarTitle = remember { mutableStateOf("Полный список") }
            val openDialog = remember { mutableStateOf(false) }
            var checklists by remember { mutableStateOf(listOf("Loading...")) }
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
                        Spacer(Modifier.weight(1f, true))
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Поиск"
                            )
                        }
                    }
                },
                drawerContent = {
                    DrawerMenu(checklists) { event ->
                        when (event) {
                            is DrawerEvents.OnItemClick -> {
                                topBarTitle.value = event.title
                            }
                        }
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                },
                bottomBar = {
                    BottomAppBar{
                        Spacer(Modifier.weight(1f, true))
                        IconButton(onClick = {  }) { Icon(Icons.Filled.Search, contentDescription = "Поиск")}
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        content = { Icon(Icons.Filled.Add, contentDescription = "Добавить")  },
                        onClick = { openDialog.value = true }
                    )
                },
                floatingActionButtonPosition = FabPosition.Center,
                isFloatingActionButtonDocked = true,
                ) { it ->
                it.calculateBottomPadding()
                viewModel.getMedicinesList()
                var list by remember {
                    mutableStateOf(listOf(Medicine("Loading...", 0, 0, "Loading...", "Loading...")))
                }
                viewModel.medslist.observe(this) {
                    list = it
                }
                LazyColumn {
                    items(list) { index ->
                        ListItem(meds = index, this@MainActivity)
                    }
                }
                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = {
                            openDialog.value = false
                        },
                        title = { Text(text = "Что вы хотите добавить?") },
                        buttons = {
                            Button(modifier = Modifier.fillMaxWidth(),
                                onClick = { openDialog.value = false }
                            ) {
                                Text("Новый список", fontSize = 12.sp)
                            }
                            Button(modifier = Modifier.fillMaxWidth(),
                                onClick = { openDialog.value = false }
                            ) {
                                Text("Новое лекарство", fontSize = 12.sp)
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ListItem(meds: Medicine, context: Activity) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = 50.dp)
        .padding(3.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        onClick = {
            val intent = Intent(context, InfoActivity::class.java)
            intent.putExtra("id", meds.id)
            context.startActivity(intent)
        }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = meds.name!!)
        }
    }
}

@Composable
fun DrawerMenu(list: List<String>, onEvent: (DrawerEvents) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Header()
            Body(list) { event ->
                onEvent(event)
            }
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

@Composable
fun Body(list: List<String>, onEvent: (DrawerEvents) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(list) { index, title ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(DrawerEvents.OnItemClick(title, index))
                        }
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
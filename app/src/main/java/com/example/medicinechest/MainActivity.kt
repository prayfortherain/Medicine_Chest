package com.example.medicinechest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.room.*
import com.example.medicinechest.database.Dependencies
import com.example.medicinechest.database.MainVM
import com.example.medicinechest.database.Medicine
import com.example.medicinechest.database.MedicineSerialized
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dependencies.init(applicationContext)
        val viewModel = MainVM(Dependencies.medicineRepository, Dependencies.httpRepository)

        setContent {

            //val trypost = format("с др", "с др", "с", "днем", "рождения", "ура")
            //viewModel.postQuery(trypost)

            var text by remember {
                mutableStateOf(emptyList<MedicineSerialized>())
            }
            viewModel.something.observe(this){
                text = it
            }
            viewModel.getSomething()

            Scaffold (
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
            ){
            LazyColumn {
                items(text) { med ->
                    if(med.name != "Артём лох"){
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 50.dp)
                            .padding(3.dp),
                            shape = RoundedCornerShape(10.dp),
                            elevation = 5.dp,
                            onClick = {
                                val intent = Intent(this@MainActivity, InfoActivity::class.java)
                                intent.putExtra("med", med.name)
                                startActivity(intent)
                            }
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth(),

                                ) {
                                Text(text = med.name)
                            }
                        }
                    }
                }
            }
            }
            /*
            if(isTablet())
                TabletScreen(context = this, viewModel = viewModel)
            else
                PhoneScreen(context = this, viewModel = viewModel)
            */
        }
    }
}

fun format(name : String, symptom : String, composition : String, contraindications : String, storageConditions : String, sideEffects : String): String {
    return """{"name": "$name", "symptom": "$symptom", "description": {"contraindications": "$contraindications", "composition": "$composition", "sideEffects": "$sideEffects", "storageConditions": "$storageConditions"}}""".trimIndent()
}
@Composable
fun isTablet(): Boolean{
    val configuration = LocalConfiguration.current
    return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
        configuration.screenWidthDp > 840
    }
    else{
        configuration.screenWidthDp > 600
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhoneScreen(context: MainActivity, viewModel: MainVM){

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var topBarTitle = remember { mutableStateOf("Список лекарств") }
    var checklists by remember { mutableStateOf(listOf("Loading...")) }
    viewModel.getLists()
    viewModel.temp_list1.observe(context){
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
            }, context = context
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                content = { Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                onClick = {
                    val intent = Intent(context, MedicineInput::class.java)
                    context.startActivity(intent)
                    viewModel.getLists()
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
    ) {
        it.calculateBottomPadding()
        viewModel.getMedicinesList( context.intent.getStringExtra("listName") ?: "Аллергия" )
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
        viewModel.medslist.observe(context) { list = it }
        LazyColumn {
            items(list) { med ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 50.dp)
                    .padding(3.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 5.dp,
                    onClick = {
                        val intent = Intent(context, InfoActivity::class.java)
                        intent.putExtra("id", med.id)
                        context.startActivity(intent)
                    }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth(),

                        ) {
                        Text(text = med.name)
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TabletScreen(context: MainActivity, viewModel: MainVM) {
    var checklists by remember { mutableStateOf(listOf("Loading...")) }
    viewModel.getLists()
    viewModel.temp_list1.observe(context){
        checklists = it
    }
    viewModel.getMedicinesList( context.intent.getStringExtra("listName") ?: "Аллергия" )
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
    viewModel.medslist.observe(context) { list = it }
    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxHeight()) {
            LazyColumn(modifier = Modifier.fillMaxWidth(0.3f)) {
                items(checklists) { title ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        onClick = {
                            viewModel.getMedicinesList(title)
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
        Column(modifier = Modifier.fillMaxHeight()) {
            LazyColumn {
                items(list) { med ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 50.dp)
                        .padding(3.dp),
                        shape = RoundedCornerShape(10.dp),
                        elevation = 5.dp,
                        onClick = {
                            val intent = Intent(context, InfoActivity::class.java)
                            intent.putExtra("id", med.id)
                            context.startActivity(intent)
                        }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(text = med.name)
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
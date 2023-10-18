package com.example.medicinechest

import android.annotation.SuppressLint
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
import androidx.room.*
import androidx.room.Room.databaseBuilder
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    var medDatabase: AppDatabase? = null
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var med: List<Medicine?> = listOf(null)
        medDatabase = databaseBuilder(applicationContext, AppDatabase::class.java, "test").createFromAsset("test.db").fallbackToDestructiveMigration().build()
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
                coroutineScope.launch {
                    med = getList() }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed( med
                    ) { index, item ->
                        ListItem(item!!.name, index, context = this@MainActivity, dao = medDatabase!!.medicineDao()!!)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(this@MainActivity, AddActivity::class.java)
                            startActivity(intent)
                        },
                        shape = CircleShape
                    ) {
                        Text(text = "+")
                    }
                }
            }
        }
    }


    suspend fun getList(): List<Medicine?> {
        return medDatabase!!.medicineDao()?.getAll()!!
    }

    }
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun ListItem(name: String, index: Int, validUntil: String = "23/10/44", context: MainActivity, dao: MedicineDao) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp,
            onClick = {
                val intent = Intent(context, InfoActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("date", validUntil)
                context.startActivity(intent)
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
                        dao.getById(index)?.let { Text(text = it.name) }
                        Text(text = validUntil)
                    }
                }
            }
        }
    }

@Entity(tableName = "Medicine")
data class Medicine(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "instruction") val instruction: String,
    @ColumnInfo(name = "sideEffects") val sideEffects: String
)

@Dao
interface MedicineDao {
    @Query("SELECT * FROM Medicine") // вот этот запрос фактически выполняется
    suspend fun getAll(): List<Medicine?> // список лекарств получаем

    @Query("SELECT * FROM Medicine WHERE id = :id")
    fun getById(id: Int): Medicine // берем лекарство по ID

}

@Database(entities = [Medicine::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao?
}

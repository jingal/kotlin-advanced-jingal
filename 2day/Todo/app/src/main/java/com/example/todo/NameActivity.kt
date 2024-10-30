package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Entity
@Entity(tableName = "name_table")
data class Name(
    @PrimaryKey val id: Int = 0,
    val name: String
)

// Dao
@Dao
interface NameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(name: Name)

    @Query("SELECT * FROM name_table LIMIT 1")
    suspend fun getName(): Name?
}

// Room Database
@Database(entities = [Name::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nameDao(): NameDao
}

data class NameState(
    val name: String = ""
)

// ViewModel
class NameViewModel(private val nameDao: NameDao) : ViewModel() {
    private val _state = MutableStateFlow(NameState())
    val state: StateFlow<NameState> = _state

    init {
        viewModelScope.launch {
            val name = nameDao.getName()
            _state.value = NameState(name = name?.name ?: "")
        }
    }

    fun saveName(newName: String) {
        viewModelScope.launch {
            val name = Name(name = newName)
            nameDao.insert(name)
            _state.value = NameState(name = newName)
        }
    }
}

// ViewModelFactory
class NameViewModelFactory(private val nameDao: NameDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NameViewModel(nameDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class NameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "name_database"
        ).build()
        val nameDao = database.nameDao()
        setContent {
            NameScreen(nameDao = nameDao)
        }
    }
}

@Composable
fun NameScreen(nameDao: NameDao) {
    val viewModel: NameViewModel = viewModel(factory = NameViewModelFactory(nameDao))
    val state by viewModel.state.collectAsState()
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("이름을 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (inputText.isNotBlank()) {
                    viewModel.saveName(inputText)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("저장")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("저장된 이름: ${state.name}")
    }
}

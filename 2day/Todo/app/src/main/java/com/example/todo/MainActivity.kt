package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class Todo(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)

class TodoViewModel : ViewModel() {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos
    private var nextId = 0

    fun addTodo(title: String) {
        val newTodo = Todo(
            id = nextId++,
            title = title
        )
        _todos.update { it + newTodo }
    }

    fun toggleTodoCompletion(item: Todo) {
        _todos.update { todos ->
            todos.map { todo ->
                if (todo.id == item.id) todo.copy(isCompleted = !todo.isCompleted) else todo
            }
        }
    }

    fun deleteTodo(item: Todo) {
        _todos.update { it.filter { todo -> todo.id != item.id } }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoScreen()
        }
    }
}

@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    val todos by viewModel.todos.collectAsState()
    var newTodoText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = newTodoText,
            onValueChange = { newTodoText = it },
            label = { Text("할 일을 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (newTodoText.isNotBlank()) {
                    viewModel.addTodo(newTodoText)
                    newTodoText = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("추가")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(todos) { todo ->
                TodoItemRow(
                    item = todo,
                    onToggleItem = { viewModel.toggleTodoCompletion(it) },
                    onDeleteItem = { viewModel.deleteTodo(it) }
                )
            }
        }
    }
}

@Composable
fun TodoItemRow(
    item: Todo,
    onToggleItem: (Todo) -> Unit,
    onDeleteItem: (Todo) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isCompleted,
            onCheckedChange = { onToggleItem(item) }
        )
        Text(
            text = item.title,
            modifier = Modifier.weight(1f),
            textDecoration = if (item.isCompleted) TextDecoration.LineThrough
            else TextDecoration.None
        )
        IconButton(onClick = { onDeleteItem(item) }) {
            Icon(Icons.Default.Delete, contentDescription = "삭제")
        }
    }
}

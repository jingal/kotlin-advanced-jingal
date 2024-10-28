package com.example.todo

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TodoViewModelUnitTest {

    private lateinit var viewModel: TodoViewModel

    @Before
    fun setup() {
        viewModel = TodoViewModel()
    }

    @Test
    fun addTodoWithNewTask() {
        // Given
        val newTask = "New Task"
        val expected = 1

        // When
        viewModel.addTodo(newTask)

        // Then
        assertEquals(newTask, viewModel.todos.value[0].title)
        assertEquals(expected, viewModel.todos.value.size)
    }

    @Test
    fun completeAllTodos() {
        // Given
        viewModel.addTodo("Task 1")
        viewModel.addTodo("Task 2")

        // When
        viewModel.todos.value.forEach { viewModel.toggleTodoCompletion(it) }

        // Then
        viewModel.todos.value.forEach {
            assertEquals(true, it.isCompleted)
        }
    }
}

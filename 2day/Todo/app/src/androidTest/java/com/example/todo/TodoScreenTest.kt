package com.example.todo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addTodo_showsNewTodoInList() {
        composeTestRule.setContent {
            TodoScreen()
        }

        // Given
        val newTask = "New Task"

        // When: 새로운 할 일 입력 후 "추가" 버튼 클릭
        composeTestRule.onNodeWithText("할 일을 입력하세요").performTextInput(newTask)
        composeTestRule.onNodeWithText("추가").performClick()

        // Then: 새로운 할 일이 리스트에 표시되는지 확인
        composeTestRule.onNodeWithText(newTask).assertIsDisplayed()
    }

    @Test
    fun deleteTodo_removesTodoFromList() {
        composeTestRule.setContent {
            TodoScreen()
        }

        // Given
        val taskToDelete = "Task to Delete"
        composeTestRule.onNodeWithText("할 일을 입력하세요").performTextInput(taskToDelete)
        composeTestRule.onNodeWithText("추가").performClick()

        // When: 삭제 버튼 클릭
        composeTestRule.onNodeWithContentDescription("삭제").performClick()

        // Then: 삭제된 할 일이 더 이상 표시되지 않는지 확인
        composeTestRule.onNodeWithText(taskToDelete).assertDoesNotExist()
    }
}

package com.example.week1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week1.domain.MockData
import com.example.week1.domain.Task
import java.time.LocalDate

class TaskViewModel : ViewModel() {

    // Required: MutableState<List<Task>>
    var tasks by mutableStateOf(listOf<Task>())
        private set

    // Keep a "base list" so filter can be toggled without losing data.
    private var allTasks: List<Task> = emptyList()

    init {
        allTasks = MockData.tasks
        tasks = allTasks
    }

    fun addTask(task: Task) {
        allTasks = allTasks + task
        tasks = allTasks
    }

    fun toggleDone(id: Int) {
        allTasks = allTasks.map { t -> if (t.id == id) t.copy(done = !t.done) else t }
        tasks = allTasks
    }

    fun removeTask(id: Int) {
        allTasks = allTasks.filterNot { it.id == id }
        tasks = allTasks
    }

    fun filterByDone(done: Boolean) {
        tasks = allTasks.filter { it.done == done }
    }

    fun sortByDueDate() {
        tasks = tasks.sortedBy { it.dueDate }
        // Optional: if you want sort to affect the base list too:
        allTasks = allTasks.sortedBy { it.dueDate }
    }

    // Helper for UI: create a simple new task quickly
    fun addTaskFromTitle(title: String) {
        val trimmed = title.trim()
        if (trimmed.isEmpty()) return

        val newId = (allTasks.maxOfOrNull { it.id } ?: 0) + 1
        val newTask = Task(
            id = newId,
            title = trimmed,
            description = "Created from UI",
            priority = 3,
            dueDate = LocalDate.now().plusDays(7),
            done = false
        )
        addTask(newTask)
    }
}

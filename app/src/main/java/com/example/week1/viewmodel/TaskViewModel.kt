package com.example.week1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.week1.model.MockData
import com.example.week1.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.update
import java.time.LocalDate

enum class SortDirection { ASC, DESC }

class TaskViewModel : ViewModel() {


    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())


    private val _doneFilter = MutableStateFlow<Boolean?>(null) // null = no filter
    private val _sortDirection = MutableStateFlow(SortDirection.ASC)


    val tasks: StateFlow<List<Task>> =
        combine(_allTasks, _doneFilter, _sortDirection) { all, doneFilter, dir ->
            var list = all


            if (doneFilter != null) {
                list = list.filter { it.done == doneFilter }

            }

            // Sort
            list = when (dir) {
                SortDirection.ASC -> list.sortedBy { it.dueDate }
                SortDirection.DESC -> list.sortedByDescending { it.dueDate }

            }

            list
        }.stateIn(
            scope = kotlinx.coroutines.GlobalScope, // simple for course; see note below
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    init {
        _allTasks.value = MockData.tasks
    }

    // Lisää
    fun addTask(task: Task) {
        _allTasks.update { it + task }
    }

    fun addTaskFromTitle(title: String) {
        val trimmed = title.trim()
        if (trimmed.isEmpty()) return

        val newId = (_allTasks.value.maxOfOrNull { it.id } ?: 0) + 1
        addTask(
            Task(
                id = newId,
                title = trimmed,
                description = "Created from UI",
                priority = 3,
                dueDate = LocalDate.now().plusDays(7),
                done = false
            )
        )
    }

    fun toggleDone(id: Int) {
        _allTasks.update { list ->
            list.map { t -> if (t.id == id) t.copy(done = !t.done) else t }
        }
    }

    fun removeTask(id: Int) {
        _allTasks.update { list -> list.filterNot { it.id == id } }
    }

    fun updateTask(updated: Task) {
        _allTasks.update { list ->
            list.map { t -> if (t.id == updated.id) updated else t }
        }
    }


    fun filterByDone(done: Boolean) {
        _doneFilter.value = done
    }

    fun clearFilters() {
        _doneFilter.value = null
    }

    fun sortByDueDate() {

        _sortDirection.value =
            if (_sortDirection.value == SortDirection.ASC) SortDirection.DESC else SortDirection.ASC
    }

    fun currentSortDirection(): SortDirection = _sortDirection.value

    fun addTaskFromDialog(title: String, description: String, dueDate: LocalDate) {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isEmpty()) return

        val newId = (_allTasks.value.maxOfOrNull { it.id } ?: 0) + 1
        addTask(
            Task(
                id = newId,
                title = trimmedTitle,
                description = description.trim(),
                priority = 3,
                dueDate = dueDate,
                done = false
            )
        )
    }

}

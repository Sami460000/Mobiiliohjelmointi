package com.example.week1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week1.domain.Task
import com.example.week1.viewmodel.TaskViewModel

@Composable
fun HomeScreen(vm: TaskViewModel = viewModel()) {

    var newTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Tasks")

        Spacer(Modifier.height(12.dp))

        // Add new task UI: TextField + Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                modifier = Modifier.weight(1f),
                label = { Text("New task title") },
                singleLine = true
            )
            Button(onClick = {
                vm.addTaskFromTitle(newTitle)
                newTitle = ""
            }) {
                Text("Add")
            }
        }

        Spacer(Modifier.height(12.dp))

        // Controls: filter + sort
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { vm.filterByDone(true) }) { Text("Show done") }
            Button(onClick = { vm.filterByDone(false) }) { Text("Show not done") }
            Button(onClick = { vm.sortByDueDate() }) { Text("Sort by due") }
        }

        Spacer(Modifier.height(12.dp))

        // List with LazyColumn
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(vm.tasks, key = { it.id }) { task ->
                TaskRow(
                    task = task,
                    onToggle = { vm.toggleDone(task.id) },
                    onDelete = { vm.removeTask(task.id) }
                )
            }
        }
    }
}

@Composable
private fun TaskRow(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.done,
            onCheckedChange = { onToggle() }
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = task.title,
            modifier = Modifier.weight(1f)
        )

        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}
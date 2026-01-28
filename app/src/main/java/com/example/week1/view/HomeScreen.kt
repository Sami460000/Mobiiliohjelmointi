package com.example.week1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week1.model.Task
import com.example.week1.viewmodel.TaskViewModel
import androidx.compose.runtime.collectAsState


@Composable
fun HomeScreen(vm: TaskViewModel = viewModel()) {


    val tasks by vm.tasks.collectAsState()

    var newTitle by remember { mutableStateOf("") }


    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Tasks", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(12.dp))

        // ui
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

        // kontrollit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { vm.clearFilters() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )) {
                Text("Show all")
            }

            Button(onClick = { vm.filterByDone(true) }) {
                Text("Done")
            }

            Button(onClick = { vm.filterByDone(false) }) {
                Text("Not done")
            }

            Button(onClick = { vm.sortByDueDate() }) {
                Text(
                    if (vm.currentSortDirection() ==
                        com.example.week1.viewmodel.SortDirection.ASC
                    )
                        "Sort due ↑"
                    else
                        "Sort due ↓"
                )
            }
        }


        // Lista
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskRow(
                    task = task,
                    onToggle = { vm.toggleDone(task.id) },
                    onDelete = { vm.removeTask(task.id) },
                    onOpenDetails = { selectedTask = task }
                )
            }
        }
    }


    if (selectedTask != null) {
        DetailDialog(
            task = selectedTask!!,
            onDismiss = { selectedTask = null },
            onSave = { updated ->
                vm.updateTask(updated)
                selectedTask = null
            },
            onDelete = { id ->
                vm.removeTask(id)
                selectedTask = null
            }
        )
    }
}

@Composable
private fun TaskRow(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onOpenDetails: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenDetails() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
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

            TextButton(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}


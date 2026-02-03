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
import com.example.week1.model.Task
import com.example.week1.viewmodel.TaskViewModel
import java.time.LocalDate
import androidx.compose.material3.ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    vm: TaskViewModel,
    onBackToHome: () -> Unit,
    onOpenThird: () -> Unit
) {
    val tasks by vm.tasks.collectAsState()

    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var dialogMode by remember { mutableStateOf<DialogMode?>(null) }

    // group by dueDate
    val grouped: Map<LocalDate, List<Task>> = remember(tasks) {
        tasks.groupBy { it.dueDate }
    }

    val datesSorted = remember(grouped) { grouped.keys.sorted() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") },
                actions = {
                    TextButton(onClick = onBackToHome) { Text("Tasks") }
                    TextButton(onClick = { dialogMode = DialogMode.ADD }) { Text("+") }
                    TextButton(onClick = { onOpenThird() }) { Text("Third") }

                }
            )
        }
    ) { padding ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { vm.clearFilters() }) { Text("All") }
            Button(onClick = { vm.filterByDone(true) }) { Text("Done") }
            Button(onClick = { vm.filterByDone(false) }) { Text("Not done") }

            Button(onClick = { vm.sortByDueDate() }) {
                Text(
                    if (vm.currentSortDirection() == com.example.week1.viewmodel.SortDirection.ASC)
                        "Due ↑"
                    else
                        "Due ↓"
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            datesSorted.forEach { date ->
                item {
                    Text(
                        text = date.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(6.dp))
                }

                items(grouped[date].orEmpty(), key = { it.id }) { task ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedTask = task
                                dialogMode = DialogMode.EDIT
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { vm.toggleDone(task.id) }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(task.title, modifier = Modifier.weight(1f))
                            TextButton(onClick = { vm.removeTask(task.id) }) {
                                Text("Delete")
                            }
                        }
                    }
                }

                item { Spacer(Modifier.height(12.dp)) }
            }
        }
    }

    // Dialogs (same as Home)
    if (dialogMode == DialogMode.ADD) {
        TaskDialog(
            mode = DialogMode.ADD,
            onDismiss = { dialogMode = null },
            onSaveNew = { t, d, date ->
                vm.addTaskFromDialog(t, d, date)
                dialogMode = null
            },
            onSaveEdit = { },
            onDelete = { }
        )
    }

    if (dialogMode == DialogMode.EDIT && selectedTask != null) {
        TaskDialog(
            mode = DialogMode.EDIT,
            task = selectedTask,
            onDismiss = {
                dialogMode = null
                selectedTask = null
            },
            onSaveNew = { _, _, _ -> },
            onSaveEdit = { updated ->
                vm.updateTask(updated)
                dialogMode = null
                selectedTask = null
            },
            onDelete = { id ->
                vm.removeTask(id)
                dialogMode = null
                selectedTask = null
            }
        )
    }
}

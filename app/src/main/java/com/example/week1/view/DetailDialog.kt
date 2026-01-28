package com.example.week1.view


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week1.model.Task

@Composable
fun DetailDialog(
    task: Task,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit,
    onDelete: (Int) -> Unit
) {
    var title by remember(task.id) { mutableStateOf(task.title) }
    var description by remember(task.id) { mutableStateOf(task.description) }
    var priorityText by remember(task.id) { mutableStateOf(task.priority.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Task details") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = priorityText,
                    onValueChange = { priorityText = it },
                    label = { Text("Priority (number)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Due date: ${task.dueDate}")
                Text("Done: ${task.done}")
            }
        },
        confirmButton = {
            Button(onClick = {
                val prio = priorityText.toIntOrNull() ?: task.priority
                onSave(task.copy(title = title.trim(), description = description.trim(), priority = prio))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Row {
                TextButton(onClick = { onDelete(task.id) }) {
                    Text("Delete")
                }
                TextButton(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    )
}

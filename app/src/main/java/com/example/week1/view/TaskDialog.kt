package com.example.week1.view


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week1.model.Task
import java.time.LocalDate

@Composable
fun TaskDialog(
    mode: DialogMode,
    task: Task? = null,
    onDismiss: () -> Unit,
    onSaveNew: (title: String, description: String, dueDate: LocalDate) -> Unit,
    onSaveEdit: (Task) -> Unit,
    onDelete: (Int) -> Unit
) {
    val isEdit = mode == DialogMode.EDIT && task != null

    var title by remember(task?.id) { mutableStateOf(task?.title ?: "") }
    var description by remember(task?.id) { mutableStateOf(task?.description ?: "") }
    var dueDateText by remember(task?.id) { mutableStateOf((task?.dueDate ?: LocalDate.now()).toString()) }

    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEdit) "Edit task" else "Add task") },
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
                    value = dueDateText,
                    onValueChange = { dueDateText = it },
                    label = { Text("Due date (YYYY-MM-DD)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (error != null) {
                    Text(error!!, color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val parsed = try {
                    LocalDate.parse(dueDateText.trim())
                } catch (e: Exception) {
                    error = "Invalid date. Use YYYY-MM-DD."
                    return@Button
                }

                if (isEdit) {
                    onSaveEdit(task!!.copy(
                        title = title.trim(),
                        description = description.trim(),
                        dueDate = parsed
                    ))
                } else {
                    onSaveNew(title, description, parsed)
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Row {
                if (isEdit) {
                    TextButton(onClick = { onDelete(task!!.id) }) {
                        Text("Delete")
                    }
                }
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        }
    )
}

enum class DialogMode { ADD, EDIT }

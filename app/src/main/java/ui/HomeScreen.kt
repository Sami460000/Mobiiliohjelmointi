package com.example.week1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week1.domain.*
import java.time.LocalDate

@Composable
fun HomeScreen() {

    var tasks by remember { mutableStateOf(MockData.tasks) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Tasks")

        Spacer(modifier = Modifier.height(12.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {
                val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                val newTask = Task(
                    id = newId,
                    title = "New Task $newId",
                    description = "Created using button",
                    priority = 3,
                    dueDate = LocalDate.now().plusDays(6),
                    done = false
                )
                tasks = addTask(tasks, newTask)
            }) {
                Text("Add")
            }

            Button(onClick = {
                val firstId = tasks.firstOrNull()?.id ?: return@Button
                tasks = toggleDone(tasks, firstId)
            }) {
                Text("Toggle first")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { tasks = filterByDone(MockData.tasks, true) }) {
                Text("Show done")
            }

            Button(onClick = { tasks = filterByDone(MockData.tasks, false) }) {
                Text("Show not done")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { tasks = sortByDueDate(tasks) }) {
                Text("Sort by due date")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task list (Text rows)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            tasks.forEach { task ->
                Column {
                    Text(text = "${task.title} (priority ${task.priority})")
                    Text(text = task.description)
                    Text(text = "Due: ${task.dueDate} | Done: ${task.done}")
                }
            }
        }
    }
}

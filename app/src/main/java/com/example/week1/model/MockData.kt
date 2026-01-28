package com.example.week1.model

import java.time.LocalDate


object MockData {
    val tasks: List<Task> = listOf(
        Task(1, "Tärkeä työ nro 1", "Keitä perunat", 3, LocalDate.now().plusDays(2), true),
        Task(2, "Tärkeä työ nro 2", "Ruoki lampaat", 5, LocalDate.now().plusDays(1), true),
        Task(3, "Tärkeä työ nro 3", "Laula lauluja", 2, LocalDate.now().plusDays(5), true),
        Task(4, "Tärkeä työ nro 4", "Syö perunat", 4, LocalDate.now().plusDays(3), false),
        Task(5, "Tärkeä työ nro 5", "Valmistu koulusta", 1, LocalDate.now().plusDays(7), false),
        Task(6, "Tärkeä työ nro 6", "Juhli lampaiden kanssa", 2, LocalDate.now().plusDays(4), false)
    )
}
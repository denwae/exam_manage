package com.exam.exammanage.exam.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.io.File
import java.util.*

@Entity
class Exam(val subject: String, @Column(name = "exam_year") val year: Int, var examFile: File? = null) {

    @Id
    val examId: UUID = UUID.randomUUID()
}
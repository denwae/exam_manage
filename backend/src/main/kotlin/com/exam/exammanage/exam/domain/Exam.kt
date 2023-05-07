package com.exam.exammanage.exam.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Lob
import java.util.*

@Entity
class Exam(
    val subject: String,
    @Column(name = "exam_year") val year: Int,
    @Column(columnDefinition = "bytea") var examFile: ByteArray? = null
) {

    @Id
    val examId: UUID = UUID.randomUUID()
}
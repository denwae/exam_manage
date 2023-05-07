package com.exam.exammanage.exam.application

import com.exam.exammanage.exam.domain.Exam
import java.io.File
import java.util.*

data class ExamDTO(val examId: UUID? = null, val subject: String, val year: Int) {
    fun toExam(): Exam {
        return Exam(subject, year)
    }

    companion object {
        fun fromExam(exam: Exam): ExamDTO {
            return ExamDTO(exam.examId, exam.subject, exam.year)
        }
    }
}
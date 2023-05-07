package com.exam.exammanage.exam.application

import com.exam.exammanage.exam.domain.Exam
import java.io.File
import java.util.*

data class ExamDTO(val examId: UUID? = null, val subject: String, val year: Int, val examFile: File? = null) {
    fun toExam(): Exam {
        return Exam(subject, year, examFile)
    }

    companion object {
        fun fromExam(exam: Exam): ExamDTO {
            return ExamDTO(exam.examId, exam.subject, exam.year, exam.examFile)
        }
    }
}
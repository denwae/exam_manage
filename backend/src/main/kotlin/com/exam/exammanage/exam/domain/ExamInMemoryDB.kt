package com.exam.exammanage.exam.domain

import com.exam.exammanage.exam.domain.exceptions.ExamNotFoundException
import com.exam.exammanage.exam.ports.IPersistExams
import java.util.UUID

class ExamInMemoryDB: IPersistExams {

    private val exams = mutableMapOf<UUID, Exam>()
    override fun saveExam(exam: Exam): Exam {
        exams[exam.examId] = exam
        return exam
    }

    override fun findExamById(id: UUID): Exam {
        return exams[id] ?: throw ExamNotFoundException("Exam with id $id not found")
    }

    override fun findExamBySubject(subject: String): Exam {
        TODO("Not yet implemented")
    }

    override fun findExamByYear(year: Int): Exam {
        TODO("Not yet implemented")
    }

    override fun findAllExams(): List<Exam> {
        TODO("Not yet implemented")
    }

    override fun deleteExamById(id: String) {
        TODO("Not yet implemented")
    }

    fun deleteAllExams() {
        exams.clear()
    }
}

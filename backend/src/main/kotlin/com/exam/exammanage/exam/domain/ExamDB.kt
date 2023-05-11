package com.exam.exammanage.exam.domain

import com.exam.exammanage.exam.domain.exceptions.ExamNotFoundException
import com.exam.exammanage.exam.ports.IPersistExams
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class ExamDB(val examRepository: ExamRepository): IPersistExams {
    override fun saveExam(exam: Exam): Exam {
        return examRepository.save(exam)
    }

    override fun findExamById(id: UUID): Exam {
        return examRepository.findByIdOrNull(id) ?: throw ExamNotFoundException("Exam with id $id not found")
    }

    override fun findExamBySubject(subject: String): Exam {
        TODO("Not yet implemented")
    }

    override fun findExamByYear(year: Int): Exam {
        TODO("Not yet implemented")
    }

    override fun findAllExams(): List<Exam> {
        return examRepository.findAll()
    }

    override fun deleteExamById(id: String) {
        TODO("Not yet implemented")
    }
}
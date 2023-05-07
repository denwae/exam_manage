package com.exam.exammanage.exam.domain

import com.exam.exammanage.exam.ports.IPersistExams
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class ExamDomainService(val examRepository: IPersistExams) {
    fun createExam(exam: Exam): Exam {
        return examRepository.saveExam(exam)
    }

    fun addExamFile(id: UUID, file: File) {
        val exam = examRepository.findExamById(id)
        exam.examFile = file
        examRepository.saveExam(exam)
    }

}
package com.exam.exammanage.exam.domain

import com.exam.exammanage.exam.domain.exceptions.ExamFileNotPresentException
import com.exam.exammanage.exam.ports.IPersistExams
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class ExamDomainService(val examRepository: IPersistExams) {
    fun createExam(exam: Exam): Exam {
        return examRepository.saveExam(exam)
    }

    fun addExamFile(id: UUID, file: ByteArray) {
        val exam = examRepository.findExamById(id)
        exam.examFile = file
        examRepository.saveExam(exam)
    }

    fun getExamFile(examId: UUID): ByteArray {
        return examRepository.findExamById(examId).examFile
            ?: throw ExamFileNotPresentException("No file present for exam with id $examId")
    }

    fun getAllExams(year: Int? = null): List<Exam> {
        var exams = examRepository.findAllExams()
        if(year != null) {
            exams = exams.filter { it.year == year }
        }
        return exams
    }

}
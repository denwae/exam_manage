package com.exam.exammanage.exam.ports

import com.exam.exammanage.exam.domain.Exam
import java.util.*

interface IPersistExams {

    fun saveExam(exam: Exam): Exam

    fun findExamById(id: UUID): Exam

    fun findExamBySubject(subject: String): Exam

    fun findExamByYear(year: Int): Exam

    fun findAllExams(): List<Exam>

    fun deleteExamById(id: String)
}

package com.exam.exammanage.unit

import com.exam.exammanage.exam.domain.Exam
import com.exam.exammanage.exam.domain.ExamDomainService
import com.exam.exammanage.exam.domain.ExamInMemoryDB
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.File

class FileServiceTest : BehaviorSpec({
    val examRepository = ExamInMemoryDB()
    val examService = ExamDomainService(examRepository)

    beforeSpec{
        examRepository.deleteAllExams()
    }

    given("a new exam"){
        val exam = Exam(subject = "English", year = 2023)

        `when`("the exam is created"){
            val returnedExam = examService.createExam(exam)
            then("an exam with the correct data should be returned"){
                exam.examId shouldNotBe null
                exam.subject shouldBe returnedExam.subject
                exam.year shouldBe returnedExam.year
            }
            then("the exam should be persisted") {
                val persistedExam = examRepository.findExamById(exam.examId)

                persistedExam.examId shouldBe exam.examId
                persistedExam.subject shouldBe exam.subject
                persistedExam.year shouldBe exam.year
            }
        }
    }

    given("an existing exam") {
        val exam = examRepository.saveExam(Exam(subject = "German", year = 2023))
        and("a file") {
            val file = File("Test_MD.pdf")
            `when` ("the file is uploaded") {
                examService.addExamFile(exam.examId, file)
                then("the file should be persisted") {
                    val persistedExam = examRepository.findExamById(exam.examId)
                    persistedExam.examFile shouldBe file
                }
            }
        }
    }

    given("an existing exam with an exam file"){
        val exam = examRepository.saveExam(Exam(subject = "German", year = 2023, examFile = File("Test_MD.pdf")))
        and("a new file"){
            val file = File("Test_MD_2.pdf")
            `when`("a new exam file is uploaded"){
                examService.addExamFile(exam.examId, file)
                then("the new file should be persisted"){
                    val persistedExam = examRepository.findExamById(exam.examId)
                    persistedExam.examFile shouldBe file
                }
            }
        }
    }
})
package com.exam.exammanage.unit

import com.exam.exammanage.exam.domain.Exam
import com.exam.exammanage.exam.domain.ExamDomainService
import com.exam.exammanage.exam.domain.ExamInMemoryDB
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldHave
import io.kotest.matchers.shouldNotBe

class FileServiceTest : BehaviorSpec({
    val examRepository = ExamInMemoryDB()
    val examService = ExamDomainService(examRepository)

    afterContainer(){
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
        and("a file") {
            val exam = examRepository.saveExam(Exam(subject = "German", year = 2023))
            val file = this::class.java.getResource("/Test_MD.pdf").readBytes()
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
        and("a new file"){
            `when`("a new exam file is uploaded"){
                val exam = examRepository.saveExam(Exam(subject = "German", year = 2023, examFile = this::class.java.getResource("/Test_MD.pdf").readBytes()!!))
                val newFile = this::class.java.getResource("/Test_MD_2.pdf").readBytes()
                examService.addExamFile(exam.examId, newFile)
                then("the new file should be persisted"){
                    val persistedExam = examRepository.findExamById(exam.examId)
                    persistedExam.examFile shouldBe newFile
                }
            }
        }
        `when`("when getting the file"){
            val exam = examRepository.saveExam(Exam(subject = "German", year = 2023, examFile = this::class.java.getResource("/Test_MD.pdf").readBytes()!!))
            val newFile = this::class.java.getResource("/Test_MD_2.pdf").readBytes()
            val downloadedFile = examService.getExamFile(exam.examId)
            then("the correct file will be returned"){
                downloadedFile shouldBe exam.examFile
            }
        }
    }

    given("4 exams in the db with different years"){


        `when`("getting all exams without a date"){
            val exam1 = examRepository.saveExam(Exam(subject = "English", year = 2023))
            val exam2 = examRepository.saveExam(Exam(subject = "German", year = 2023))
            val exam3 = examRepository.saveExam(Exam(subject = "French", year = 2024))
            val exam4 = examRepository.saveExam(Exam(subject = "Spanish", year = 2022))
            val exams = examService.getAllExams()
            then("all four exams should be returned"){
                exams.size shouldBe 4
            }
        }
        `when`("getting all exams with a date"){
            val exam1 = examRepository.saveExam(Exam(subject = "English", year = 2023))
            val exam2 = examRepository.saveExam(Exam(subject = "German", year = 2023))
            val exam3 = examRepository.saveExam(Exam(subject = "French", year = 2024))
            val exam4 = examRepository.saveExam(Exam(subject = "Spanish", year = 2022))
            val returnedExams = examService.getAllExams(2023)
            then("only the two exams with the correct year should be returned"){
                returnedExams.size shouldBe 2
                returnedExams shouldContainAll listOf(exam1, exam2)
            }
        }
    }
})
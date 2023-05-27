package com.exam.exammanage.integration

import com.exam.exammanage.exam.application.ExamDTO
import com.exam.exammanage.exam.domain.Exam
import com.exam.exammanage.exam.domain.ExamRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.transaction.Transactional
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.multipart
import org.springframework.test.web.servlet.post

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExamControllerTest(mockMvc: MockMvc, examRepository: ExamRepository) : BehaviorSpec({

    given("an exam DTO") {
        val examDTO = ExamDTO(subject = "History", year = 2023)
        `when`("calling POST /exams") {
            val returnedExam: ExamDTO = jacksonObjectMapper().readValue(
                mockMvc.post("/exams") {
                    contentType = MediaType.APPLICATION_JSON
                    content = jacksonObjectMapper().writeValueAsString(examDTO)
                }.andDo {
                    print()
                }.andExpect {
                    status { isCreated() }
                }.andReturn().response.contentAsString

            )
            then("the correct exam should be returned") {
                returnedExam.examId shouldNotBe null
                returnedExam.subject shouldBe examDTO.subject
                returnedExam.year shouldBe examDTO.year
            }
            then("the exam should be persisted") {
                val persistedExam = examRepository.findByIdOrNull(returnedExam.examId!!)!!
                persistedExam.examId shouldBe returnedExam.examId
                persistedExam.subject shouldBe returnedExam.subject
                persistedExam.year shouldBe returnedExam.year
            }
        }
    }

    given("an existing exam in the database") {
        val exam = examRepository.save(Exam(subject = "Computer Science", year = 2023))
        and("a file") {
            val file = this::class.java.getResource("/Test_MD.pdf").readBytes()
            `when`("calling POST /exams/{id}/file") {
                mockMvc.multipart("/exams/${exam.examId}/file") {
                    file(MockMultipartFile("file", "Test_MD.pdf", MediaType.APPLICATION_PDF.type, file))
                }.andDo {
                    print()
                }.andExpect {
                    status { isOk() }
                }.andReturn().response.contentAsString
                then("the file should be persisted") {
                    val returnedExam = examRepository.findByIdOrNull(exam.examId)!!
                    returnedExam.examFile shouldBe file
                }
            }
        }
    }

    given("an existing exam with an exam file") {
        lateinit var exam: Exam
        beforeContainer {
            examRepository.deleteAll()
            exam = examRepository.save(
                Exam(
                    subject = "Politics",
                    year = 2023,
                    examFile = this::class.java.getResource("/Test_MD.pdf").readBytes()
                )
            )
        }

        and("a new file") {
            val file = this::class.java.getResource("/Test_MD_2.pdf").readBytes()
            `when`("calling POST /exams/{id}/file") {
                mockMvc.multipart("/exams/${exam.examId}/file") {
                    file(MockMultipartFile("file", "Test_MD_2.pdf", MediaType.APPLICATION_PDF.type, file))
                }.andDo {
                    print()
                }.andExpect {
                    status { isOk() }
                }.andReturn().response.contentAsString
                then("the new file should be persisted") {
                    val returnedExam = examRepository.findByIdOrNull(exam.examId)!!
                    returnedExam.examFile shouldBe file
                }
            }
        }
        `when`("calling GET /exams/{id}/file") {
            val returnedFile = mockMvc.get("/exams/${exam.examId}/file")
                .andDo {
                    print()
                }.andExpectAll {
                    status { isOk() }
                }.andReturn().response.contentAsByteArray
            then("the correct file should be returned") {
                returnedFile shouldBe exam.examFile
            }
        }
    }

    given("multiple exams in the db with different dates") {
        beforeContainer{
            examRepository.deleteAll()
        }
        `when`("calling GET /exams") {
            val exam1 = examRepository.save(Exam(subject = "Computer Science", year = 2023))
            val exam2 = examRepository.save(Exam(subject = "History", year = 2023))
            val exam3 = examRepository.save(Exam(subject = "Geography", year = 2024))
            val exam4 = examRepository.save(Exam(subject = "Maths", year = 2022))
            val returnedExams: List<Exam> = jacksonObjectMapper().readValue(mockMvc.get("/exams")
                .andDo {
                    print()
                }.andExpectAll {
                    status { isOk() }
                }.andReturn().response.contentAsString
            )
            then("all exams should be returned") {
                returnedExams.size shouldBe 4
            }
        }
        `when`("calling GET /exams?year=2023"){
            val exam1 = examRepository.save(Exam(subject = "Computer Science", year = 2023))
            val exam2 = examRepository.save(Exam(subject = "History", year = 2023))
            val exam3 = examRepository.save(Exam(subject = "Geography", year = 2024))
            val exam4 = examRepository.save(Exam(subject = "Maths", year = 2022))
            val returnedExams: List<ExamDTO> = jacksonObjectMapper().readValue(
                mockMvc.get("/exams"){
                    param("year", "2023")
                }
                .andDo {
                    print()
                }.andExpectAll {
                    status { isOk() }
                }.andReturn().response.contentAsString
            )
            then("only 2023 exams should be returned"){
                returnedExams.size shouldBe 2
                returnedExams shouldContainAll listOf(ExamDTO.fromExam(exam1), ExamDTO.fromExam(exam2))
            }
        }
    }
}) {
    override fun extensions() = listOf(SpringExtension)
}
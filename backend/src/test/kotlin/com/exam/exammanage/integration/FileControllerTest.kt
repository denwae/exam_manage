package com.exam.exammanage.integration

import com.exam.exammanage.exam.application.ExamDTO
import com.exam.exammanage.exam.ports.IPersistExams
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.transaction.Transactional
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FileControllerTest(mockMvc: MockMvc, examRepository: IPersistExams) : BehaviorSpec({

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
                val persistedExam = examRepository.findExamById(returnedExam.examId!!)
                persistedExam.examId shouldBe returnedExam.examId
                persistedExam.subject shouldBe returnedExam.subject
                persistedExam.year shouldBe returnedExam.year
            }
        }
    }

    given("an existing exam") {
        and("a file") {
            `when`("calling POST /exams/{id}/file") {
                then("the file should be persisted") {

                }
            }
        }
    }

    given("an existing exam with an exam file") {
        and("a new file") {
            `when`("calling POST /exams/{id}/file") {
                then("the new file should be persisted") {

                }
            }
        }
    }
}) {
    override fun extensions() = listOf(SpringExtension)
}
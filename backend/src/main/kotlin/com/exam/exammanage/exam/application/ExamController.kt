package com.exam.exammanage.exam.application

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller()
@RequestMapping("/exams")
class ExamController(private val examService: ExamApplicationService) {

    @PostMapping
    fun createExam(@RequestBody examDTO: ExamDTO): ResponseEntity<ExamDTO> {
        return ResponseEntity(examService.createExam(examDTO), HttpStatus.CREATED)
    }
}
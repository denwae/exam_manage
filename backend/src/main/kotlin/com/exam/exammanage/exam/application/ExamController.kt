package com.exam.exammanage.exam.application

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Controller()
@RequestMapping("/exams")
class ExamController(private val examService: ExamApplicationService) {

    @PostMapping
    fun createExam(@RequestBody examDTO: ExamDTO): ResponseEntity<ExamDTO> {
        return ResponseEntity(examService.createExam(examDTO), HttpStatus.CREATED)
    }

    @GetMapping("/{examId}/file")
    fun downloadFile(@PathVariable examId: UUID): ResponseEntity<ByteArray> {
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=$examId.pdf")
            .body(examService.getExamFile(examId))
    }

    @PostMapping("/{examId}/file")
    fun uploadFile(@PathVariable examId: UUID, @RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        examService.addFile(examId, file)
        return ResponseEntity.ok("File uploaded successfully")
    }

    @GetMapping
    fun getAllExams(@RequestParam("year", required = false) year: Int?): ResponseEntity<List<ExamDTO>> {
        return ResponseEntity.ok(examService.getAllExams(year))
    }
}
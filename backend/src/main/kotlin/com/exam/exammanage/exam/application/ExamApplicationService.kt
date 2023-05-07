package com.exam.exammanage.exam.application

import com.exam.exammanage.exam.domain.ExamDomainService
import org.springframework.stereotype.Service

@Service
class ExamApplicationService(private val examDomainService: ExamDomainService) {
    fun createExam(examDTO: ExamDTO): ExamDTO {
        return ExamDTO.fromExam(examDomainService.createExam(examDTO.toExam()))
    }

}

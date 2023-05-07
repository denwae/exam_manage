package com.exam.exammanage.exam.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ExamRepository: JpaRepository<Exam, UUID> {
}
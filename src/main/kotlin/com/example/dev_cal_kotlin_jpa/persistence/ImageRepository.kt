package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.Id

@Repository
interface ImageRepository : JpaRepository<Image, Long>{

    fun findByBoardId(boardId: Id)

}
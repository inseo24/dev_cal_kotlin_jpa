package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

// boardID 찾는 fun는 기본 curd로 처리 가능할 거 같으니 일단 스킵
@Repository
interface BoardRepository : JpaRepository<Board, Long>{
    fun findBoardById(id : Long)
}
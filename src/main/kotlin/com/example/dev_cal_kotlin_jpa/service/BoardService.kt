package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Board
import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.persistence.BoardRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import kotlin.RuntimeException

@Service
class BoardService(
    val userRepository: UserRepository,
    val boardRepository: BoardRepository,
    val modelMapper: ModelMapper,
) {
    fun create(boardDto: BoardDto, email: String): ResponseEntity<ResponseDto<BoardDto>> {
        val user = userRepository.findByEmail(email) ?: throw RuntimeException()
        val entity = modelMapper.map(boardDto, Board::class.java)
        entity.user = user
        val result = boardRepository.save(entity)
        val response = ResponseDto<BoardDto>().apply {
            this.data = modelMapper.map(result, BoardDto::class.java)
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    fun findAll(): ResponseEntity<ResponseDto<BoardDto>> {
        val result = boardRepository.findAll().map { modelMapper.map(it, BoardDto::class.java) }
        val response = ResponseDto<BoardDto>().apply {
            this.data = result
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    fun findOne(id: Long): ResponseEntity<ResponseDto<BoardDto>> {
        val result = boardRepository.findById(id).orElseThrow()
        val response = ResponseDto<BoardDto>().apply {
            this.data = modelMapper.map(result, BoardDto::class.java)
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    fun delete(id: Long): ResponseEntity<HttpStatus> {
        val entity = boardRepository.findById(id).orElseThrow()
        boardRepository.delete(entity)
        return ResponseEntity.ok().build()
    }

}
package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Board
import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.persistence.BoardRepository
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BoardService(
        val repo: BoardRepository,
        val modelMapper: ModelMapper
) {
        fun create(boardDto: BoardDto): BoardDto {
                val entity = repo.save(modelMapper.map(boardDto, Board::class.java))
                return modelMapper.map(entity, BoardDto::class.java)
        }

        fun findAll() : MutableList<BoardDto> {
                return repo.findAll()
                        .map {
                                modelMapper.map(it, BoardDto::class.java)
                        }.toMutableList()
        }

        fun findOne(boardId : Long) : BoardDto {
                return modelMapper.map(repo.findById(boardId), BoardDto::class.java)
        }

        fun delete(boardId: Long) : ResponseEntity<Any> {
                return try {
                        repo.deleteById(boardId)
                        ResponseEntity(Any(), HttpStatus.OK)

                } catch (e : Exception){
                        ResponseEntity(Any(), HttpStatus.BAD_REQUEST)

                }
        }

}
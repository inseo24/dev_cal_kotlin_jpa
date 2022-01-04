package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Board
import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.persistence.BoardRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BoardService(
        val userRepository: UserRepository,
        val repo: BoardRepository,
        val modelMapper: ModelMapper
) {
        fun create(boardDto: BoardDto, email: String): ResponseEntity<Any> {
                val user = userRepository.findByEmail(email)

                return user?.let {
                        val entity = modelMapper.map(boardDto, Board::class.java)
                        entity.user = user
                        repo.save(entity)
                        ResponseEntity.ok().build()
                }?:ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        }

        fun findAll() : MutableList<BoardDto> {
                return repo.findAll()
                        .map {
                                modelMapper.map(it, BoardDto::class.java)
                        }.toMutableList()
        }

        fun findOne(id : Long) : BoardDto {
                val entity = repo.findById(id).orElseThrow()
                return modelMapper.map(entity, BoardDto::class.java)
        }

        fun delete(id: Long) : ResponseEntity<Any> {
                val entity = repo.findById(id).orElseThrow()
                repo.delete(entity)
                return ResponseEntity(Any(), HttpStatus.OK)
        }

}
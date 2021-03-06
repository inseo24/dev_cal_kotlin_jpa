package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Comment
import com.example.dev_cal_kotlin_jpa.dto.CommentDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.BoardRepository
import com.example.dev_cal_kotlin_jpa.persistence.CommentRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CommentService(
    val boardRepository: BoardRepository,
    val userRepository: UserRepository,
    val commentRepository: CommentRepository,
    val modelMapper: ModelMapper,
) {

    fun findAll(): ResponseEntity<ResponseDto<CommentDto>> {
        val result = commentRepository.findAll().map { modelMapper.map(it, CommentDto::class.java) }
        val response = ResponseDto<CommentDto>().apply {
            this.data = result
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    fun findAllCommentsByBoardId(boardId: Long): ResponseEntity<ResponseDto<CommentDto>> {
        val result =
            commentRepository.findAllCommentsByBoardId(boardId).map { modelMapper.map(it, CommentDto::class.java) }
        val response = ResponseDto<CommentDto>().apply {
            this.data = result
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    fun create(commentDto: CommentDto, boardId: Long, email: String): ResponseEntity<ResponseDto<CommentDto>> {
        val boardEntity = boardRepository.findById(boardId).orElseThrow()
        val user = userRepository.findByEmail(email) ?: throw RuntimeException()
        val entity = Comment(commentDto.comment, user, boardEntity)
        val result = commentRepository.save(entity)
        val response = ResponseDto<CommentDto>().apply {
            this.data = result
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    @Transactional
    fun update(commentDto: CommentDto, email: String, id: Long): ResponseEntity<ResponseDto<CommentDto>> {
        val user = userRepository.findByEmail(email) ?: throw RuntimeException()
        val comment = commentRepository.findById(id).orElseThrow()
        if (comment.user == user) {
            comment.comment = commentDto.comment
            val response = ResponseDto<CommentDto>().apply {
                this.data = modelMapper.map(comment, CommentDto::class.java)
                this.status = "200 OK"
            }
            return ResponseEntity.ok().body(response)
        } else {
            throw RuntimeException()
        }
    }

    @Transactional
    fun delete(id: Long, email: String): ResponseEntity<HttpStatus> {
        val user = userRepository.findByEmail(email) ?: throw RuntimeException()
        val original = commentRepository.findById(id).orElseThrow()
        if (original.user == user) {
            commentRepository.deleteCommentByIdAndUserId(id, user.id)
        } else {
            throw RuntimeException()
        }
        return ResponseEntity.ok().build()
    }

}
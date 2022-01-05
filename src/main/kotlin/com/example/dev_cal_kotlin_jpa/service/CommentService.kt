package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Comment
import com.example.dev_cal_kotlin_jpa.dto.CommentDto
import com.example.dev_cal_kotlin_jpa.persistence.BoardRepository
import com.example.dev_cal_kotlin_jpa.persistence.CommentRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CommentService(
        val boardRepository: BoardRepository,
        val userRepository: UserRepository,
        val commentRepository: CommentRepository,
        val modelMapper: ModelMapper,
) {

    fun findAll(): MutableList<CommentDto> {
        return commentRepository.findAll()
            .map {
                modelMapper.map(it, CommentDto::class.java)
            }.toMutableList()
    }

    fun findCommentsByBoardId(boardId: Long): MutableList<CommentDto> {
        return commentRepository.findAllCommentsByBoardId(boardId)
            .map {
                modelMapper.map(it, CommentDto::class.java)
            }.toMutableList()
    }

    fun create(commentDto: CommentDto, boardId: Long, email: String): Comment? {
        val boardEntity = boardRepository.findById(boardId).orElseThrow()
        val user = userRepository.findByEmail(email)
        val entity = user?.let { Comment(commentDto.comment, it, boardEntity) }
        return entity?.let {
            commentRepository.save(entity)
        }
    }


    @Transactional
    fun update(commentDto: CommentDto, email: String, id: Long): MutableList<CommentDto> {
        val user = userRepository.findByEmail(email)
        user?.let {
            var original = commentRepository.findById(id).orElseThrow()
            if (original.user == user) {
                original.comment = commentDto.comment
            } else {
                throw RuntimeException("wrong user")
            }
        }
        return findAll()
    }

    // 수정 필요
    // fun delete(id : Long, email: String) {
    //         repo.deleteByCommentIdAndUserId(id, email)
    // }

}
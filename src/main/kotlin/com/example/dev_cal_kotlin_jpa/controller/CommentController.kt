package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.CommentDto
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import com.example.dev_cal_kotlin_jpa.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController(
    val commentService: CommentService,
) {

    @GetMapping
    fun findAll(): ResponseEntity<ResponseDto<CommentDto>> {
        return commentService.findAll()
    }

    @GetMapping("/{boardId}")
    fun findAllCommentsByBoardId(@PathVariable boardId: Long): ResponseEntity<ResponseDto<CommentDto>> {
        return commentService.findAllCommentsByBoardId(boardId)
    }

    @PostMapping(path = ["/{email}/{boardId}"])
    fun create(
        @RequestBody commentDto: CommentDto,
        @PathVariable email: String,
        @PathVariable boardId: Long,
    ): ResponseEntity<ResponseDto<CommentDto>> {
        return commentService.create(commentDto, boardId, email)
    }

    @PutMapping(path = ["/{email}/{id}"])
    fun update(
        @RequestBody commentDto: CommentDto,
        @PathVariable email: String,
        @PathVariable id: Long,
    ): ResponseEntity<ResponseDto<CommentDto>> {
        return commentService.update(commentDto, email, id)
    }

    @DeleteMapping(path = ["/{email}/{id}"])
    fun delete(@PathVariable email: String, @PathVariable id: Long): ResponseEntity<HttpStatus> {
        return commentService.delete(id, email)
    }
}
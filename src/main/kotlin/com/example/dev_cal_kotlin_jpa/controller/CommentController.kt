package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.domain.Comment
import com.example.dev_cal_kotlin_jpa.dto.CommentDto
import com.example.dev_cal_kotlin_jpa.service.CommentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController (
        val service: CommentService
){

        @GetMapping
        fun findAll() : MutableList<CommentDto> {
                return service.findAll()
        }

        @GetMapping(path = ["/{boardId}"])
        fun findCommentsByBoardId(@PathVariable boardId : Long) : MutableList<CommentDto> {
                return service.findCommentsByBoardId(boardId)
        }

        @PostMapping(path = ["/{email}/{boardId}"])
        fun create(@RequestBody commentDto: CommentDto, @PathVariable email : String, @PathVariable boardId: Long): Comment? {
                return service.create(commentDto, boardId, email)
        }

        @PutMapping(path = ["/{email}/{id}"])
        fun update(@RequestBody commentDto: CommentDto, @PathVariable email: String, @PathVariable id : Long): MutableList<CommentDto>{
                return update(commentDto, email, id)
        }

        // 수정 필요
       // @DeleteMapping(path = ["/{email}/{id}"])
       // fun delete(@PathVariable email: String, @PathVariable id : Long){
       //         return service.delete(id, email)
       // }
}
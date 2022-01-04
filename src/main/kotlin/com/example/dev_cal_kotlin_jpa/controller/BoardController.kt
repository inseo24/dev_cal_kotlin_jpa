package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.service.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
class BoardController (
        val service: BoardService
){

        @PostMapping
        fun create(@RequestBody boardDto: BoardDto) : BoardDto {
                return service.create(boardDto)
        }

        @GetMapping
        fun findAll() : MutableList<BoardDto> {
                return service.findAll()
        }

        @GetMapping(path = ["/{boardId}"])
        fun findOne(@PathVariable boardId : Long): BoardDto{
                return service.findOne(boardId)
        }

        @DeleteMapping(path=["/{boardId}"])
        fun delete(@PathVariable boardId: Long): ResponseEntity<Any> {
                return service.delete(boardId)
        }


}
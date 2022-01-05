package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.service.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/board")
class BoardController(
        val service: BoardService,
) {

    @PostMapping("/{email}")
    fun create(@Valid @RequestBody boardDto: BoardDto, @PathVariable email: String): ResponseEntity<Any> {
        return service.create(boardDto, email)
    }

    @GetMapping
    fun findAll(): MutableList<BoardDto> {
        return service.findAll()
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long): BoardDto {
        return service.findOne(id)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        return service.delete(id)
    }


}
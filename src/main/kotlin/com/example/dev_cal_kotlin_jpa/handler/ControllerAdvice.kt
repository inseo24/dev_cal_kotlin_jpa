package com.example.dev_cal_kotlin_jpa.handler

import com.example.dev_cal_kotlin_jpa.responseDto.Error
import com.example.dev_cal_kotlin_jpa.responseDto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.time.LocalDateTime
import java.util.NoSuchElementException
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {

        val errors = mutableListOf<Error>()

        e.bindingResult.allErrors.forEach { errorObject ->
            val error = Error().apply {
                this.field = (errorObject as FieldError).field
                this.message = errorObject.defaultMessage
                this.value = errorObject.rejectedValue
            }.apply {
                errors.add(this)
            }
        }

        val errorResponse = ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "method Argument Not Valid"
            this.path = request.requestURI.toString()
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    fun handleHttpMediaTypeNotAcceptableException(): ResponseEntity<HttpStatus> {
        return ResponseEntity.ok().build()
    }

    @ResponseBody
    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementException(): ResponseEntity<HttpStatus> {
        return ResponseEntity.badRequest().build()
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException::class)
    fun runtimeException(): ResponseEntity<HttpStatus> {
        return ResponseEntity.badRequest().build()
    }

}
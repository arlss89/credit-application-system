package me.dio.credit.application.system.exception

import java.time.LocalDateTime
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerValidException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionDetails> {
        val errors = HashMap<String, String?>()

        ex.bindingResult.allErrors.toList().forEach { error ->
            val fieldName = (error as FieldError).field
            val messageError = error.defaultMessage

            errors[fieldName] = messageError
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ExceptionDetails(
                title = "Bad Request! Please, consult docs",
                status = HttpStatus.BAD_REQUEST.value(),
                timestamp = LocalDateTime.now(),
                exception = ex.javaClass.toString(),
                details = errors
            )
        )
    }

    @ExceptionHandler(DataAccessException::class)
    fun handlerDataAccessException(ex: DataAccessException): ResponseEntity<ExceptionDetails> {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ExceptionDetails(
                title = "Bad Request! Please, consult docs",
                status = HttpStatus.CONFLICT.value(),
                timestamp = LocalDateTime.now(),
                exception = ex.javaClass.toString(),
                details = mutableMapOf(ex.cause.toString() to ex.message)
            )
        )
    }

    @ExceptionHandler(BusinessException::class)
    fun handlerBusinessException(ex: BusinessException): ResponseEntity<ExceptionDetails> {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ExceptionDetails(
                title = "Bad Request! Please, consult docs",
                status = HttpStatus.NOT_FOUND.value(),
                timestamp = LocalDateTime.now(),
                exception = ex.javaClass.toString(),
                details = mutableMapOf("message" to ex.message)
            )
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerBusinessException(ex: IllegalArgumentException): ResponseEntity<ExceptionDetails> {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ExceptionDetails(
                title = "Bad Request! Please, consult docs",
                status = HttpStatus.NOT_FOUND.value(),
                timestamp = LocalDateTime.now(),
                exception = ex.javaClass.toString(),
                details = mutableMapOf(ex.cause.toString() to ex.message)
            )
        )
    }
}
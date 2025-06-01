package com.example.grpc_aggregator_service.controller.advice

import io.grpc.Status
import io.grpc.StatusRuntimeException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ApplicationExceptionHandler {

    @ExceptionHandler(StatusRuntimeException::class)
    fun handleStatusRuntimeException(e: StatusRuntimeException): ResponseEntity<String> {
        return when (e.status.code) {
            Status.Code.INVALID_ARGUMENT, Status.Code.FAILED_PRECONDITION -> ResponseEntity.badRequest()
                .body(e.status.description)

            Status.Code.NOT_FOUND -> ResponseEntity.notFound().build()
            else -> ResponseEntity.internalServerError().body(e.message)
        }
    }
}
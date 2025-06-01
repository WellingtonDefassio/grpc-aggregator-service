package com.example.grpc_aggregator_service.controller

import com.example.grpc_aggregator_service.service.UserService
import com.vinsguru.user.UserInformation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController(val userService: UserService) {

    @GetMapping(value = ["{userId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserInformation(@PathVariable userId: Int): UserInformation {
        return userService.getUserInformation(userId)
    }
}
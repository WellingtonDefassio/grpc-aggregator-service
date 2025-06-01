package com.example.grpc_aggregator_service.service

import com.vinsguru.user.UserInformation
import com.vinsguru.user.UserInformationRequest
import com.vinsguru.user.UserServiceGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service

@Service
class UserService(
    @GrpcClient("user-service") private val userClient: UserServiceGrpc.UserServiceBlockingStub
) {

    fun getUserInformation(userId: Int): UserInformation {
        val request = UserInformationRequest.newBuilder()
            .setUserId(userId)
            .build()

        return userClient.getUserInformation(request)
    }

}
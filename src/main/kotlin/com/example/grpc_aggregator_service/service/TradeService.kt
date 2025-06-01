package com.example.grpc_aggregator_service.service

import com.vinsguru.user.UserServiceGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service

@Service
class TradeService(
    @GrpcClient("user-service") private val userClient: UserServiceGrpc.UserServiceBlockingStub,
    @GrpcClient("stock-service") private val stockClient: UserServiceGrpc.UserServiceBlockingStub
) {



}
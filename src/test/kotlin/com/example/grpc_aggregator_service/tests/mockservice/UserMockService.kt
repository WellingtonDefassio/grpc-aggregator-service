package com.example.grpc_aggregator_service.tests.mockservice

import com.vinsguru.user.*
import io.grpc.Status
import io.grpc.stub.StreamObserver

class UserMockService : UserServiceGrpc.UserServiceImplBase() {
    override fun getUserInformation(
        request: UserInformationRequest,
        responseObserver: StreamObserver<UserInformation>
    ) {
        if (request.userId == 1) {
            val user = UserInformation.newBuilder()
                .setUserId(1)
                .setBalance(100)
                .setName("integration-test-1")
                .build()

            responseObserver.onNext(user)
            responseObserver.onCompleted()
        } else {
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException())
        }
    }

    override fun tradeStock(request: StockTradeRequest, responseObserver: StreamObserver<StockTradeResponse>) {
        val response = StockTradeResponse.newBuilder()
            .setUserId(request.userId)
            .setTicker(request.ticker)
            .setAction(request.action)
            .setQuantity(request.quantity)
            .setTotalPrice(1000)
            .setBalance(0)
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}
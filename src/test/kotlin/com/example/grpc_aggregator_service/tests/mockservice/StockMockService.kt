package com.example.grpc_aggregator_service.tests.mockservice

import com.google.protobuf.Empty
import com.vinsguru.common.Ticker
import com.vinsguru.stock.PriceUpdate
import com.vinsguru.stock.StockPriceRequest
import com.vinsguru.stock.StockPriceResponse
import com.vinsguru.stock.StockServiceGrpc
import io.grpc.stub.StreamObserver

class StockMockService: StockServiceGrpc.StockServiceImplBase() {
    override fun getStockPrice(request: StockPriceRequest, responseObserver: StreamObserver<StockPriceResponse>) {
        val response = StockPriceResponse
            .newBuilder()
            .setPrice(15)
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getPriceUpdates(request: Empty, responseObserver: StreamObserver<PriceUpdate>) {
        for (i in 1..5){
            val priceUpdate = PriceUpdate.newBuilder().setPrice(i).setTicker(Ticker.AMAZON).build()
            responseObserver.onNext(priceUpdate)
        }
        responseObserver.onCompleted()
    }
}
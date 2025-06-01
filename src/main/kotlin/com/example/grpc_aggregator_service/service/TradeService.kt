package com.example.grpc_aggregator_service.service

import com.vinsguru.stock.StockPriceRequest
import com.vinsguru.stock.StockServiceGrpc
import com.vinsguru.user.StockTradeRequest
import com.vinsguru.user.StockTradeResponse
import com.vinsguru.user.UserServiceGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service

@Service
class TradeService(
    @GrpcClient("user-service") private val userClient: UserServiceGrpc.UserServiceBlockingStub,
    @GrpcClient("stock-service") private val stockClient: StockServiceGrpc.StockServiceBlockingStub
) {

    fun trade(request: StockTradeRequest): StockTradeResponse {
        val priceRequest = StockPriceRequest.newBuilder()
            .setTicker(request.ticker).build()

        val priceResponse = stockClient.getStockPrice(priceRequest)
        val tradeRequest = request.toBuilder().setPrice(priceResponse.price).build()
        return userClient.tradeStock(tradeRequest)
    }

}
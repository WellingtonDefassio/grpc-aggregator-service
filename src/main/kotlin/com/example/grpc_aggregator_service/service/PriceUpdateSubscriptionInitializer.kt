package com.example.grpc_aggregator_service.service

import com.google.protobuf.Empty
import com.vinsguru.stock.StockServiceGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Service
class PriceUpdateSubscriptionInitializer(
    @GrpcClient("stock-service")
    private val stockClient: StockServiceGrpc.StockServiceStub,
    private val listener: PriceUpdateListener
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        stockClient
            .withWaitForReady()
            .getPriceUpdates(Empty.getDefaultInstance(), listener)
    }
}
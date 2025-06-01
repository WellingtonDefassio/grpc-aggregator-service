package com.example.grpc_aggregator_service.controller

import com.example.grpc_aggregator_service.service.PriceUpdateListener
import com.google.common.util.concurrent.Uninterruptibles
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("stock")
class StockController(
    private val listener: PriceUpdateListener
) {

    @GetMapping("updates", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun priceUpdates(): SseEmitter {
        return listener.createEmitter()
    }
}
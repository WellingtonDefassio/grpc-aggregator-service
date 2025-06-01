package com.example.grpc_aggregator_service.controller

import com.example.grpc_aggregator_service.service.TradeService
import com.vinsguru.user.StockTradeRequest
import com.vinsguru.user.StockTradeResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("trade")
class TradeController(val tradeService: TradeService) {

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun trade(@RequestBody request: StockTradeRequest): StockTradeResponse {
        return tradeService.trade(request);
    }
}
package com.example.grpc_aggregator_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrpcAggregatorServiceApplication

fun main(args: Array<String>) {
	runApplication<GrpcAggregatorServiceApplication>(*args)
}

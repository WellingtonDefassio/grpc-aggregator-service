package com.example.grpc_aggregator_service.service

import com.example.grpc_aggregator_service.dto.PriceUpdateDto
import com.vinsguru.stock.PriceUpdate
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*

@Service
class PriceUpdateListener(
    @Value("\${sse.timeout:300000}")
    val sseTimeout: Long
) : StreamObserver<PriceUpdate> {

    private val logger = LoggerFactory.getLogger(PriceUpdateListener::class.java)
    private val emitters = Collections.synchronizedSet<SseEmitter>(HashSet())

    override fun onNext(priceUpdate: PriceUpdate) {
        val dto = PriceUpdateDto(priceUpdate.ticker.name, priceUpdate.price)
        emitters.removeIf { e ->
            !send(e, dto)
        }
    }

    override fun onError(e: Throwable) {
        logger.error("streaming error", e)
        emitters.forEach {
            it.completeWithError(e)
        }
        emitters.clear()
    }

    override fun onCompleted() {
        emitters.forEach {
            it.complete()
        }
        emitters.clear()
    }

    fun createEmitter(): SseEmitter {
        val emitter = SseEmitter(sseTimeout)
        emitters.add(emitter)
        emitter.onTimeout {
            emitters.remove(emitter)
        }
        emitter.onError {
            emitters.remove(emitter)
        }
        return emitter
    }

    fun send(emitter: SseEmitter, o: Any): Boolean {
        try {
            emitter.send(o)
            return true
        } catch (e: Exception) {
            logger.warn("sse error {}", e.message)
            return false
        }
    }
}
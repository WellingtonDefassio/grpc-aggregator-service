package com.example.grpc_aggregator_service.config

import com.google.protobuf.util.JsonFormat
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter
import java.util.concurrent.Executors

@Configuration
class ApplicationConfiguration {

    private val logger = LoggerFactory.getLogger(ApplicationConfiguration::class.java)

    @Bean
    fun channelConfigure(): GrpcChannelConfigurer {
        return GrpcChannelConfigurer { channelBuilder, name ->
                logger.info("channel builder {}", name)
                channelBuilder.executor(Executors.newVirtualThreadPerTaskExecutor()) //just for demo
        }
    }

    @Bean
    fun convertProtoToJson(): ProtobufJsonFormatHttpMessageConverter{
        return ProtobufJsonFormatHttpMessageConverter(
            JsonFormat.parser().ignoringUnknownFields(),
            JsonFormat.printer().omittingInsignificantWhitespace().includingDefaultValueFields()
        )
    }
}
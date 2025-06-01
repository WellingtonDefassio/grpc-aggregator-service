package com.example.grpc_aggregator_service.tests

import com.example.grpc_aggregator_service.tests.mockservice.StockMockService
import com.example.grpc_aggregator_service.tests.mockservice.UserMockService
import com.vinsguru.user.UserInformation
import net.devh.boot.grpc.server.service.GrpcService
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.server.LocalServerPort
import kotlin.test.Test

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
class UserTradeTest {

    @LocalServerPort
    private var port: Int? = null

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `test user information`() {
        val url = "http://localhost:$port/user/1"
        val response = restTemplate.getForEntity<UserInformation>(url, UserInformation::class)
        assertThat(response.statusCode.value()).isEqualTo(200)
        assertThat(response.body).isNotNull
        val user = response.body
        assertThat(user!!.userId).isEqualTo(1)
        assertThat(user!!.name).isEqualTo("integration-test-1")
    }

    @Test
    fun `test unknown user information`() {
        val url = "http://localhost:$port/user/2"
        val response = restTemplate.getForEntity<UserInformation>(url, UserInformation::class)
        assertThat(response.statusCode.value()).isEqualTo(404)
        assertThat(response.body).isNull()
    }


    @TestConfiguration
    internal class Config {

        @GrpcService
        fun stockMockService(): StockMockService {
            return StockMockService()
        }

        @GrpcService
        fun userMockService(): UserMockService {
            return UserMockService()
        }
    }

}
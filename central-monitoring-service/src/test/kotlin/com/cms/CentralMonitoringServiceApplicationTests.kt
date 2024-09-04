package com.cms

import com.cms.service.SensorService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.slf4j.Logger
import org.springframework.test.util.ReflectionTestUtils

@SpringBootTest
class CentralMonitoringServiceApplicationTests {

	private lateinit var sensorService: SensorService
	private val mockLogger = mockk<Logger>(relaxed = true)

	@BeforeEach
	fun setup() {
		sensorService = SensorService(tempThreshold = 30, humidityThreshold = 60)
		ReflectionTestUtils.setField(sensorService, "logger", mockLogger)
	}

	@Test
	fun `should log error for temperature exceeding threshold`() {
		val message = "sensorType=t1;value=35\n"
		sensorService.receiveMessage(message)

		verify { mockLogger.error("ALARM: Temperature exceeded threshold. Value: 35") }
	}

	@Test
	fun `should log error for humidity exceeding threshold`() {
		val message = "sensorType=h1;value=65\n"
		sensorService.receiveMessage(message)

		verify { mockLogger.error("ALARM: Humidity exceeded threshold. Value: 65") }
	}

	@Test
	fun `should not log error for temperature below threshold`() {
		val message = "sensorType=t1;value=25\n"
		sensorService.receiveMessage(message)

		verify(exactly = 0) { mockLogger.error(any()) }
	}

	@Test
	fun `should not log error for humidity below threshold`() {
		val message = "sensorType=h1;value=55\n"
		sensorService.receiveMessage(message)

		verify(exactly = 0) { mockLogger.error(any()) }
	}

	@Test
	fun `should handle messages with incorrect sensor type`() {
		val message = "sensorType=x1;value=50\n"
		sensorService.receiveMessage(message)

		verify(exactly = 0) { mockLogger.error(any()) }
	}


}

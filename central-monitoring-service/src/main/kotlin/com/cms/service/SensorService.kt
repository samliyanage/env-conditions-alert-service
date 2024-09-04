package com.cms.service

import org.springframework.stereotype.Service
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.slf4j.LoggerFactory

@Service
class SensorService(
    @Value("\${monitoring.temperature.threshold}") private val tempThreshold: Int,
    @Value("\${monitoring.humidity.threshold}") private val humidityThreshold: Int
) {
    private val logger = LoggerFactory.getLogger(SensorService::class.java)

    @RabbitListener(queues = ["temperature", "humidity"])
    fun receiveMessage(message: String) {
        val parts = message.split(";")
        val sensorType = parts[0].split("=")[1]
        val valueString = parts[1].split("=")[1]
        val value = valueString.replace("\n", "").toInt()

        when (sensorType) {
            "t1" -> {
                if (value > tempThreshold) {
                    logger.error("ALARM: Temperature exceeded threshold. Value: $value")
                }
            }
            "h1" -> {
                if (value > humidityThreshold) {
                    logger.error("ALARM: Humidity exceeded threshold. Value: $value")
                }
            }
        }
    }
}
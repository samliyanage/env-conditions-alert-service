package com.app.wh.config.udp

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.DatagramPacket
import java.net.DatagramSocket
import kotlin.concurrent.thread

@Component
class UdpReceiver(
    private val rabbitTemplate: RabbitTemplate,
    @Value("\${sensors.temperature.port}") private val tempPort: Int,
    @Value("\${sensors.humidity.port}") private val humPort: Int
) {

    private val tempSocket = DatagramSocket(tempPort)
    private val humSocket = DatagramSocket(humPort)

    @PostConstruct
    fun start() {
        thread { receiveData(tempSocket, "temperature") }
        thread { receiveData(humSocket, "humidity") }
    }

    private fun receiveData(socket: DatagramSocket, sensorType: String) {
        while (true) {
            val buffer = ByteArray(1024)
            val packet = DatagramPacket(buffer, buffer.size)
            socket.receive(packet)
            val message = String(packet.data, 0, packet.length)
            rabbitTemplate.convertAndSend(sensorType, message)
        }
    }

    @PreDestroy
    fun stop() {
        tempSocket.close()
        humSocket.close()
    }
}
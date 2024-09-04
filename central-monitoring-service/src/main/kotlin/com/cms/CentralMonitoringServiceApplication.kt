package com.cms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CentralMonitoringServiceApplication

fun main(args: Array<String>) {
	runApplication<CentralMonitoringServiceApplication>(*args)
}

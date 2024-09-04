package com.app.wh

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WhServiceApplication

fun main(args: Array<String>) {
	runApplication<WhServiceApplication>(*args)
}

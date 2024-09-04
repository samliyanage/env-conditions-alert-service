package com.app.wh.config.mq

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
class RabbitMQConfig {

    @Bean
    fun topicExchange(): TopicExchange {
        return TopicExchange("sensors")
    }

    @Bean
    fun temperatureQueue(): Queue {
        return Queue("temperature")
    }

    @Bean
    fun humidityQueue(): Queue {
        return Queue("humidity")
    }

    @Bean
    fun bindingTemperature(): Binding {
        return BindingBuilder.bind(temperatureQueue()).to(topicExchange()).with("temperature")
    }

    @Bean
    fun bindingHumidity(): Binding {
        return BindingBuilder.bind(humidityQueue()).to(topicExchange()).with("humidity")
    }

    @Bean
    fun rabbitAdmin(rabbitTemplate: RabbitTemplate): RabbitAdmin {
        return RabbitAdmin(rabbitTemplate)
    }
}
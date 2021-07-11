package com.work_queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者
 */
public class Producer_WorkQueues {

    public static final String QUEUE_NAME = "work_queues";

    public static void main(String[] args) throws Exception{
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.144.132");
        factory.setUsername("admin");
        factory.setPassword("123");

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){

            /**
             * 1. String queue, 队列名称
             * 2. boolean durable, 是否持久化，当mq重启后还在
             * 3. boolean exclusive,
             *      1.是否独占，只能有一个消费者监听该队列
             *      2. 当connection关闭时是否删除队列
             * 4. boolean autoDelete, 是否自动删除，当没有consumer时自动删除
             * 5. Map<String, Object> arguments 参数
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            for (int i = 1; i <= 10; i++) {
                String message = i+ "-work_queues";
                /**
                 * 1. String exchange, 交换机名称，简单模式下交换机会使用默认名称 ""
                 * 2. String routingKey, 路由key
                 * 3. BasicProperties props, 配置信息
                 * 4. byte[] body 消息数据
                 */
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
        }
    }
}

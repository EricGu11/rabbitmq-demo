package com.consuimer_ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * 生产者-测试手动应答
 */
public class Producer_ConsumerAck {

    public static final String QUEUE_NAME = "consumer_ack";

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

            //String message = "consumer_ack";

            Scanner scanner = new Scanner(System.in);

            System.out.println("生产者准备发送消息");

            while (scanner.hasNext()){
                String msg = scanner.next();
                /**
                 * 1. String exchange, 交换机名称，简单模式下交换机会使用默认名称 ""
                 * 2. String routingKey, 路由key
                 * 3. BasicProperties props, 配置信息
                 * 4. byte[] body 消息数据
                 */
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            }


        }
    }
}

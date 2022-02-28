package com.dreamtea;

import com.dreamtea.Game.Server.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LlkApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(LlkApplication.class, args);
        Server nettyServer = (Server) ctx.getBean("nettyServer");
        nettyServer.start();
    }

}

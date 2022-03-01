package com.dreamtea;

import com.dreamtea.Game.GameServer.GameServer;
import com.dreamtea.Game.GroundServer.GroundServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LlkApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(LlkApplication.class, args);
        ((GameServer) ctx.getBean("gameServer")).start();
        ((GroundServer) ctx.getBean("groundServer")).start();
    }

}

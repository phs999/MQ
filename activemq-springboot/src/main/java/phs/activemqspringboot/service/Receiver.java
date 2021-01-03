package phs.activemqspringboot.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Receiver {
    
    @JmsListener(destination = "springboot" ,containerFactory="jmsListenerContainerTopic")
    public void receive(String msg){
        System.out.println("收到消息："+msg);
    }
    @JmsListener(destination = "user" ,containerFactory="jmsListenerContainerQueue")
    public void receive2(HashMap msg){
        System.out.println("收到消息："+msg);
    }

}

package phs.activemqspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phs.activemqspringboot.service.Receiver;
import phs.activemqspringboot.service.SenderService;

import javax.jms.JMSException;

@RestController
public class MainController {

    @Autowired
    SenderService senderSrv;


    @RequestMapping("send")
    public String send() throws JMSException {
        senderSrv.send("springboot","hello");
        senderSrv.send2("springboot","hello");
        senderSrv.send3("springboot","hello");

        return "ok";
    }

}

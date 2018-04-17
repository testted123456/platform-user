package com.nonobank.platformuser.component;

import com.nonobank.platformuser.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by tangrubei on 2018/4/16.
 */
@Component
public class AfterRunner {

    @Autowired
    UsersService usersService;


    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        usersService.callRemoteServiceInitUrlMap();
        // start your monitoring in here
    }
}

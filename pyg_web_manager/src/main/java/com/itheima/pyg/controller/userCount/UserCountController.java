package com.itheima.pyg.controller.userCount;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.service.user.UserService;
import com.itheima.pyg.service.userCount.UserCount;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userCount")
public class UserCountController {

    @Reference
    private UserCount userCount;


    @RequestMapping("/findUser")
    public long findUser() {

       /* System.out.println("用户人数"+user);*/
        return userCount.findUser();
    }

}

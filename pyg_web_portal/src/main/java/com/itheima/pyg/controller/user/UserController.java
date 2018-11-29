package com.itheima.pyg.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.pojo.order.OrderItem;
import com.itheima.pyg.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;


    /**
     * 用户中心，显示我的收藏
     * @return
     */
    @RequestMapping("showMyCollection")
    public List<OrderItem>  showMyCollection(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!"anonymousUser".equals(userId)){

            //根据当前登录用户从redis中查出该用户的收藏商品
            List<OrderItem> orderItemList = userService.showMyCollection(userId);
            if(orderItemList.size()>0){
                return orderItemList;
            }else {
                return new ArrayList<>();
            }
        }
        return  null;
    }


}

package com.itheima.pyg.controller.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.entity.Result;
import com.itheima.pyg.service.order.OrderService;
import com.itheima.pyg.service.pay.WxPayService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 未付款订单支付
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference(timeout = 1000*60*6)
    private WxPayService wxPayService;

    @Reference
    private OrderService orderService;



    /**
     * 生成二维码,并显示订单编号和总金额
     * @return
     */
    @RequestMapping("createNative")
    public Map createNative(String out_trade_no){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);

        //根据订单号查找对应的金额
        Long totalFee =orderService.findTotalFeeFromRedis(out_trade_no);
        System.out.println(totalFee);


        if (!"anonymousUser".equals(username)){
            return wxPayService.createNative(out_trade_no,totalFee+"");
        }else {
            return new HashMap();
        }
    }


    @RequestMapping("queryPayStatus")
    public Result queryPayStatus(String out_trade_no){
        Map<String,String> map = wxPayService.queryPayStatusWhile(out_trade_no);
        if (map==null){
            return new Result(false,"支付失败");

        }else {
            if ("SUCCESS".equals(map.get("trade_state"))){
                orderService.updateUnPayOrderStatus(out_trade_no,map.get("transaction_id"));
                return new Result(true,"支付成功");
            }else {
                wxPayService.closePay(out_trade_no);
                return new Result(false,"二维码超时");
            }
        }
    }

}

package com.itheima.pyg.controller.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.entity.Result;
import com.itheima.pyg.pojo.address.Address;
import com.itheima.pyg.service.user.AddressService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {
    @Reference
    private AddressService addressService;

    //用户地址查询
    @RequestMapping("findListByUserName")
    public List<Address> findListByUserName(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressService.findListByUserName(name);
    }


    //新增用户收货地址
    @RequestMapping("addAddress")
    public Result addAddress(@RequestBody Address address){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            addressService.addAddress(address,userId);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }


    //删除用户收货地址
    @RequestMapping("delete")
    public Result deleteAddress(Long id){

        try {
            addressService.deleteAddress(id);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }
    //修改用户收货地址
    @RequestMapping("update")
    public Address updateAddress(Long id){
        return addressService.updateAddrss(id);
    }

    @RequestMapping("/editorAddress")
    public Result editorAddress(Long id){

        try {
            addressService.editorAddress(id);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }
}

package com.itheima.pyg.service.user;

import com.itheima.pyg.pojo.address.Address;

import java.util.List;

public interface AddressService {
    List<Address>   findListByLoginUser(String userId);

    //查询用户收货地址
    public List<Address>findListByUserName(String userName);

    //新增用户收货地址
    public void addAddress(Address address, String userId);

    //删除用户收货地址
    public void deleteAddress(Long id);

    //修改用户收货地址

    public Address updateAddrss(Long id);

    //编辑收货用户地址
    public void editorAddress(Long id);
}

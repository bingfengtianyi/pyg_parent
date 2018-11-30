package com.itheima.pyg.service.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pyg.dao.address.AddressDao;
import com.itheima.pyg.pojo.address.Address;
import com.itheima.pyg.pojo.address.AddressQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;
    /**
     * 根据用户id查询地址列表
     * @param userId
     * @return
     */
    @Override
    public List<Address> findListByLoginUser(String userId) {
        AddressQuery query = new AddressQuery();
        AddressQuery.Criteria criteria = query.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Address> addressList = addressDao.selectByExample(query);
        return addressList;
    }

    //查询用户地址
    @Override
    public List<Address> findListByUserName(String userName) {
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.createCriteria().andUserIdEqualTo(userName);
        return addressDao.selectByExample(addressQuery);
    }

    ////新增用户收货地址
    @Transactional
    @Override
    public void addAddress(Address address, String userId) {
        Address address1 = new Address();
        address1.setUserId(userId);

        address1.setMobile(address.getMobile());
        address1.setAddress(address.getAddress());
        address1.setContact(address.getContact());
        address1.setIsDefault("0");
        address1.setNotes(address.getNotes());
        address1.setCreateDate(new Date());
        addressDao.insertSelective(address1);

    }


    //删除用户收货地址
    @Transactional
    @Override
    public void deleteAddress(Long id) {
        addressDao.deleteByPrimaryKey(id);
    }

    //修改用户收货地址
    @Override
    public Address updateAddrss(Long id) {
        return addressDao.selectByPrimaryKey(id);
    }


    //编辑收货用户地址
    @Override
    public void editorAddress(Long id) {
        AddressQuery query = new AddressQuery();
        query.createCriteria().andIdEqualTo(id);
        addressDao.deleteByExample(query);
        Address address1 = new Address();

        addressDao.insertSelective(address1);

    }
}

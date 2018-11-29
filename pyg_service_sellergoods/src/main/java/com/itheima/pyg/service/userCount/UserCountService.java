package com.itheima.pyg.service.userCount;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pyg.dao.user.UserDao;
import com.itheima.pyg.pojo.user.User;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserCountService implements UserCount {
    @Autowired
    private UserDao userDao;
    @Override
    public Long findUser() {

        return userDao.selectUser();
    }
}

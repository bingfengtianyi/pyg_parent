package com.itheima.pyg.dao.user;

import com.itheima.pyg.pojo.user.User;
import com.itheima.pyg.pojo.user.UserQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    int countByExample(UserQuery example);

    int deleteByExample(UserQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserQuery example);

    User selectByPrimaryKey(Long id);

    //用户
    Long selectUser();

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserQuery example);

    int updateByExample(@Param("record") User record, @Param("example") UserQuery example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
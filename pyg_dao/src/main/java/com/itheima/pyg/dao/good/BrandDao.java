package com.itheima.pyg.dao.good;


import com.itheima.pyg.pojo.good.Brand;
import com.itheima.pyg.pojo.good.BrandQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BrandDao {
    int countByExample(BrandQuery example);

    int deleteByExample(BrandQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(Brand record);

    int insertSelective(Brand record);

    List<Brand> selectByExample(BrandQuery example);

    Brand selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Brand record, @Param("example") BrandQuery example);

    int updateByExample(@Param("record") Brand record, @Param("example") BrandQuery example);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);


    /**
     * revise start: 批量删除 Long[] ids包装类型修改为 long[] ids 基本类型 gengweiwei
     * @param ids
     */
    //批量删除
    void deleteByPrimaryKeys(long[] ids);
    /**
     * revise end: 批量删除 Long[] ids包装类型修改为 long[] ids 基本类型 gengweiwei
     * @param ids
     */




    /**
     * 新增模板品牌列表查询
     */
    List<Map<String,String>> selectOptionList();
}
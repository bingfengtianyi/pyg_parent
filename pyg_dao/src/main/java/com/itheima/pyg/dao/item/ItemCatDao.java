package com.itheima.pyg.dao.item;

import com.itheima.pyg.pojo.item.ItemCat;
import com.itheima.pyg.pojo.item.ItemCatQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemCatDao {
    /**
     * 通过主键查找itemCat集合 11.28
     * @param传入parentId
     * @return返回itemCat集合
     */
    List<ItemCat> findItemCatListByParentId(@Param("parentId") Long parentId);

    int countByExample(ItemCatQuery example);

    int deleteByExample(ItemCatQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(ItemCat record);

    int insertSelective(ItemCat record);

    List<ItemCat> selectByExample(ItemCatQuery example);

    ItemCat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ItemCat record, @Param("example") ItemCatQuery example);

    int updateByExample(@Param("record") ItemCat record, @Param("example") ItemCatQuery example);

    int updateByPrimaryKeySelective(ItemCat record);

    int updateByPrimaryKey(ItemCat record);
}
package com.itheima.pyg.service.itemcat;

import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.pojo.item.ItemCat;

import java.util.List;

public interface ItemCatService {
    /**
     * 向数据库添加分类,用于excel数据导入数据库
     * @param itemCat
     */
    void save(ItemCat itemCat);
    /**
     * 根据父ID查询分类列表
     * @param parentId
     * @return
     */
    List<ItemCat> findByParentId(long parentId);

    /**
     * 查询商品分类实体
     * @param id
     * @return
     */
    ItemCat findOne(long id);

    /**
     * 查询全部商品分类
     * @return
     */
    List<ItemCat> findAll();


    /**
     * revise start: 增加带条件的分页查询接口  gengweiwei
     * @param pageNum
     * @param pageSize
     * @param itemCat
     * @return
     */
    PageResult search(Integer pageNum, Integer pageSize, ItemCat itemCat);

    /**
     * revise end: 增加带条件的分页查询接口  gengweiwei
     */

}

package com.itheima.pyg.service.itemcat;

import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.Result;
import com.itheima.pyg.pojo.item.ItemCat;

import java.util.List;

public interface ItemCatService {
    /**
     * 查找itemCat集合
     * @return
     */
    public List<ItemCat> findItemCatList();
    /**
     * 向数据库添加分类,用于excel数据导入数据库
     * @param itemCat
     */


    /**
     * revise start: 修改接口返回值   gengweiwei 2018-11-29
     */
    Result save(ItemCat itemCat);
    /**
     * 根据父ID查询分类列表
     * @param parentId
     * @return
     */

    /**
     * revise end: 修改接口返回值   gengweiwei 2018-11-29
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
     * 分页查询全部商品分类
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<ItemCat> findPage(Integer pageNum,Integer pageSize);


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


    /**
     * revise start: 删除分类   gengweiwei 2018-11-29
     * @param ids
     * @return
     */
    Result delete(long[] ids);
    /**
     * revise end: 删除分类  gengweiwei 2018-11-29
     */



    /**
     * 更新分类
     * @param itemCat
     * @return
     */
    Result update(ItemCat itemCat);

}

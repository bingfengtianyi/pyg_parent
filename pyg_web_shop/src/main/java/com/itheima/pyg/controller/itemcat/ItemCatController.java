package com.itheima.pyg.controller.itemcat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.Result;
import com.itheima.pyg.pojo.item.ItemCat;
import com.itheima.pyg.service.itemcat.ItemCatService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    /**
     * 根据父ID查询分类列表
     * @param parentId
     * @return
     */
    @RequestMapping("findByParentId")
    public List<ItemCat>    findByParentId(long parentId){
        return itemCatService.findByParentId(parentId);
    }

    /**
     * 查询商品分类实体
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public  ItemCat findOne(long id){
        return itemCatService.findOne(id);
    }

    /**
     * 查询商品全部分类
     * @return
     */
    @RequestMapping("findAll")
    public List<ItemCat>    findAll(){
        return itemCatService.findAll();
    }




    /**
     * revise start: 带条件分页查询  gengweiwei
     * @param pageNum
     * @param pageSize
     * @param itemCat
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(Integer pageNum, Integer pageSize, @RequestBody ItemCat itemCat) {
        String sellerId  = SecurityContextHolder.getContext().getAuthentication().getName();
        itemCat.setSellerId(sellerId);
        return itemCatService.search(pageNum, pageSize, itemCat);
    }


    /**
     * revise end: 带条件分页查询  gengweiwei
     */


    /**
     * revise start: 删除分类  gengweiwei 2018-11-30
     */
    @RequestMapping("delete")
    public Result delete(long[] ids){
        return itemCatService.delete(ids);
    }
    /**
     * revise start: 删除分类  gengweiwei  2018-11-30
     */

    /**
     * revise start: 更新分类  gengweiwei 2018-11-30
     */
    @RequestMapping("update")
    public Result   update( @RequestBody ItemCat itemCat){
        return itemCatService.update(itemCat);
    }
    /**
     * revise end: 更新分类  gengweiwei 2018-11-30
     */


    /**
     * 添加模板
     * @param itemCat
     * @return
     */
    @RequestMapping("add")
    public Result  add(@RequestBody ItemCat itemCat){
        String sellerId  = SecurityContextHolder.getContext().getAuthentication().getName();
        itemCat.setSellerId(sellerId);
        try {
            itemCatService.save(itemCat);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "添加失败");
        }
    }


}

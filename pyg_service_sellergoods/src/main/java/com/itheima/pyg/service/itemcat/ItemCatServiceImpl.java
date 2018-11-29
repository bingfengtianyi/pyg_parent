package com.itheima.pyg.service.itemcat;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pyg.dao.item.ItemCatDao;
import com.itheima.pyg.pojo.item.ItemCat;
import com.itheima.pyg.pojo.item.ItemCatQuery;
import com.itheima.pyg.service.itemcat.ItemCatService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Resource
    private ItemCatDao itemCatDao;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 查询各目录并进行分装
     * 11.28
     * @return
     */
    @Override
    public List<ItemCat> findItemCatList() {
        //从缓存中查找数据分类
        List<ItemCat> itemCatList = (List<ItemCat>) redisTemplate.boundHashOps("itemCat").get("itemCatList");
        //如果缓存中没有 ,向数据库中查询
        if (itemCatList == null) {
            //首先查询顶级的目录
     /*       List<ItemCat> itemCatListByParentId = itemCatDao.findItemCatListByParentId(0L);*/
          List<ItemCat> itemCatListByParentId= itemCatDao.findItemCatListByParentId(0L);
            //遍历一级分类的集合
            for (ItemCat itemCat1 : itemCatListByParentId) {
                //查询2级分类的集合将一级分类的Id作为条件
               /* List<ItemCat> itemCatListByParentId2 = itemCatDao.findItemCatListByParentId(itemCat1.getId());*/
                List<ItemCat> itemCatListByParentId2= itemCatDao.findItemCatListByParentId(itemCat1.getId());
                //遍历二级商品的集合
                for (ItemCat itemCat2 : itemCatListByParentId2) {
                    //查询三级商品的集合
                   /* List<ItemCat> itemCat3 = itemCatDao.findItemCatListByParentId(itemCat2.getId());*/
                    List<ItemCat> itemCat3 = itemCatDao.findItemCatListByParentId(itemCat2.getId());
                        //将遍历的3级商品的分类集合封装到2级商品的集合中
                        itemCat2.setItemCatList(itemCat3);
                    }
                    //再将二级的实体对象分装到一级目录的集合中
                itemCat1.setItemCatList(itemCatListByParentId2);
                }
                //最后将商品返回到集存储到Redis中
            redisTemplate.boundHashOps("itemCat").put("itemCatList",itemCatListByParentId);
            return itemCatListByParentId;
            }
            //不为空说明Redis中已经有数据了,直接返回即可
        return itemCatList;
    }


    @Override
    public void save(ItemCat itemCat) {

    }

    /**
     * 根据父ID查询分类列表
     * @param parentId
     * @return
     */
    @Override
    public List<ItemCat> findByParentId(long parentId) {
        List<ItemCat> itemCats = itemCatDao.selectByExample(null);
        if (itemCats!=null&&itemCats.size()>0){
            for (ItemCat itemCat : itemCats) {
                redisTemplate.boundHashOps("itemCat").put(itemCat.getName(),itemCat.getTypeId());
            }
        }
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        itemCatQuery.createCriteria().andParentIdEqualTo(parentId);
        return itemCatDao.selectByExample(itemCatQuery);
    }

    /**
     * 查询商品分类实体
     * @param id
     * @return
     */
    @Override
    public ItemCat findOne(long id) {
        return itemCatDao.selectByPrimaryKey(id);
    }

    /**
     * 查询全部商品分类
     * @return
     */
    @Override
    public List<ItemCat> findAll() {
        return itemCatDao.selectByExample(null);
    }
}

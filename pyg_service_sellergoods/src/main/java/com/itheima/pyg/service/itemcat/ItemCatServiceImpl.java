package com.itheima.pyg.service.itemcat;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.pyg.dao.item.ItemCatDao;
import com.itheima.pyg.entity.PageResult;
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
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(ItemCat itemCat) {

    }

    /**
     * 根据父ID查询分类列表
     *
     * @param parentId
     * @return
     */
    @Override
    public List<ItemCat> findByParentId(long parentId) {
        List<ItemCat> itemCats = itemCatDao.selectByExample(null);
        if (itemCats != null && itemCats.size() > 0) {
            for (ItemCat itemCat : itemCats) {
                redisTemplate.boundHashOps("itemCat").put(itemCat.getName(), itemCat.getTypeId());
            }
        }
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        itemCatQuery.createCriteria().andParentIdEqualTo(parentId);
        return itemCatDao.selectByExample(itemCatQuery);
    }

    /**
     * 查询商品分类实体
     *
     * @param id
     * @return
     */
    @Override
    public ItemCat findOne(long id) {
        return itemCatDao.selectByPrimaryKey(id);
    }

    /**
     * 查询全部商品分类
     *
     * @return
     */
    @Override
    public List<ItemCat> findAll() {
        return itemCatDao.selectByExample(null);
    }

    /**
     * 分页查询全部商品分类
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<ItemCat> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //进行查询
        Page<ItemCat> page = (Page<ItemCat>) itemCatDao.selectByExample(null);
        //封装PageResult对象
        PageResult<ItemCat> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }
}

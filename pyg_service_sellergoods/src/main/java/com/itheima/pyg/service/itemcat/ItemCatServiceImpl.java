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
import java.util.Set;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Resource
    private ItemCatDao itemCatDao;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;



    @Override
    public void save(ItemCat itemCat) {

        /**
         * revise start: 添加方法补充完成  gengweiwei
         */
        itemCatDao.insertSelective(itemCat);
        // 1 商家将新建品牌存入redis且设置以"ItemCat_status=0"为键表明为待审核状态,值为插入对象itemCat
        redisTemplate.boundSetOps("ItemCat_status=0").add(itemCat);
        //2 运营商可以通过下面.members方法取出存入在redis中的Set集合,遍历即可取出所有对象itemCat,
        // 后续建议将"ItemCat_status=0"更改为"ItemCat_status=1"
        Set members = redisTemplate.boundSetOps("ItemCat_status=0").members();
        for (Object member : members) {
            System.out.println("每一个itemCat对象明细如下"+member);
        }
        /**
         * revise end: 添加方法补充完成  gengweiwei
         */

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
            /**
             * revise start: 1 查询输入框输入条件封装到criteria对象中  gengweiwei
             */
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        itemCatQuery.createCriteria().andParentIdEqualTo(parentId);
        for (ItemCat itemCat : itemCats) {
            if (itemCat.getName()!=null&&!"".equalsIgnoreCase(itemCat.getName().trim())) {
                itemCatQuery.createCriteria().andNameLike("%"+itemCat.getName().trim()+"%");
            }
        }
            /**
             * revise start: 1 查询输入框输入条件封装到criteria对象中  gengweiwei
             */

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

    /**
     * revise start: 增加带条件的分页查询接口实现类  gengweiwei
     */
    @Override
    public PageResult search(Integer pageNum, Integer pageSize, ItemCat itemCat) {
        PageHelper.startPage(pageNum,pageSize);
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        if (itemCat.getName()!=null&&!"".equalsIgnoreCase(itemCat.getName().trim())) {
            itemCatQuery.createCriteria().andNameLike("%"+itemCat.getName()+"%");
        }
        if(itemCat.getSellerId()!=null&&!"".equalsIgnoreCase(itemCat.getSellerId().trim())){
            itemCatQuery.createCriteria().andSellerIdEqualTo(itemCat.getSellerId().trim());
        }
        Page<ItemCat> Page=(Page<ItemCat>) itemCatDao.selectByExample(itemCatQuery);
        return new PageResult(Page.getTotal(),Page.getResult());
    }

    /**
     * revise start: 增加带条件的分页查询接口实现类  gengweiwei
     */

}

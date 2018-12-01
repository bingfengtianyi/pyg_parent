package com.itheima.pyg.service.brand;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.pyg.dao.good.BrandDao;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.Result;
import com.itheima.pyg.pojo.good.Brand;
import com.itheima.pyg.pojo.good.BrandQuery;
import com.itheima.pyg.service.brand.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class BrandServiceImpl   implements BrandService {

    @Resource
    private BrandDao brandDao;


    /**
     * revise start: 添加RedisTemplate字段  gengweiwei
     */
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * revise end: 添加RedisTemplate字段  gengweiwei
     */



    @Override
    public List<Brand> findAllBrands() {
        return brandDao.selectByExample(null);
    }

    /**
     * 分页查询品牌信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findBrandListByPage(Integer pageNum, Integer pageSize) {
        //设置分页查询条件
        PageHelper.startPage(pageNum,pageSize);
        //进行查询
        Page<Brand> page = (Page<Brand>) brandDao.selectByExample(null);
        //封装PageResult对象
        PageResult<Brand>   pageResult = new PageResult<>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 带条件分页查询
     * @param pageNum
     * @param pageSize
     * @param brand
     * @return
     */
    @Override
    public PageResult searchBrands(Integer pageNum, Integer pageSize, Brand brand) {
        //设置分页条件
        PageHelper.startPage(pageNum,pageSize);
        //获得封装查询条件的对象
        BrandQuery brandQuery = new BrandQuery();
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        //封装查询条件
        if (brand.getName()!=null && !"".equalsIgnoreCase(brand.getName().trim())){
            criteria.andNameLike("%"+brand.getName().trim()+"%");
        }
        if (brand.getFirstChar()!=null && !"".equalsIgnoreCase(brand.getFirstChar().trim())){
            criteria.andFirstCharEqualTo(brand.getFirstChar().trim());
        }


        /**
         * revise start: 1 判断sellerId且封装到criteria对象中,详见对应BrandQuery类增加条件  gengweiwei
         */
        if (brand.getSellerId()!=null&&!"".equals(brand.getSellerId().trim())) {

            criteria.andSellerIdEqualTo(brand.getSellerId().trim());
        }
        /**
         * revise end: 1 判断sellerId且封装到criteria对象中,详见对应BrandQuery类增加条件  gengweiwei
         */



        //设置按照id倒序排列
        brandQuery.setOrderByClause("id desc");
        //按照条件分页查询
        Page<Brand> page = (Page<Brand>) brandDao.selectByExample(brandQuery);
        PageResult<Brand> pageResult = new PageResult<>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 新建品牌
     * @param brand
     * @return
     */
    @Override
    @Transactional
    public Result addBrand(Brand brand) {
        Result result = new Result();
        try {
            brandDao.insertSelective(brand);

            /**
             * revise start: 存入Set类型  gengweiwei
             */
            // 1 商家将新建品牌存入redis且设置以"brand_status=0"为键表明为待审核状态,值为插入对象brand
            redisTemplate.boundSetOps("Brand_status=0").add(brand);
            //2 运营商可以通过下面.members方法取出存入在redis中的Set集合,遍历即可取出所有对象brand,
            // 后续建议将"brand_status=0"更改为"brand_status=1"
            Set members = redisTemplate.boundSetOps("Brand_status=0").members();
            for (Object member : members) {
                System.out.println("每一个brand对象明细如下"+member);
            }
            /**
             * revise end: 存入Set类型  gengweiwei
             */


            result.setFlag(true);
            result.setMessage("创建品牌成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("创建品牌失败");
        }finally {
            return result;
        }
    }

    /**
     * 根据id查询品牌信息
     * @param id
     * @return
     */
    @Override
    public Brand findOne(long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    /**
     * 更新品牌信息
     * @param brand
     * @return
     */
    @Override
    @Transactional
    public void updateBrand(Brand brand) {
        brandDao.updateByPrimaryKeySelective(brand);
    }






    /**
     * revise start: 删除品牌 gengweiwei
     * @param ids
     */
    @Override
    @Transactional
    public void deleteBrandsByIds(long[] ids) {
        if (ids!=null&&ids.length>0){
            brandDao.deleteByPrimaryKeys(ids);
        }
    }
    /**
     * revise end: 删除品牌 gengweiwei
     * @param ids
     */







    /*** 查询品牌列表项,用作模板关联品牌新增时下拉框数据显示*/
    @Override
    public List<Map<String, String>> selectOptionList() {
        return brandDao.selectOptionList();
    //return null
    }

}

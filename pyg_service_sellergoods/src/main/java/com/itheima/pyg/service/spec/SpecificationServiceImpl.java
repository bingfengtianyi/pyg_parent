package com.itheima.pyg.service.spec;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.pyg.dao.specification.SpecificationDao;
import com.itheima.pyg.dao.specification.SpecificationOptionDao;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.Result;
import com.itheima.pyg.entity.vo.SpecificationVo;
import com.itheima.pyg.pojo.specification.Specification;
import com.itheima.pyg.pojo.specification.SpecificationOption;
import com.itheima.pyg.pojo.specification.SpecificationOptionQuery;
import com.itheima.pyg.pojo.specification.SpecificationQuery;
import com.itheima.pyg.service.spec.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class SpecificationServiceImpl   implements SpecificationService {

    @Resource
    private SpecificationDao    specificationDao;

    @Resource
    private SpecificationOptionDao specificationOptionDao;


    /**
     * revise start: 添加RedisTemplate字段  gengweiwei
     */
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * revise end: 添加RedisTemplate字段  gengweiwei
     */




    /**
     * 查询全部规格信息
     * @return
     */
    @Override
    public List<Specification> findAll() {
        return specificationDao.selectByExample(null);
    }

    /**
     * 分页查询规格信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<Specification> page = (Page<Specification>) specificationDao.selectByExample(null);
        PageResult<Specification> pageResult = new PageResult<>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 带条件的分页查询
     * @param pageNum
     * @param pageSize
     * @param specification
     * @return
     */
    @Override
    public PageResult search(Integer pageNum, Integer pageSize, Specification specification) {
        PageHelper.startPage(pageNum,pageSize);
        SpecificationQuery specificationQuery = new SpecificationQuery();
        if (specification.getSpecName()!=null&&!"".equalsIgnoreCase(specification.getSpecName().trim())){
            specificationQuery.createCriteria().andSpecNameLike("%"+specification.getSpecName()+"%");
        }

        /**
         * revise start: 1 判断sellerId且封装到criteria对象中,详见对应SpecificationQuery类增加条件  gengweiwei
         */

        if(specification.getSellerId()!=null&&!"".equalsIgnoreCase(specification.getSellerId().trim())){
            specificationQuery.createCriteria().andSellerIdEqualTo(specification.getSellerId().trim());
        }

        /**
         * revise end: 1 判断sellerId且封装到criteria对象中,详见对应SpecificationQuery类增加条件  gengweiwei
         */

        specificationQuery.setOrderByClause("id desc");
        Page<Specification> page = (Page<Specification>) specificationDao.selectByExample(specificationQuery);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 新增规格
     * @param specificationVo
     */
    @Override
    public Result add(SpecificationVo specificationVo) {
        Result result = null;
        try {

            /**
             * revise start: 存入Set类型  gengweiwei
             */
            Specification specification = specificationVo.getSpecification();
            specificationDao.insertSelective(specification);
            // 1 商家将新建规格存入redis且以"Specification_status=0"为键表明为待审核状态,值为插入对象specification
            redisTemplate.boundSetOps("Specification_status=0").add(specification);
            //2 运营商可以通过下面.members方法取出存入在redis中的Set集合,遍历即可取出所有对象specification,
            // 后续建议将"Specification_status=0"更改为"Specification_status=1"
            Set members = redisTemplate.boundSetOps("Specification_status=0").members();
            for (Object member : members) {
                System.out.println("每一个specification对象明细如下"+member);
            }
            /**
             * revise end: 存入Set类型  gengweiwei
             */
            List<SpecificationOption> specificationOptionList = specificationVo.getSpecificationOptionList();
            if (specificationOptionList!=null&&specificationOptionList.size()>0){
                for (SpecificationOption specificationOption : specificationOptionList) {
                    specificationOption.setSpecId(specification.getId());
                }
                specificationOptionDao.insertSelectives(specificationOptionList);
            }
            result = new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"添加失败");
        } finally {
            return result;
        }
    }

    /**
     * 查询规格及规格项的包装类对象
     * @param id
     * @return
     */
    @Override
    public SpecificationVo findOne(long id) {
        Specification specification = specificationDao.selectByPrimaryKey(id);
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        specificationOptionQuery.createCriteria().andSpecIdEqualTo(specification.getId());
        List<SpecificationOption> specificationOptionList = specificationOptionDao.selectByExample(specificationOptionQuery);
        return new SpecificationVo(specification,specificationOptionList);
    }

    /**
     * 更新规格
     * @param specificationVo
     * @return
     */
    @Override
    public Result update(SpecificationVo specificationVo) {
        Result result = null;
        try {
            Specification specification = specificationVo.getSpecification();
            specificationDao.updateByPrimaryKeySelective(specification);
            SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
            specificationOptionQuery.createCriteria().andSpecIdEqualTo(specification.getId());
            specificationOptionDao.deleteByExample(specificationOptionQuery);
            List<SpecificationOption> specificationOptionList = specificationVo.getSpecificationOptionList();
            if (specificationOptionList!=null&&specificationOptionList.size()>0){
                for (SpecificationOption specificationOption : specificationOptionList) {
                    specificationOption.setSpecId(specification.getId());
                }
                specificationOptionDao.insertSelectives(specificationOptionList);
            }
            result = new Result(true,"更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"更新失败");
        } finally {
            return result;
        }
    }

    /**
     * 删除规格
     * @param ids
     * @return
     */
    @Override
    public Result delete(long[] ids) {
        Result result = null;
        try {
            SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
            List<Long>  idsList = new ArrayList<>();
            for (long id : ids) {
                idsList.add(id);
            }
            specificationOptionQuery.createCriteria().andSpecIdIn(idsList);
            specificationOptionDao.deleteByExample(specificationOptionQuery);
            SpecificationQuery specificationQuery = new SpecificationQuery();
            specificationQuery.createCriteria().andIdIn(idsList);
            specificationDao.deleteByExample(specificationQuery);
            result = new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"删除失败");
        } finally {
            return  result;
        }
    }

    /**
     * 查询规格信息用于模板信息关联
     * @return
     */
    @Override
    public List<Map<String, String>> selectOptionList() {
        return specificationDao.selectOptionList();
    }

    @Override
    public void save(Specification specification) {
        specificationDao.insertSelective(specification);
    }
}

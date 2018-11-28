package com.itheima.pyg.controller.brand;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.Result;
import com.itheima.pyg.pojo.good.Brand;
import com.itheima.pyg.service.brand.BrandService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * revise start:新增controller BrandController处理品牌申请相关业务 gengweiwei
 *
 * */

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;


    /**
     * 根据条件分页查询品牌
     * @param pageNum
     * @param pageSize
     * @param brand
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(Integer pageNum, Integer pageSize, @RequestBody Brand brand){
        String sellerId  = SecurityContextHolder.getContext().getAuthentication().getName();
        brand.setSellerId(sellerId);
        try {
            return brandService.searchBrands(pageNum,pageSize,brand);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 新建品牌
     * @param brand
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand){
        String sellerId  = SecurityContextHolder.getContext().getAuthentication().getName();
        brand.setSellerId(sellerId);
        try {
            brandService.addBrand(brand);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "添加失败");
        }
    }


    /**
     * 根据ids删除品牌
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(long[] ids) throws Exception {

        try {
            brandService.deleteBrandsByIds(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }


    /**
     * 更新数据前,根据id查询品牌信息用于回显
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public Brand findOne(long id){
        return brandService.findOne(id);
    }


    /**
     * 更新品牌数据
     * @param brand
     * @return
     */
    @RequestMapping("update")
    public Result updateBrand(@RequestBody Brand brand){
        Result result =  null;
        try {
            brandService.updateBrand(brand);
            result = new Result(true,"更新品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"更新品牌失败");
        }finally {
            return result;
        }
    }


    /**
     * 查询品牌列表项,用作模板关联品牌新增时下拉框数据显示
     * @return
     */
    @RequestMapping("selectOptionList")
    public List<Map<String,String>> selectOptionList(){
        return brandService.selectOptionList();
    }

}

/**
 * revise end:新增controller BrandController处理品牌申请相关业务 gengweiwei
 *
 * */
package com.itheima.pyg.controller.template;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.Result;
import com.itheima.pyg.pojo.template.TypeTemplate;
import com.itheima.pyg.service.template.TypeTemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("typeTemplate")
public class TypeTemplateController {

    @Reference
    private TypeTemplateService typeTemplateService;

    /**
     * 查询模板实体对象,确定模板加载后的品牌和扩展属性列表
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public TypeTemplate findOne(long id){
        return  typeTemplateService.findOne(id);
    }

    /**
     * 根据模板id查询对应的规格和规格选项
     * @param id
     * @return
     */
    @RequestMapping("findBySpecList")
    public List<Map>    findBySpecList(long id){
        return typeTemplateService.findBySpecList(id);
    }


    /**
     * revise start:新增 删除模板  带条件的分页查询  添加模板  更新模板 业务 gengweiwei
     *
     * */

    /**
     * 删除模板
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    public Result delete(long[] ids){
        return typeTemplateService.delete(ids);
    }


    /**
     * 带条件的分页查询
     * @param pageNum
     * @param pageSize
     * @param typeTemplate
     * @return
     */
    @RequestMapping("search")
    public PageResult search(Integer pageNum, Integer pageSize, @RequestBody TypeTemplate typeTemplate){
        return typeTemplateService.search(pageNum,pageSize,typeTemplate);
    }

    /**
     * 添加模板
     * @param typeTemplate
     * @return
     */
    @RequestMapping("add")
    public Result   add(@RequestBody TypeTemplate typeTemplate){
        return typeTemplateService.add(typeTemplate);
    }

    /**
     * 更新模板
     * @param typeTemplate
     * @return
     */
    @RequestMapping("update")
    public Result   update(@RequestBody TypeTemplate typeTemplate){
        return typeTemplateService.update(typeTemplate);
    }


    /**
     * revise end:新增 删除模板  带条件的分页查询  添加模板  更新模板 业务 gengweiwei
     *
     * */


    /**
     * 根据模板id查询对应的规格和规格选项
     * @param id
     * @return
     */
    @RequestMapping("findByBrandList")
    public List<Map>    findByBrandList(long id){
        return typeTemplateService.findByBrandList(id);
    }



}

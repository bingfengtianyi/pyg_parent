package com.itheima.pyg.controller.itemCat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.pojo.item.ItemCat;
import com.itheima.pyg.service.itemcat.ItemCatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    /**
     * 11.28调用业务层方法
     * @return
     */
    @RequestMapping("/findItemCatList")
    public List<ItemCat> findItemCatList() {
        return itemCatService.findItemCatList();
    }

}

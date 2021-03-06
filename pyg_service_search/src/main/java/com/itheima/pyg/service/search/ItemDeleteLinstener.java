package com.itheima.pyg.service.search;

import com.alibaba.fastjson.JSON;
import com.itheima.pyg.pojo.item.Item;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Map;

@Component
public class ItemDeleteLinstener implements MessageListener {

    @Resource
    private SolrTemplate solrTemplate;

    @Override
    public void onMessage(Message message) {

        try {
            // 接收消息
            TextMessage textMessage = (TextMessage) message;
            String json = textMessage.getText();
            // 将json转换为itemList对象
            List<String> itemIds = JSON.parseArray(json, String.class);
            System.out.println("监听接收itemIds"+itemIds);
            solrTemplate.deleteById(itemIds);
            solrTemplate.commit();

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}

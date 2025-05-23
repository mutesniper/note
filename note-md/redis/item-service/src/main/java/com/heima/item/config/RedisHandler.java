package com.heima.item.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.item.pojo.Item;
import com.heima.item.pojo.ItemStock;
import com.heima.item.service.IItemService;
import com.heima.item.service.IItemStockService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisHandler implements InitializingBean {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IItemStockService stockService;
    @Autowired
    private IItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化缓存
        //1.查询商品信息
        List<Item> itemList=itemService.list();
        //2.放入缓存
        for (Item item : itemList) {
            //2.1把item序列化为json
            String json = MAPPER.writeValueAsString(item);
            //2.2存入redis
            redisTemplate.opsForValue().set("item:id:"+item.getId(),json);
        }
        //3.查询商品库存信息
        List<ItemStock> stockList=stockService.list();
        //4.放入缓存
        for (ItemStock stock : stockList) {
            //2.1把stock序列化为json
            String json = MAPPER.writeValueAsString(stock);
            //2.2存入redis
            redisTemplate.opsForValue().set("item:stock:id:"+stock.getId(),json);
        }
    }
}

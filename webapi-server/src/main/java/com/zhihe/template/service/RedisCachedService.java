package com.zhihe.template.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCachedService {
    @Resource(name="stringRedisTemplate")
    StringRedisTemplate stringRedisTemplate;


    @Resource(name="redisTemplate")
    RedisTemplate<Object, Object> redisTemplate;


    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOps;

    /**
     * 设置缓存
     *
     * @param key：关键字
     * @param exprieTime：过期时间，单位秒（例如exprieTime=30，为30秒） 填0将不设置
     * @param value：值
     */
    public void set(String key, int exprieTime, Object value) {
        valOps.set(key, value);
        if(exprieTime>0)
        {
            redisTemplate.expire(key, exprieTime, TimeUnit.SECONDS);
        }
    }
    /**
     * 设置设备缓存
     *
     * @param key：关键字
     * @param value：值
     */
    public void set(String key, Object value) {
        valOps.set(key, value);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取缓存值
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return valOps.get(key);
    }

    /**
     * 获得缓存中的数据并重置其过期时间.
     *
     * @param key
     * @param exprieTime
     */
    public Object getAndTouch(String key, int exprieTime) {
        Object value = valOps.get(key);
        if (value != null) {
            redisTemplate.expire(key, exprieTime, TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 判断缓存中是否有对应的value
     * 暂不建议使用，存在不准确的情况
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        boolean hasOk =  redisTemplate.hasKey(key);
        if(hasOk)
        {
            Long exptime = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            if(exptime==0)//已经到期尚未删除的Key。
            {
                hasOk =  false;
                return hasOk;
            }
        }
        return hasOk;
    }
    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void removeKeys(List<String> keys) {
        for (String key : keys) {
            delete(key);
        }
    }
}
package com.hidiu.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author fancie
 * @title: RedisUtil
 * @projectName project-frame
 * @description: TODO
 * @date 2022/2/8 下午8:32
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 添加geo节点
     *
     * @param key
     * @param point
     * @param member
     */
    public void addPoint(String key, Point point, Object member) {
        redisTemplate.opsForGeo().add(key, point, member);
    }

    /**
     * 以point为中心，查找半径radius范围内的节点
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> radius(String key, Object member, Double radius, int size) {
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending().limit(size);
        Distance distance = new Distance(radius, Metrics.MILES);
        return redisTemplate.opsForGeo().radius(key, member, distance, args);
    }

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Set<String> keysSet(String key) {
        try {
            return redisTemplate.keys(key);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }


    /**
     * 失效所有的key
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public void expireKeys(String key) {
        try {
            Set<String> keys = redisTemplate.keys(key);
            for (String k : keys) {
                redisTemplate.expire(k, 0l, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("execute fail", e);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Iterator<String> keysIterator(String key) {
        try {
            return redisTemplate.keys(key).iterator();
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void deleteByKeys(Set<String> key) {
        if (key != null && key.size() > 0) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));

            }
        }
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        Object ret = key == null ? null : redisTemplate.opsForValue().get(key);
        log.debug("■■■RedisUtils■■■:{}={}", key, ret);
        return ret;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }

    }

    /**
     * 设置原子锁setNx
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, Object value) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 递增
     */
    public long incr(String key, long delta) {
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delta;
    }

    /**
     * 递减
     */
    public long decr(String key, long delta) {
        if (delta <= 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {

        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * HashGet
     * 获取hash中的所有key
     *
     * @param key 键 不能为null
     * @return 值
     */
    public Set<Object> hgetKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * HashGet
     *
     * @param key 键 不能为null
     * @return 值
     */
    public List<Object> hgetValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    public boolean hmsetnew(String key, Map<Object, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    public boolean hSetDelete(String key, String item) {
        try {
            redisTemplate.opsForHash().delete(key, item);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public long hIncrement(String key, String item, long by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }


    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> zGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }

    /**
     * 根据key获取Set中的所有值，按Score从大到小排列
     *
     * @param key 键
     * @return
     */
    public Set<Object> zGetReverse(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }


    public Set<ZSetOperations.TypedTuple<Object>> zGetSet1(String key, long start, long end) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
            return tuples;
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }


    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }


    /**
     * 取差集
     *
     * @param key1 键
     * @param key2 键
     * @return
     */
    public Set<Object> sDifference(String key1, String key2) {
        try {
            return redisTemplate.opsForSet().difference(key1, key2);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }

//    public long incr(String key, long delta){
//        if(delta<0){
//            throw new RuntimeException("递增因子必须大于0");
//        }
//        return redisTemplate.opsForValue().increment(key, delta);
//    }

    /**
     * 获取有过期时间的自增长ID
     *
     * @param key
     * @param expireTime
     * @return
     */
    public long generate(String key, Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long expire = counter.getExpire();
        if (expire == -1) {
            counter.expireAt(expireTime);
        }
        return counter.incrementAndGet();
    }

    /***
     生成订单编号
     **/
    public String generateOrderId() {
        //生成id为当前日期（yyMMddHHmmss）+6位（从000000开始不足位数补0）
        LocalDateTime now = LocalDateTime.now();
        String orderIdPrefix = getOrderIdPrefix(now);//生成yyyyMMddHHmmss
        String orderId = orderIdPrefix + String.format("%1$06d", generate(orderIdPrefix, getExpireAtTime(now)));
        return orderId;
    }

    public static String getOrderIdPrefix(LocalDateTime now) {
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }

    //设置过期时间为80秒
    public Date getExpireAtTime(LocalDateTime now) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = now.plusSeconds(80);
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * 随机不重复的
     *
     * @param key
     * @param count
     * @return
     */
    public Set<Object> distinctRandomMembers(String key, long count) {
        try {
            return redisTemplate.opsForSet().distinctRandomMembers(key, count);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("execute fail", e);
            return 0;
        }
    }

    public void sSetWithTTL(String key, long expired, Object... values) {
        try {
            redisTemplate.opsForSet().add(key, values);
            redisTemplate.expire(key, expired, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("execute fail", e);
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            log.error("execute fail", e);
            return 0;
        }
    }


    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("execute fail", e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("execute fail", e);
            return 0;
        }
    }
    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("execute fail", e);
            return 0;
        }
    }

    /**
     * 获取Zset size
     *
     * @param key
     * @return
     */
    public long lGetZSetSize(String key) {
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            log.error("execute fail", e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("execute fail", e);
            return 0;
        }
    }


    public boolean zsetAdd(String key, Object value, long score) {
        try {
            redisTemplate.opsForZSet().add(key, value, score);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    public boolean zSetHas(String key, Object value) {
        try {
            Double score = redisTemplate.opsForZSet().score(key, value);
            if (score == null || score == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    public boolean zsetRemove(String key, Object value) {
        try {
            redisTemplate.opsForZSet().remove(key, value);
            return true;
        } catch (Exception e) {
            log.error("execute fail", e);
            return false;
        }
    }

    public void zSetAdd1(String key, Object value, double score) {

        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 获取当前数据的排名
     *
     * @param key 键
     * @return
     */
    public Long getRanking(String key, String value) {
        Long rankNum = redisTemplate.opsForZSet().reverseRank(key, value);
        return rankNum;
    }

    /**
     * 根据key获取Set中的获取值
     *
     * @param key 键
     * @return
     */
    public Set<Object> zGetSet(String key, long start, long end) {
        try {
            long size = redisTemplate.opsForZSet().size(key);
            if (size < end) {
                end = size;
            }
            if (start > size) {
                start = 0;
            }
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }

    public long zSetSize(String key) {
        try {
            Long count = redisTemplate.opsForZSet().size(key);
            return count == null ? 0 : count;
        } catch (Exception e) {
            log.error("execute fail", e);
            return 0;
        }
    }

    /**
     * 获取该栏目的得分
     *
     * @param key
     * @param item
     * @return
     */
    public Double zGetScore(String key, String item) {
        try {
            Double score = redisTemplate.opsForZSet().score(key, item);
            if (score == null) {
                return new Double(0);
            } else {
                return score;
            }
        } catch (Exception e) {
            log.error("execute fail", e);
            return new Double(0);
        }
    }

    /**
     * 根据key获取Set中的所有值
     * 逆向排序
     *
     * @param key 键
     * @return
     */
    public Set<Object> zGetSetByReverseRange(String key, long start, long end) {
        try {
            long size = redisTemplate.opsForZSet().size(key);
            if (size < end) {
                end = size;
            }
            if (start > size) {
                start = 0;
            }

            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            log.error("execute fail", e);
            return null;
        }
    }
}

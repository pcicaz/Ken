package com.csczn.test.morphia;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.logging.Logger;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.csczn.test.morphia.bean.ObjectOne;
import com.csczn.test.morphia.bean.ObjectTwo;
import com.csczn.test.morphia.dao.ITestDao;
import com.csczn.test.morphia.dao.TestDao;
import com.mongodb.WriteResult;

public class MorphiaTest {
    private static final Logger logger = MorphiaLoggerFactory.get(MorphiaTest.class);
    
    private ITestDao dao;
    
    @SuppressWarnings("resource")
    @Before
    public void init() {
        dao = (TestDao)new ClassPathXmlApplicationContext("config/spring/applicationContext.xml").getBean("dao");
    }
    
    @After
    public void destory() {
        logger.info("done!");
    }
    
    /**
     * 将hotelList插入数据库
     * <p>
     * 
     * @Title: saveObjectOneList
     *         </p>
     * 
     * @param dao
     * @param hotelList
     */
    public void save() {
        List<ObjectOne> hotelList = new ArrayList<ObjectOne>();
        for (int i = 0; i < 100; i++) {
            ObjectOne one = new ObjectOne();
            one.setName("编号为[" + i + "]的旅店");
            one.setStars(i % 10);
            ObjectTwo two = new ObjectTwo();
            two.setCountry("中国");
            two.setCity("北京");
            two.setStreet("上帝南路");
            two.setPostCode("10000" + (i % 10));
            one.setTwo(two);
            hotelList.add(one);
        }
        for (ObjectOne hotel : hotelList) {
            Key<ObjectOne> key = getDao().save(hotel);
            logger.info("id为[" + key.getId() + "]的记录已被插入");
        }
    }
    
    /**
     * 更新操作测试
     * <p>
     * 
     * @Title: updateTest
     *         </p>
     * 
     * @throws Exception
     */
    public void update()
        throws Exception {
        // 生成查询条件
        Query<ObjectOne> q = getDao().createQuery().field("stars").greaterThanOrEq(9);
        // 生成更新操作
        UpdateOperations<ObjectOne> ops = getDao().createUpdateOperations().set("two.city", "shanghai").inc("stars");
        UpdateResults ur = getDao().updateFirst(q, ops);
        if (ur.getUpdatedExisting()) {
            logger.info("更新成功，更新条数为[" + ur.getUpdatedCount() + "]，插入条数为[" + ur.getInsertedCount() + "]");
        }
        else {
            logger.info("没有记录符合更新条件");
        }
    }
    
    /**
     * 删除操作测试
     * <p>
     * 
     * @Title: deleteTest
     *         </p>
     */
    public void delete() {
        // 根据ID删除对象，这里不是太规范
        ObjectId id = (ObjectId)getDao().findIds().get(0);
        WriteResult w = getDao().deleteById(id);
        logger.info("根据id删除记录，id：" + id + ":" + w.getN());
        
        // 删除stars<=100的对象
        Query<ObjectOne> q = getDao().createQuery().field("stars").greaterThanOrEq(100);
        w = getDao().deleteByQuery(q);
        logger.info("删除stars<=100的对象:" + w.getN());
        
    }
    
    /**
     * 查询测试
     * <p>
     * 
     * @Title: queryObjectOne
     *         </p>
     */
    @Test
    public void query() {
        logger.info(getDao().count(getDao().createQuery().order("-name"))+"");
        
        logger.info("\n条件查询");
        for (ObjectOne hotel : getDao().find(getDao().createQuery().field("name").equal("编号为[84]的旅店"))) {
            logger.info(hotel.getName());
        }
        
        logger.info("\n条件查询(子对象)");
        for (ObjectOne hotel : getDao().find(getDao().createQuery().filter("two.postCode =", "100004"))) {
            logger.info(hotel.getName() + ":" + hotel.getTwo().getPostCode());
        }
        
        logger.info("\n条件查询（or）");
        Query<ObjectOne> q = getDao().createQuery();
        q.or(q.criteria("name").equal("编号为[85]的旅店"), q.criteria("two.postCode").equal("100004"));
        QueryResults<ObjectOne> qr = getDao().find(q);
        for (ObjectOne hotel : qr) {
            logger.info(hotel.getName() + ":" + hotel.getTwo().getPostCode());
        }
        
        logger.info("\n按name的倒序查询");
        for (ObjectOne hotel : getDao().find(getDao().createQuery().order("-name"))) {
            logger.info(hotel.getName());
        }
        
        logger.info("\n从第2条记录开始取10条");
        for (ObjectOne hotel : getDao().find(getDao().createQuery().offset(1).limit(10))) {
            logger.info(hotel.getName());
        }
        
        logger.info("\n统计star大于等于9的数目：" + getDao().count(getDao().createQuery().field("stars").greaterThanOrEq(9)));
        //
        // // 显示符合条件的记录ID
        // List<ObjectId> ids = getDao().findIds("stars", 8);
        // logger.info("\ngetDao().findIds(\"stars\", 8)=");
        // for (ObjectId id : ids) {
        // logger.info(id);
        // }
    }
    
    /** 
     * 测试乐观锁<p>
     * 
     */
    public void version() {
        logger.info("\n条件查询");
        ObjectOne obj = getDao().findOne(getDao().createQuery().field("name").equal("编号为[84]的旅店"));
        logger.info(obj.getName() + ":" + obj.getStars() + ":" + obj.getVersion());
        ObjectOne newObj = (ObjectOne)obj.clone();
        logger.info(newObj.getName() + ":" + newObj.getStars() + ":" + newObj.getVersion());
        obj.setStars(obj.getStars() + 1);
        getDao().save(obj);
        logger.info(obj.getName() + ":" + obj.getStars() + ":" + obj.getVersion());
        newObj.setStars(newObj.getStars() + 1);
        getDao().save(newObj);
    }
    
    public ITestDao getDao() {
        return dao;
    }
    
    public void setDao(ITestDao dao) {
        this.dao = dao;
    }
}

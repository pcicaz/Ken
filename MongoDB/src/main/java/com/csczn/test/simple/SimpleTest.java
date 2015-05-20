package com.csczn.test.simple;

//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import org.bson.Document;
//import org.bson.types.ObjectId;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.mongodb.Block;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoCredential;
//import com.mongodb.MongoException;
//import com.mongodb.QueryOperators;
//import com.mongodb.ReadPreference;
//import com.mongodb.ServerAddress;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Filters;
//import com.mongodb.client.model.UpdateOptions;
//import com.mongodb.client.result.UpdateResult;

public class SimpleTest
{
    
//    private MongoClient client = null;
//    
//    private MongoDatabase db;
//    
//    private MongoCollection<Document> collection;
//    
//    @Before
//    public void init()
//    {
//        
//        try
//        {
//            
//            List<ServerAddress> seeds = new LinkedList<ServerAddress>();
//            // seeds.add(new ServerAddress("localhost", 27017));
//            seeds.add(new ServerAddress("localhost", 9080));
//            seeds.add(new ServerAddress("localhost", 9081));
//            seeds.add(new ServerAddress("localhost", 9082));
//            List<MongoCredential> credentialsList = new LinkedList<MongoCredential>();
//             credentialsList.add(MongoCredential.createCredential("root",
//             "admin", "root".toCharArray()));
//            // 允许一次验证多个不同的数据库
//            client = new MongoClient(seeds, credentialsList);
//            // 设置读写分离，主写从读
//            client.setReadPreference(ReadPreference.secondary());
//        }
//        catch (MongoException e)
//        {
//            e.printStackTrace();
//        }
//        
//        // 获取temp DB；如果默认没有创建，mongodb会自动创建
//        
//        db = client.getDatabase("test");
//        
//        // 获取users DBCollection；如果默认没有创建，mongodb会自动创建
//        
//        collection = db.getCollection("users");
//        
//    }
//    
//    @After
//    public void destory()
//    {
//        if (client != null)
//            client.close();
//        
//        client = null;
//        db = null;
//        collection = null;
//        System.gc();
//        
//    }
//    
//    public void print(Object o)
//    {
//        System.out.println(o);
//    }
//    
//    /**
//     * <b>function:</b> 查询所有数据
//     */
//    private void queryAll()
//    {
//        print("查询users的所有数据：");
//        // db游标
//        FindIterable<Document> it = collection.find();
//        
//        it.forEach(new Block<Document>()
//        {
//            @Override
//            public void apply(final Document document)
//            {
//                System.out.println(document);
//            }
//        });
//    }
//    
//    @Test
//    public void add()
//    {
//        print("begin add------------------------------------------------------------------------");
//        // 先查询所有数据
//        queryAll();
//        
//        Document user = new Document();
//        user.put("name", "test");
//        user.put("age", 24);
//        // users.save(user)保存，getN()获取影响行数
//        // print(collection.insert(user).getN());
//        collection.insertOne(user);
//        // 扩展字段，随意添加字段，不影响现有数据
//        user.put("sex", "男");
//        collection.replaceOne(new Document("sex", "男"), user);
//        
//        // 添加List集合
//        List<Document> list = new ArrayList<Document>();
//        list.add(user);
//        Document user2 = new Document("name", "lucy");
//        user.put("age", 22);
//        list.add(user2);
//        // 添加List集合
//        collection.insertMany(list);
//        
//        // 查询下数据，看看是否添加成功
//        print("count: " + collection.count());
//        
//        queryAll();
//    }
//    
//    // @Test
//    public void query()
//    {
//        print("begin query------------------------------------------------------------------------");
//        queryAll();
//        print("find id = 4de73f7acd812d61b4626a77: "
//            + collection.count(new Document("_id", new ObjectId("4de73f7acd812d61b4626a77"))));
//        print("find age = 24: " + collection.count(new Document("age", 24)));
//        
//        print("find age >= 24: " + collection.count(new Document("age", new Document(QueryOperators.GTE, 24))));
//        print("find age <= 24: " + collection.count(new Document("age", new Document(QueryOperators.LTE, 24))));
//        print("查询age!=25：" + collection.count(new Document("age", new Document(QueryOperators.NE, 25))));
//        print("查询age in 25/26/27：" + collection.count(Filters.in("age", 25, 26, 27)));
//        print("查询age not in 25/26/27：" + collection.count(Filters.nin("age", 25, 26, 27)));
//        
//        FindIterable<Document> it = collection.find(new Document("sex", new Document(QueryOperators.EXISTS, true)));
//        print("查询age exists 排序：");
//        it.forEach(new Block<Document>()
//        {
//            @Override
//            public void apply(final Document document)
//            {
//                System.out.println(document);
//            }
//        });
//        // print("只查询age属性："
//        // + collection.count(Filters.new Document("age", true)));
//        
//        // 查询修改、删除
//        // print("findAndRemove 查询age=25的数据，并且删除: "
//        // + collection.findOneAndDelete(new Document("age", 25)));
//        // // 查询age=26的数据，并且修改name的值为Abc
//        // print("findAndModify: "
//        // + collection.findOneAndUpdate(new Document("age", 25),
//        // new Document("$set", new Document("name", "Abc"))));
//    }
//    
//    public void modify()
//    {
//        // 与上一版最大不同，update必须使用修改器包装，否者无法使用，如要进行对象替换需要使用replace
//        print("begin modify------------------------------------------------------------------------");
//        queryAll();
//        UpdateOptions uo = new UpdateOptions();
//        uo.upsert(true);
//        
//        print("修改："
//            + collection.findOneAndUpdate(new Document("_id", new ObjectId("552c6c7785cf492c40c718f9")), new Document(
//                "$set", new Document("age", 34))));
//        
//        UpdateResult ur =
//            collection.updateOne(new Document("_id", new ObjectId("4de73f7acd812d61b4626a77")), new Document("$set",
//                new Document("name", "abc")), uo);
//        print("修改：" + ur.getModifiedCount());
//        
//        Map<String, Object> map = new LinkedHashMap<String, Object>();
//        map.put("name", "test");
//        Map<String, Object> map1 = new LinkedHashMap<String, Object>();
//        map1.put("test", "test");
//        map1.put("test1", "test");
//        map1.put("test2", "test");
//        Map<String, Object> map2 = new LinkedHashMap<String, Object>();
//        map2.put("col", map1);
//        map2.put("name", "abc");
//        map2.put("age", 25);
//        map2.put("sex", "男");
//        print("修改："
//            + collection.updateMany(new Document(map1), new Document("$set", new Document(map2))).getModifiedCount());
//        
//        // replace每次只能替换一条
//        print("替换：" + collection.replaceOne(new Document(map1), new Document(map)).getModifiedCount());
//        queryAll();
//    }
//    
//    public void remove()
//    {
//        print("begin remove------------------------------------------------------------------------");
//        queryAll();
//        print("删除id = 553490f56e0d783260687d3b："
//            + collection.deleteOne(new Document("_id", new ObjectId("553490f56e0d783260687d3b"))).getDeletedCount());
//        
//        print("remove age <= 24: "
//            + collection.deleteOne(new Document("age", new Document("$lte", 24))).getDeletedCount());
//        queryAll();
//    }
}

package com.csczn.test.simple;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.QueryOperators;
import com.mongodb.ServerAddress;

public class Driver2130Test {

//	private MongoClient client = null;
//
	private DB db;

	private DBCollection collection;
	
	private MongoClient client = null;
//  
//  private MongoDatabase db;
//  
//  private MongoCollection<Document> collection;

	@Before
	public void init() {

		try {

			List<ServerAddress> seeds = new LinkedList<ServerAddress>();
			seeds.add(new ServerAddress("localhost", 27017));
			List<MongoCredential> credentialsList = new LinkedList<MongoCredential>();
			// credentialsList.add(MongoCredential.createCredential("root",
			// "admin", "root".toCharArray()));
			// 允许一次验证多个不同的数据库
			client = new MongoClient(seeds, credentialsList);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}

		// 获取temp DB；如果默认没有创建，mongodb会自动创建

		db = client.getDB("test");

		// 获取users DBCollection；如果默认没有创建，mongodb会自动创建

		collection = db.getCollection("users");

	}

	@After
	public void destory() {
		if (client != null)
			client.close();

		client = null;
		db = null;
		collection = null;
		System.gc();

	}

	public void print(Object o) {
		System.out.println(o);
	}

	/**
	 * <b>function:</b> 查询所有数据
	 */
	private void queryAll() {
		print("查询users的所有数据：");
		// db游标
		DBCursor cur = collection.find();
		while (cur.hasNext()) {
			print(cur.next());
		}
	}

	public void add() {
		print("begin add------------------------------------------------------------------------");
		// 先查询所有数据
		queryAll();
		print("count: " + collection.count());

		DBObject user = new BasicDBObject();
		user.put("name", "test");
		user.put("age", 24);
		// users.save(user)保存，getN()获取影响行数
		// print(collection.insert(user).getN());
		// 扩展字段，随意添加字段，不影响现有数据
		user.put("sex", "男");
		 print(collection.save(user).getN());
		// 添加多条数据，传递Array对象
		// print(collection.insert(user, new BasicDBObject("name",
		// "tom")).getN());

		// 添加List集合
		List<DBObject> list = new ArrayList<DBObject>();
		list.add(user);
		DBObject user2 = new BasicDBObject("name", "lucy");
		user.put("age", 22);
		list.add(user2);
		// 添加List集合
		print(collection.insert(list).getN());

		// 查询下数据，看看是否添加成功
		print("count: " + collection.count());

		queryAll();
	}

	public void remove() {
		print("begin remove------------------------------------------------------------------------");
		queryAll();
		print("删除id = 55273f747c1f9bde48cedf8f："
				+ collection.remove(
						new BasicDBObject("_id", new ObjectId(
								"55273f747c1f9bde48cedf8f"))).getN());

		print("remove age >= 24: "
				+ collection
						.remove(new BasicDBObject("age", new BasicDBObject(
								"$gte", 24))).getN());
	}

	public void modify() {
		print("begin modify------------------------------------------------------------------------");
		queryAll();
		print("修改："
				+ collection.update(
						new BasicDBObject("_id", new ObjectId(
								"55273f747c1f9bde48cedf8f")),
						new BasicDBObject("age", 99)).getN());

		print("修改："
				+ collection.update(
						new BasicDBObject("_id", new ObjectId(
								"4dde2b06feb038463ff09042")),
						new BasicDBObject("age", 121), true,// 如果数据库不存在，是否添加
						false// false只修改第一条，true如果有多条或一条没有就不修改
						).getN());

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("name", "abc");
		print("修改："
				+ collection.update(
						new BasicDBObject(map),
						new BasicDBObject().append("$set", new BasicDBObject(
								"age", 26)), // 多条修改时需要使用修改器
						false,// 如果数据库不存在，是否添加
						true// 多条修改
						).getN());
		queryAll();
	}

	@Test
	public void query() {
		print("begin query------------------------------------------------------------------------");
		print("find id = 4de73f7acd812d61b4626a77: "
				+ collection.find(
						new BasicDBObject("_id", new ObjectId(
								"4de73f7acd812d61b4626a77"))).toArray());
		print("find age = 24: "
				+ collection.find(new BasicDBObject("age", 24)).toArray());

		print("find age >= 24: "
				+ collection
						.find(new BasicDBObject("age", new BasicDBObject(
								"$gte", 24))).toArray());
		print("find age <= 24: "
				+ collection
						.find(new BasicDBObject("age", new BasicDBObject(
								"$lte", 24))).toArray());
		print("查询age!=25："
				+ collection.find(
						new BasicDBObject("age", new BasicDBObject("$ne", 25)))
						.toArray());
		print("查询age in 25/26/27："
				+ collection.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.IN, new int[] { 25, 26, 27 })))
						.toArray());
		print("查询age not in 25/26/27："
				+ collection.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.NIN, new int[] { 25, 26, 27 })))
						.toArray());
		print("查询age exists 排序："
				+ collection.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.EXISTS, true))).toArray());
		print("只查询age属性："
				+ collection.find(null, new BasicDBObject("age", true))
						.toArray());

		// 只查询一条数据，多条去第一条
		print("findOne: " + collection.findOne());
		print("findOne: " + collection.findOne(new BasicDBObject("age", 26)));
		print("findOne: "
				+ collection.findOne(new BasicDBObject("age", 26),
						new BasicDBObject("name", true)));
		// 查询修改、删除
		print("findAndRemove 查询age=25的数据，并且删除: "
				+ collection.findAndRemove(new BasicDBObject("age", 25)));
		// 查询age=26的数据，并且修改name的值为Abc
		print("findAndModify: "
				+ collection.findAndModify(new BasicDBObject("age", 26),
						new BasicDBObject("name", "Abc")));
		print("findAndModify: "
				+ collection.findAndModify(new BasicDBObject("age", 26), // 查询age=28的数据
						new BasicDBObject("name", true), // 查询name属性
						new BasicDBObject("age", true), // 按照age排序
						false, // 是否删除，true表示删除
						new BasicDBObject("name", "Abc"), // 修改的值，将name修改成Abc
						true, true));

	}
}

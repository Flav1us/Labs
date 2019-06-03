package rabbit;
/*
C:\Program Files\MongoDB\Server\4.0\bin\mongod --dbpath F:\Programs\MongoDB\Server\4.0\database1 --port 27017 --replSet rs0
*/

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

import java.util.Random;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

public class MongoWorker {
	private static final String COLL_NAME = "notes_coll";
	private static final String DBNAME = "notes";
	
	private MongoClient mongoClient;

	private MongoDatabase db;
	private String SEP = RabbitRecieverMain.SEP;
	
	private static final Logger logger = Logger.getLogger(MongoWorker.class);
	
	private Random rand = new Random();

	public MongoWorker() {
		mongoClient = MongoClients.create("mongodb://localhost:27017,localhost:27018,localhost:27019");
		db = mongoClient.getDatabase(DBNAME);
	}
	
	public String create(String user, String title, String content) {
		logger.info("adding:\n user: " + user + "\ntitle " + title +  "\ncontent: " + content);
		Document doc = new Document();
		String id = Long.toHexString(rand.nextLong());
		doc.append("_id", id);
		doc.append("user", user);
		doc.append("title", title);
		doc.append("content", content);
		db.getCollection(COLL_NAME).insertOne(doc);
		return id;
	}

	public boolean delete(String id) {
		//BasicDBObject bobj = new BasicDBObject();
		//bobj.put("_id", new ObjectId(id));
		
		Bson bobj = eq("_id", id);
		DeleteResult dr = db.getCollection(COLL_NAME).deleteOne(bobj );
		if(dr.getDeletedCount() == 1) {
			logger.info("deleted successfully");
			return true;
		}
		else {
			logger.warn("deleted count: " + dr.getDeletedCount());
			return false;
		}
	}
	
	public String get(String user) {
		Bson filter = eq("user", user);
		MongoCursor<Document> c = db.getCollection(COLL_NAME).find(filter).iterator();
		StringBuilder res = new StringBuilder();
		while(c.hasNext()) {
			Document d = c.next();
			res.append(d.getString("_id")).append(SEP)
			.append(d.getString("user")).append(SEP)
			.append(d.getString("title")).append(SEP)
			.append(d.getString("content")).append(SEP);
		}
		if(res.length() > 0) {
			res.delete(res.length()-SEP.length(), res.length());
		}
		return res.toString();
	}

	protected void finalize() {
		mongoClient.close();
	}

	public String get_by_id(String user, String id) {
		Bson filter = and(eq("_id", id), eq("user", user));
		MongoCursor<Document> c = db.getCollection(COLL_NAME).find(filter).iterator();
		if (!c.hasNext()) {
			logger.info("no items found for id " + id);
			return null;
		}
		Document res = c.next();
		return res.getString("_id") + SEP + res.getString("user") + SEP + res.getString("title") + SEP + res.getString("content");
	}
}

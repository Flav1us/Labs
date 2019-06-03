package rabbit;
/*
C:\Program Files\MongoDB\Server\4.0\bin\mongod --dbpath F:\Programs\MongoDB\Server\4.0\database1 --port 27017 --replSet rs0
*/

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClientFactory;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

public class MongoWorker {
	private static final String COLL_NAME = "notes_coll";
	private static final String DBNAME = "notes";
	
	private MongoClient mongoClient;

	private MongoDatabase db;
	
	private static final Logger logger = Logger.getLogger(MongoWorker.class);

	public MongoWorker() {
		mongoClient = MongoClients.create("mongodb://localhost:27017,localhost:27018,localhost:27019");
		db = mongoClient.getDatabase(DBNAME);
	}
	
	public void add(String user, String content) {
		logger.info("adding:\n user: " + user + "\ncontent: " + content);
		Document doc = new Document();
		doc.append("user", user);
		doc.append("content", content);
		db.getCollection(COLL_NAME).insertOne(doc);
	}

	public long delete(String user, String content) {
		Bson filter = and(eq("user", user), eq("content", content));
		DeleteResult dr = db.getCollection(COLL_NAME).deleteOne(filter);
		logger.info("deleting:\n user: " + user + "\ncontent: " + content + "\n" + dr.getDeletedCount() + " entry(ies) deleted");
		return dr.getDeletedCount();
	}
	
	protected void finalize() {
		mongoClient.close();
	}
}

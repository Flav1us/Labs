package main;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import com.company.Value;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.impl.protocol.codec.MapClearCodec;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.TransactionalMap;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;


public class Client {

	static HazelcastInstance client;
	
	static {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getGroupConfig().setName("dev");
		clientConfig.getNetworkConfig().addAddress("25.55.37.13");
		//clientConfig.getNetworkConfig().addAddress("25.59.39.157");
		client = HazelcastClient.newHazelcastClient(clientConfig);
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		Map<String, String> map = client.getReplicatedMap("map");
		/*map.put("1", "Tokyo");
		map.put("2", "Paris");
		map.put("3", "New York");*/
		/*map.remove("2");
		map.put("4", "StPetersbrug");
		map.put("5", "KamenetsPodolsky");*/
		/*mapCustomers.put(1, "Joe");
		mapCustomers.put(2, "Ali");
		mapCustomers.put(3, "Avi");
		mapCustomers.put(4, "Ton");
		mapCustomers.put(5, "Ksyuha");
		mapCustomers.put(6, "StasAFK");*/
		
		transaction();
		client.shutdown();
	}

	private static void transaction() {
		TransactionOptions options = new TransactionOptions()
                .setTransactionType( TransactionOptions.TransactionType.TWO_PHASE );

        TransactionContext context = client.newTransactionContext( options );
        context.beginTransaction();
        
        TransactionalMap<String, String> map = context.getMap( "map" );
        
        try {
            //process obj
        	for(String key : map.keySet()) {
    			System.out.println(key + "\t" + map.get(key));
    		}
            map.put( "4", "Kyiv" );
            System.out.println("inserted");
            for(String key : map.keySet()) {
    			System.out.println(key + "\t" + map.get(key));
    		}
            System.out.println("sleeping");
            Thread.sleep(10000);
            context.commitTransaction();
            System.out.println("commit");
        } catch ( Throwable t ) {
            context.rollbackTransaction();
            System.out.println("rollback");
        }
	}

	private static void createReplicatedMap() {
		Map<String, String> map = client.getReplicatedMap("replicated_map");
		map.put("1", "Tokyo");
		map.put("2", "Paris");
		map.put("3", "New York");

		System.out.println("Finished loading map");
	}

	private static void printMap(Map<String, String> map) {
		for(Map.Entry<String, String> e : map.entrySet()) {
			System.out.println(e.getKey() + "\t" + e.getValue());
		}
	}
	
	private static void optimisticLock() throws InterruptedException, IOException {
		IMap<String, Value> map = client.getMap("map");
		
		String key = "1";
        map.putIfAbsent( key, new Value() );
        System.out.println( "Starting with optimistic lock" );
        new Scanner(System.in).nextLine();
        for ( int k = 0; k < 1000; k++ ) {
            if ( k % 10 == 0 ) System.out.println( "At: " + k );
            for (; ; ) {
                Value oldValue = map.get( key );
                Value newValue = new Value( oldValue );
                Thread.sleep( 10 );
                newValue.amount++;
                if (map.containsKey(key) && map.get(key).equals(oldValue) ) {
                		map.put(key, newValue);
                		break;
                }
            }
        }
        System.in.read();
        System.out.println( "Finished! Result = " + map.get( key ).amount );
	}

	
	private static void pessimisticLock() throws InterruptedException, IOException {
		
		IMap<String, Value> map = client.getMap("map");
		
		String key = "1";
        map.putIfAbsent(key, new Value());
        System.out.println( "Starting with pessimistic lock\ninput any line to start" );
        new Scanner(System.in).nextLine();
        for ( int k = 0; k < 1000; k++ ) {
        	if ( k % 100 == 0 ) System.out.println( "At: " + k );
            map.lock( key );
            try {
                Value value = map.get( key );
                Thread.sleep( 10 );
                value.amount++;
                map.put( key, value );
            } finally {
                map.unlock( key );
            }
        }
        System.in.read();
        System.out.println( "Finished! Result = " + map.get( key ).amount );
	}

	public static void noLock() throws InterruptedException {
		
		IMap<String, Value> map = client.getMap("map");
		
		String key = "1";
       map.put( key, new Value() );
        System.out.println( "Starting without lock" );
        new Scanner(System.in).nextLine();
        for ( int k = 0; k < 1000; k++ ) {
            if ( k % 100 == 0 ) System.out.println( "At: " + k );
            Value value = map.get( key );
            Thread.sleep( 10 );
            value.amount++;
            map.put( key, value );
        }
        System.out.println( "Finished! Result = " + map.get(key).amount );
	}
	
	private static void task4() {
		Map<Integer, String> mapCustomers = client.getMap("customers");
		//mapCustomers.put(4, "Ton");
		for(Map.Entry<Integer, String> e : mapCustomers.entrySet()) {
			System.out.println(e.getKey() + "\t" + e.getValue());
		}
		System.out.println("map size: "+ mapCustomers.size());
		System.out.println("members: " + client.getCluster().getMembers().size());
	}
	
	public static void lockingMap() throws InterruptedException {
		IMap<String, Value> map = client.getMap( "map" );
        String key = "1";
        map.put( key, new Value() );
        System.out.println( "Starting" );
        for ( int k = 0; k < 1000; k++ ) {
            if ( k % 100 == 0 ) System.out.println( "At: " + k );
            Value value = map.get( key );
            Thread.sleep( 10 );
            value.amount++;
            map.put( key, value );
        }
        System.out.println( "Finished! Result = " + map.get(key).amount );
	}

	public static void showMap() {
		Map<Integer, String> mapCustomers = client.getMap("customers");
		System.out.println("Customer with key 4: "+ mapCustomers.get(4));
		System.out.println("Map Size:" + mapCustomers.size());
	}
	


}

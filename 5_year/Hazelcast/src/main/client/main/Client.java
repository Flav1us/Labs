package main;

import java.io.Serializable;
import java.util.Map;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class Client {

	static HazelcastInstance client;
	
	static {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getGroupConfig().setName("dev");
		clientConfig.getNetworkConfig().addAddress("25.59.39.157", "localhost");
		client = HazelcastClient.newHazelcastClient(clientConfig);
	}
	
	public static void main(String[] args) {
		Map<Integer, String> mapCustomers = client.getMap("customers");
		//mapCustomers.put(4, "Ton");
		for(Map.Entry<Integer, String> e : mapCustomers.entrySet()) {
			System.out.println(e.getKey() + "\t" + e.getValue());
		}
		System.out.println("map size: "+ mapCustomers.size());
		System.out.println("members: " + client.getCluster().getMembers().size());
		//client.;
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
	
	static class Value implements Serializable {
        public int amount;
    }

}

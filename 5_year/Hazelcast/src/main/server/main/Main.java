package main;

import java.util.Map;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import main.Client.Value;

public class Main {
	
	static String ip1 = "25.59.39.157";
	static String ip2 = "";

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		Config cfg = new Config();
		cfg.getMapConfig("default").setBackupCount(1);
		JoinConfig join = cfg.getNetworkConfig().getJoin();
		//cfg.setProperty(name, value);
		join.getMulticastConfig().setEnabled(false);
		join.getAwsConfig().setEnabled(false);
		join.getTcpIpConfig().addMember(ip1)/*.addMember(ip2)*/.setEnabled(true);
		
		noLock(cfg);
		//instance.shutdown();

	}

	private static void noLock(Config cfg) throws InterruptedException {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
		IMap<String, Value> map = instance.getMap("map");
		
		String key = "1";
        map.put( key, new Value() );
        System.out.println( "Starting without lock" );
        for ( int k = 0; k < 1000; k++ ) {
            if ( k % 100 == 0 ) System.out.println( "At: " + k );
            Value value = map.get( key );
            Thread.sleep( 10 );
            value.amount++;
            map.put( key, value );
        }
        System.out.println( "Finished! Result = " + map.get(key).amount );
	}

	private static void pessimisticLock(Config cfg) throws InterruptedException {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
		IMap<String, Value> map = instance.getMap("map");
		
		String key = "1";
        map.put( key, new Value() );
        System.out.println( "Starting with pessimistic lock" );
        for ( int k = 0; k < 1000; k++ ) {
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
        System.out.println( "Finished! Result = " + map.get( key ).amount );
	}

	private static void task4(Config cfg) {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
		Map<Integer, String> mapCustomers = instance.getMap("customers");
		for(Map.Entry<Integer, String> e : mapCustomers.entrySet()) {
			mapCustomers.remove(e.getKey());
		}
		mapCustomers.put(1, "Joe");
		mapCustomers.put(2, "Ali");
		mapCustomers.put(3, "Avi");
		mapCustomers.put(4, "Ton");
		mapCustomers.put(5, "Ksyuha");

		System.out.println("Customer with key 1: "+ mapCustomers.get(1));
		System.out.println("Map Size:" + mapCustomers.size());
		System.out.println("backups: " + cfg.getMapConfig("default").getBackupCount());
	}

}

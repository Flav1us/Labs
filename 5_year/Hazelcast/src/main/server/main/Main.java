package main;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;

import com.company.Value;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.LockConfig;
import com.hazelcast.config.QuorumConfig;
import com.hazelcast.config.QuorumListenerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;
import com.hazelcast.quorum.Quorum;
import com.hazelcast.quorum.QuorumFunction;
import com.hazelcast.quorum.QuorumService;
import com.hazelcast.quorum.QuorumType;

public class Main {
	
	static String ip1 = "25.20.130.138";//"25.59.39.157";
	static String ip2 = "25.55.37.13";
			
	static HazelcastInstance instance;
	//static HazelcastInstance instance2;

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		Config cfg = new Config().setInstanceName("myInstance");
		cfg.getMapConfig("default").setBackupCount(2);
		JoinConfig join = cfg.getNetworkConfig().getJoin();
		//cfg.setProperty(name, value);
		join.getMulticastConfig().setEnabled(false);
		join.getAwsConfig().setEnabled(false);
		join.getTcpIpConfig().addMember(ip1).addMember(ip2).setEnabled(true);
		//cfg.getQuorumConfig("my_qourum").setSize(2).setEnabled(true);
		
		
		QuorumConfig qc = new QuorumConfig("qname", true, 5);
		
		LockConfig lc = new LockConfig();
		lc.setName("myLock").setQuorumName(qc.getName());
		
		
		cfg = cfg.addLockConfig(lc);
		
		instance = Hazelcast.newHazelcastInstance();
		
		
		//instance.shutdown();
		/*Map<Integer, String> mapCustomers = instance.getMap("customers");
		mapCustomers.put(1, "Joe");
		mapCustomers.put(2, "Ali");
		mapCustomers.put(3, "Avi");
		mapCustomers.put(4, "AnTon");
		mapCustomers.put(5, "Ksyuha");
		mapCustomers.put(6, "kappa");
		for(int i = 7; i < 1000; i++) {
			mapCustomers.put(i, String.valueOf(new Random().nextInt()%1000));
		}*/
		
		/*while(true) {
			System.out.println("map size:\t"+mapCustomers.size());
			Thread.sleep(1500);
		}*/
		//instance2 = Hazelcast.newHazelcastInstance(cfg);
		//pessimisticLock(cfg);
		//instance.shutdown();

	}

	private static void lock() {
		ILock lock = instance.getLock("myLock");
		//System.out.println(instance.getLock("myLock"));
		System.out.println(instance.getConfig().getLockConfig("myLock").getQuorumName());
		//System.out.println(new Scanner(System.in).nextLine());
		lock.lock();
		System.out.println("locked");
		lock.unlock();
		System.out.println("unlocked");
	}

	public static void noLock(Config cfg) throws InterruptedException {
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

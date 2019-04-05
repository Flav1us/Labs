package main;

import java.util.Map;
import java.util.Map.Entry;

import com.hazelcast.spi.discovery.DiscoveryStrategy;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Main {
	
	static String ip1 = "25.59.39.157";
	static String ip2 = "";

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Config cfg = new Config();
		cfg.getMapConfig("default").setBackupCount(0);
		JoinConfig join = cfg.getNetworkConfig().getJoin();
		//cfg.setProperty(name, value);
		join.getMulticastConfig().setEnabled(false);
		join.getAwsConfig().setEnabled(false);
		join.getTcpIpConfig().addMember(ip1)/*.addMember(ip2)*/.setEnabled(true);
		
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
		
		//instance.shutdown();

	}

}

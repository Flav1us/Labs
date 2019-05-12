package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class testFutureMultith {
	
	@Test
	public void test() throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newFixedThreadPool(4); //4 physical proccessors on my laptop
		List<Future<Integer>> tasks = new ArrayList<Future<Integer>>(); 
		for(int i = 0; i < 4; i++) {
			 tasks.add(es.submit(new task(i)));
		}
		for(int i = 0; i < tasks.size(); i++) {
			System.out.println(i + "\t" + tasks.get(i).get());
		}
		System.out.println("done");
	}
	
	private class task implements Callable<Integer> {
		int id;

		public task(int id) {
			super();
			this.id = id;
		}

		@Override
		public Integer call() throws Exception {
			int rand_sleep = (int)(Math.random()*3000);
			System.out.println(id + " sleeping " + rand_sleep);
			Thread.sleep(rand_sleep);
			System.out.println(id + " awake");
			return id;
		}
		
	}
}

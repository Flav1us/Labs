package test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LogTest {
	Logger logger = Logger.getLogger(LogTest.class);

	@Test
	public void simple_log_test() {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");  
		Date date = new Date(System.currentTimeMillis());
		logger.debug(formatter.format(date));
	}
}

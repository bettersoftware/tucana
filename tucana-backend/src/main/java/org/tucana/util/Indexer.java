/**
 * 
 */
package org.tucana.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tucana.service.ConstellationService;

/**
 * @author kamann
 *
 */
public class Indexer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/META-INF/spring/backend-context.xml");
		ConstellationService service = (ConstellationService) applicationContext.getBean("constellationService");
		
		service.reIndexDatabase();
	}

}

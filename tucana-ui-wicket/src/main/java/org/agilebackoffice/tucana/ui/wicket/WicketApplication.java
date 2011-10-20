package org.agilebackoffice.tucana.ui.wicket;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see org.agilebackoffice.tucana.ui.wicket.Start#main(String[])
 */
@Component(value = "wicketApplication")
public class WicketApplication extends WebApplication {
	
	 @Autowired
     private ApplicationContext applicationContext;
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();
		
		super.getComponentInstantiationListeners().add(new SpringComponentInjector(this,
				applicationContext, true));
		
		mountPage("/home", HomePage.class);
	}
}

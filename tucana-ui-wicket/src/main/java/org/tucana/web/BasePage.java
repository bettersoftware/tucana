/**
 * 
 */
package org.tucana.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Abstract base class to implement default behaviour.
 * <br>
 * This class is used for markup inheritance.
 * 
 * @author Thorsten Kamann
 */
public abstract class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;
	public String search;
	
	/**
	 * This constructor will be called if the user enters one or more searchitems in the
	 * searchform.
	 * 
	 * @param search
	 */
	public BasePage(String search) {
		super();
		this.search = search;
		add(getSearchForm());
	}

	/**
	 * Defaault constructor for this page. 
	 * 
	 * @param parameters The page parameters
	 */
	public BasePage(PageParameters parameters) {
		super(parameters);
		add(getSearchForm());
	}
	
	/**
	 * @return the searchitems entered by the user
	 */
	public String getSearch() {
		return search;
	}

	private Form<BasePage> getSearchForm(){
		IModel<BasePage> model = new CompoundPropertyModel<BasePage>(this);
		Form<BasePage> searchForm = new Form<BasePage>("searchForm", model);
		TextField<String> searchField = new TextField<String>("search");
		searchForm.add(searchField);
		Button submitButton = new Button("submit") { // (4)
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				System.out.println("OnSubmit, name = " + search);
				setResponsePage(new HomePage(search));
			}
		};
		searchForm.add(submitButton);
		return searchForm;		
	}

}

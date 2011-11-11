package org.tucana.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.StringUtils;
import org.tucana.domain.Constellation;
import org.tucana.service.ConstellationService;

public class HomePage extends BasePage {
	private static final long serialVersionUID = 1L;

	private List<Constellation> constellations = new ArrayList<Constellation>();

	@SpringBean
	private ConstellationService service;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		initComponents();
	}

	public HomePage(String search) {
		super(search);
		initComponents();
	}

	private void initComponents() {
		getConstellations();
		
		//TODO Implement later automatically redirect to detailpage if only one result was found
//		if (constellations.size() == 1){
//			PageParameters params = new PageParameters();
//			params.set("code", ((Constellation) constellations.get(0)).getCode());
//			add(new RedirectPage(new ConstellationDetails(params)));
////			setResponsePage(new ConstellationDetails(params));
//		}else{
//
//		
//		}
		add(new Label("c_count", constellations.size() + ""));
		add(getRepeatingView());
	}

	private RepeatingView getRepeatingView() {
		RepeatingView repeatingView;

		repeatingView = new RepeatingView("datatable");

		int index = 0;
		for (Constellation constellation : constellations) {
			AbstractItem item = new AbstractItem(repeatingView.newChildId());
			item.add(new Label("c_code", constellation.getCode()));
			item.add(new Label("c_name", constellation.getName()));
			item.add(new Label("c_gen_name", constellation.getGenitiveName()));
			item.add(getConstellationDetailsLink(constellation.getCode()));

			final int idx = index;
			item.add(AttributeModifier.replace("class",
					new AbstractReadOnlyModel<String>() {
						private static final long serialVersionUID = 1L;

						@Override
						public String getObject() {
							return (idx % 2 == 1) ? "even" : "odd";
						}
					}));

			index++;
			repeatingView.add(item);
		}

		return repeatingView;
	}
	
	private BookmarkablePageLink<ConstellationDetails> getConstellationDetailsLink(String code){
		PageParameters params = new PageParameters();
		
		params.add("code", code);
		return new BookmarkablePageLink<ConstellationDetails>("c_detail_link", ConstellationDetails.class, params);
	}

	private List<Constellation> getConstellations() {
		constellations = service.findAllConstellations();
		return constellations;
	}
}

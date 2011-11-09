package org.tucana.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
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
	
	private List<Constellation> getConstellations() {
		constellations = service.findAllConstellations();
		return constellations;
	}
}

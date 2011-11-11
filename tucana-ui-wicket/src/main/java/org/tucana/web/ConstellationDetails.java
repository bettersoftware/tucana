/**
 * 
 */
package org.tucana.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.StringUtils;
import org.tucana.domain.Constellation;
import org.tucana.domain.ConstellationName;
import org.tucana.service.ConstellationService;

/**
 * This page shows the details of a constellation.
 * 
 * @author Peter Smith
 */
public class ConstellationDetails extends BasePage {
	private static final long serialVersionUID = 1L;
	private String code;
	private Constellation constellation;
	
	@SpringBean
	private ConstellationService service;
	
	/**
	 * Default constructor for this page. The parameters contains at least one entry with
	 * the name <code>code</code>. This entry contains the code of a constellation (eg. and for Andromeda)
	 * 
	 * @param parameters
	 */
	public ConstellationDetails(final PageParameters parameters){
		super(parameters);
		
		this.code = parameters.get("code").toString();
		setUpConstellationData();
		initComponents();
	}
	
	private void initComponents(){
		add(getDetailLabel());
		addDataLabels();
		addNameLabels();
		addStarCardImage();
	}
	
	private Label getDetailLabel(){
		return new Label("c_details_for", constellation.getName()); 
	}
	
	private void addDataLabels(){		
		add(new Label("c_details_name_label", "Name"));
		add(new Label("c_details_name_text", constellation.getName()));
		
		add(new Label("c_details_gen_name_label", "Genitive Name"));
		add(new Label("c_details_gen_name_text", constellation.getGenitiveName()));
		
		add(new Label("c_details_code_label", "Code"));
		add(new Label("c_details_code_text", constellation.getCode()));
	
		add(new Label("c_details_hemisphere_label", "Hemisphere"));
		String hemisphere = constellation.getHemisphere();
		String vh = "";
		if (hemisphere.contains("N")){
			vh = "North";
		}
		if (hemisphere.contains("S")){
			vh = (StringUtils.hasText(vh))? vh+"/South": "South";
		}
		add(new Label("c_details_hemisphere_text", vh));
		
		add(new Label("c_details_author_label", "1st Author"));
		add(new Label("c_details_author_text", constellation.getAuthor()+" ("+constellation.getAuthorYear()+")"));
		
		add(new Label("c_details_area_label", "Sky Area"));
		add(new Label("c_details_area_text", constellation.getArea()+" (°)²"));
		
		add(new Label("c_details_stars3m_label", "# Stars > 3m"));
		add(new Label("c_details_stars3m_text", constellation.getNumberOfStarsGreater3M()+""));
		
		add(new Label("c_details_stars4m_label", "# Stars > 4m"));
		add(new Label("c_details_stars4m_text", constellation.getNumberOfStarsGreater4M()+""));
	}
	
	private void addNameLabels(){
		for (ConstellationName cn : constellation.getNames()) {
			add(new Label("c_names_"+cn.getLangCode()+"_label", cn.getLangCode()));
			add(new Label("c_names_"+cn.getLangCode()+"_text", cn.getName()));
		}
	}
	
	private void addStarCardImage(){		
		add(new Image("c_starcard", new DynamicImageResource() {
			private static final long serialVersionUID = 1L;

			@Override
			protected byte[] getImageData(Attributes attributes) {
				return constellation.getStarCardData();
			}
		}));
	}
	
	
	
	private void setUpConstellationData(){
		constellation = service.findConstellationByCode(code);
	}

}

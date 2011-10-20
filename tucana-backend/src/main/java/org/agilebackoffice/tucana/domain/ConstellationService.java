/**
 * 
 */
package org.agilebackoffice.tucana.domain;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kamann
 * 
 */
@Service
@Transactional
public class ConstellationService {

	@Inject
	private ConstellationRepository constellationRepository;

	public Constellation persistConstellation(
			Constellation constellation2Persist) {
		return constellationRepository
				.persistConstellation(constellation2Persist);
	}
	
	public void reIndexDatabase(){
		constellationRepository.reIndexDatabase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.agilebackoffice.wafe.domain.IConstellation#findAllConstellations()
	 */
	public List<Constellation> findAllConstellations() {
		return constellationRepository.findAllConstellations();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.agilebackoffice.wafe.domain.IConstellation#findConstellationByCode
	 * (java.lang.String)
	 */
	public Constellation findConstellationByCode(String code) {
		return constellationRepository.findConstellationByCode(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.agilebackoffice.wafe.domain.IConstellation#
	 * findAllConstellationByCodeOrName(java.lang.String)
	 */
	public List<Constellation> findAllConstellationByCodeOrName(String search) {
		return constellationRepository
				.findAllConstellationsByFullTextSearch(search);
	}
}

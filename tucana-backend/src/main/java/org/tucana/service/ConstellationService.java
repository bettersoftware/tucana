/**
 * 
 */
package org.tucana.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tucana.domain.Constellation;
import org.tucana.repository.ConstellationRepository;

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

	
	/**
	 * @return
	 */
	public List<Constellation> findAllConstellations() {
		return constellationRepository.findAllConstellations();
	}

	
	/**
	 * @param code
	 * @return
	 */
	public Constellation findConstellationByCode(String code) {
		return constellationRepository.findConstellationByCode(code);
	}
	
	/**
	 * @param search
	 * @return
	 */
	public List<Constellation> findAllConstellationByCodeOrName(String search) {
		return constellationRepository.findAllConstellationsByFullTextSearch(search);
	}
	
	/**
	 * 
	 */
	public void reIndexDatabase() {
		constellationRepository.reIndexDatabase();
	}
}

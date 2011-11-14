/**
 * 
 */
package org.tucana.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Repository;
import org.tucana.domain.Constellation;

/**
 * @author kamann
 *
 */
@Repository
public class ConstellationRepository {

	@PersistenceContext
	private EntityManager em;

	public Constellation persistConstellation(
			Constellation constellation2Persist) {
		if (constellation2Persist.getId() != null) {
			em.merge(constellation2Persist);
		} else {
			em.persist(constellation2Persist);
		}
		return constellation2Persist;
	}

	@SuppressWarnings("unchecked")
	public List<Constellation> findAllConstellations() {
		return em.createQuery("from Constellation c order by c.code").getResultList();
	}

	public Constellation findConstellationByCode(String code) {
		Query query = em
				.createQuery("from Constellation c where c.code = :code");
		query.setParameter("code", code);

		return (Constellation) query.getSingleResult();
	}	
	
	public void reIndexDatabase(){
		FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
		List<Constellation> constellations = findAllConstellations();
		for (Constellation constellation : constellations) {
			ftem.index(constellation);
		}
	}

}

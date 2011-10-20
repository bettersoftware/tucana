/**
 * 
 */
package org.agilebackoffice.tucana.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * @author kamann
 *
 */
@Repository
public class CommentRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public Comment persistComment(
			Comment comment2Persist) {
		if (comment2Persist.getId() != null) {
			em.merge(comment2Persist);
		} else {
			em.persist(comment2Persist);
		}
		return comment2Persist;
	}
	
	public Comment removeComment (Comment comment2Remove){
		if (comment2Remove.getId() != null){
			em.remove(comment2Remove);
		}
		return comment2Remove;
	}

	@SuppressWarnings("unchecked")
	public List<Comment> findAllCommentsByConstellation(Constellation constellation){
		Query query = em.createQuery("from Comment c WHERE c.constellation = :constellation");
		query.setParameter("constellation", constellation);
		return query.getResultList();
	}
}

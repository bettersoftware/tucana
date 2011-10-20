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

	@Inject
	private CommentRepository commentRepository;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.agilebackoffice.wafe.domain.IConstellation#findAllCommentsByConstellation
	 * (org.agilebackoffice.wafe.domain.Constellation)
	 */
	public List<Comment> findAllCommentsByConstellation(
			Constellation constellation) {
		return commentRepository.findAllCommentsByConstellation(constellation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.agilebackoffice.wafe.domain.IConstellation#addCommentToConstellation
	 * (org.agilebackoffice.wafe.domain.Constellation,
	 * org.agilebackoffice.wafe.domain.Comment)
	 */
	public Comment addCommentToConstellation(Constellation constellation,
			Comment comment) {
		comment.setConstellation(constellation);
		return commentRepository.persistComment(comment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.agilebackoffice.wafe.domain.IConstellation#removeCommentFromConstellation
	 * (org.agilebackoffice.wafe.domain.Comment)
	 */
	public Comment removeCommentFromConstellation(Comment comment) {
		return commentRepository.removeComment(comment);
	}

}

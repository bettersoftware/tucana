/**
 * 
 */
package org.tucana.repository;

import static org.junit.Assert.*

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.tucana.domain.Constellation
import org.tucana.service.ConstellationService

/**
 * @author kamann
 *
 */
class ConstellationServiceTest {
	private List<Constellation> data = []
	private ConstellationRepository repository
	private ConstellationService service

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		20.times { data << new Constellation(code: "c$it") }

		repository = [findAllConstellations: {data},
			findConstellationByCode: {code -> data.find{ it.code == code }}] as ConstellationRepository

		service = new ConstellationService()
		service.constellationRepository = repository
	}

	@Test
	final void "test service method findAllConstellations"(){
		Assert.assertEquals(20, service.findAllConstellations().size())
	}

	@Test
	final void "test service method findConstellationByCode"(){
		Assert.assertEquals("c3", service.findConstellationByCode("c3").code)
	}

	@Test
	final void "test failing service method findConstellationByCode"(){
		Assert.assertNull(service.findConstellationByCode("fghgh"))
	}
}

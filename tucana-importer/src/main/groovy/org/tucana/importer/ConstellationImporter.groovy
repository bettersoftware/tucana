/**
 * 
 */
package org.tucana.importer

import groovy.util.slurpersupport.NodeChild

import org.tucana.domain.Constellation
import org.tucana.domain.ConstellationName;
import org.tucana.service.ConstellationService;
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.SimpleXmlSerializer
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * @author kamann
 *
 */
class ConstellationImporter {
	private static def dataWithNamedConstellations

	public static void main(def args){

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/META-INF/spring/backend-context.xml")

		ConstellationService service = applicationContext.getBean("constellationService")


		String url = "http://de.wikipedia.org/wiki/Liste_der_Sternbilder"
		def page = getCleanedHtml(url)
		def tables = page.body.'**'.findAll{
			it.name() == "table" && it.@class.text().contains('wikitable sortable')
		}

		if (tables.size() != 1){
			throw new RuntimeException("Expected only one table. Please check the xpath and the source of wikipedia!")
		}

		def table = tables[0]
		def rows = table.tbody.tr


		rows.each{ row ->
			if (!row.@style.text()){
				def cells = row.td
				Constellation constellation = new Constellation()
				constellation.name = cells[1]
				constellation.genitiveName = cells[2]
				constellation.code = cells[3].text().toLowerCase()
				constellation.hemisphere = cells[4]
				constellation.author = cells[5]
				constellation.authorYear = cells[6].text() as int
				constellation.area = toDouble(cells[7].text())
				constellation.visibilityArea = cells[8].text().replace("bis", "-")
				constellation.greatestMagnitude = toDouble(cells[9].text())
				constellation.numberOfStarsGreater3M = cells[10].text() as int
				constellation.numberOfStarsGreater4M = cells[11].text() as int
				constellation.starCardData = new URL(retrieveStarCardRef(cells[12].a.@href.text())).bytes

				Map names = getAllNamesForConstellation (constellation.code)
				List cn = []
				names.each{String key, String value ->
						cn << new ConstellationName(name: value, langCode: key, code: constellation.code)
				}
				constellation.names = cn;
				
				println constellation
				service.persistConstellation constellation
				
			}	else{
				println "a"
			}
		}

		println service.findAllConstellations().size()
		println service.findConstellationByCode("ori").name
		
//		Thread.sleep(5000)
//		((ClassPathXmlApplicationContext)applicationContext).close()
	}

	private static toDouble(String value){
		return (value.replaceAll("\\.", "").replace(",", ".")) as double
	}

	private static retrieveStarCardRef(String wikipediaRef){
		def url = "http://de.wikipedia.org$wikipediaRef"
		def page = getCleanedHtml(url)
		def pngUrl = page.body.'**'.find{ it.name() == "div" && it.@class.text().contains('fullMedia')}.a.@href.text()
		if (!pngUrl.startsWith("http:")){
			pngUrl = "http:$pngUrl"
		}
		return pngUrl
	}
	
	private static Map getAllNamesForConstellation(String code){
		if (!dataWithNamedConstellations){
			def page = getCleanedHtml("http://de.wikipedia.org/wiki/Liste_der_Sternbilder_in_verschiedenen_Sprachen")
			dataWithNamedConstellations = page.body.'**'.find { it.name() == "table" && it.@class.text().contains("wikitable sortable") }.tbody.tr
		}

		println code
		def row = dataWithNamedConstellations.'**'.find { it.name = "td" && it.text().toLowerCase() == "$code" }.parent()
		Map names = [:]
		names.de = row.td[1].text()
		names.en = row.td[5].text()
		names.su = row.td[6].text()
		names.fr = row.td[7].text()
		names.'it' = row.td[8].text()
		names.es = row.td[12].text()
		return names
	}

	private static NodeChild getCleanedHtml(String url){
		def cleaner = new HtmlCleaner()
		def node = cleaner.clean(url.toURL())

		// Convert from HTML to XML
		def props = cleaner.getProperties()
		def serializer = new SimpleXmlSerializer(props)
		def xml = serializer.getXmlAsString(node)

		// Parse the XML into a document we can work with
		new XmlSlurper(false,false).parseText(xml)
	}
}

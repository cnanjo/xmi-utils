package guru.mwangaza.eap.xmi.reader

import static org.junit.Assert.*

import org.junit.Before;
import org.junit.Test
import guru.mwangaza.uml.UmlClass;
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlProperty

class PropertyReaderTest {

	private UmlModel model
	private XmiReader xmiReader
	private UmlClass childType1
	
	@Before
	public void setup() {
		xmiReader = XmiReader.configureDefaultXmiReader();
		model = xmiReader.loadModel(xmiReader.loadFromStream,"/xmi/XmiUtilsTestProject.xml")
		model.buildIndex()
		childType1 = model.getObjectByName("ChildType1")
		assertNotNull(childType1)
	}
	
	@Test
	public void testChildType1AttributeLoading() {
		List<UmlProperty> properties = childType1.getProperties()
		UmlProperty attribute1 = properties[0]
		UmlProperty attribute2 = properties[1]
		
		assertEquals(2, properties.size())
		
		assertNotNull(attribute1)
		assertNotNull(attribute2)
		
		assertEquals("attribute_1", attribute1.name)
		assertEquals(0, attribute1.low)
		assertEquals(1, attribute1.high)
		
		assertEquals("attribute_2", attribute2.name)
		assertEquals(0, attribute2.low)
		assertEquals(-1, attribute2.high)
	}
	
//	@Test
//	public void testAtributeTaggedValues() {
//		assertEquals(2, childType1.properties.size)
//		assertEquals(0, childType1.properties[0].tags.size)
//		assertEquals("map.fhir.attribute.cardinality.low", childType1.properties[1].tags[0].key)
//		assertEquals("0", childType1.properties[1].tags[0].value)
//		assertEquals("map.fhir.attribute.cardinality.high", childType1.properties[1].tags[1].key)
//		assertEquals("1", childType1.properties[1].tags[1].value)
//	}

}

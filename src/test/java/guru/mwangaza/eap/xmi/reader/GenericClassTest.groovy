package guru.mwangaza.eap.xmi.reader

import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class GenericClassTest {
	
	private UmlModel model
	private List<UmlClass> classes1
	private List<UmlClass> classes2
	
	@Before
	public void setup() {
		//Load first model
		XmiReader xmiReader = XmiReader.configureDefaultXmiReader();
		model = xmiReader.loadModelFromClassPath("/xmi/GenericClassTest.xml")
		model.buildIndex()
	}

	@Test
	public void testGenericParameter() {
		UmlClass genericClass = model.getObjectByName("GenericClass")
		assertNotNull(genericClass)
//		var prop = genericClass.getProperties().get(0).getFirstType()
//		println prop
		assertEquals("param1", genericClass.getProperties().get(0).getName())
		assertEquals("S", genericClass.getProperties().get(0).getFirstType().getName())
		assertEquals("param2", genericClass.getProperties().get(1).getName())
		assertEquals("T", genericClass.getProperties().get(1).getFirstType().getName())
	}

}

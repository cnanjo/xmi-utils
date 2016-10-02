package guru.mwangaza.eap.xmi.reader;

import static org.junit.Assert.*

import org.junit.Before;
import org.junit.Test
import guru.mwangaza.uml.UmlModel

class PackageReaderTest {
	
	private UmlModel model
	private UmlModelLoader loader
	
	@Before
	public void setup() {
		loader = new UmlModelLoader()
		model = loader.loadModel(loader.loadFromStream,"/xmi/UnitTestEapModel.xml")
		model.buildIndex()
	}

	@Test
	public void testReadPackage() {
		assertEquals(1, model.packages.size)
		assertEquals("Model", model.packages[0].name)
		
		assertEquals(1, model.packages[0].packages.size)
		assertEquals("TopLevelPackage", model.packages[0].packages[0].name)
		
		assertEquals(2, model.packages[0].packages[0].packages.size)
		assertEquals("Datatypes", model.packages[0].packages[0].packages[0].name)
		assertEquals("Core", model.packages[0].packages[0].packages[1].name)
	}

}

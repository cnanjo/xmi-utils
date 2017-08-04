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
		model = loader.loadModel(loader.loadFromStream,"/xmi/XmiUtilsTestProject.xml")
		model.buildIndex()
	}

	@Test
	public void testReadPackage() {
		assertEquals(1, model.packages.size)
		assertEquals(2, model.packages[0].packages.size)
		assertEquals("parent_package", model.packages[0].name)
		assertEquals("child_package_1", model.packages[0].packages[0].name)
		assertEquals("child_package_2", model.packages[0].packages[1].name)
	}

}

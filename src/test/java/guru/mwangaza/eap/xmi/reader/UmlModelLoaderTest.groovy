package guru.mwangaza.eap.xmi.reader

import static org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import guru.mwangaza.uml.UmlModel

class UmlModelLoaderTest {
	
	def loader
	def resourcePath = "/xmi/CIMI_Model.xmi";

	@Before
	public void setUp() throws Exception {
		loader = new UmlModelLoader("http://www.omg.org/spec/UML/20131001", "http://www.omg.org/spec/XMI/20131001")
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadFromFilePath() {
		loader.loadFromStream(resourcePath)
	}
	
	@Test
	public void testLoadFromClassPath() {
		assertNotNull(loader.loadModelFromClassPath(resourcePath))
	}

	@Test
	public void testLoadModel() {
		UmlModel model = loader.loadModel(loader.loadFromStream,resourcePath)
		model.buildIndex()
		println model
//		UmlPackage topLevelPackage = model.getPackages().get(0);
//		UmlPackage secondLevelPackage = topLevelPackage.getPackages().get(0);
//		UmlPackage thirdLevelPackage = secondLevelPackage.getPackages().get(0);
//		assertEquals("EA_Model", model.getName())
//		assertEquals(1, model.getPackages().size())
//		assertEquals("Model",topLevelPackage.getName())
//		assertEquals(1, topLevelPackage.getPackages().size())
//		assertEquals("QUICK Class Model", secondLevelPackage.getName())
//		assertEquals(6, secondLevelPackage.getPackages().size())
//		assertTrue(thirdLevelPackage.getPackages().size() == 3)
//		assertEquals("action", thirdLevelPackage.getName())
//		assertNotNull(model.getObjectByName("ConditionOccurrence"))
//		println model.getObjectByName("ConditionOccurrence")
	}

}

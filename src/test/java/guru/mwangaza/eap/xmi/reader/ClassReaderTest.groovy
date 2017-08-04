package guru.mwangaza.eap.xmi.reader

import guru.mwangaza.uml.TaggedValue
import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlPackage
import guru.mwangaza.uml.UmlStereotype
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class ClassReaderTest {
	
	private UmlModel model
	private XmiReader xmiReader
	private List<UmlClass> classes1
	private List<UmlClass> classes2
	
	@Before
	public void setup() {
		xmiReader = XmiReader.configureDefaultMagicDrawXmiReader();
		model = xmiReader.loadModel(xmiReader.loadFromStream,"/xmi/XmiUtilsTestProject.xml")
		model.buildIndex()
	}

	@Test
	public void testClassUnmarshalling() {
		assertNotNull(model.getObjectByName("child_package_1"));
		assertEquals(3, ((UmlPackage)model.getObjectByName("child_package_1")).getClasses().size());
	}
	
	@Test
	public void testClassId() {
		assertNotNull(model.getObjectByName("ParentType1").getId())
		assertNotNull(model.getObjectByName("ChildType1").getId())
		assertNotNull(model.getObjectByName("ChildType2").getId())
	}

	@Test
	public void testClassInheritance() {
		UmlClass childType1 = model.getObjectByName("ChildType1");
		UmlClass childType2 = model.getObjectByName("ChildType2");
		UmlClass parentType1 = model.getObjectByName("ParentType1");
		assertEquals(1, childType1.getGeneralizations().size());
		assertEquals("ParentType1", childType1.getGeneralizations().get(0).getName());
		assertEquals(1, childType2.getGeneralizations().size());
		assertEquals("ParentType1", childType2.getGeneralizations().get(0).getName());
	}

	@Test
	public void testClassTaggedValues() {
		UmlClass child = model.getObjectByName("ChildType1")
		UmlStereotype stereotype = child.getStereotype()
		Map<String, TaggedValue> tags = stereotype.getTaggedValues()
		assertNotNull(child)
		assertNotNull(stereotype)
		assertEquals("Stereotype1", stereotype.getName())
		assertEquals(2, tags.size())
		assertNotNull("test-tag-1", child.getStereotype().getTaggedValue("test-tag-1").getKey())
		assertEquals("Value 1", child.getStereotype().getTaggedValue("test-tag-1").getValue())
	}

	@Test
	public void testProfileLoading() {

	}

//	@Test
//	public void testGetTaggedValueByKey() {
//		UmlClass child = model.getObjectByName("Child")
//		assertNotNull(child)
//		child.buildTaggedValueIndex()
//		TaggedValue tag = child.getTaggedValueByKey("map.fhir.class");
//		assertNotNull(tag)
//	}
//
//	@Test
//	public void testGeneralizationLoading() {
//		UmlClass child = model.getObjectByName("Child")
//		assertNotNull(child)
//		assertEquals(2, child.getGeneralizations().size())
//		assertEquals("Parent1", child.getGeneralizations()[0].getName())
//		assertEquals("Parent2", child.getGeneralizations()[1].getName())
//	}
}

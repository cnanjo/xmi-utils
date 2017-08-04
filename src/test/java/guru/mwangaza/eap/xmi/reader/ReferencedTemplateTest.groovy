package guru.mwangaza.eap.xmi.reader

import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlTemplateParameter
import guru.mwangaza.uml.UmlTemplateSignature
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class ReferencedTemplateTest {
	
	private UmlModel model
	private List<UmlClass> classes1
	private List<UmlClass> classes2
	
	@Before
	public void setup() {
		//Load first model
		XmiReader xmiReader = XmiReader.configureDefaultXmiReader();
		model = xmiReader.loadModelFromClassPath("/xmi/ReferencedTemplateTest.xml")
		model.buildIndex()
	}

	@Test
	public void testTemplateBinding() {
		UmlClass array = model.getObjectByName("Array")
		assertNotNull(array)
		UmlTemplateSignature signature = array.getTemplateSignature()
		UmlTemplateParameter parameter = signature.getTemplateparameter("EAID_BC0E68CA_B736_4237_9505_B4099E4CC5BA");
		assertNotNull(parameter)
		assertEquals("T", parameter.getName());
	}

}

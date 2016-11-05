package guru.mwangaza.eap.xmi.reader

import guru.mwangaza.uml.TaggedValue
import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlPackage
import guru.mwangaza.uml.UmlTemplateBinding
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class TemplateBindingReaderTest {
	
	private UmlModel model
	private List<UmlClass> classes1
	private List<UmlClass> classes2
	
	@Before
	public void setup() {
		//Load first model
		UmlModelLoader loader = new UmlModelLoader("http://www.omg.org/spec/UML/20131001", "http://www.omg.org/spec/XMI/20131001")
		UmlModel dependency = loader.loadModelFromClassPath("/xmi/GenericTestCore.xml")
		dependency.buildIndex()

		loader = new UmlModelLoader("http://www.omg.org/spec/UML/20131001", "http://www.omg.org/spec/XMI/20131001")
		Map<String, UmlModel> dependencies = new HashMap<>();
		dependencies.put("GenericTestCore.xml", dependency)
		loader.setDependencies(dependencies)
		model = loader.loadModelFromClassPath("/xmi/GenericTestProject.xml")
		model.buildIndex()
	}

	@Test
	public void testTemplateBinding() {
		List<Package> packages = model.getPackages();
		UmlPackage rootPkg = packages.get(0);
		assertEquals(1, packages.size());
		assertEquals(8, rootPkg.getClasses().size())
		UmlClass dateInterval = model.getObjectByName("DateInterval")
		assertNotNull(dateInterval)
		UmlTemplateBinding binding = dateInterval.getTemplateBinding()
		assertNotNull(binding)
		testBindingDateInterval(binding)

		UmlClass textType = model.getObjectByName("TextType")
		assertNotNull(textType)
		binding = textType.getTemplateBinding()
		assertNotNull(binding)
		testBindingTextType(binding)
	}

	public void testBindingDateInterval(UmlTemplateBinding binding) {
		assertNotNull(binding.getSignature())
		assertEquals(2, binding.getBindings().size())
		assertEquals("T", binding.getBindings().get(0).getFormalParameter().getName())
		assertEquals("U", binding.getBindings().get(1).getFormalParameter().getName())
		assertEquals("DATE_TIME", binding.getBindings().get(0).getActualParameter().getName())
		assertEquals("REAL", binding.getBindings().get(1).getActualParameter().getName())
		assertEquals("INTERVAL_VALUE", binding.getSignature().getOwningClass().getName())
	}

	public void testBindingTextType(UmlTemplateBinding binding) {
		assertNotNull(binding.getSignature())
		assertEquals(1, binding.getBindings().size())
		assertEquals("T", binding.getBindings().get(0).getFormalParameter().getName())
		assertEquals("TEXT", binding.getBindings().get(0).getActualParameter().getName())
		assertEquals("GenericType", binding.getSignature().getOwningClass().getName())
	}

	public void testBindingUriType(UmlTemplateBinding binding) {
		assertNotNull(binding.getSignature())
		assertEquals(2, binding.getBindings().size())
		assertEquals("T", binding.getBindings().get(0).getFormalParameter().getName())
		assertEquals("U", binding.getBindings().get(1).getFormalParameter().getName())
		assertEquals("DATE_TIME", binding.getBindings().get(0).getActualParameter().getName())
		assertEquals("REAL", binding.getBindings().get(1).getActualParameter().getName())
		assertEquals("INTERVAL_VALUE", binding.getSignature().getOwningClass().getName())
	}

}

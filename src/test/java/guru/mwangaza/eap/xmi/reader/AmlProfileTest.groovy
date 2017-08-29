package guru.mwangaza.eap.xmi.reader

import guru.mwangaza.uml.TaggedValue
import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlPackage
import guru.mwangaza.uml.UmlStereotype
import guru.mwangaza.uml.UmlStereotypeDefinition
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class AmlProfileTest {
    private UmlModel coreModel
    private UmlModel foundationModel
    private UmlModel clinicalModel
    private XmiReader xmiReader
    private List<UmlClass> classes1
    private List<UmlClass> classes2

    @Before
    public void setup() {
        Map<String, UmlModel> dependencies = new HashMap<>();
        xmiReader = XmiReader.configureDefaultXmiReader();
        coreModel = xmiReader.loadModel(xmiReader.loadFromStream,"/xmi/CIMI_RM_CORE.v0.0.3.xml")
        coreModel.buildIndex()
        dependencies.put("CIMI_RM_CORE.v0.0.3.mdzip", coreModel)

        xmiReader = XmiReader.configureDefaultXmiReader();
        xmiReader.setDependencies(dependencies);
        foundationModel = xmiReader.loadModel(xmiReader.loadFromStream,"/xmi/CIMI_RM_FOUNDATION.v0.0.3.xml")
        foundationModel.addDependency("CIMI_RM_CORE.v0.0.3.xml", coreModel)
        foundationModel.buildIndex()
        dependencies.put("CIMI_RM_FOUNDATION.v0.0.3.mdzip", foundationModel)

        xmiReader = XmiReader.configureDefaultXmiReader();
        xmiReader.setDependencies(dependencies);
        clinicalModel = xmiReader.loadModel(xmiReader.loadFromStream,"/xmi/CIMI_RM_CLINICAL.v0.0.3.xml")
        clinicalModel.buildIndex()
    }

    @Test
    public void testAmlProfileReference() {
        UmlPackage coreModelPkg = coreModel.getObjectByName("CIMI Reference Model")
        assertNotNull coreModelPkg
        UmlStereotype stereotype = coreModelPkg.getStereotypes().get("ReferenceModel").get(0);
        assertNotNull stereotype
        TaggedValue value = stereotype.getTaggedValue("rmNamespace")
        assertNotNull value
        assertEquals('RM_CORE', value.getValue())
        value = stereotype.getTaggedValue("rmPublisher")
        assertNotNull value
        assertEquals('CIMI', value.getValue())
        value = stereotype.getTaggedValue("rmVersion")
        assertNotNull value
        assertEquals('0.0.3', value.getValue())

        UmlPackage foundationModelPkg = foundationModel.getObjectByName("CIMI Foundation RM")
        assertNotNull foundationModelPkg
        stereotype = foundationModelPkg.getStereotypes().get("ReferenceModel").get(0);
        assertNotNull stereotype
        value = stereotype.getTaggedValue("rmNamespace")
        assertNotNull value
        assertEquals('RM_FOUNDATION', value.getValue())
        value = stereotype.getTaggedValue("rmPublisher")
        assertNotNull value
        assertEquals('CIMI', value.getValue())
        value = stereotype.getTaggedValue("rmVersion")
        assertNotNull value
        assertEquals('0.0.3', value.getValue())

        UmlPackage cimiClinicalPkg = clinicalModel.getObjectByName("CIMI Clinical")
        assertNotNull cimiClinicalPkg
        stereotype = cimiClinicalPkg.getStereotypes().get("ReferenceModel").get(0);
        assertNotNull stereotype
        value = stereotype.getTaggedValue("rmNamespace")
        assertNotNull value
        assertEquals('RM_CLINICAL', value.getValue())
        value = stereotype.getTaggedValue("rmPublisher")
        assertNotNull value
        assertEquals('CIMI', value.getValue())
        value = stereotype.getTaggedValue("rmVersion")
        assertNotNull value
        assertEquals('0.0.3', value.getValue())
    }
}

package guru.mwangaza.eap.xmi.reader

import groovy.xml.Namespace
import guru.mwangaza.uml.TaggedValue
import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlPackage
import guru.mwangaza.uml.UmlProperty
import guru.mwangaza.uml.UmlStereotype


/**
 * Reader used to unmarshal an XMI 2.1 UML model.
 * 
 * @author cnanjo
 *
 */
class UmlModelLoader {

	public static final String DEFAULT_XMI_NAMESPACE = "http://www.omg.org/spec/XMI/20131001"
	public static final String DEFAULT_UML_NAMESPACE = "http://www.omg.org/spec/UML/20131001"
	public static final String AML_RM_NAMESPACE = "http://www.omg.org/spec/AML/20150501/ReferenceModelProfile.xmi"
	
	Namespace uml
	Namespace xmi
	Namespace rm
	PackageReader packageReader
	Map<String, UmlModel> dependencies

	def loadFromFilePath = {filePath -> new XmlParser().parse(new File(filePath))}
	def loadFromStream = {filePath -> new XmlParser().parse((InputStream)getClass().getResourceAsStream(filePath))}

	public UmlModelLoader() {
		uml = new groovy.xml.Namespace(DEFAULT_UML_NAMESPACE, 'uml')
		xmi = new groovy.xml.Namespace(DEFAULT_XMI_NAMESPACE, 'xmi')
		rm = new groovy.xml.Namespace(AML_RM_NAMESPACE, 'rm')
		packageReader = new PackageReader(uml, xmi)
	}

	public UmlModelLoader(String umlNamespace, String xmiNamespace) {
		uml = new groovy.xml.Namespace(umlNamespace, 'uml')
		xmi = new groovy.xml.Namespace(xmiNamespace, 'xmi')
		rm = new groovy.xml.Namespace(AML_RM_NAMESPACE, 'rm')
		packageReader = new PackageReader(uml, xmi)
	}
	
	def UmlModel loadModel(def loadClosure, def loadArguments) {
		def xmlModel = loadClosure.call(loadArguments)
		return processModel(xmlModel)
	}
	
	def UmlModel loadModelFromClassPath(String path) {
		UmlModel model = loadModel(loadFromStream,path)
		model.buildIndex();
		return model
	}

	def UmlModel loadFromFilePath(String path) {
		UmlModel model = loadModel(loadFromFilePath,path)
		model.buildIndex();
		return model
	}

	def UmlModel processModel(Node node) {
		List<Node> modelNodes = new ArrayList<Node>()
		ReaderUtils.findNodes(node, modelNodes, "Model")
		UmlModel model = new UmlModel(modelNodes[0].'@name')
		if(dependencies != null) {
			model.setDependencies(dependencies)
		}
		handleUmlPrimitiveTypes(model);
		packageReader.processPackagedElements(modelNodes[0].children(), model, model)
		model.populateTypes()
		List<UmlStereotype> stereotypes = processAMLReferenceModelStereotype(node.getAt(rm.ReferenceModel), model)
		processXmiElementExtensions(node, model)
		return model
	}

	//TODO Generalize stereotype processing
	def List<UmlStereotype> processAMLReferenceModelStereotype(def node, UmlModel model) { //TODO Make whole thing more generic and pluggable rather than AML-specific
		List<UmlStereotype> stereotypes = new ArrayList<>();
		UmlStereotype stereotype = null;
		node.each { it ->
			stereotype = new UmlStereotype()
			stereotypes.add(stereotype)
			def id = it.attribute(xmi.id)
			def packageReferenceId = it.'@base_Package'
			def namespace = it.'@rmNamespace'
			def rmPublisher = it.'@rmPublisher'
			def rmVersion = it.'@rmVersion'
			stereotype.setName("ReferenceModel")
			stereotype.setId(id)
			if(namespace != null) {
				stereotype.addTaggedValue(new TaggedValue("rmNamespace", namespace))
			}
			if(rmPublisher != null) {
				stereotype.addTaggedValue(new TaggedValue("rmPublisher", rmPublisher))
			}
			if(rmVersion != null) {
				stereotype.addTaggedValue(new TaggedValue("rmVersion", rmVersion))
			}
			if(packageReferenceId != null) {
				stereotype.setBasePackageId(packageReferenceId)
				UmlPackage basePackage = model.getObjectById(packageReferenceId);
				if (basePackage != null) {
					basePackage.addStereotype(stereotype);
				} else {
					println "Skipping base package: " + packageReferenceId;
				}
			}
		}
		return stereotypes
	}

	def handleUmlPrimitiveTypes(UmlModel model) {
		UmlClass umlString = new UmlClass("String")
		umlString.setId("http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi#String")
		model.putObject(umlString.getId(), umlString)
	}

	def processXmiElementExtensions(Node node, UmlModel model) {
		NodeList elements = node[xmi.Extension].elements.element
		elements.each{ it -> 
			def type = it.attribute(xmi.type)
			if(type == 'uml:Class') {
				processClassElementExtension(it, model)
			}
		}
	}
	
	def processClassElementExtension(Node node, UmlModel model) {
		def id = node.attribute(xmi.idref)
		UmlClass item = model.getObjectById(id)
		def name = node.'@name'
		def documentation = node.properties[0].'@documentation'
		item.setDescription(documentation)
		processTags(item, node)
		if(node.attributes.attribute.size() > 0) {
			processExtendedClassAttributes(node.attributes.attribute, model);
		}
	}
	
	def processTags(def item, Node node) {
		def tags = node.tags.tag
		tags.each { it ->
			def tagName = it.'@name'
			def tagValue = it.'@value'
			if(item != null) {
				item.addTag(new TaggedValue(tagName, tagValue));
			} else {
				println "Skipping " + name
			}
		}
		item.buildTaggedValueIndex();
	}
	
	def processExtendedClassAttributes(def attributes, UmlModel model) {
		attributes.each { it ->
			String id = it.attribute(xmi.idref)
			String documentation = it.documentation[0].'@value'
			UmlProperty attribute = model.getObjectById(id)
			if(documentation != null) {
				attribute.setDocumentation(documentation)
			}
			processTags(attribute, it)
		}
	}
	
}

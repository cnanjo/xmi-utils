package guru.mwangaza.eap.xmi.reader

import groovy.xml.Namespace
import guru.mwangaza.uml.TaggedValue
import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlProperty


/**
 * Reader used to unmarshal an XMI 2.1 UML model.
 * 
 * @author cnanjo
 *
 */
class UmlModelLoader {

	public static final String DEFAULT_XMI_NAMESPACE = "http://www.omg.org/spec/XMI/20131001";
	public static final String DEFAULT_UML_NAMESPACE = "http://www.omg.org/spec/UML/20131001";
	
	Namespace uml
	Namespace xmi
	PackageReader packageReader
	Map<String, UmlModel> dependencies

	def loadFromFilePath = {filePath -> new XmlParser().parse(new File(filePath))}
	def loadFromStream = {filePath -> new XmlParser().parse((InputStream)getClass().getResourceAsStream(filePath))}

	public UmlModelLoader() {
		uml = new groovy.xml.Namespace(DEFAULT_UML_NAMESPACE, 'uml')
		xmi = new groovy.xml.Namespace(DEFAULT_XMI_NAMESPACE, 'xmi')
		packageReader = new PackageReader(uml, xmi)
	}

	public UmlModelLoader(String umlNamespace, String xmiNamespace) {
		uml = new groovy.xml.Namespace(umlNamespace, 'uml')
		xmi = new groovy.xml.Namespace(xmiNamespace, 'xmi')
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
		processXmiElementExtensions(node, model)
		return model
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

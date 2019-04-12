/**
 * Copyright (C) 2013 - 2017 Claude Nanjo
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by cnanjo.
 */
package guru.mwangaza.eap.xmi.reader

import groovy.xml.QName
import guru.mwangaza.uml.TaggedValue
import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlComponent
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlPackage
import guru.mwangaza.uml.UmlProfileDefinition
import guru.mwangaza.uml.UmlProperty
import guru.mwangaza.uml.UmlStereotype

class XmiReader extends BaseXmiReader {

	XmiReaderContext context;
	PackageReader packageReader
	Map<String, UmlModel> dependencies = new HashMap<>()
	Map<String, UmlProfileDefinition> profileDefinitions = new HashMap<>()
	Map<String, UmlStereotype> stereotypeDefinitions = new HashMap<>()
	BaseProfileReader profileReader

	def uml
	def xmi

	def loadFromFilePath = {filePath -> new XmlParser().parse(new File(filePath))}
	def loadFromStream = {filePath -> new XmlParser().parse((InputStream)getClass().getResourceAsStream(filePath))}

	public XmiReader(XmiReaderContext context) {
		this.context = context
		this.uml = context.getNamespace("uml")
		this.xmi = context.getNamespace("xmi")
		packageReader = new PackageReader(context)
	}

	def addProfileReader(BaseProfileReader reader) {
		this.profileReader = reader
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

	def addProfileDefinition(UmlProfileDefinition profileDefinition) {
		this.addProfileDefinition(profileDefinition)
	}

	def UmlModel processModel(Node node) {
		List<Node> modelNodes = new ArrayList<Node>()
		XmiReaderUtils.findNodes(node, modelNodes, "Model")
		UmlModel model = new UmlModel(modelNodes[0].'@name')
		if(dependencies != null) {
			model.setDependencies(dependencies)
		}
		handleUmlPrimitiveTypes(model);
		packageReader.processPackagedElements(modelNodes[0].children(), model, model)

		def profileNodes = node.depthFirst().findAll { it ->
			XmiReaderUtils.getLocalName(it.name()) == 'Profile'
		}

		profileNodes.each { it ->
			MagicDrawProfileReader reader = new MagicDrawProfileReader(getContext())
			reader.processUmlProfile(it, model);
		}

		model.populateTypes()
		model.handleParameterReferences();
		//List<UmlStereotype> stereotypes = processAMLReferenceModelStereotype(node.getAt(rm.ReferenceModel), model)
		processXmiElementExtensions(node, model)
		processProfileDefinition(node, model)
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
		umlString.setUmlPrimitive(true);
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

	//Experimental
	def processProfileDefinition(def rootXmlNode, UmlModel model) {
		model.getProfileDefinitionMap().each { profileName, umlProfileDefinitions ->
			umlProfileDefinitions.each { umlProfileDefinition ->
				if (umlProfileDefinition.getName().equals("ReferenceModelProfile")) {
					print "HERE"
				}
				umlProfileDefinition.getStereotypeDefinitions().each {
					umlStereotypeDefinition ->
						if (umlStereotypeDefinition != null) {
							List<Node> foundStereotypeNodes = new ArrayList<>()
							XmiReaderUtils.findNodes(rootXmlNode, foundStereotypeNodes, umlStereotypeDefinition.getName())
							foundStereotypeNodes.each { foundStereotypeNode ->
								UmlComponent umlComponent = null;
								UmlStereotype stereotype = new UmlStereotype()
								stereotype.setName(umlStereotypeDefinition.getName())
								Collection<UmlProperty> tags = umlStereotypeDefinition.getProperties().values()
								tags.each { tag ->
									def tagValue = foundStereotypeNode.attribute(tag.getName())
									if (tag != null && tag.getName().equalsIgnoreCase("base_Element") ||
											tag.getName().equalsIgnoreCase("base_Package") ||
											tag.getName().equalsIgnoreCase("base_Property") ||
											tag.getName().equalsIgnoreCase("base_Abstraction")) {
										umlComponent = model.getObjectById(tagValue);
									}
									if (tagValue == null) {
										String tagAsElementName = tag.getName().replaceAll('-', '_')
										def nodeList = foundStereotypeNode."${tagAsElementName}"
										if (nodeList.size() == 1) {
											tagValue = foundStereotypeNode.text()
										} else if (nodeList.size() > 1) {
											print "More than one tag value element with that name"
										}
									}
									stereotype.addTaggedValue(new TaggedValue(tag.getName(), tagValue))
								}
								if (umlComponent != null) {//TODO Think this through
									List<UmlStereotype> stereotypeList = umlComponent.getStereotypes().get(stereotype.getName());
									if(stereotypeList == null || stereotypeList.size() == 0) {
										umlComponent.addStereotype(stereotype);
									} else {
										Boolean found = false;
										for(int index = 0; index < stereotypeList.size(); index++) {
											UmlStereotype existingStereotypeWithSameName = stereotypeList.get(index)
											if (stereotype.equals(existingStereotypeWithSameName)) {
												found = true;
												break;
											}
										}
										umlComponent.addStereotype(stereotype)

									}
								}
							}
						}
				}
			}
		}
	}

	/**
	 * Factory method for the creation of a new XMI Reader configured will all default settings.
	 *
	 * @return
	 */
	public static XmiReader configureDefaultXmiReader() {
		XmiReaderContext context = new XmiReaderContext();
		context.addNamespace("uml", new groovy.xml.Namespace(XmiReaderContext.DEFAULT_UML_NAMESPACE, "uml"));
		context.addNamespace("xmi", new groovy.xml.Namespace(XmiReaderContext.DEFAULT_XMI_NAMESPACE, "xmi"));
		context.addNamespace("xsi", new groovy.xml.Namespace(XmiReaderContext.DEFAULT_XSI_NAMESPACE, "xsi"));
		context.addNamespace("rm", new groovy.xml.Namespace(XmiReaderContext.AML_RM_NAMESPACE, "rm"));
		XmiReader xmiReader = new XmiReader(context);
		return xmiReader;
	}

	public static XmiReader configureDefaultMagicDrawXmiReader() {
		XmiReader xmiReader = configureDefaultXmiReader()
		xmiReader.addProfileReader(new MagicDrawProfileReader(xmiReader.getContext()))
		return xmiReader
	}
	
}

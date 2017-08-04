package guru.mwangaza.eap.xmi.reader

import groovy.xml.Namespace
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlPackage


/**
 * Reader used to unmarshal an XMI 2.1 UML package.
 * 
 * @author cnanjo
 *
 */
class PackageReader {
	
	def uml
	def xmi
	def classReader
	def datatypeReader
	def documentationReader

	public PackageReader(XmiReaderContext context) {
		this.uml = context.getNamespace("uml");
		this.xmi = context.getNamespace("xmi");
		classReader = new ClassReader(context)
		datatypeReader = new DatatypeReader(context)
		documentationReader = new DocumentationReader(context)
	}
	
	public UmlPackage readPackage(Node packageNode, UmlModel model) {
		UmlPackage umlPackage = new UmlPackage(packageNode.'@name')
		umlPackage.setId(packageNode.attribute(xmi.id))
		model.putObject(umlPackage.getId(), umlPackage)
		packageNode.ownedComment.each { it -> documentationReader.processUmlComment(it, umlPackage, model)}
		return umlPackage
	}

	def processPackagedElements(NodeList packagedElements, def parent, UmlModel model) {
		packagedElements.each{it -> processPackagedElement(it, parent, model)}
	}
	
	def processPackagedElement(Node packagedElement, def parent, UmlModel model) {
		def parsedItem = null
		if(packagedElement instanceof Node && XmiReaderUtils.getLocalName(packagedElement.name()) == "packagedElement") {
			if(packagedElement.attribute(xmi.type) == 'uml:Package') {
				parsedItem = readPackage(packagedElement, model)
				parent.addPackage(parsedItem)
				processPackagedElements(packagedElement.children(), parsedItem, model)
			} else if(packagedElement.attribute(xmi.type) == 'uml:Class') {
				classReader.processUmlClass(packagedElement, parent, model)
			} else if(packagedElement.attribute(xmi.type) == 'uml:DataType') {
				datatypeReader.processUmlDatatype(packagedElement, parent, model)
			} else {
				//throw new RuntimeException("Unknown packagedElement child " + packagedElement.attribute(xmi.type)) 
			}
		}
		return parsedItem
	}
	
}

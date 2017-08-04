package guru.mwangaza.eap.xmi.reader

import groovy.xml.Namespace
import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlProperty


/**
 * Reader used to unmarshal an XMI 2.1 UML class.
 * 
 * @author cnanjo
 *
 */
class DatatypeReader {

	def uml
	def xmi
	def documentationReader

	public DatatypeReader(XmiReaderContext context) {
		this.uml = context.getNamespace("uml")
		this.xmi = context.getNamespace("xmi")
		documentationReader = new DocumentationReader(context)
	}
	
	public UmlClass readDatatype(Node classNode, UmlModel model) {
		UmlClass umlClass = new UmlClass(classNode.'@name')
		umlClass.setPrimitive(true);
		umlClass.setId(classNode.attribute(xmi.id))
		umlClass.setProperties(new ArrayList<UmlProperty>())
		classNode.ownedComment.each { it -> documentationReader.processUmlComment(it, umlClass, model)}
		model.putObject(umlClass.getId(), umlClass)
		umlClass.setModel(model);
		return umlClass
	}
	
	def processUmlDatatype(Node classNode, def parent, UmlModel model) {
		def umlClass = readDatatype(classNode, model)
		parent.addClass(umlClass)
	}

}

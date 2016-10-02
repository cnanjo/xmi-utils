package guru.mwangaza.eap.xmi.reader

import groovy.xml.Namespace
import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel


/**
 * Reader used to unmarshal an XMI 2.1 UML class.
 * 
 * @author cnanjo
 *
 */
class ClassReader {
	
	def uml
	def xmi
	def propertyReader

	public ClassReader(Namespace uml, Namespace xmi) {
		this.uml = uml
		this.xmi = xmi
		propertyReader = new PropertyReader(uml, xmi)
	}
	
	public UmlClass readClass(Node classNode, UmlModel model) {
		UmlClass umlClass = new UmlClass(classNode.'@name')
		umlClass.setId(classNode.attribute(xmi.id))
		model.putObject(umlClass.getId(), umlClass)
		umlClass.setModel(model);
		classNode.ownedAttribute.each { it -> propertyReader.processOwnedAttribute(it, umlClass, model) }
		classNode.generalization.each { it -> umlClass.addGeneralizationId(it.'@general') }
		return umlClass
	}
	
	def processUmlClass(Node classNode, def parent, UmlModel model) {
		def umlClass = readClass(classNode, model)
		parent.addClass(umlClass)
	}

}

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
	def templateReader
	def templateBindingReader
	def documentationReader

	public ClassReader(Namespace uml, Namespace xmi) {
		this.uml = uml
		this.xmi = xmi
		propertyReader = new PropertyReader(uml, xmi)
		templateReader = new TemplateSignatureReader(uml, xmi)
		templateBindingReader = new TemplateBindingReader(uml, xmi)
		documentationReader = new DocumentationReader(uml, xmi)
	}
	
	public UmlClass readClass(Node classNode, UmlModel model) {
		UmlClass umlClass = new UmlClass(classNode.'@name')
		umlClass.setId(classNode.attribute(xmi.id))
		model.putObject(umlClass.getId(), umlClass)
		umlClass.setModel(model);
		classNode.ownedAttribute.each { it -> propertyReader.processOwnedAttribute(it, umlClass, model) }
		classNode.generalization.each { generalization ->
			if (generalization.@general != null && (generalization.@general.size() > 0 || generalization.@general.get(0) != null)) {
				umlClass.addGeneralizationId(generalization.'@general')
			} else {
				umlClass.addGeneralizationId(generalization.general.'@href')
			}
		}
		classNode.ownedComment.each { it -> documentationReader.processUmlComment(it, umlClass, model)}
		if(classNode.'@isAbstract' != null && classNode.'@isAbstract'.equalsIgnoreCase("true")) {
			umlClass.setAbstract(true);
		}
		classNode.ownedTemplateSignature.each { it -> templateReader.processTemplateSignature(it, umlClass, model) }

		classNode.templateBinding.each { it ->
			templateBindingReader.processTemplateBinding(it, umlClass, model)

		}

		return umlClass
	}
	
	def processUmlClass(Node classNode, def parent, UmlModel model) {
		def umlClass = readClass(classNode, model)
		parent.addClass(umlClass)
	}

}

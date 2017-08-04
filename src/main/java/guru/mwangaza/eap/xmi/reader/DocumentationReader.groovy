package guru.mwangaza.eap.xmi.reader

import groovy.xml.Namespace
import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlComment
import guru.mwangaza.uml.UmlComponent
import guru.mwangaza.uml.UmlModel


/**
 * Reader used to unmarshal an XMI 2.1 UML class.
 * 
 * @author cnanjo
 *
 */
class DocumentationReader {

	def uml
	def xmi

	public DocumentationReader(XmiReaderContext context) {
		this.uml = context.getNamespace("uml")
		this.xmi = context.getNamespace("xmi")
	}
	
	public UmlComment readDocumentation(Node commentNode, UmlModel model) {
		UmlComment umlComment = new UmlComment(commentNode.'@body')
		umlComment.setId(commentNode.attribute(xmi.id))
		return umlComment
	}
	
	def processUmlComment(Node classNode, UmlComponent parent, UmlModel model) {
		def umlComment = readDocumentation(classNode, model)
		parent.setDocumentation(umlComment.getBody())
	}

}

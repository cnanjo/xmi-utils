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

import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlInterface
import guru.mwangaza.uml.UmlModel

/**
 * Reader used to unmarshal an XMI 2.1 UML class.
 *
 * @author cnanjo
 *
 */
class InterfaceReader {

	def uml
	def xmi
	def propertyReader
	def templateReader
	def templateBindingReader
	def documentationReader

	public InterfaceReader(XmiReaderContext context) {
		this.uml = context.getNamespace("uml");
		this.xmi = context.getNamespace("xmi");
		propertyReader = new PropertyReader(context)
		templateReader = new TemplateSignatureReader(context)
		templateBindingReader = new TemplateBindingReader(context)
		documentationReader = new DocumentationReader(context)
	}
	
	public UmlInterface readInterface(Node interfaceNode, UmlModel model) {
		UmlInterface umlInterface = new UmlInterface(interfaceNode.'@name')
		umlInterface.setId(interfaceNode.attribute(xmi.id))
		model.putObject(umlInterface.getId(), umlInterface)
		umlInterface.setModel(model);
		interfaceNode.ownedAttribute.each { it -> propertyReader.processOwnedAttribute(it, umlInterface, model) }
		interfaceNode.generalization.each { generalization ->
			if (generalization.@general != null && (generalization.@general.size() > 0 || generalization.@general.get(0) != null)) {
				umlInterface.addGeneralizationId(generalization.'@general')
			} else {
				umlInterface.addGeneralizationId(generalization.general.'@href')
			}
		}
		interfaceNode.ownedComment.each { it -> documentationReader.processUmlComment(it, umlInterface, model)}
		if(interfaceNode.'@isAbstract' != null && interfaceNode.'@isAbstract'.equalsIgnoreCase("true")) {
			umlInterface.setAbstract(true);
		}
		interfaceNode.ownedTemplateSignature.each { it -> templateReader.processTemplateSignature(it, umlInterface, model) }

		interfaceNode.templateBinding.each { it ->
			templateBindingReader.processTemplateBinding(it, umlInterface, model)

		}

		umlInterface.getProperties().each{ prop -> if(prop.getFirstType() instanceof UmlClass){throw new RuntimeException("ERROR HERE " + umlInterface.getName())}}

		return umlInterface
	}
	
	def processUmlInterface(Node interfaceNode, def parent, UmlModel model) {
		def umlInterface = readInterface(interfaceNode, model)
		parent.addInterface(umlInterface)
		umlInterface.setParentPackage(parent);
	}

}

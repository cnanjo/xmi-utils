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

	public ClassReader(XmiReaderContext context) {
		this.uml = context.getNamespace("uml");
		this.xmi = context.getNamespace("xmi");
		propertyReader = new PropertyReader(context)
		templateReader = new TemplateSignatureReader(context)
		templateBindingReader = new TemplateBindingReader(context)
		documentationReader = new DocumentationReader(context)
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

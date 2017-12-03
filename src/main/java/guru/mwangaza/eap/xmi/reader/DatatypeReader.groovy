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

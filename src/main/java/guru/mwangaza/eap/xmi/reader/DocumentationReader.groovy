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

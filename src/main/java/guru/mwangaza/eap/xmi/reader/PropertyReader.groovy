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
import guru.mwangaza.uml.BaseClassifier
import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlProperty


/**
 * Reader used to unmarshal an XMI 2.1 UML class property.
 * 
 * @author cnanjo
 *
 */
class PropertyReader {
	
	def uml
	def xmi
	def documentationReader


	public PropertyReader(XmiReaderContext context) {
		this.uml = context.getNamespace("uml")
		this.xmi = context.getNamespace("xmi")
		documentationReader = new DocumentationReader(context)
	}

	public UmlProperty readProperty(Node propertyNode, UmlModel model) {
		def upperValue = propertyNode.upperValue.'@value'[0]
		def lowerValue = propertyNode.lowerValue.'@value'[0]
		def sth = propertyNode.lowerValue;
		if(propertyNode.upperValue.size() > 0) {
			if (upperValue == "*" || upperValue == "-1") {
				upperValue = -1
			} else if (upperValue == null) {
				throw new RuntimeException("Investigate this case. Is there really a default value for upperValue?")
			} else {
				upperValue = upperValue.toInteger()
			}
		} else {
			upperValue = 1;
		}
		if(propertyNode.lowerValue.size() > 0) {
			if (lowerValue != null) {
				lowerValue = lowerValue.toInteger();
			} else {
				lowerValue = 0;
			}
		} else {
			lowerValue = 1;
		}
		UmlProperty property = new UmlProperty(propertyNode.'@name', 
			lowerValue,
			upperValue)
		property.setId(propertyNode.attribute(xmi.id))
		def type = propertyNode.'@type'
		if(type != null) {
			property.setTypeId(type)
		} else {
			type = propertyNode.type.'@href'[0]
			property.setTypeId(type)
		}
		if(type != null) {
			model.putObject(property.getId(), property)
		} else {
			println "Orphaned property! " + property
		}
		propertyNode.ownedComment.each { it -> documentationReader.processUmlComment(it, property, model)}
		return property
	}
	
	def processOwnedAttribute(Node ownedAttribute, BaseClassifier parent, UmlModel model) {
		UmlProperty parsedItem = null
		if(ownedAttribute.attribute(xmi.type) == 'uml:Property') {
			parsedItem = readProperty(ownedAttribute, model)
			parent.addProperty(parsedItem);
			parsedItem.setSource(parent);
		} else {
			throw new RuntimeException("Unknown attribute " + ownedAttribute.attribute(xmi.type))
		}
		return parsedItem
	}
}

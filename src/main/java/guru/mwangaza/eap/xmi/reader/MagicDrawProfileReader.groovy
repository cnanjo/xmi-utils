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

import guru.mwangaza.uml.TaggedValue
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlProfileDefinition
import guru.mwangaza.uml.UmlProperty
import guru.mwangaza.uml.UmlStereotype
import guru.mwangaza.uml.UmlStereotypeDefinition

/**
 * Copyright 2017 Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by cnanjo on 8/2/17.
 */

class MagicDrawProfileReader extends BaseProfileReader {
    def uml
    def xmi
    def xsi
    def uri

    public MagicDrawProfileReader(XmiReaderContext context) {
        this.uml = context.getNamespace("uml")
        this.xmi = context.getNamespace("xmi")
        this.xsi = context.getNamespace("xsi")
    }

    public UmlProfileDefinition readProfile(Node umlProfileNode, UmlModel model) {
        UmlProfileDefinition umlProfile = new UmlProfileDefinition(umlProfileNode.'@name')
        umlProfile.setId(umlProfileNode.attribute(xmi.id))
        umlProfileNode.packagedElement.each { it ->
            String type = it.attribute(xsi.type)
            if(type != null && type.equalsIgnoreCase("uml:Stereotype")) {
                processStereotype(it, umlProfile)
            } else {
                println 'Skipping node of type ' + type;
            }
        }
        model.putObject(umlProfile.getId(), umlProfile)
        return umlProfile
    }

    public void processStereotype(Node umlStereotypeNode, UmlProfileDefinition umlProfile) {
        UmlStereotypeDefinition stereotype = new UmlStereotypeDefinition()
        stereotype.setName(umlStereotypeNode.'@name')
        umlStereotypeNode.ownedAttribute.each {it ->
            String name = it.'@name'
            UmlProperty property = new UmlProperty(name)
            stereotype.addProperty(property)
            property.setSource(stereotype);
        }
        umlProfile.addStereotypeDefinition(stereotype)
    }

    def processUmlProfile(Node umlProfileNode, UmlModel model) {
        def umlProfile = readProfile(umlProfileNode, model)
        if(umlProfile.getName().equals("ReferenceModelProfile")) {
            print "STOP HERE"
        }
        model.addProfile(umlProfile)
    }
}

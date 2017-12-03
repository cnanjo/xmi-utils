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
package guru.mwangaza.uml;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cnanjo on 10/3/16.
 */
public class UmlTemplateSignature extends UmlComponent implements Identifiable, Cloneable {
    private List<UmlTemplateParameter> parameters;
    private UmlClass owningClass;
    private Map<String, UmlTemplateParameter> idToParameterMap;

    public UmlTemplateSignature() {
        parameters = new ArrayList<UmlTemplateParameter>();
        idToParameterMap = new HashMap<String, UmlTemplateParameter>();
    }

    public List<UmlTemplateParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<UmlTemplateParameter> parameters) {
        this.parameters = parameters;
    }

    public UmlClass getOwningClass() {
        return owningClass;
    }

    public void setOwningClass(UmlClass owningClass) {
        this.owningClass = owningClass;
    }

    public void addTemplateParameter(UmlTemplateParameter parameter) {
        parameters.add(parameter);
    }

    public UmlTemplateParameter getTemplateparameter(String parameterId) {
        return idToParameterMap.get(parameterId);
    }

    public void populateTemplateParameters(UmlModel model) {
        for(UmlTemplateParameter parameter : parameters) {
                parameter.populateParameterType(model);
        }
    }

    public void populateTemplateSignature(UmlClass parent, UmlModel model) {
        populateTemplateParameters(model);
        setOwningClass(parent);
    }

    public UmlTemplateParameter getParameter(String id) {
        return idToParameterMap.get(id);
    }

    public void putParameter(String id, UmlTemplateParameter param) {
        idToParameterMap.put(id, param);
    }

    public String toString() {
        return "id: " + getId() + " - params: " + parameters;
    }
}

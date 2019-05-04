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

/**
 * Created by cnanjo on 11/3/16.
 */
public class ParameterSubstitution {

    private String Id;
    private UmlTemplateParameter formalParameter;
    private String formalParameterRefId;
    private BaseClassifier actualParameter;
    private String actualParameterRefId;

    public ParameterSubstitution() {}

    public ParameterSubstitution(UmlTemplateParameter formal, BaseClassifier actual) {
        this.formalParameter = formal;
        this.actualParameter = actual;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public UmlTemplateParameter getFormalParameter() {
        return formalParameter;
    }

    public void setFormalParameter(UmlTemplateParameter formalParameter) {
        this.formalParameter = formalParameter;
    }

    public String getFormalParameterRefId() {
        return formalParameterRefId;
    }

    public void setFormalParameterRefId(String formalParameterRefId) {
        this.formalParameterRefId = formalParameterRefId;
    }

    public BaseClassifier getActualParameter() {
        return actualParameter;
    }

    public void setActualParameter(BaseClassifier actualParameter) {
        this.actualParameter = actualParameter;
    }

    public String getActualParameterRefId() {
        return actualParameterRefId;
    }

    public void setActualParameterRefId(String actualParameterRefId) {
        this.actualParameterRefId = actualParameterRefId;
    }

    public void populateParameterSubstitution(UmlModel model) {
        formalParameter = (UmlTemplateParameter) model.getIdMap().get(formalParameterRefId);
        actualParameter = (UmlClass) model.getIdMap().get(actualParameterRefId);
    }
}

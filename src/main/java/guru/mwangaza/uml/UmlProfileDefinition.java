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

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
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
 * Created by cnanjo on 8/2/17.
 */

public class UmlProfileDefinition extends UmlComponent implements Identifiable{
    private String projectName;
    private List<UmlStereotypeDefinition> stereotypeDefinitions;

    public UmlProfileDefinition(String name) {
        setName(name);
        stereotypeDefinitions = new ArrayList<>();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<UmlStereotypeDefinition> getStereotypeDefinitions() {
        return stereotypeDefinitions;
    }

    public void setStereotypeDefinitions(List<UmlStereotypeDefinition> stereotypes) {
        this.stereotypeDefinitions = stereotypes;
    }

    public void addStereotypeDefinition(UmlStereotypeDefinition stereotype) {
        this.stereotypeDefinitions.add(stereotype);
    }
}

package guru.mwangaza.eap.xmi.reader

import guru.mwangaza.uml.UmlClass
import guru.mwangaza.uml.UmlModel
import guru.mwangaza.uml.UmlProfileDefinition
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

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
 * Created by cnanjo on 8/5/17.
 */

class ProfileReaderTest {

    private UmlModel model
    private XmiReader xmiReader
    private List<UmlClass> classes1
    private List<UmlClass> classes2

    @Before
    public void setup() {
        xmiReader = XmiReader.configureDefaultXmiReader();
        model = xmiReader.loadModel(xmiReader.loadFromStream,"/xmi/XmiUtilsTestProfile.xml")
        model.buildIndex()
    }

    @Test
    void testLoadProfile() {
        UmlProfileDefinition profile = model.getProfileDefinitionMap().get("TestProfile1")
        assertNotNull profile
    }
}

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
import guru.mwangaza.uml.UmlPackage
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class InterfaceRealizationTest {
	
	private UmlModel model
	private XmiReader xmiReader
	private List<UmlClass> classes1
	private List<UmlClass> classes2
	
	@Before
	public void setup() {
		xmiReader = XmiReader.configureDefaultMagicDrawXmiReader();
		model = xmiReader.loadModel(xmiReader.loadFromStream,"/xmi/InterfaceRealization.xml")
		model.buildIndex()
	}

	@Test
	public void testInterfaceUnmarshalling() {
		assertNotNull(model.getObjectByName("mypackage"));
		assertEquals(2, ((UmlPackage)model.getObjectByName("mypackage")).getInterfaces().size());
		assertNotNull(model.getObjectByName("Interface1"))
		assertNotNull(model.getObjectByName("Interface2"))
	}
	
	@Test
	public void testInterfaceId() {
		assertNotNull(model.getObjectByName("Interface1").getId())
		assertNotNull(model.getObjectByName("Interface2").getId())
	}

	@Test
	public void testInterfaceInheritance() {
		UmlClass class1 = model.getObjectByName("Class1")
		assertEquals(2, class1.getRealizationIds().size())
		assertEquals(2, class1.getRealizations().size())
	}
}

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

import guru.mwangaza.uml.*
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class InterfaceReaderTest {
	
	private UmlModel model
	private XmiReader xmiReader
	private List<UmlClass> classes1
	private List<UmlClass> classes2
	
	@Before
	public void setup() {
		xmiReader = XmiReader.configureDefaultMagicDrawXmiReader();
		model = xmiReader.loadModel(xmiReader.loadFromStream,"/xmi/UmlInterfaceProject.xml")
		model.buildIndex()
	}

	@Test
	public void testInterfaceUnmarshalling() {
		assertNotNull(model.getObjectByName("TestPackage"));
		assertEquals(2, ((UmlPackage)model.getObjectByName("TestPackage")).getInterfaces().size());
		assertEquals(1, ((UmlInterface)model.getObjectByName("Interface1")).getProperties().size())
		assertEquals(1, ((UmlInterface)model.getObjectByName("Interface2")).getProperties().size())
	}
	
	@Test
	public void testInterfaceId() {
		assertNotNull(model.getObjectByName("Interface1").getId())
		assertNotNull(model.getObjectByName("Interface2").getId())
	}

	@Test
	public void testInterfaceInheritance() {
		UmlInterface interface1 = model.getObjectByName("Interface1");
		UmlInterface interface2 = model.getObjectByName("Interface2");
		assertEquals(0, interface1.getGeneralizations().size());
		assertEquals("Interface1", interface2.getGeneralizations().get(0).getName());
		assertEquals(1, interface2.getGeneralizations().size());
	}
}

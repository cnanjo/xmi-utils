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
import guru.mwangaza.uml.UmlModel
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

class OperationReaderTest {
	
	private UmlModel model
	private XmiReader xmiReader
	private List<UmlClass> classes1
	private List<UmlClass> classes2
	
	@Before
	public void setup() {
		xmiReader = XmiReader.configureDefaultMagicDrawXmiReader();
		model = xmiReader.loadModel(xmiReader.loadFromStream,"/xmi/MethodSerialization.xml")
		model.buildIndex()
	}

	@Test
	public void testClassUnmarshalling() {
		UmlClass umlClass = (UmlClass)model.getObjectByName("MyClass")
		assertNotNull(umlClass);
		assertEquals(2, umlClass.getOperations().size())
		assertEquals("MyClass", umlClass.getOperations().get(0).getName())
		assertEquals("myMethod", umlClass.getOperations().get(1).getName())
		assertTrue(umlClass.getOperations().get(1).isStatic())
		assertTrue(umlClass.getOperations().get(1).isAbstract())
		assertEquals(0, umlClass.getOperations().get(0).getParameters().size())
		assertEquals(3, umlClass.getOperations().get(1).getParameters().size())
		assertEquals("_18_5_3_88f027a_1555365591991_492475_4950", umlClass.getOperations().get(1).getParameters().get(1).getTypeId())
		assertEquals("MyType", umlClass.getOperations().get(1).getParameters().get(0).getType().getName());
	}
}

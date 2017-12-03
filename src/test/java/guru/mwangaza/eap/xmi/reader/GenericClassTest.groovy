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

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class GenericClassTest {
	
	private UmlModel model
	private List<UmlClass> classes1
	private List<UmlClass> classes2
	
	@Before
	public void setup() {
		//Load first model
		XmiReader xmiReader = XmiReader.configureDefaultXmiReader();
		model = xmiReader.loadModelFromClassPath("/xmi/GenericClassTest.xml")
		model.buildIndex()
	}

	@Test
	public void testGenericParameter() {
		UmlClass genericClass = model.getObjectByName("GenericClass")
		assertNotNull(genericClass)
//		var prop = genericClass.getProperties().get(0).getFirstType()
//		println prop
		assertEquals("param1", genericClass.getProperties().get(0).getName())
		assertEquals("S", genericClass.getProperties().get(0).getFirstType().getName())
		assertEquals("param2", genericClass.getProperties().get(1).getName())
		assertEquals("T", genericClass.getProperties().get(1).getFirstType().getName())
	}

}

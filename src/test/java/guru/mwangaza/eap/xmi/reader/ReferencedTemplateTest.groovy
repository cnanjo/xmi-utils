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
import guru.mwangaza.uml.UmlTemplateParameter
import guru.mwangaza.uml.UmlTemplateSignature
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class ReferencedTemplateTest {
	
	private UmlModel model
	private List<UmlClass> classes1
	private List<UmlClass> classes2
	
	@Before
	public void setup() {
		//Load first model
		XmiReader xmiReader = XmiReader.configureDefaultXmiReader();
		model = xmiReader.loadModelFromClassPath("/xmi/ReferencedTemplateTest.xml")
		model.buildIndex()
	}

	@Test
	public void testTemplateBinding() {
		UmlClass array = model.getObjectByName("Array")
		assertNotNull(array)
		UmlTemplateSignature signature = array.getTemplateSignature()
		UmlTemplateParameter parameter = signature.getTemplateparameter("EAID_BC0E68CA_B736_4237_9505_B4099E4CC5BA");
		assertNotNull(parameter)
		assertEquals("T", parameter.getName());
	}

}

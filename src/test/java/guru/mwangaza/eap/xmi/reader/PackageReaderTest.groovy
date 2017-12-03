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
package guru.mwangaza.eap.xmi.reader;

import static org.junit.Assert.*

import org.junit.Before;
import org.junit.Test
import guru.mwangaza.uml.UmlModel

class PackageReaderTest {
	
	private UmlModel model
	private XmiReader xmiReader
	
	@Before
	public void setup() {
		xmiReader = XmiReader.configureDefaultXmiReader();
		model = xmiReader.loadModel(xmiReader.loadFromStream,"/xmi/XmiUtilsTestProject.xml")
		model.buildIndex()
	}

	@Test
	public void testReadPackage() {
		assertEquals(1, model.packages.size)
		assertEquals(2, model.packages[0].packages.size)
		assertEquals("parent_package", model.packages[0].name)
		assertEquals("child_package_1", model.packages[0].packages[0].name)
		assertEquals("child_package_2", model.packages[0].packages[1].name)
	}

}

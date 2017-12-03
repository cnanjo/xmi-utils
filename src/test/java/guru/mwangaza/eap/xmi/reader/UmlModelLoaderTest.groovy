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

import static org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import guru.mwangaza.uml.UmlModel

class UmlModelLoaderTest {
	
	def xmiReader
	def resourcePath = "/xmi/CIMI_Model.xmi";

	@Before
	public void setUp() throws Exception {
		xmiReader = XmiReader.configureDefaultXmiReader();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadFromFilePath() {
		xmiReader.loadFromStream(resourcePath)
	}
	
	@Test
	public void testLoadFromClassPath() {
		assertNotNull(xmiReader.loadModelFromClassPath(resourcePath))
	}

	@Test
	public void testLoadModel() {
		UmlModel model = xmiReader.loadModel(xmiReader.loadFromStream,resourcePath)
		model.buildIndex()
		println model
//		UmlPackage topLevelPackage = model.getPackages().get(0);
//		UmlPackage secondLevelPackage = topLevelPackage.getPackages().get(0);
//		UmlPackage thirdLevelPackage = secondLevelPackage.getPackages().get(0);
//		assertEquals("EA_Model", model.getName())
//		assertEquals(1, model.getPackages().size())
//		assertEquals("Model",topLevelPackage.getName())
//		assertEquals(1, topLevelPackage.getPackages().size())
//		assertEquals("QUICK Class Model", secondLevelPackage.getName())
//		assertEquals(6, secondLevelPackage.getPackages().size())
//		assertTrue(thirdLevelPackage.getPackages().size() == 3)
//		assertEquals("action", thirdLevelPackage.getName())
//		assertNotNull(model.getObjectByName("ConditionOccurrence"))
//		println model.getObjectByName("ConditionOccurrence")
	}

	@Test
	public void testLoadCIMI() {
		Map<String, UmlModel> dependencies = new HashMap<String, UmlModel>();
		xmiReader = XmiReader.configureDefaultXmiReader();
		UmlModel core = xmiReader.loadModelFromClassPath("/xmi/CIMI RM v3.0.5.xml")
		core.buildIndex();
		dependencies.put("CIMI RM v3.0.5.mdzip", core);
		xmiReader = XmiReader.configureDefaultXmiReader();
		xmiReader.setDependencies(dependencies);
		UmlModel foundation = xmiReader.loadModelFromClassPath("/xmi/CIMI RM Foundation v3.0.5.xml")
		foundation.buildIndex();
		dependencies.put("CIMI RM Foundation v3.0.5.mdzip", foundation);
		xmiReader = XmiReader.configureDefaultXmiReader();
		xmiReader.setDependencies(dependencies);
		UmlModel clinical = xmiReader.loadModelFromClassPath("/xmi/CIMI RM Clinical v3.0.5.xml")
		clinical.buildIndex()
		println clinical
	}

	@Test
	public void testLoadCIMICore() {
		Map<String, UmlModel> dependencies = new HashMap<String, UmlModel>();
		xmiReader = XmiReader.configureDefaultXmiReader();
		UmlModel core = xmiReader.loadModelFromClassPath("/xmi/CIMI_RM_CORE.v0.0.2.xml")
		core.buildIndex();
		Object o = core.getObjectByName("Boolean");
		assertNotNull(o);
		assertTrue(o instanceof UmlClass);
		assertEquals("Boolean", ((UmlClass)o).getName());
		assertEquals("logical True/False values; usually physically represented as an integer, but need not be", ((UmlClass)o).getDocumentation());
		println core
	}

}

/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.TEIModules.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.TEIModules.TEIImporterMetaData;

public class TEIMetadataTest {
	TEIImporterMetaData metadata = new TEIImporterMetaData();
	
	@Test
	public void pathStackTest(){
		metadata.push("fileDesc");
		metadata.push("titleStmt");
		metadata.push("title");
		metadata.pop();
		metadata.push("title");
		
		System.out.println(metadata.getcurrentpath());
		assertEquals("/fileDesc[1]/titleStmt[1]/title[2]", metadata.getcurrentpath());
		
		metadata.push_to_XPathMap("tag");
		
	}
}
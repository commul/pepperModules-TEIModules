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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.TEIModules;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SPointingRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructure;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

/**
 * This is a dummy implementation of a {@link PepperImporter}, which can be used as a template to create your own
 * module from. The current implementation creates a corpus-structure looking like this:
 * <pre>
 *       c1
 *    /      \
 *   c2      c3
 *  /  \    /  \
 * d1  d2  d3  d4
 * </pre>
 * For each document d1, d2, d3 and d4 the same document-structure is created. The document-structure contains 
 * the following structure and annotations:
 * <ol>
 * 	<li>primary data</li>
 *  <li>tokenization</li>
 *  <li>part-of-speech annotation for tokenization</li>
 *  <li>information structure annotation via spans</li>
 *  <li>anaphoric relation via pointing relation</li>
 *  <li>syntactic annotations</li>
 * </ol> 
 * This dummy implementation is supposed to give you an impression, of how Pepper works and how you can create
 * your own implementation along that dummy. It further shows some basics of creating a simple Salt model. 
 * <br/>
 * <strong>This code contains a lot of TODO's. Please have a look at them and adapt the code for your needs
 * </strong>
 * At least, a list of not used but helpful methods:
 * <ul>
 *  <li>the salt model to fill can be accessed via {@link #getSaltProject()}</li>
 * 	<li>customization properties can be accessed via {@link #getProperties()}</li>
 *  <li>a place where resources of this bundle are, can be accessed via {@link #getResources()}</li>
 * </ul>
 * If this is the first time, you are implementing a Pepper module, we strongly recommend, to take a look into the
 * 'Developer's Guide for Pepper modules', you will find on <a href="https://korpling.german.hu-berlin.de/saltnpepper/">https://korpling.german.hu-berlin.de/saltnpepper/</a>.
 * @author Florian Zipser
 * @version 1.0
 *
 */
//TODO change the name of the component, for example use the format name and the ending Importer (FORMATImporterComponent)
@Component(name="TEIImporterComponent", factory="PepperImporterComponentFactory")
//TODO change the name of the class from 'SampleImporter' to whatever you like, in Eclipse you can do that via marking the name and press STRG + ALT + 'r' 
public class TEIImporter extends PepperImporterImpl implements PepperImporter{
// =================================================== mandatory ===================================================
	// this is a logger, for recording messages during program process, like debug messages
	private static final Logger logger= LoggerFactory.getLogger(TEIImporter.class);
	
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * <br/>
	 * A constructor for your module. Set the coordinates, with which your module shall be registered. 
	 * The coordinates (modules name, version and supported formats) are a kind of a fingerprint, 
	 * which should make your module unique.
	 */
	public TEIImporter(){
		super();
		//TODO change the name of the module, for example use the format name and the ending Importer (FORMATImporter)
		this.setName("TEIImporter");
		//TODO change the version of your module, we recommend to synchronize this value with the maven version in your pom.xml
		this.setVersion("0.0.1");
		//TODO change "sample" with format name and 1.0 with format version to support
		this.addSupportedFormat("TEI", "2.6.0", null);
		//TODO change the endings in endings of files you want to import, see also predefined endings beginning with 'ENDING_' 
		this.getSDocumentEndings().add("xml");
	}
	
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * <br/>
	 * This method is called by the pepper framework to import the corpus-structure for the passed {@link SCorpusGraph} object. 
	 * In Pepper each import step gets an own {@link SCorpusGraph} to work on. This graph has to be filled with {@link SCorpus} 
	 * and {@link SDocument} objects representing the corpus-structure of the corpus to be imported. 
	 * <br/>
	 * In many cases, the corpus-structure can be retrieved from the file-structure of the source files. Therefore Pepper provides
	 * a default mechanism to map the file-structure to corpus-structure. This default mechanism can be configured. To
	 * adapt the default behavior to your needs, we recommend, to take a look into the 'Developer's Guide for Pepper modules', 
	 * you will find on <a href="https://u.hu-berlin.de/saltnpepper/">https://u.hu-berlin.de/saltnpepper/</a>.
	 * <br/>
	 * Just to show the creation of a corpus-structure for our sample purpose, we here create a simple corpus-structure 
	 * manually. The simple contains a root-corpus <i>c1</i> having two sub-corpora <i>c2</i> and <i>c3</i>. 
	 * Each sub-corpus contains two documents <i>d1</i> and <i>d2</i> for <i>d3</i> and <i>d4</i> and <i>c1</i> for <i>c3</i>.
	 * <pre>
	 *       c1
	 *    /      \
	 *   c2      c3
	 *  /  \    /  \
	 * d1  d2  d3  d4
	 * </pre>
	 * The URIs of the corpora and documents would be:
	 * <ul>
	 *  <li>salt:/c1</li>
	 *  <li>salt:/c1/c2</li>
	 *  <li>salt:/c1/c2/d1</li>
	 *  <li>salt:/c1/c2/d2</li>
	 *  <li>salt:/c1/c3</li>
	 *  <li>salt:/c1/c3/d3</li>
	 *  <li>salt:/c1/c3/d4</li>
	 * </ul>
	 * 
	 * @param corpusGraph the CorpusGraph object, which has to be filled.
	 */
	@Override
	public void importCorpusStructure(SCorpusGraph sCorpusGraph) throws PepperModuleException{	
		/**
		 * TODO this implementation is just a showcase, in production you might want to use the default. 
		 * If yes, uncomment the following line and delete the rest of the implementation, or delete
		 * the entire method to trigger the default method.
		 */
		//super.importCorpusStructure(sCorpusGraph);
		
		// creates the super-corpus c1, in Salt you can create corpora via a URI
		SCorpus c1= sCorpusGraph.createSCorpus(URI.createURI("salt:/c1")).get(0);
		//creates the sub-corpora c2 and c3, in Salt you can also create corpora adding a corpus to a parent
		SCorpus c2= sCorpusGraph.createSCorpus(c1, "c2");
		SCorpus c3= sCorpusGraph.createSCorpus(c1, "c3");
		
		//creates the documents d1, d2 as children of c2 
		SDocument d1= sCorpusGraph.createSDocument(c2, "d1");
		SDocument d2= sCorpusGraph.createSDocument(c2, "d2");
		
		//creates the documents d3, d4 as children of c3 via the URI mechanism 
		SDocument d3= sCorpusGraph.createSDocument(URI.createURI("salt:/c1/c3/d3"));
		SDocument d4= sCorpusGraph.createSDocument(URI.createURI("salt:/c1/c3/d4"));
		
		//adds a meta-annotation 'author' to all documents, a meta-annotation has a namespace, a name and a value
		d1.createSMetaAnnotation(null, "author", "Bart Simpson");
		d2.createSMetaAnnotation(null, "author", "Lisa Simpson");
		d3.createSMetaAnnotation(null, "author", "Marge Simpson");
		d4.createSMetaAnnotation(null, "author", "Homer Simpson");
		
		//also corpora can take meta-annotations
		c3.createSMetaAnnotation(null, "author", "Maggie Simpson");
	}
	
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * <br/>
	 * This method creates a customized {@link PepperMapper} object and returns it. You can here do some additional initialisations. 
	 * Thinks like setting the {@link SElementId} of the {@link SDocument} or {@link SCorpus} object and the {@link URI} resource is done
	 * by the framework (or more in detail in method {@link #start()}).<br/> 
	 * The parameter <code>sElementId</code>, if a {@link PepperMapper} object should be created in case of the object to map is either 
	 * an {@link SDocument} object or an {@link SCorpus} object of the mapper should be initialized differently. 
	 * <br/>
	 * Just to show how the creation of such a mapper works, we here create a sample mapper of type {@link SampleMapper}, 
	 * which only produces a fixed document-structure in method  {@link SampleMapper#mapSDocument()} and enhances the 
	 * corpora for further meta-annotations in the method {@link SampleMapper#mapSCorpus()}.
	 * <br/>
	 * If your mapper needs to have set variables, this is the place to do it.
	 * @param sElementId {@link SElementId} of the {@link SCorpus} or {@link SDocument} to be processed. 
	 * @return {@link PepperMapper} object to do the mapping task for object connected to given {@link SElementId}
	 */
	public PepperMapper createPepperMapper(SElementId sElementId){
		TEIMapper mapper= new TEIMapper();
		/**
		 * TODO Set the exact resource, which should be processed by the created mapper object, if the default 
		 * mechanism of importCorpusStructure() was used, the resource could be retrieved by 
		 * getSElementId2ResourceTable().get(sElementId), just uncomment this line
		 */
		mapper.setResourceURI(getSElementId2ResourceTable().get(sElementId));
		return(mapper);
	}
	
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * <br/>
	 * This method is called by the pepper framework and returns if a corpus located at the given {@link URI} is importable
	 * by this importer. If yes, 1 must be returned, if no 0 must be returned. If it is not quite sure, if the given corpus
	 * is importable by this importer any value between 0 and 1 can be returned. If this method is not overridden, 
	 * null is returned.
	 * @return 1 if corpus is importable, 0 if corpus is not importable, 0 < X < 1, if no definitive answer is possible,  null if method is not overridden 
	 */
	public Double isImportable(URI corpusPath){
		//TODO some code to analyze the given corpus-structure
		return(null);
	}

// =================================================== optional ===================================================	
	/**
	 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
	 * <br/>
	 * This method is called by the pepper framework after initializing this object and directly before start processing. 
	 * Initializing means setting properties {@link PepperModuleProperties}, setting temporary files, resources etc.
	 * returns false or throws an exception in case of {@link PepperModule} instance is not ready for any reason.
	 * <br/>
	 * So if there is anything to do, before your importer can start working, do it here.
	 * @return false, {@link PepperModule} instance is not ready for any reason, true, else.
	 */
	@Override
	public boolean isReadyToStart() throws PepperModuleNotReadyException{
		//TODO make some initializations if necessary
		return(super.isReadyToStart());
	}
}
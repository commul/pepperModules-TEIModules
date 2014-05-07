package de.hu_berlin.german.korpling.saltnpepper.pepperModules.teiModules;

import java.util.Stack;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Node;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructure;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructuredNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;

/**
 * This class parses an xml file following the model of 'BesaReader'.
 * 
 * @author XMLTagExtractor
 **/
public class TEITagLibraryReader extends DefaultHandler2 implements
		TEITagLibrary {
	
	private Boolean USER_DEFINED_TOKENIZATION = true;
	private Boolean SUB_TOKENIZATION = true;
	private Boolean NO_INPUT_TOKENIZATION = true;
	
	
	EList<SStructuredNode> tokennode= new BasicEList<SStructuredNode>();
	private Stack<String> tagStack = new Stack<String>();
	private SDocumentGraph sDocGraph = null;
	private STextualDS primaryText = null;

	public SDocumentGraph getsDocGraph() {
		return sDocGraph;
	}

	public void setsDocGraph(SDocumentGraph sDocGraph) {
		this.sDocGraph = sDocGraph;
	}

	public void startDocument ()
    {
		sDocGraph = SaltFactory.eINSTANCE.createSDocumentGraph();
    }
	
	public void characters(char ch[], int start, int length) {
		
		String temp = "";
		if (!tagStack.empty())
			if (tagStack.peek() == TAG_P){
				for (int i = start; i < start + length; i++) {
					temp = temp + ch[i];

				}
		}
		temp = temp.replaceAll("\\s+"," ");
		temp = temp.trim();
		
		if (SUB_TOKENIZATION){
			if (primaryText != null){
				/*in case primaryText is empty, but exists, initialize primaryText with temp
				 *to avoid "null" as part of the string; otherwise add temp to primaryText
				 */
				if (!temp.isEmpty() && primaryText.getSText()==null){
					primaryText.setSText(temp);
					System.out.println(primaryText.getSEnd());
					SToken temp_tok = sDocGraph.createSToken(primaryText, 0, primaryText.getSEnd());
					//System.out.println(sDocGraph.getOverlappedSTokens(primaryText, tokennode));
				}
			
				/*add a single space character to split the first and last word from 
				 *two neighboring chunks of text*
				 */
				else if (!temp.isEmpty() && !(primaryText.getSText()==null)){
					temp = " "+temp;
					primaryText.setSText(primaryText.getSText()+temp);
					
				}
			}
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (TAG_SUMMARY.equals(qName)) {
		} 
		
		else if (TAG_BODY.equals(qName)) {
			//nothing
		}
		
		else if (TAG_ORIGDATE.equals(qName)) {
		} else if (TAG_MSNAME.equals(qName)) {
		} else if (TAG_LANGUSAGE.equals(qName)) {
		} else if (TAG_TEXTLANG.equals(qName)) {
		} else if (TAG_CHANGE.equals(qName)) {
		} else if (TAG_SURNAME.equals(qName)) {
		} else if (TAG_PLACENAME.equals(qName)) {
		} else if (TAG_MSDESC.equals(qName)) {
		} else if (TAG_TITLESTMT.equals(qName)) {
		} else if (TAG_PHR.equals(qName)) {
		} else if (TAG_REVISIONDESC.equals(qName)) {
		} else if (TAG_LICENCE.equals(qName)) {
		} 
		
		else if (TAG_TEXT.equals(qName)) {
			primaryText = SaltFactory.eINSTANCE.createSTextualDS();
			sDocGraph.addSNode(primaryText);
			
		} 
		
		else if (TAG_FORENAME.equals(qName)) {
		} else if (TAG_FILEDESC.equals(qName)) {
		} else if (TAG_LISTBIBL.equals(qName)) {
		} else if (TAG_COUNTRY.equals(qName)) {
		} else if (TAG_PB.equals(qName)) {
		} else if (TAG_BIBLSCOPE.equals(qName)) {
		} else if (TAG_LANGUAGE.equals(qName)) {
		} else if (TAG_IDNO.equals(qName)) {
		} else if (TAG_SERIES.equals(qName)) {
		} else if (TAG_CREATION.equals(qName)) {
		} else if (TAG_ENCODINGDESC.equals(qName)) {
		} else if (TAG_COLLECTION.equals(qName)) {
		} else if (TAG_DATE.equals(qName)) {
		} else if (TAG_PUBLISHER.equals(qName)) {
		} else if (TAG_MSIDENTIFIER.equals(qName)) {
		} else if (TAG_AUTHOR.equals(qName)) {
		} else if (TAG_AUTHORITY.equals(qName)) {
		} else if (TAG_MSCONTENTS.equals(qName)) {
		} else if (TAG_TITLE.equals(qName)) {
		} else if (TAG_PUBPLACE.equals(qName)) {
		} else if (TAG_TEI.equals(qName)) {
		} else if (TAG_REPOSITORY.equals(qName)) {
		} else if (TAG_MSPART.equals(qName)) {
		} else if (TAG_SOURCEDESC.equals(qName)) {
		} else if (TAG_PROFILEDESC.equals(qName)) {
		} else if (TAG_PUBLICATIONSTMT.equals(qName)) {
		} else if (TAG_LOCUS.equals(qName)) {
		} else if (TAG_AVAILABILITY.equals(qName)) {
		} else if (TAG_ORIGIN.equals(qName)) {
		} else if (TAG_MSITEM.equals(qName)) {
		} else if (TAG_M.equals(qName)) {
		} else if (TAG_INCIPIT.equals(qName)) {
		} else if (TAG_W.equals(qName)) {
		} else if (TAG_HISTORY.equals(qName)) {
		} else if (TAG_REF.equals(qName)) {
		} else if (TAG_BIBL.equals(qName)) {
		} else if (TAG_TEIHEADER.equals(qName)) {
		} 
		
		else if (TAG_P.equals(qName)) {
			tagStack.push(TAG_P);
		}
		
		else if (TAG_AB.equals(qName)) {
		} else if (TAG_OBJECTTYPE.equals(qName)) {
		} else if (TAG_ORIGPLACE.equals(qName)) {
		} else if (TAG_LB.equals(qName)) {
			
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
		if (TAG_P.equals(qName)) {
			tagStack.pop();
		}
		
		
			  
		
	}
}

package de.hu_berlin.german.korpling.saltnpepper.pepperModules.teiModules;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;

/**
 * This class parses an xml file following the model of 'BesaReader'.
 * 
 * @author XMLTagExtractor
 **/
public class TEITagLibraryReader extends DefaultHandler2 implements
		TEITagLibrary {
	String text = "";

	private SDocumentGraph sDocGraph = null;

	public SDocumentGraph getsDocGraph() {
		return sDocGraph;
	}

	public void setsDocGraph(SDocumentGraph sDocGraph) {
		this.sDocGraph = sDocGraph;
	}

	public void characters(char ch[], int start, int length) {
		String temp = "";
		for (int i = start; i < start + length; i++) {
			temp = temp + ch[i];

		}
		temp = temp.replaceAll("\\s+", " ");
		text = text + temp;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (TAG_SUMMARY.equals(qName)) {
		} else if (TAG_BODY.equals(qName)) {
		} else if (TAG_ORIGDATE.equals(qName)) {
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
		} else if (TAG_TEXT.equals(qName)) {
		} else if (TAG_FORENAME.equals(qName)) {
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
		} else if (TAG_P.equals(qName)) {
		} else if (TAG_AB.equals(qName)) {
		} else if (TAG_OBJECTTYPE.equals(qName)) {
		} else if (TAG_ORIGPLACE.equals(qName)) {
		}
	}
}

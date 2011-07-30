package org.testtoolinterfaces.testresultinterface;

import java.io.File;
import java.io.IOError;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.testtoolinterfaces.testresult.TestCaseResult;
import org.testtoolinterfaces.testresult.TestCaseResultLink;
import org.testtoolinterfaces.utils.Trace;
import org.xml.sax.XMLReader;

public class TestCaseResultReader
{
	/**
	 */
	public TestCaseResultReader()
	{
		Trace.println(Trace.CONSTRUCTOR);
	}

	/** 
	 * @throws IOError when reading fails
	 */
	public TestCaseResult readTcResultFile( TestCaseResultLink aTestCaseResultLink )
	{
		Trace.println(Trace.SUITE, "readTcResultFile( " + aTestCaseResultLink.getId() + " )", true);

		return readTcResultFile( aTestCaseResultLink.getLink() );
	}

	/** 
	 * @throws IOError when reading fails
	 */
	public TestCaseResult readTcResultFile( File aTestCaseResultFile )
	{
		Trace.println(Trace.SUITE, "readTcResultFile( " + aTestCaseResultFile.getPath() + " )", true);

		// create a parser
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(false);
        TestCaseResult testCaseResult;
		try
		{
			SAXParser saxParser = spf.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();

	        // create a handler
			TestCaseResultXmlHandler handler = new TestCaseResultXmlHandler(xmlReader);

	        // assign the handler to the parser
	        xmlReader.setContentHandler(handler);

	        // parse the document
	        xmlReader.parse(aTestCaseResultFile.getAbsolutePath());
	        
	        testCaseResult = handler.getTestCaseResult();
		}
		catch (Exception e)
		{
			Trace.print(Trace.SUITE, e);
			throw new IOError( e );
		}

		return testCaseResult;
	}
}

package com.nik.smartnote.vk.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Николай on 12.01.2016.
 */
public class XMLParser {
//todo переделать, что бы не ошибкой управлялось
    public String parsXMLTeg(String xml, String teg) {


    XmlPullParserFactory factory = null;
    try

    {
        factory = XmlPullParserFactory.newInstance();

        factory.setNamespaceAware(true);
        XmlPullParser xmlPullParser = factory.newPullParser();

        xmlPullParser.setInput(new StringReader(xml));
        while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {

            if (xmlPullParser.getEventType() == XmlPullParser.START_TAG) {

                if (xmlPullParser.getName().equals(teg)) {

                    try {

                        return xmlPullParser.nextText();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }

            try {
                xmlPullParser.next();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    catch(XmlPullParserException e)

    {
        e.printStackTrace();
    }

        return null;
    }

}

/*
 * Copyright (c) 2020, mange
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package se.metricspace.riksdagen;

/**
 * @author Mange
 */
public class DocumentFinder {
    private static final String BETSEARCH = "https://data.riksdagen.se/dokumentlista/?sok=&doktyp=bet&rm=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=rel&sortorder=desc&rapport=&utformat=xml&a=s&from=";
    private static final String DIRQUERY  = "https://data.riksdagen.se/dokumentlista/?sok=&doktyp=dir&rm=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=datum&sortorder=desc&rapport=&utformat=xml&a=&from=";
    private static final String FRQUERY   = "https://data.riksdagen.se/dokumentlista/?sok=&doktyp=fr&rm=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=datum&sortorder=desc&rapport=&utformat=xml&a=s&from=";
    private static final String IPQUERY   = "https://data.riksdagen.se/dokumentlista/?sok=&doktyp=ip&rm=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=datum&sortorder=desc&rapport=&utformat=xml&a=s&from=";
    private static final String MOTQUERY  = "https://data.riksdagen.se/dokumentlista/?sok=&doktyp=mot&rm=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=datum&sortorder=desc&rapport=&utformat=xml&a=s&from=";
    private static final String PROPQUERY = "https://data.riksdagen.se/dokumentlista/?sok=&doktyp=prop&rm=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=datum&sortorder=desc&rapport=&utformat=xml&a=s&from=";
    private static final String RIRQUERY  = "https://data.riksdagen.se/dokumentlista/?sok=&doktyp=rir&rm=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=datum&sortorder=desc&rapport=&utformat=xml&a=s&from=";
    private static final String SOUQUERY  = "https://data.riksdagen.se/dokumentlista/?sok=&doktyp=sou&rm=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=datum&sortorder=desc&rapport=&utformat=xml&a=s&from=";

    public static java.util.List<Document> findBetankandeByDateLimit(java.util.Date oldestDate, java.util.Date newestDate) throws org.xml.sax.SAXException, java.io.IOException, javax.xml.parsers.ParserConfigurationException {
        return findDocumentsByDateLimit(BETSEARCH, oldestDate, newestDate);
    }

    public static java.util.List<Document> findDirektivByDateLimit(java.util.Date oldestDate, java.util.Date newestDate) throws org.xml.sax.SAXException, java.io.IOException, javax.xml.parsers.ParserConfigurationException {
        return findDocumentsByDateLimit(DIRQUERY, oldestDate, newestDate);
    }

    public static java.util.List<Document> findFragaByDateLimit(java.util.Date oldestDate, java.util.Date newestDate) throws org.xml.sax.SAXException, java.io.IOException, javax.xml.parsers.ParserConfigurationException {
        return findDocumentsByDateLimit(FRQUERY, oldestDate, newestDate);
    }

    public static java.util.List<Document> findInterpellationByDateLimit(java.util.Date oldestDate, java.util.Date newestDate) throws org.xml.sax.SAXException, java.io.IOException, javax.xml.parsers.ParserConfigurationException {
        return findDocumentsByDateLimit(IPQUERY, oldestDate, newestDate);
    }

    public static java.util.List<Document> findMotionerByDateLimit(java.util.Date oldestDate, java.util.Date newestDate) throws org.xml.sax.SAXException, java.io.IOException, javax.xml.parsers.ParserConfigurationException {
        return findDocumentsByDateLimit(MOTQUERY, oldestDate, newestDate);
    }

    public static java.util.List<Document> findPropositionerByDateLimit(java.util.Date oldestDate, java.util.Date newestDate) throws org.xml.sax.SAXException, java.io.IOException, javax.xml.parsers.ParserConfigurationException {
        return findDocumentsByDateLimit(PROPQUERY, oldestDate, newestDate);
    }

    public static java.util.List<Document> findRirByDateLimit(java.util.Date oldestDate, java.util.Date newestDate) throws org.xml.sax.SAXException, java.io.IOException, javax.xml.parsers.ParserConfigurationException {
        return findDocumentsByDateLimit(RIRQUERY, oldestDate, newestDate);
    }

    public static java.util.List<Document> findSoUByDateLimit(java.util.Date oldestDate, java.util.Date newestDate) throws org.xml.sax.SAXException, java.io.IOException, javax.xml.parsers.ParserConfigurationException {
        return findDocumentsByDateLimit(SOUQUERY, oldestDate, newestDate);
    }

    public static java.util.List<Document> findDocumentsByDateLimit(String theSearch, java.util.Date oldestDate, java.util.Date newestDate) throws org.xml.sax.SAXException, java.io.IOException, javax.xml.parsers.ParserConfigurationException {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String datelimit = (null!=oldestDate) ? format.format(oldestDate) : format.format(new java.util.Date(System.currentTimeMillis()-864000000L));
        String dateTolimit = (null!=newestDate) ? format.format(newestDate) : format.format(new java.util.Date(System.currentTimeMillis()+864000000L));
        org.w3c.dom.Document doc = loadDocument(theSearch+datelimit+"&tom="+dateTolimit);
        org.w3c.dom.Element element = (null!=doc) ? doc.getDocumentElement() : null;
        java.util.List<Document> documents = new java.util.ArrayList<>();
        while(null!=element) {
            String nasta_sida=element.getAttribute("nasta_sida");
            org.w3c.dom.NodeList children = element.getElementsByTagName("dokument");
            String date = null;
            if(null!=children && children.getLength()>0) {
                for(int index=0;index<children.getLength();index++)   {
                    org.w3c.dom.Node child = children.item(index);
                    Document document = Document.parse(child);
                    if(null!=document) {
                        documents.add(document);
                    }
                }
            } else {
                System.out.println("No children found");
            }
            doc = (null!=nasta_sida && nasta_sida.trim().length()>0) ? loadDocument(nasta_sida) : null;
            element = (null!=doc) ? doc.getDocumentElement() : null;
        }
        java.util.Collections.sort(documents);
        return documents;
    }

    private static org.w3c.dom.Document loadDocument(String url) throws org.xml.sax.SAXException, java.io.IOException, javax.xml.parsers.ParserConfigurationException {
        javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new java.net.URL(url).openStream());
    }
}

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
public class Document implements Comparable<Document>{
    private String itsBeteckning = null;
    private String itsDatum = null;
    private String itsDokumentId = null;
    private String itsDokumentTyp = null;
    private String itsDokumentUrlHtml = null;
    private String itsId = null;
    private String itsKallId = null;
    private String itsNotis = null;
    private String itsNotisRubrik = null;
    private String itsOrgan = null;
    private String itsPdfUrl = null;
    private String itsPublicerad = null;
    private String itsRiksMote = null;
    private String itsSummary = null;
    private java.util.Date itsSystemDate = null;
    private String itsTitel = null;
    private String itsTyp = null;
    private String itsUnderTitel = null;

    public Document() {
    }

    private static String cleanString(String theValue) {
        return (null!=theValue && theValue.trim().length()>0) ? theValue.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("&nbsp;", " ").trim() : null;
    }
    @Override
    public int compareTo(Document document) {
        int compare = document.itsDatum.compareTo(itsDatum);
        if(0==compare) {
            if(null!=document.itsTyp && null!=itsTyp) {
                compare = document.itsTyp.compareTo(itsTyp);
            }
            if(0==compare && null!=document.itsBeteckning && null!=itsBeteckning) {
                compare = String.format("%5s", document.itsBeteckning).compareTo(String.format("%5s", itsBeteckning));
            }
        }
        return compare;
    }

    @Override
    public boolean equals(Object someObject) {
        boolean eq = false;
        if(null!=someObject && someObject instanceof Document) {
            eq = hashCode() == someObject.hashCode();
        }
        return eq;
    }

    public String getBeteckning() {
        return itsBeteckning;
    }

    public String getDatum() {
        return (null!=itsDatum) ? itsDatum.substring(0, 10) : null;
    }

    public String getDokumentId() {
        return itsDokumentId;
    }

    public String getDokumentUrlHtml() {
        return itsDokumentUrlHtml;
    }

    public String getDokumentTyp() {
        return itsDokumentTyp;
    }

    public String getId() {
        return itsId;
    }

    public String getKallId() {
        return itsKallId;
    }

    public String getNotis() {
        return itsNotis;
    }

    public String getNotisRubrik() {
        return itsNotisRubrik;
    }

    public String getOrgan() {
        return itsOrgan;
    }

    public String getPdfUrl() {
        return itsPdfUrl;
    }

    public String getPublicerad() {
        return itsPublicerad;
    }

    public String getRiksMote() {
        return itsRiksMote;
    }

    public String getSummary() {
        return itsSummary;
    }

    public java.util.Date getSystemDate() {
        return itsSystemDate;
    }

    public String getTitel() {
        return itsTitel;
    }

    public String getTyp() {
        return itsTyp;
    }

    public String getUnderTitel() {
        return itsUnderTitel;
    }

    @Override
    public int hashCode() {
        return (null!=itsId) ? itsId.hashCode() : 0;
    }

    public static Document parse(org.w3c.dom.Node theItem) {
        Document document = (null!=theItem) ? new Document() : null;

        if(null!=theItem) {
            org.w3c.dom.NodeList children = theItem.getChildNodes();
            for(int index = 0; index < children.getLength(); index++) {
                org.w3c.dom.Node child = children.item(index);
                switch (child.getNodeName()) {
                    case "ardometyp":
                        break;
                    case "avdelning":
                        break;
                    case "beredningsdag":
                        break;
                    case "beslutad":
                        break;
                    case "beslutsdag":
                        break;
                    case "beteckning":
                        document.setBeteckning(child.getTextContent());
                        break;
                    case "database":
                        break;
                    case "datum":
                        document.setDatum(child.getTextContent());
                        break;
                    case "debatt":
                        break;
                    case "debattdag":
                        break;
                    case "debattgrupp":
                        break;
                    case "debattnamn":
                        break;
                    case "debattsekunder":
                        break;
                    case "dokintressent":
                        break;
                    case "dok_id":
                        break;
                    case "doktyp":
                        document.setDokumentTyp(child.getTextContent());
                        break;
                    case "dokumentformat":
                        break;
                    case "dokumentnamn":
                        break;
                    case "dokument_url_text":
                        break;
                    case "dokumentstatus_url_xml":
                        break;
                    case "dokument_url_html":
                        document.setDokumentUrlHtml(child.getTextContent());
                        break;
                    case "domain":
                        break;
                    case "filbilaga":
                        document.itsPdfUrl = parsePdfUrl(child);
                        break;
                    case "id":
                        document.setId(child.getTextContent());
                        break;
                    case "inlamnad":
                        break;
                    case "justeringsdag":
                        break;
                    case "kalla":
                        break;
                    case "kall_id":
                        document.setKallId(child.getTextContent());
                        break;
                    case "klockslag":
                        break;
                    case "lang":
                        break;
                    case "motionstid":
                        break;
                    case "notis":
                        document.setNotis(child.getTextContent());
                        break;
                    case "notisrubrik":
                        document.setNotisRubrik(child.getTextContent());
                        break;
                    case "nummer":
                        break;
                    case "organ":
                        document.setOrgan(child.getTextContent());
                        break;
                    case "plats":
                        break;
                    case "publicerad":
                        document.setPublicerad(child.getTextContent());
                        break;
                    case "rddata":
                        break;
                    case "rdrest":
                        break;
                    case "relaterat_id":
                        break;
                    case "relurl":
                        break;
                    case "reservationer":
                        break;
                    case "rm":
                        document.setRiksMote(child.getTextContent());
                        break;
                    case "score":
                        break;
                    case "slutdatum":
                        break;
                    case "sokdata":
                        break;
                    case "status":
                        break;
                    case "struktur":
                        break;
                    case "subtyp":
                        break;
                    case "summary":
                        document.setSummary(child.getTextContent());
                        break;
                    case "systemdatum":
                        document.setSystemDate(child.getTextContent());
                        break;
                    case "tempbeteckning":
                        break;
                    case "tilldelat":
                        break;
                    case "titel":
                        document.setTitel(child.getTextContent());
                        break;
                    case "traff":
                        break;
                    case "typ":
                        document.setTyp(typeName(child.getTextContent()));
                        break;
                    case "undertitel":
                        document.setUnderTitel(child.getTextContent());
                        break;
                    case "url":
                        break;
                    default: 
                        if(!child.getNodeName().equals("#text")) {
                            System.out.println(child.getNodeName()+": "+child.getTextContent());
                        }
                    break;
                }
            }
        }

        return document;
    }

    private static String parsePdfUrl(org.w3c.dom.Node theItem) {
        String url = null;
        org.w3c.dom.NodeList children = theItem.getChildNodes();
        for(int index = 0; index < children.getLength(); index++) {
            org.w3c.dom.Node child = children.item(index);
            if(child.getNodeName().equals("fil")) {
                org.w3c.dom.NodeList grandChildren = child.getChildNodes();
                String typ = null;
                String name = null;
                String storlek = null;
                String urlX = null;
                for(int indeX = 0; indeX < grandChildren.getLength(); indeX++) {
                    org.w3c.dom.Node grandChild = grandChildren.item(indeX);
                    switch (grandChild.getNodeName()) {
                        case "typ":
                            typ = (null!=grandChild.getTextContent()) ? grandChild.getTextContent().trim() : null;
                            break;
                        case "namn":
                            name = (null!=grandChild.getTextContent()) ? grandChild.getTextContent().trim() : null;
                            break;
                        case "storlek":
                            storlek = (null!=grandChild.getTextContent()) ? grandChild.getTextContent().trim() : null;
                            break;
                        case "url":
                            urlX = (null!=grandChild.getTextContent()) ? grandChild.getTextContent().trim() : null;
                            break;
                        case "text":
                            break;
                        case "title":
                            break;
                        case "#text":
                            break;
                        default:
                            System.out.println("Unhandled grandchild: "+grandChild.getNodeName());
                            break;
                    }
                }
                if(null!=typ && typ.equalsIgnoreCase("pdf")) {
                    url = urlX;  
                }
            } else {
            }
        }
        return url;
    }

    public void setBeteckning(String theValue) {
        itsBeteckning = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
    }

    public void setDatum(String theValue) {
        itsDatum = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
    }

    public void setDokumentId(String theValue) {
        itsDokumentId = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
    }

    public void setDokumentTyp(String theValue) {
        itsDokumentTyp = cleanString(theValue);
    }

    public void setDokumentUrlHtml(String theValue) {
        itsDokumentUrlHtml = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
    }

    public void setId(String theValue) {
        itsId = cleanString(theValue);
    }

    public void setKallId(String theValue) {
        itsKallId = cleanString(theValue);
    }

    public void setNotis(String theValue) {
        itsNotis = cleanString(theValue);
    }

    public void setNotisRubrik(String theValue) {
        itsNotisRubrik = cleanString(theValue);
    }

    public void setOrgan(String theValue) {
        itsOrgan = cleanString(theValue);
    }

    public void setPublicerad(String theValue) {
        itsPublicerad = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
    }

    public void setRiksMote(String theValue) {
        itsRiksMote = cleanString(theValue);
    }

    public void setSummary(String theValue) {
        itsSummary = cleanString(theValue);
    }

    public void setSystemDate(String theValue) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            itsSystemDate = (null!=theValue && theValue.trim().length()>0) ? formatter.parse(theValue.trim()) : null;
        } catch (java.text.ParseException exception) {
            formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
            try {
                itsSystemDate = formatter.parse(theValue.trim());
            } catch (java.text.ParseException ex) {
                System.out.println("ParseException getting systemdatum: "+exception.getMessage());
            }
        }
    }

    public void setTitel(String theValue) {
        itsTitel = cleanString(theValue);
    }

    public void setTyp(String theValue) {
        itsTyp = cleanString(theValue);
    }

    public void setUnderTitel(String theValue) {
        itsUnderTitel = cleanString(theValue);
    }

    @Override
    public String toString() {
        return "se.metricspace.riksdagen.Document: "+itsId+" - "+itsDatum+" - "+itsTyp;
    }

    private static String typeName(String theType) {
        String type = "";
        if(null!=theType && theType.trim().length()>0) {
            theType = theType.trim().toLowerCase();
            switch (theType) {
                case "bet": 
                  type = "Bet";
                  break;
                case "dir": 
                    type = "Dir";
                    break;
                case "fr": 
                    type = "Fr";
                    break;
                case "ip": 
                    type = "Ip";
                    break;
                case "mot": 
                    type = "Mot";
                    break;
                case "prop": 
                    type = "Prop";
                    break;
                case "rir": 
                    type = "RiR";
                    break;
                case "sou": 
                    type = "SoU";
                    break;
                default:
                    System.out.println("Unknown type: "+theType);
                    type = theType;
                    break;
            }
        }
        return type;
    }
}

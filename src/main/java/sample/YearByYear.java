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
package sample;

/**
 * @author Mange
 */
public class YearByYear {
    public static void main(String[] theArgs) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
        for(int year=1995; year<2018; year++) {
            System.out.println("processing year "+year);
            java.util.List<se.metricspace.riksdagen.Document> documents = new java.util.ArrayList<>();
            try {
                java.util.Date first = formatter.parse(year+"-01-01");
                java.util.Date last = formatter.parse(year+"-12-31");
                for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findBetankandeByDateLimit(first, last)) {
                  documents.add(doc);
                }
                for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findDirektivByDateLimit(first, last)) {
                  documents.add(doc);
                }
                for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findFragaByDateLimit(first, last)) {
                  documents.add(doc);
                }
                for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findInterpellationByDateLimit(first, last)) {
                  documents.add(doc);
                }
                try {
                    for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findMotionerByDateLimit(first, last)) {
                      documents.add(doc);
                    }
                } catch (java.io.IOException exception) {
                   System.out.println("IOException processing motioner: "+exception.getMessage());
                }
                for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findPropositionerByDateLimit(first, last)) {
                  documents.add(doc);
                }
                for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findRirByDateLimit(first, last)) {
                  documents.add(doc);
                }
                for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findSoUByDateLimit(first, last)) {
                  documents.add(doc);
                }
            } catch(java.io.IOException exception) {
                System.out.println("IOException processing documents: "+exception.getMessage());
            } catch(org.xml.sax.SAXException exception) {
                System.out.println("SAXException processing documents: "+exception.getMessage());
            } catch(javax.xml.parsers.ParserConfigurationException exception) {
                System.out.println("ParserConfigurationException processing documents: "+exception.getMessage());
            } catch(java.text.ParseException exception) {
                System.out.println("ParseExecption: "+exception.getMessage());
            }
            if(null!=documents && documents.size()>0) {
                java.util.Collections.sort(documents);
                System.out.println(documents.size()+" matches found!");
                try {
                    se.metricspace.riksdagen.HtmlRenderer.renderHtmlDivMode(year+".html", documents);
                } catch(java.io.IOException exception) {
                    System.out.println("IOException processing documents: "+exception.getMessage());
                }
            } else {
                System.out.println("No matches found!");
            }
        }
    }
}

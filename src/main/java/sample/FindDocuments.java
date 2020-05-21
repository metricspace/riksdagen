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
public class FindDocuments {
    public static void main(String[] theArgs) {
        String FROMDATE="2019-09-01";
        String TOMDATE="2020-06-01";
        try {
            java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.List<se.metricspace.riksdagen.Document> documents = se.metricspace.riksdagen.DocumentFinder.findBetankandeByDateLimit(formatter.parse(FROMDATE), formatter.parse(TOMDATE));
            for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findDirektivByDateLimit(formatter.parse(FROMDATE), formatter.parse(TOMDATE))) {
              documents.add(doc);
            }
            for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findFragaByDateLimit(formatter.parse(FROMDATE), formatter.parse(TOMDATE))) {
              documents.add(doc);
            }
            for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findInterpellationByDateLimit(formatter.parse(FROMDATE), formatter.parse(TOMDATE))) {
              documents.add(doc);
            }
            for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findMotionerByDateLimit(formatter.parse(FROMDATE), formatter.parse(TOMDATE))) {
              documents.add(doc);
            }
            for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findPropositionerByDateLimit(formatter.parse(FROMDATE), formatter.parse(TOMDATE))) {
              documents.add(doc);
            }
            for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findRirByDateLimit(formatter.parse(FROMDATE), formatter.parse(TOMDATE))) {
              documents.add(doc);
            }
            for(se.metricspace.riksdagen.Document doc:se.metricspace.riksdagen.DocumentFinder.findSoUByDateLimit(formatter.parse(FROMDATE), formatter.parse(TOMDATE))) {
              documents.add(doc);
            }

            if(null!=documents && documents.size()>0) {
                java.util.Collections.sort(documents);
                System.out.println(documents.size()+" matches found!");
                se.metricspace.riksdagen.HtmlRenderer.renderHtmlDivMode("rm201920.html", documents);
            } else {
                System.out.println("No matches found!");
            }
        } catch(java.io.IOException exception) {
            System.out.println("IOException processing documents: "+exception.getMessage());
        } catch(org.xml.sax.SAXException exception) {
            System.out.println("SAXException processing documents: "+exception.getMessage());
        } catch(javax.xml.parsers.ParserConfigurationException exception) {
            System.out.println("ParserConfigurationException processing documents: "+exception.getMessage());
        } catch(java.text.ParseException exception) {
            System.out.println("ParseException processing documents: "+exception.getMessage());
        }
    }
}

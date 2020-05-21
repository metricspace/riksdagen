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
public class HtmlRenderer {
    private static void printEpilog(java.io.PrintStream thePrintStream) throws java.io.IOException {
        thePrintStream.println("    </div>");
        thePrintStream.println("  </body>");
        thePrintStream.println("</html>");
    }

    private static void printProlog(java.io.PrintStream thePrintStream) throws java.io.IOException {
        thePrintStream.println("<!DOCTYPE html>");
        thePrintStream.println("<html lang=\"sv\">");
        thePrintStream.println("  <head>\n" +
    "    <meta charset=\"utf-8\">\n" +
    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
    "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\">\n" +
    "    <script src=\"https://code.jquery.com/jquery-3.4.1.slim.min.js\" integrity=\"sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n\" crossorigin=\"anonymous\"></script>\n" +
    "<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\"></script>\n" +
    "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js\" integrity=\"sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6\" crossorigin=\"anonymous\"></script>\n" +
    "    <title>Some Title</title>\n" +
    "    <meta name=\"Keywords\" content=\"magnus, mange, metric space, metriskt rum, norden\" />\n" +
    "    <meta name=\"Description\" content=\"Some Description\" />\n" +
    "    <meta name=\"author\" content=\"Magnus Norden\"/>\n" +
    "    <link href=\"https://media.metricspace.se/favicon.jpg\" rel=\"icon\" type=\"image/jpeg\" />\n" +
    "  </head>");
        thePrintStream.println("  <body>");
        thePrintStream.println("    <div class=\"content\">");
    }

    public static void renderHtmlDivMode(String theFileName, java.util.List<Document> theDocuments) throws java.io.IOException {
        java.io.FileOutputStream targetStream = null;
        java.io.PrintStream printStream = null;
        try {
            targetStream = new java.io.FileOutputStream(theFileName);
            printStream = new java.io.PrintStream(targetStream);
            renderHtmlDivMode(printStream, theDocuments);
        } finally {
            if(null!=printStream) {
                try {
                    printStream.close();
                } catch (Throwable exception) {
                }
            }
            if(null!=targetStream) {
                try {
                    targetStream.close();
                } catch (Throwable exception) {
                }
            }
        }
    }

    public static void renderHtmlDivMode(java.io.PrintStream thePrintStream, java.util.List<Document> theDocuments) throws java.io.IOException {
        printProlog(thePrintStream);

        thePrintStream.println("<div class=\"row\">");
        thePrintStream.println("<div class=\"col-md-2\"><strong>Datum</strong></div>");
        thePrintStream.println("<div class=\"col-md-2\"><strong>Benämning</strong></div>");
        thePrintStream.println("<div class=\"col-md-8\"><strong>Titel</strong></div>");
        thePrintStream.println("</div>");
        for(Document document: theDocuments) {
          String title = document.getTyp()+" "+document.getRiksMote()+":"+document.getBeteckning();
          String url = null;
          if (null!=document.getDokumentUrlHtml() && document.getDokumentUrlHtml().trim().length()>0) {
              url = document.getDokumentUrlHtml().trim();
          } else if(null!=document.getPdfUrl() && document.getPdfUrl().trim().length()>0) {
              url = document.getPdfUrl().trim();
          }
          thePrintStream.println("<div class=\"row\">");
          thePrintStream.println("<div class=\"col-md-2\">"+document.getDatum()+"</div>");
          thePrintStream.print("<div class=\"col-md-2\">");
          if(null!=url) {
              thePrintStream.print("<a href=\""+url+"\" title=\""+title+"\">");
          }
          thePrintStream.print(title);
          if(null!=url) {
              thePrintStream.print("</a>");
          }
          thePrintStream.println("</div>");
          thePrintStream.print("<div class=\"col-md-8\">");
          if("Mot".equals(document.getTyp())) {
            thePrintStream.print("<a href=\"https://www.riksdagen.se/sv/dokument-lagar/dokument/motion/_"+document.getId()+"\"");
            thePrintStream.print(" title=\""+title+"\">");
            thePrintStream.print(document.getTitel());
            thePrintStream.print("</a>");
          } else {
            thePrintStream.print(document.getTitel());
          }
          thePrintStream.println("</div>");
          thePrintStream.println("</div>");
        }
        thePrintStream.println("<div class=\"row\">");
        thePrintStream.println("<div class=\"col-md-2\"><strong>Datum</strong></div>");
        thePrintStream.println("<div class=\"col-md-2\"><strong>Benämning</strong></div>");
        thePrintStream.println("<div class=\"col-md-8\"><strong>Titel</strong></div>");
        thePrintStream.println("</div>");

        printEpilog(thePrintStream);
    }

    public static void renderHtmlVerboseMode(String theFileName, java.util.List<Document> theDocuments) throws java.io.IOException {
        java.io.FileOutputStream targetStream = null;
        java.io.PrintStream printStream = null;
        try {
          targetStream = new java.io.FileOutputStream(theFileName);
          printStream = new java.io.PrintStream(targetStream);
          renderHtmlVerboseMode(printStream, theDocuments);
        } finally {
          if(null!=printStream) {
            try {
              printStream.close();
            } catch (Throwable exception) {
            }
          }
          if(null!=targetStream) {
            try {
              targetStream.close();
            } catch (Throwable exception) {
            }
          }
        }
    }

    public static void renderHtmlVerboseMode(java.io.PrintStream thePrintStream, java.util.List<Document> theDocuments) throws java.io.IOException {
        printProlog(thePrintStream);
        printEpilog(thePrintStream);
    }
}

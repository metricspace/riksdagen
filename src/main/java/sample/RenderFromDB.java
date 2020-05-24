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
public class RenderFromDB {
    // https://www.riksdagen.se/sv/dokument-lagar/dokument/betankande/_H701JuU40
    // https://www.riksdagen.se/sv/dokument-lagar/dokument/direktiv/_H8B156
    // https://www.riksdagen.se/sv/dokument-lagar/dokument/fraga/_H7111374
    // https://www.riksdagen.se/sv/dokument-lagar/dokument/interpellation/_H710404
    // https://www.riksdagen.se/sv/dokument-lagar/dokument/motion/_H7023618
    // https://www.riksdagen.se/sv/dokument-lagar/dokument/proposition/_H703167
    // https://www.riksdagen.se/sv/dokument-lagar/dokument/rir/_H8B53
    // https://www.riksdagen.se/sv/dokument-lagar/dokument/utredning/_H8B327
    public static void main(String[] theArgs) {
        // tested with mysql 8
        // assuming usage of the provied mysql8sample.sql
        // use better username/password
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException exception) {
            System.out.println("ClassNotFoundException: "+exception.getMessage());
        }
        java.sql.Connection connection = null;
        try {
            // use better dbname/username/password/timezone here ...
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/riksdagen?useUnicode=true&serverTimezone=UTC", "someuser", "somepassword");
            for(String riksMote:se.metricspace.riksdagen.DbDocument.getRiksMoten(connection)) {
                renderToHtml(se.metricspace.riksdagen.DbDocument.fetchFromTableForRiksMote(connection, riksMote), riksMote);
            }
            renderToHtml(se.metricspace.riksdagen.DbDocument.fetchFromTableForDateLimit(connection, "2017-12-01"), "last30");
        } catch(java.sql.SQLException exception) {
            System.out.println("SQLException: "+exception.getMessage());
        } finally {
            if(null!=connection) {
                try {
                    connection.close();
                } catch(java.sql.SQLException exception) {
                }
                connection = null;
            }
        }
    }

    private static void renderToHtml(java.util.List<se.metricspace.riksdagen.DbDocument> someDocuments, String riksMote) {
        java.io.FileOutputStream targetStream = null;
        java.io.PrintWriter printWriter = null;
        try {
            targetStream = new java.io.FileOutputStream("rm"+riksMote.replaceAll("/","")+".html");
            printWriter = new java.io.PrintWriter(targetStream);
            appendFile(printWriter, "prolog.html");
            targetStream.flush();
            for(se.metricspace.riksdagen.DbDocument document: someDocuments) {
                printWriter.println("	<div class=\"row\">");
                printWriter.print("		<div class=\"col-md-2\">");
                printWriter.print(document.getDatum());
                printWriter.println("</div>");
                printWriter.print("		<div class=\"col-md-2\">");
                if(null!=document.getProvidedUrl()) {
                    printWriter.print("<a href=\""+document.getProvidedUrl()+"\" title=\""+document.getBenamning()+"\">");
                    printWriter.print(document.getBenamning());
                    printWriter.print("</a>");
                } else {
                    printWriter.print(document.getBenamning());
                }
                printWriter.println("</div>");
                printWriter.print("		<div class=\"col-md-8\">");
                if(null!=document.getDerivedUrl()) {
                    printWriter.print("<a href=\""+document.getDerivedUrl()+"\" title=\""+document.getBenamning()+"\">");
                    printWriter.print(document.getTitel());
                    printWriter.print("</a>");
                } else {
                    printWriter.print(document.getTitel());
                }
                printWriter.println("</div>");
                printWriter.println("	</div>");
           }
           targetStream.flush();
           appendFile(printWriter, "epilog.html");
        } catch(java.io.IOException exception) {
            System.out.println("IOException: "+exception.getMessage());
        } finally {
            if(null!=printWriter) {
                printWriter.close();
                printWriter = null;
            }
            if(null!=targetStream) {
                try {
                    targetStream.close();
                } catch(java.io.IOException exception) {
                }
                targetStream = null;
            }
        }
    }

    private static void appendFile(java.io.PrintWriter printWriter, String someFileName) throws java.io.IOException {
        java.io.FileInputStream sourceStream = null;
        java.io.InputStreamReader reader = null;
        java.io.LineNumberReader lineReader = null;
        try {
            sourceStream = new java.io.FileInputStream(someFileName);
            reader = new java.io.InputStreamReader(sourceStream);
            lineReader = new java.io.LineNumberReader(reader);
            String line = null;
            while(null!=(line=lineReader.readLine())) {
                printWriter.println(line);
            }
        } finally {
            if(null!=lineReader) {
                try {
                    lineReader.close();
                } catch(java.io.IOException exception) {
                }
                lineReader = null;
            }
            if(null!=reader) {
                try {
                    reader.close();
                } catch(java.io.IOException exception) {
                }
                reader = null;
            }
            if(null!=sourceStream) {
                try {
                    sourceStream.close();
                } catch(java.io.IOException exception) {
                }
                sourceStream = null;
            }
        }
    }
}

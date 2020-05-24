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
public class FindMotionAndSaveToDB {
    public static void main(String[] args) {
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
            java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
            for(int year=2020; year < 2021; year++) {
                String FROMDATE=(year-1)+"-12-15";
                String TOMDATE=(year+1)+"-01-15";
                try {
                    for(se.metricspace.riksdagen.Document document:se.metricspace.riksdagen.DocumentFinder.findMotionerByDateLimit(formatter.parse(FROMDATE), formatter.parse(TOMDATE))) {
                        updateDocument(connection, document);
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

    private static boolean documentExists(java.sql.Connection connection, se.metricspace.riksdagen.Document someDocument) throws java.sql.SQLException {
        boolean exists = false;
        java.sql.PreparedStatement statement = null;
        java.sql.ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select count(*) as cnt from motion where id=?;", java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, someDocument.getId());
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                exists = (resultSet.getInt("cnt")>0);
            }
        } finally {
            if(null!=resultSet) {
                try {
                    resultSet.close();
                } catch(java.sql.SQLException exception) {
                }
                resultSet = null;
            }
            if(null!=statement) {
                try {
                    statement.close();
                } catch(java.sql.SQLException exception) {
                }
                statement = null;
            }
        }
        return exists;
    }

    private static void insert(java.sql.Connection connection, se.metricspace.riksdagen.Document someDocument) throws java.sql.SQLException {
        java.sql.PreparedStatement statement = null;
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            statement = connection.prepareStatement("insert into motion(id, beteckning, datum, dokument_typ, dokument_url_html, riks_mote, titel) values(?, ?, ?, ?, ?, ?, ?);");
            statement.setString(1, someDocument.getId());
            statement.setInt(2, Integer.parseInt(someDocument.getBeteckning()));
            statement.setDate(3, new java.sql.Date(dateFormat.parse(someDocument.getDatum()).getTime()));
            statement.setString(4, someDocument.getTyp());
            statement.setString(5, someDocument.getDokumentUrlHtml());
            statement.setString(6, someDocument.getRiksMote());
            statement.setString(7, someDocument.getTitel());
            statement.executeUpdate();
        } catch(java.text.ParseException exception) {
            System.out.println("Problem parsing date: "+exception.getMessage());
        } catch(NumberFormatException exception) {
            System.out.println("Problem parsing id: "+exception.getMessage());
        } finally {
            if(null!=statement) {
                try {
                    statement.close();
                } catch(java.sql.SQLException exception) {
                }
                statement = null;
            }
        }
    }

    private static void updateDocument(java.sql.Connection connection, se.metricspace.riksdagen.Document someDocument) throws java.sql.SQLException {
        if(documentExists(connection, someDocument)) {
//            System.out.println("Update");
        } else {
            insert(connection, someDocument);
        }
    }
}

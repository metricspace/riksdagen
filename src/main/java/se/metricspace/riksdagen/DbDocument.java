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
public class DbDocument implements Comparable<DbDocument> {
    private String itsBeteckning = null;
    private String itsDatum = null;
    private String itsDokumentTyp = null;
    private String itsDokumentUrlHtml = null;
    private String itsId = null;
    private String itsRiksMote = null;
    private String itsTitel = null;

    private DbDocument() {
    }

    private DbDocument(String someId, String someBeteckning, String someDatum, String someDokumentTyp, String someDokumentUrlHtml, String someRiksMote, String someTitel) {
        itsBeteckning = someBeteckning;
        itsDatum = someDatum;
        itsDokumentTyp = someDokumentTyp;
        itsDokumentUrlHtml = someDokumentUrlHtml;
        itsId = someId;
        itsRiksMote = someRiksMote;
        itsTitel = someTitel;
    }

    private String compString() {
        String compStr = itsDatum+":"+itsDokumentTyp;
        if("bet".equalsIgnoreCase(itsDokumentTyp)) {
            compStr += itsBeteckning;
        } else {
            int id = Integer.parseInt(itsBeteckning);
            compStr += String.format("%04d", id);
        }
        return compStr;
    }

    @Override
    public int compareTo(DbDocument someDocument) {
        int compare = 0;
        if(null!=someDocument) {
            compare = someDocument.compString().compareTo(compString());
        }
        return compare;
    }

    @Override
    public boolean equals(Object someObject) {
        boolean eq = false;
        if(null!=someObject && someObject instanceof DbDocument) {
            eq = hashCode()==someObject.hashCode();
        }
        return eq;
    }

    public static java.util.List<DbDocument> fetchFromTableForDateLimit(java.sql.Connection connection, String someDateLimit) throws java.sql.SQLException {
        java.util.List<DbDocument> documents = new java.util.ArrayList<>();

        java.sql.PreparedStatement statement = null;
        java.sql.ResultSet resultSet = null;

        try {
            String sqlSelect = "(select * from fraga where datum>=? order by datum desc, beteckning desc) union (select * from interpellation where datum>=? order by datum desc, beteckning desc) union (select * from motion where datum>=? order by datum desc, beteckning desc) union (select * from proposition where datum>=? order by datum desc, beteckning desc) union (select * from rir where datum>=? order by datum desc, beteckning desc) union (select * from dir where datum>=? order by datum desc, beteckning desc) union (select * from sou where datum>=? order by datum desc, beteckning desc) order by datum desc, dokument_typ desc, beteckning desc;";
            statement = connection.prepareStatement(sqlSelect, java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, someDateLimit);
            statement.setString(2, someDateLimit);
            statement.setString(3, someDateLimit);
            statement.setString(4, someDateLimit);
            statement.setString(5, someDateLimit);
            statement.setString(6, someDateLimit);
            statement.setString(7, someDateLimit);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                DbDocument document = parse(resultSet);
                if(null!=document && !documents.contains(document)) {
                    documents.add(document);
                }
            }
            sqlSelect = "select * from betankande where datum>=? order by datum desc, beteckning desc;";
            statement = connection.prepareStatement(sqlSelect, java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, someDateLimit);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                DbDocument document = parse(resultSet);
                if(null!=document && !documents.contains(document)) {
                    documents.add(document);
                }
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
        
        java.util.Collections.sort(documents);
        return documents;
    }

    public static java.util.List<DbDocument> fetchFromTableForRiksMote(java.sql.Connection connection, String someRiksMote) throws java.sql.SQLException {
        java.util.List<DbDocument> documents = new java.util.ArrayList<>();

        String lowerLimit = DbDocument.getMinDateForRiksMote(connection, someRiksMote);
        String upperLimit = DbDocument.getMaxDateForRiksMote(connection, someRiksMote);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date lower = new java.util.Date(formatter.parse(lowerLimit).getTime()-14L*86400000L);
            lowerLimit = formatter.format(lower);
            java.util.Date upper = new java.util.Date(formatter.parse(upperLimit).getTime()+15L*86400000L);
            upperLimit = formatter.format(upper);
        } catch(java.text.ParseException exception) {
            System.out.println("ParseException processing date: "+exception.getMessage());
        }
        java.sql.PreparedStatement statement = null;
        java.sql.ResultSet resultSet = null;
        try {
            String sqlSelect = "(select * from fraga where riks_mote=? order by datum desc, beteckning desc) union (select * from interpellation where riks_mote=? order by datum desc, beteckning desc) union (select * from motion where riks_mote=? order by datum desc, beteckning desc) union (select * from proposition where riks_mote=? order by datum desc, beteckning desc) union (select * from rir where riks_mote=? order by datum desc, beteckning desc) union (select * from dir where datum>=? and datum<=? order by datum desc, beteckning desc) union (select * from sou where datum>=? and datum<=? order by datum desc, beteckning desc) order by datum desc, dokument_typ desc, beteckning desc;";
            statement = connection.prepareStatement(sqlSelect, java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, someRiksMote);
            statement.setString(2, someRiksMote);
            statement.setString(3, someRiksMote);
            statement.setString(4, someRiksMote);
            statement.setString(5, someRiksMote);
            statement.setString(6, lowerLimit);
            statement.setString(7, upperLimit);
            statement.setString(8, lowerLimit);
            statement.setString(9, upperLimit);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                DbDocument document = parse(resultSet);
                if(null!=document && !documents.contains(document)) {
                    documents.add(document);
                }
            }
            sqlSelect = "select * from betankande where riks_mote=? order by datum desc, beteckning desc;";
            statement = connection.prepareStatement(sqlSelect, java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, someRiksMote);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                DbDocument document = parse(resultSet);
                if(null!=document && !documents.contains(document)) {
                    documents.add(document);
                }
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
        java.util.Collections.sort(documents);
        return documents;
    }


    public String getBenamning() {
        return getDokumentTyp()+" "+getRiksMote()+":"+getBeteckning();
    }

    public String getBeteckning() {
        return itsBeteckning;
    }

    public String getDatum() {
        return itsDatum;
    }

    public String getDerivedUrl() {
        String url = null;
        switch(itsDokumentTyp) {
            case "Bet":
                url = "https://www.riksdagen.se/sv/dokument-lagar/dokument/betankande/_"+itsId;
                break;
            case "Dir":
                url = "https://www.riksdagen.se/sv/dokument-lagar/dokument/direktiv/_"+itsId;
                break;
            case "Fr":
                url = "https://www.riksdagen.se/sv/dokument-lagar/dokument/fraga/_"+itsId;
                break;
            case "Ip":
                url = "https://www.riksdagen.se/sv/dokument-lagar/dokument/interpellation/_"+itsId;
                break;
            case "Mot":
                url = "https://www.riksdagen.se/sv/dokument-lagar/dokument/motion/_"+itsId;
                break;
            case "Prop":
                url = "https://www.riksdagen.se/sv/dokument-lagar/dokument/proposition/_"+itsId;
                break;
            case "RiR":
                url = "https://www.riksdagen.se/sv/dokument-lagar/dokument/rir/_"+itsId;
                break;
            case "SoU":
                url = "https://www.riksdagen.se/sv/dokument-lagar/dokument/utredning/_"+itsId;
                break;
            default:
                System.out.println("Unknown type:"+itsDokumentTyp);
                break;
        }
        return url;
    }

    public String getDokumentTyp() {
        return itsDokumentTyp;
    }

    public String getDokumentUrlHtml() {
        return itsDokumentUrlHtml;
    }

    public String getId() {
        return itsId;
    }

    public static String getMaxDateForRiksMote(java.sql.Connection connection, String someRiksMote) throws java.sql.SQLException {
        String [] tables = {"betankande", "fraga", "interpellation", "motion", "proposition"};
        String date = null;
        for(String table: tables) {
           String someDate = getMaxDateForRiksMoteInTable(connection, someRiksMote, table);
           if(null!=someDate) {
               if(null==date) {
                   date = someDate;
               } else {
                   if(date.compareTo(someDate)<0) {
                       date = someDate;
                   }
               }
           }
        }
        return date;
    }

    private static String getMaxDateForRiksMoteInTable(java.sql.Connection connection, String someRiksMote, String someTable) throws java.sql.SQLException {
        String date = null;
        java.sql.PreparedStatement statement = null;
        java.sql.ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select max(datum) as date from "+someTable+" where riks_mote=?;", java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, someRiksMote);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                date = resultSet.getString("date");
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
        return date;
    }

    public static String getMinDateForRiksMote(java.sql.Connection connection, String someRiksMote) throws java.sql.SQLException {
        String date = null;
        String [] tables = {"betankande", "fraga", "interpellation", "motion", "proposition"};
        for(String table: tables) {
           String someDate = getMinDateForRiksMoteInTable(connection, someRiksMote, table);
           if(null!=someDate) {
               if(null==date) {
                   date = someDate;
               } else {
                   if(date.compareTo(someDate)>0) {
                       date = someDate;
                   }
               }
           }
        }
        return date;
    }

    private static String getMinDateForRiksMoteInTable(java.sql.Connection connection, String someRiksMote, String someTable) throws java.sql.SQLException {
        String date = null;
        java.sql.PreparedStatement statement = null;
        java.sql.ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select min(datum) as date from "+someTable+" where riks_mote=?;", java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, someRiksMote);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                date = resultSet.getString("date");
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
        return date;
    }

    public String getProvidedUrl() {
        return itsDokumentUrlHtml.startsWith("http://") ? itsDokumentUrlHtml.replaceAll("http://", "https://") : itsDokumentUrlHtml;
    }

    public String getRiksMote() {
        return itsRiksMote;
    }

    public static java.util.List<String> getRiksMoten(java.sql.Connection connection) throws java.sql.SQLException {
        java.util.List<String> riksMoten = new java.util.ArrayList<>();
        java.sql.PreparedStatement statement = null;
        java.sql.ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select distinct(riks_mote) from fraga order by riks_mote;", java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String riksMote = resultSet.getString("riks_mote");
                if(null!=riksMote && !riksMoten.contains(riksMote)) {
                    riksMoten.add(riksMote);
                }
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
        return riksMoten;
    }

    public String getTitel() {
        return itsTitel;
    }

    @Override
    public int hashCode() {
        return (itsDokumentTyp+itsId).hashCode();
    }

    private static DbDocument parse(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        DbDocument document = null;
        String id = resultSet.getString("id");
        String beteckning = resultSet.getString("beteckning");
        String datum = resultSet.getString("datum");
        String dokument_typ = resultSet.getString("dokument_typ");
        String dokument_url_html = resultSet.getString("dokument_url_html");
        String riks_mote = resultSet.getString("riks_mote");
        String titel = resultSet.getString("titel");
        if(null!=id && null!=beteckning && null!=datum && null!=dokument_typ && null!=dokument_url_html && null!=riks_mote && null!=titel) {
            document = new DbDocument(id, beteckning, datum, dokument_typ, dokument_url_html, riks_mote, titel);
        }
        return document;
    }

    @Override
    public String toString() {
        return this.itsDokumentTyp+" "+itsRiksMote+":"+itsBeteckning+" ("+itsId+")";
    }
}

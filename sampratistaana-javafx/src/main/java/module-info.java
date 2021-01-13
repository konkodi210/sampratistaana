module org.sampratistaana {
	requires org.hibernate.orm.core;
	requires java.persistence;
	requires net.bytebuddy;
	requires sqlite.jdbc;
	requires java.sql;
    requires java.xml.bind;
    requires javafx.controls;
    requires javafx.fxml;    
    requires java.naming;
    requires java.activation;
    requires java.desktop;    
    requires poi;   
    requires com.google.gson;
    requires com.fasterxml.classmate;
    requires org.controlsfx.controls;
    
    opens org.sampratistaana.controllers;
    opens org.sampratistaana.beans;
    opens org.sampratistaana to javafx.fxml;
    
    exports org.sampratistaana;
}
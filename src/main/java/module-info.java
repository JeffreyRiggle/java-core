module ilusr.core {
    requires java.base;
    requires java.xml;
    requires java.desktop;
    requires spring.beans;
    requires spring.core;
    requires spring.context;
    requires javafx.controls;
    requires javafx.swing;
    requires javafx.fxml;
    requires junit;

    exports ilusr.core.data;
    exports ilusr.core.datamanager.xml;
    exports ilusr.core.datamanager.unformatted;
    exports ilusr.core.environment;
    exports ilusr.core.i18n;
    exports ilusr.core.interfaces;
    exports ilusr.core.io;
    exports ilusr.core.ioc;
    exports ilusr.core.javafx;
    exports ilusr.core.javafx.splashscreen;
    exports ilusr.core.mvpbase;
    exports ilusr.core.test;
    exports ilusr.core.url;
}
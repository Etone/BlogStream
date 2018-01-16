
# Anmerkungen zur Reddit App

-Damit die reddit App funktioniert, wird **JDK9** benoetigt. Ist sie nicht vorhanden, erhaelt man bei dem Versuch, das Token zu holen, einen 500 Fehler "Access Token Denied" und eine nichts-sagende Exception im Programm. Dies scheint wegen einer Abhaengigkeit der Spring OAuth dependency zu passieren. Wird das Projekt auf JDK9 umgestellt, funktioniert auch die Reddit App.

-Mit JDK9 bekommt man aber einen weiteren Fehler, den man mit einer VM Option in IntelliJ beheben kann. Hierfür muss der Befehl (`--add-modules=java.xml.bind`) bei der Eureka Launchconfiguration angegeben werden. [Beispielabbildung hier](https://i.imgur.com/vyKnNAY.png) . Auslöser scheint eine veraltete Abhaengigkeit zu sein, die in JDK9 Probleme verursacht?(#)

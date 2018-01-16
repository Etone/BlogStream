
#Anmerkungen zur Reddit App
-Damit die reddit App läuft, wird **JDK9** benötigt. Ist sie nicht vorhanden, erhält man bei dem Versuch, das Token zu holen, einen 500 Fehler "Access Token Denied" und eine nichts-sagende Exception im Programm. Dies scheint wegen einer Abhängigkeit der Spring OAuth dependency zu passieren. Wird das Projekt auf JDK9 umgestellt, funktioniert auch die Reddit App.
-Mit JDK9 bekommt man aber einen weiteren Fehler, den man mit einer VM Option in IntelliJ beheben kann. Hierfür muss der Befehl " --add-modules=java.xml.bind " bei der Eureka Launchconfiguration angegeben werden. [Beispielabbildung hier](https://i.imgur.com/vyKnNAY.png) . Auslöser scheint eine veraltete Abhängigkeit zu sein, die in JDK9 Probleme verursacht? 

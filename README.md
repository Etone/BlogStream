
#Anmerkungen zur Reddit App
-Damit die reddit App l�uft, wird **JDK9** ben�tigt. Ist sie nicht vorhanden, erh�lt man bei dem Versuch, das Token zu holen, einen 500 Fehler "Access Token Denied" und eine nichts-sagende Exception im Programm. Dies scheint wegen einer Abh�ngigkeit der Spring OAuth dependency zu passieren. Wird das Projekt auf JDK9 umgestellt, funktioniert auch die Reddit App.
-Mit JDK9 bekommt man aber einen weiteren Fehler, den man mit einer VM Option in IntelliJ beheben kann. Hierf�r muss der Befehl " --add-modules=java.xml.bind " bei der Eureka Launchconfiguration angegeben werden. [Beispielabbildung hier](https://i.imgur.com/vyKnNAY.png) . Ausl�ser scheint eine veraltete Abh�ngigkeit zu sein, die in JDK9 Probleme verursacht? 

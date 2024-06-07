# Beschreibung

TickTack ist eine Anwendung zur Erfassung und Verwaltung von Arbeitszeiten.
Sie bietet die Möglichkeit Arbeitszeiten einfach mit Start- und Endzeit wochenweise einzutragen,
wobei Pausenzeiten automatisch abgezogen werden.
Außerdem liefert die Anwendung eine Übersicht über die in der aktuellen Woche bereits
geleisteten Arbeitsstunden, sowie den aktuellen Stand der Über- bzw. Minusstunden.
Die wöchentliche Arbeitszeit und die Dauer der Pausenzeit, die nach 6h Arbeitszeit
automatisch abgezogen wird, können in den Settings angepasst werden.
Somit liefert TickTack dem Nutzer eine einfache und anschauliche Möglichkeit Arbeitszeiten zu dokumentieren und
minimiert den manuellen Aufwand der Zeiterfassung.

# Anforderungen

Client:

- Navigation zwischen den verschiedenen Seiten
- Overview-Page:
    - Anzeige der geleisteten Arbeitsstunden der aktuellen Woche
    - Anzeige der aktuellen Minus-/Überstunden über alle Wochen hinweg
- Timesheet-Page:
    - Hinzufügen neuer Wochen für Zeiteinträge
    - Bearbeiten von existierenden Wochen mit Zeiteinträgen
    - Löschen von Wochen mit allen Zeiteinträgen
- Settings-Page:
    - Festlegung des Benutzernamens
    - Festlegung der wöchentlichen Arbeitsstunden
    - Festlegung der Pausen-Dauer (die nach 6h Arbeitszeit automatisch abgezogen wird)

Server:

- Speichern der Zeiteinträge in der Datenbank
- Laden der Zeiteinträge aus der Datenbank
- Zusammenbauen der einzelnen Zeiteinträge zu Wochen
- Berechnung der Gesamtarbeitszeiten
- Berechnung der Minus-/Überstunden

# Technologien

## Frontend

Das Frontend der Anwendung ist mit dem komponentenbasierten Framework `Angular` umgesetzt, welches die Erstellung von
Single-Page-Applications ermöglicht.

Komponenten in `Angular` setzen sich aus mehreren Dateien zusammen. Die Logik einer Komponente ist in einer `TypeScript`
-Klasse definiert.
In einer weiteren Datei, dem `HTML`-Template, ist mithilfe von `HTML` festgelegt, wie eine Komponente visuell
dargestellt wird.
Zusätzlich ist es optional möglich in einer `CSS`-Datei den Style der einzelnen `HTML`-Elemente im `HTML`-Template
anzupassen.

Um die Erstellung der Benutzeroberfläche zu vereinfachen, wurde außerdem die Komponentenbibliothek [`Angular Material`](https://material.angular.io/)
verwendet.
Diese stellt vorgefertigte Komponenten zur Verfügung und vermindert somit den manuellen Entwicklungsaufwand.

Zusätzlich kommt das `CSS`-Framework [`Tailwind CSS`](https://tailwindcss.com/) zum Einsatz. Dieses stellt gebrauchsfertige `CSS`-Klassen bereit,
die direkt in den `HTML`-Templates
genutzt werden können. Somit ist das Schreiben eigener `CSS`-Klassen nicht nötig, was wiederum den manuellen
Implementierungsaufwand verringert.

## Backend

Das Backend ist in einer `Spring Boot`-Anwendung mit der Programmiersprache `Java` implementiert. `Spring Boot` basiert
auf dem open-source
Framework `Spring` und ermöglicht eine einfache und schnelle Erstellung von `Spring`-Anwendungen. Möglich ist dies durch
die Bereitstellung von
Templates für weit verbreitete Anwendungsfälle und dem somit geringen Konfigurationsaufwand.

## API

Die Kommunikation zwischen Frontend und Backend wird mit Hilfe einer API ermöglicht.
Die Beschreibung der API erfolgt nach der `OpenAPI` Spezifikation. Dies ermöglicht die automatische Generierung
des API-Codes, sowohl für den Server als auch für den Client. Dafür kommen die spezifischen `OpenAPI`-Generatoren für
`Angular` und `Spring Boot` zum Einsatz. Dies reduziert den manuellen Implementierungsaufwand und ermöglicht eine
einfache Anpassung des Codes bei Änderungen der API.

## Datenbank

Die Speicherung der Daten erfolgt mithilfe einer Datenbank. Dazu ist eine `PostgreSQL` Datenbank an das `Spring Boot`
-Backend angebunden.

# Umsetzung

## Erzeugung der einzelnen Projekte

Der erste Schritt der Umsetzung bestand in der Erstellung der Projekte für die Front- und Backend-Anwendung.

Die Erzeugung eines `Angular` Projektes erfolgt mit Hilfe der `Angular` CLI. Um diese zu nutzen, muss zunächst Node.js auf
dem Rechner installiert sein.
Anschließend kann über die Kommandozeile mit dem Befehl `npm install -g @angular/cli` die `Angular` CLI installiert
werden. Ein neues Projekt kann nun
mit `ng new project-name` generiert werden.

Ein neues `Spring Boot` Projekt kann mit Hilfe des [Spring Initializr](https://start.spring.io/) erstellt werden. Auf dieser
Webseite kann das neue Projekt konfiguriert werden.
Beispielsweise kann das gewünschte Build-Tool sowie die Programmiersprache ausgewählt werden. In diesem Fall
wurde `Maven` als Build-Tool und `Java` als Programmiersprache gewählt.
Zusätzlich können hier bereits benötigte Dependencies hinzugefügt werden. Für dieses Projekt wurden zum Beispiel `Spring
Web`, `Spring Data JPA` und der Treiber für die
`PostgreSQL` Datenbank ausgewählt. Anschließend kann das Projekt erzeugt und als .ZIP Datei heruntergeladen werden.

![spring_initializr.png](resources%2Fspring_initializr.png)

Neben der Webseite bieten mittlerweile auch viele IDEs die Möglichkeit Spring Projekte direkt zu erzeugen.

## Codegenerierung mit OpenAPI

Als Nächstes wurde die Code-Generierung für das Front- und Backend eingerichtet. Dazu wurde zunächst die
Schnittstellenbeschreibung [api.yml](api.yml)
nach der `OpenAPI` Spezifikation erzeugt. Die Schnittstellenbeschreibung wurde im Laufe des Projektes mehrfach angepasst,
da zu Beginn die Struktur der
Daten und die benötigten Requests noch nicht eindeutig feststanden. Daher war es von Vorteil, dass der Code für beide
Projekte nach Veränderungen der Schnittstellenbeschreibung einfach
neu generiert werden konnte.

### Codegenerierung Angular

Die Codegenerierung für `Angular` erfolgt mit Hilfe der `openapi-generator-cli`. Die CLI wird mit Hilfe von `npm install
@openapitools/openapi-generator-cli` dem Projekt hinzugefügt. Um die Verwendung des Generators zu vereinfachen, ist
im `scripts`
Attribut der [package.json](angular-frontend%2Fpackage.json) der Befehl `generate:api` hinterlegt. Die für diesen
Befehl definierte Aktion besteht im Aufruf des `generate` Befehls der `openapi-generator-cli` mit bestimmten Parametern.
Die Parameter
geben den Pfad der Input-Datei, also der Schnittstellenbeschreibung, und den Output-Pfad für den generierten Code an.
Außerdem
ist der zu verwendende Generator angegeben. In diesem Fall ist dieser Parameter des Generators auf `typescript-angular`
gesetzt. Durch diese
Definition kann der Generator bequem über `npm run generate:api` gestartet werden ohne jedes Mal alle Parameter mitgeben
zu müssen.

Der generierte Code ist nicht im Git Repository inkludiert und muss nach dem Auschecken über den zuvor genannten Befehl erzeugt werden.
Nach der Generierung finden sich die erzeugten Klassen in dem Ordner [api](angular-frontend%2Fsrc%2Fapp%2Fapi) des
angular-frontend Projektes. Erzeugt werden
die benötigten Datenstrukturen ([model](angular-frontend%2Fsrc%2Fapp%2Fapi%2Fmodel)) und mehrere `Angular`-Services (ein
Service pro `tag` in der
Schnittstellenbeschreibung [api.yml](api.yml)). Die Angular-Services enthalten die Funktionalität, die benötigt ist, um
die HTTP-Requests, die in [api.yml](api.yml)
definiert sind abzuschicken.

### Codegenerierung Spring Boot

Die Codegenerierung für `Spring Boot` erfolgt mit dem `openapi-generator-maven-plugins`. Dieses Plugin ist über
die [pom.xml](springboot-backend%2Fpom.xml) in den
Buildprozess des Projektes eingebunden, so dass der Code bei jedem Bauen des Projektes neu generiert wird. Auf diese Weise
werden Änderungen der
Schnittstellenbeschreibung immer direkt übernommen.

In der [pom.xml](springboot-backend%2Fpom.xml) sind die vorgenommenen Einstellungen für das Plugin zu sehen. Die
Datei [api.yml](api.yml)
ist wieder als Input für den Generator angegeben. Als Zielpackages für den generierten Code
sind `com.openapi.api` `und com.openapi.model` festgelegt.
Das Attribut `generatorName` gibt an, welcher Generator verwendet wird. Für dieses Projekt wird der `spring` Generator
genutzt.
In den `configOptions` werden weitere generatorspezifische Einstellungen vorgenommen.
In diesem Fall ist die `Spring Boot` Version 3 festgelegt sowie die Verwendung des Delegate Patterns.

Wie bei der Generierung für das Frontend ist der erzeugt Code nicht im Git Repository enthalten und muss zunächst lokal generiert werden, indem das Projekt einmal gebaut wird.
Anschließend befindet sich der für `Spring Boot` generierte Code unter [target/generated-sources/openapi/src/main/java](springboot-backend%2Ftarget%2Fgenerated-sources%2Fopenapi%2Fsrc%2Fmain%2Fjava).

Auch hier finden sich
in [model](springboot-backend%2Ftarget%2Fgenerated-sources%2Fopenapi%2Fsrc%2Fmain%2Fjava%2Fcom%2Fopenapi%2Fmodel) die
benötigten Datentypen.
In [api](springboot-backend%2Ftarget%2Fgenerated-sources%2Fopenapi%2Fsrc%2Fmain%2Fjava%2Fcom%2Fopenapi%2Fapi) findet
sich für jedes `tag` der Schnittstellenbeschreibung
zwei Interfaces mit den Suffixen `Api` und `Delegate` sowie eine `Controller` Klasse.
Das `Delegate` Interface dient als
Einsprungpunkt für die eigene Implementierung der Logik. Dafür wird im `Spring Boot` Backend
eine `DelegateImpl` Klasse erzeugt (s. [api](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fapi)),
die das `Delegate` implementiert. In der
`DelegateImpl` Klasse wird somit festgelegt, was beim Aufruf eines Requests geschieht. Findet
sich keine entsprechende `DelegateImpl` Klasse liefert das `Delegate` standardmäßig die HTTP
Error Response 501 (not implemented).

## Implementierung Angular

### Navigation

Um einen Rahmen für die Anwendung zu schaffen, wurde als erster Schritt eine Navigation erstellt.
Die `main-nav` Komponente enthält einen App Header und ein Seitenmenü, über das zwischen den verschiedenen Seiten der Anwendung gewechselt werden kann.
Bei dieser Komponente handelt es sich um einen Blueprint von `Angular Material`. Sie wurde automatisch mit dem Befehl `ng generate @angular/material: material-nav --name=main-nav` erzeugt.
Anschließend sind nur geringe Anpassungen der Komponente nötig, um diese zu nutzen, wie zum Beispiel das Eintragen des Anwendungsnamens und das Anpassen der Items im Seitenmenü.

Damit die Navigation funktioniert müssen die `routerLinks` der Listenitems im Seitenmenü im [app-routing.module.ts](angular-frontend%2Fsrc%2Fapp%2Fapp-routing.module.ts) hinterlegt werden.
Hier wird festgelegt welche Komponente geladen wird, wenn zu einem bestimmten Pfad navigiert wird.

### Overview-Page

Die Overview-Page stellt die Startseite der Anwendung dar. Sie zeigt eine kurze Begrüßungsnachricht an den Nutzer,
so wie die Anzahl der in der aktuellen Woche geleisteten Stunden und den aktuellen Stand der Minus-/ Überstunden.

![overview-page.png](resources%2Foverview-page.png)

Wurde die Wochenarbeitszeit erreicht, ist die Anzahl der geleisteten Arbeitsstunden grün eingefärbt. Andernfalls ist diese Zahl rot. 
Ähnlich verhält es sich mit den Minus-/Überstunden. Ist dieser Wert im Minus, wird die Zahl rot angezeigt. Bei einem positiven Wert ist die Zahl grün.
Möglich ist dies mit `Angular`'s `ngClass` Direktive. Mit `ngClass` können `CSS`-Styles über Bedingungen geknüpft an `HTML`-Elemente angehängt werden.

Die angezeigten Daten erhält die Komponente über den `DataService` der Anwendung. Der `DataService` wiederum nutzt den generierten
`TimesheetService` um die entsprechenden Daten vom Backend zu erhalten. Zusätzlich erhält die Komponente über den `AppSettingsService` die
Information über die aktuell eingestellte Anzahl der wöchentlich zu leistenden Arbeitsstunden. Der `AppSettingsService` nutzt den generierten
`SettingsService` um die aktuell gespeicherten Einstellungen zu erhalten.

### Timesheet-Page

Die Timesheet-Page ist für die Erfassung von Arbeitszeiten verantwortlich und somit Hauptbestandteil der Anwendung.
Die Seite zeigt eine Woche mit einer List von Zeiteinträgen, ein Eintrag pro Arbeitstag (Montag-Freitag).

Die Start- und Endzeiten sind Input-Felder vom Typ `time` und können vom Benutzer angepasst werden.
Die Spalte _DURATION_ wird automatisch aus Start- und Endzeit berechnet, wobei die Pausendauer direkt abgezogen wird, wenn die Arbeitszeit 6 Stunden überschreitet.
In der letzten Spalte _ABSENT_ kann ein Tag als Krankheits-, Urlaubs- oder Feiertag markiert werden. Dies wird bei der Berechnung der Minus-/Überstunden berücksichtigt.
Unterhalb der Einträge wird die Gesamtdauer der eingetragenen Zeiten angezeigt.

Über Pfeile neben dem Wochennamen (KW+Jahr) kann zwischen verschiedenen Wochen navigiert werden.
Befindet man sich in der letzten gespeicherten Woche, wird der rechte Pfeil zu einem Plus-Button.
Über diesen Button kann die aktuelle Woche hinzugefügt werden.
Ist diese bereits vorhanden wird eine Meldung angezeigt, die dies dem Nutzer mitteilt.
Über den Löschen-Button unten rechte kann die aktuell geöffnete Woche gelöscht werden.
Dazu erscheint ein Dialog, der abfragt, ob die Woche wirklich gelöscht werden soll.

![timesheet-page.png](resources%2Ftimesheet-page.png)

Die benötigten Daten der Wochen mit den einzelnen Zeiteinträgen erhält die Komponente über den `DataService`.
Beim Öffnen der Seite werden mit Hilfe von `dataService.getWeeksAll()` alle aktuell gespeicherten Wochen vom Backend geladen.
Dann wird die letzte gespeicherte Woche als anzuzeigende Woche gesetzt.

Die Berechnung der Dauer der einzelnen Einträge und der Gesamtarbeitszeit der Woche finden in den Methoden `calcTimeDifference()` und `calcTotalTime()` der Komponente statt.

Das Hinzufügen einer neuen Woche geschieht in der Methode `addNewWeek()`.
Dazu wird ein neues `Week` Objekt erzeugt, dem Einträge mit den Daten der aktuellen Woche hinzugefügt werden.
Diese Daten werden aus dem aktuellen Datum berechnet. Dieses neue `Week` Objekt wird anschließend gespeichert und die Wochen noch einmal neu vom Backend geladen.

Der Dialog, der bei Klick auf den Löschen-Button geöffnet wird, ist in die Komponente `DeleteWeekDialog` ausgelagert. 
Zum Öffnen des Dialogs wird die von `Angular Material` zur Verfügung gestellte Klasse `MatDialog` genutzt.
Beim Schließen des Dialogs kann mit Hilfe des Observer Patterns auf die Eingabe des Benutzers reagiert werden.
Bei einem Klick auf den OK-Button wird `true` zurückgegeben und anschließend die Woche mit dem `DataService` gelöscht.

Das Speichern der Daten der aktuell ausgewählten Woche erfolgt, sobald ein Input, also eine Start- oder Endzeit, verändert wird, indem die Methode `saveData()` beim Auslösen des `change` Events des Input Elementes aufgerufen wird.
Die Speicherung erfolgt wiederum über den `DataService`.
Auf diese Weise ist kein Speichern Button nötig und Veränderungen werden immer direkt übernommen.

### Settings-Page

In der Settings-Page können die Basiswerte der wöchentlichen Arbeitszeit und der Pausendauer angepasst werden.
Ebenso besteht die Möglichkeit den Benutzernamen zu verändern.

![settings-page.png](resources%2Fsettings-page.png)

Auch hier werden die Werte analog zum Timesheet gespeichert, sobald ein Input verändert wird.
Die Speicherung erfolgt mit Hilfe von `saveSettings()`.
Diese Methode wiederum nutzt dafür die entsprechende Methode des `AppSettingsService`.

## Implementierung Spring Boot

### Anbindung der Datenbank

Damit die Anwendung funktioniert muss sie mit einer `PostgreSQL`-Datenbank verknüpft werden.
Dazu muss zunächst [PostgreSQL](https://www.postgresql.org/download/) installiert sein.
Beim erstmaligen Öffnen der `PostgreSQL` Shell muss ein Benutzername und ein Passwort festgelegt werden.
Anschließend kann mit dem Befehl `CREATE DATABASE db-name;` eine neue Datenbank erzeugt werden.

Um diese nun mit der `Spring Boot` Anwendung zu verknüpfen, müssen in [application.properties](springboot-backend%2Fsrc%2Fmain%2Fresources%2Fapplication.properties)
die entsprechenden Werte für `spring.datasource.url`, `spring.datasource.username` und `spring.datasource.passwort` gesetzt werden.
Wird der Standardport von `PostgreSQL` beibehalten, lautet die URL `jdbc:postgresql://localhost:5432/db-name`.
Benutzername und Passwort entsprechen den bei der Einrichtung der Shell gesetzten Werten.
Um diese Werte nicht offen im Quellcode stehen zu haben, können sie in Systemumgebungsvariablen gespeichert werden.
In `Spring Boot` können sie dann mit `${VARIABLEN_NAME}` referenziert werden.
Durch die Einstellung `spring.jpa.hibernate.ddl-auto=update` werden die benötigten Tabellen automatisch erstellt, falls diese noch nicht vorhanden sind.

Um Einträge in der Datenbank speichern zu können müssen nun Klassen für die Entitäten, die gespeichert werden sollen, erstellt werden.
Für diese Anwendung wurden die Entitäten [`TimeEntryDTO`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fpostgresql%2Fmodel%2FTimeEntryDTO.java) und [`SettingsDTO`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fpostgresql%2Fmodel%2FSettingsDTO.java) angelegt.
Für jede der Entitäten wird anschließend ein Repository Interface angelegt, welches von `JpaRepository` erbt.
Dadurch werden alle benötigten Methoden für Datenbankzugriffe wie `findAll()`, `findById()` oder `save()` für die entsprechende Entität zur Verfügung gestellt.
Da diese Standardfunktionen in diesem Anwendungsfall ausreichen ist keine weitere manuelle Implementierung notwendig.

### Umsetzung der DelegateImpls

Wie bereits im Abschnitt [Codegenerierung Spring Boot](#codegenerierung-spring-boot) erläutert, findet in den `DelegateImpl` Klassen die Umsetzung der Logik hinter den einzelnen API Aufrufen statt.
Die `DelegateImpl`-Klassen finden sich im [api-package](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fapi) der `Spring Boot` Anwendung.

Um eine weitere Entkopplung des generierten Codes und der Business-Logik zu erzielen, kommt das Facade-Pattern zum Einsatz.
In den einzelnen Methoden der `DelegateImpl`-Klassen werden daher stets nur Methoden der [`ServiceFacade`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Ffacade%2FServiceFacade.java) aufgerufen.
Die [`ServiceFacade`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Ffacade%2FServiceFacade.java) wiederum hat Zugriff auf die Services [`WeekDataService`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fservices%2FWeekDataService.java) und [`SettingsService`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fservices%2FSettingsService.java).

In diesen Services ist die tatsächliche Logik implementiert.
[`WeekDataService`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fservices%2FWeekDataService.java) enthält alle Operationen, die mit den Zeiteinträgen in Verbindung stehen.
Da nur die Zeiteinträge ohne einen Bezug zu den einzelnen Wochen gespeichert werden, baut die Methode `loadWeeksAll()` beispielsweise die einzelnen Wochen zusammen.
Zum Laden bzw. Speichern der Daten verwendet [`WeekDataService`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fservices%2FWeekDataService.java) das [`TimeEntryRepository`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fpostgresql%2Frepository%2FTimeEntryRepository.java).
Da für einige Operationen, wie die Berechnung der in der aktuellen Woche geleisteten Arbeitsstunden, Informationen über die aktuellen Settings benötigt wird, kommt ebenfalls das [`SettingsRepository`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fpostgresql%2Frepository%2FSettingsRepository.java) zum Einsatz.

Analog dazu sind im [`SettingsService`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fservices%2FSettingsService.java) alle die Settings betreffenden Operationen umgesetzt.
Hier wird lediglich das [`SettingsRepository`](springboot-backend%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffhdw%2Fwebproject%2Fpostgresql%2Frepository%2FSettingsRepository.java) benötigt.

# Fazit und Ausblick

Die vorherigen Abschnitte erläutern wie eine Webanwendung mit Hilfe von `Angular` und `Spring Boot` realisiert werden kann.

Die Entwicklung des Frontends wurde durch den Einsatz von `Angular Material` und `Tailwind CSS` beschleunigt und vereinfacht.
Im Backend ermöglichten die `Spring Boot` Starter Web und Data JPA eine schnelle Umsetzung.
Der `OpenAPI` Generator konnte sowohl im Frontend als auch im Backend verwendet werden und sorgte damit für eine flexible Vorgehensweise, die besonders zu Beginn des Projektes von Vorteil war.

Die aktuelle Umsetzung von TickTack liefert die Basisfunktionalitäten einer Zeiterfassungs-Anwendung und kann somit bereits genutzt werden.
Denkbare Erweiterungen der Anwendung sind die Folgenden:
- Multiuser Funktionalität (bisher kann nur 1 Benutzer die Anwendung nutzen, es gibt keinen Login o.ä.)
- Optimierung des Timesheets (Auswahlmöglichkeit beim Hinzufügen der Wochen, GoTo-Option für Wochenauswahl, automatisches Eintragen von gesetzlichen Feiertagen)
- CSV-Export der gespeicherten Daten




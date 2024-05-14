# Beschreibung

TickTack ist eine Anwendung zur Erfassung und Verwaltung von Arbeitszeiten.
Sie bietet die Möglichkeit Arbeitszeiten einfach mit Start- und Endzeit einzutragen,
wobei Pausenzeiten automatisch abgezogen werden.
Außerdem liefert die Anwendung eine Übersicht über die in der aktuellen Woche bereits
geleisteten Arbeitsstunden, sowie den aktuellen Stand der Über- bzw. Minusstunden.
Die wöchentliche Arbeitszeit und die Dauer der Pausenzeit, die nach 6h Arbeitszeit
automatisch abgezogen können, in den Settings angepasst werden.
Somit liefert TickTack dem Nutzer eine einfache und anschauliche Möglichkeit Arbeitszeiten zu dokumentieren und
minimiert den manuellen Aufwand der Zeiterfassung.

# Anforderungen

Client:
- Overview-Page:
  - Anzeige der geleisteten Arbeitsstunden der aktuellen Woche
  - Anzeige der aktuellen Minus-/Überstunden über alle Wochen hinweg
- Timesheet-Page:
  - Hinzufügen neuer Zeiteinträge
  - Bearbeiten von existierenden Zeiteinträgen
- Settings-Page:
  - Festlegung der wöchentlichen Arbeitsstunden
  - Festlegung der Pausen-Dauer (die nach 6h Arbeitszeit automatisch abgezogen wird)

Server:
- Speichern der Zeiteinträge in der Datenbank
- Laden der Zeiteinträge aus der Datenbank
- Berechnung der Gesamtarbeitszeiten
- Berechnung der Minus-/Überstunden

# Technologien

Das Frontend der Anwendung wird mit dem komponentenbasierten Framework Angular umgesetzt.
Die primäre Entwicklungssprache von Angular ist TypeScript. Zusätzlich kommen HTML und CSS zur Definierung der UI zum Einsatz.
Außerdem wird die Komponentenbibliothek Angular Material verwendet. Diese stellt vorgefertigte Komponenten
zur Verfügung und erleichtert somit die Entwicklung der UI .\
Der Server wird in einer SpringBoot Anwendung implementiert. Das open-source Framework ermöglicht die einfache
Erstellungen von Web-Anwendungen mit der Programmiersprache Java.
Die Kommunikation zwischen Client und Server soll mit einer API ermöglicht werden.
Die Beschreibung der API erfolgt nach der OpenAPI Spezifikation. Dies ermöglicht die automatische Generierung
des API-Codes, sowohl für den Server als auch für den Client. Dafür kommen die spezifischen OpenAPI-Generatoren für
Angular und SpringBoot zum Einsatz. Dies reduziert den manuellen Implementierungsaufwand und Ermöglicht eine
einfach Anpassung des Codes bei Änderungen der API.
Die Speicherung der Daten erfolgt mit Hilfe einer Datenbank. Dazu wird voraussichtlich eine MySQL Datenbank
an die Server-Anwendung angebunden.

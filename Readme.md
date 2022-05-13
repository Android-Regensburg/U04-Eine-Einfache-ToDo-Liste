# U04 | Eine einfache ToDo-Liste

## Aufgabenstellung

In dieser Aufgabe implementieren Sie eine einfache, nicht-persistente ToDo-Liste. NutzerInnen können neue Aufgaben zur Liste hinzufügen. Die neuesten Listenelemente werden ganz oben in der Liste eingefügt. Durch einen langen Klick auf einzelne Einträge können Aufgaben als *erledigt* markiert werden. Solche Aufgaben werden dann ans Ende der Liste verschoben und mit einer veränderten Darstellung angezeigt. **Zur Umsetzung der Aufgabe verwenden Sie einen `ArrayAdapter` und ein selbst-erstelltes Layout für die Listeneinträge.**

## Hinweise

**Achtung:** *Spätestens hier beginnen die Übungsaufgaben komplizierter zu werden. Nehmen Sie sich ausreichend Zeit und erarbeiten Sie Schritt für Schritt Ihre eigene Lösung.* Versuchen Sie die Zusammenhänge und Hintergründe zu verstehen. An schwierigen Stellen bieten wir Ihnen in der Aufgabenbeschreibung zwei unterschiedlich komplexe Lösungswege an. Wenn Sie sich für den "einfacheren" Weg entscheiden, können Sie später gerne den alternativen Vorschlag in Ihre funktionierende Lösung integrieren.

### Inflaten des Layouts

Um aus der von Ihnen vorbereiteten XML-Datei im Code ein konkretes View-Objekt zu erzeugen, können Sie sich an diesem Code orientieren. Als `resource` wird dabei die ID des jeweiligen Layouts (XML-Datei) übergeben.

```java
private View inflateViewTask(int resource, ViewGroup parent) {
  LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  return inflater.inflate(resource, parent, false);
}
```

### Trennung von UI und Programmlogik
Versuchen Sie bei der Lösung der Aufgabe bewusst eine **strikte** Trennung zwischen der internen Repräsentation der *Tasks* und deren Darstellung im *User Interface* zu erreichen. Behalten Sie dabei den Folgenden Grundsatz im Kopf: "Ihre App verwaltet intern eine Liste von Aufgaben. Über das *User Interface* können NutzerInnen Parameter für neue Aufgaben eingegeben und den Status existierender Aufgaben ändern. Diese Aktionen haben zuerst Auswirkungen auf die interne Liste. Der ListView zeigt stets den aktuellen Stand dieser internen Liste an, das *User Interface* verwaltet diese Liste aber nicht selbstständig."

## Vorgehen

### Starterpaket

Laden Sie sich das Starterpaket herunter, das Sie links auf dieser Seite finden. Entpacken Sie den Projektordner und öffnen Sie das Projekt in *Android Studio*. Beim ersten Start synchronisiert *Android Studio* die Projektinhalte und installiert ggf. noch fehlende Abhängigkeiten. Im Startercode finden Sie ein rudimentäres Android-Projekt. Neben einem einfachen Layout (`activity_main.xml`) für die zentrale *Activity* der App ist dort auch eine `Task`-Klasse vorgegeben. Nutzen Sie letztere zur internen/logischen Repräsentation von Aufgaben innerhalb Ihrer Anwendung. Die Klasse verfügt  über Methoden zur (tiefen) Kopie einer Aufgabe und implementiert das `Comparable`-*Interface*, dass eine Sortierung mehrerer Aufgaben nach den oben genannten Kriterien erlaubt. Eine sehr einfache Möglicheit zur automatischen Sortierung von Objekten, deren Klassen *[Comparable](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)* implementieren, ist die Verwendung der [`Collections.sort`-Methode](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#sort-java.util.List-), die eine als Parameter übergebene List *in-place* sortiert und dabei die jeweilige Implementierung der `comparteTo`-Methode verwendet. **Sie müssen/sollten die `Task`-Klasse zur Lösung der Aufgabe nicht verändern.**

**Zwischenziel**: Das Starterpaket sollte sich auf dem Emulator (oder Ihrem physischen Smartphone) fehlerfrei starten lassen.

### Schritt 1: Interne Aufgaben-Liste

Im ersten Schritt kümmern Sie sich darum, dass Ihre App über eine Möglichkeit verfügt, die Liste der relevanten Tasks intern zu verwalten. Für diesen Teil der Aufgabe gibt es *mindestens* zwei mögliche Ansätze, die das Konzept der klaren Trennung zwischen UI und Datengrundlagen unterschiedlich stark umsetzen.
   * **Im einfachen Fall** verfügt die zentrale Activity der App über eine ArrayList, in der beliebig viele Aufgaben (`Task`-Objekte) gespeichert werden können. Ergänzen Sie zwei nicht-öffentliche Methoden über die neue Aufgaben (auf Basis eines übergebenen Beschreibungstexts) hinzugefügt und der Status (*offen* bzw. *erledigt*) bestehender Aufgaben angepasst werden können.
   * **Alternativ** können Sie für die interne Verwaltung der Aufgabenliste auch eine vollständig separate Komponente verwenden. Erstellen Sie dazu eine Klasse `TaskManager`, in der die interne Verwaltung der Aufgaben implementiert wird. Diese Klasse soll: a) eine Liste von Task-Objekten verwalten, b) eine öffentliche Methode zum Hinzufügen neuer Aufgaben auf Basis einer Aufgabenbeschreibung (`description`) anbieten, c) eine öffentliche Methode zum Umschalten des Status (*offen* zu *erledigt*) einer Aufgabe innerhalb der Liste anbieten und d) über eine öffentliche Methode eine sortierte Kopie der aktuell gespeicherten Aufgaben nach Außen geben.
   
Testen Sie die Funktion dieser Komponente, indem Sie die ArrayList oder den Manager in der `onCreate`-Methode Ihrer Activity initialisieren, programmatisch mit Inhalt befüllen und die gespeicherten Elemente per `Log.d`-Befehl ausgeben.

**Zwischenziel:** Die App hat eine Möglichkeit, eine Liste von Task-Objekten zu verwalten. Es existieren Methoden zum Hinzufügen von Tasks und zum Verändern des Status der Tasks. Beim Start der Anwendung werden die programmatisch hinzugefügten Listenelemente im Logcat-Fenster ausgegeben.

### Schritt 2: Ein Layout für Listeneinträge

Erstellen Sie ein eigenes Layout für die Listeneinträge. Erzeugen Sie dazu unter `layout` im `res`-Ordner eine neue XML-Datei. Hier definieren Sie die UI-Elemente (Views), die später Teil eines einzelnen Eintrags des `ListView`  sein sollen. Im einfachsten Fall sorgen Sie dafür, dass über zwei *TextViews*, die z.B. Kinder eines *Linear-Layouts* sein können, Platz für die Anzeige der Beschreibung und des Erstellungsdatums der einzelnen Aufgaben ist. Vergessen Sie nicht, alle Elemente, die später im Quellcode mit Inhalten gefüllt werden sollen, mit eindeutigen IDs auszustatten.

Befüllen Sie die Elemente zum Testen mit Texten. Überprüfen Sie in der Vorschau der *Split* oder *Design* Ansicht, ob die Layout-Bausteine korrekt angezeigt werden. (Die Texte können Sie danach wieder entfernen.)

**Zwischenziel:** Es gibt ein XML-Layout, das beschreibt wie ein Listeneintrag aussieht. In der Vorschau sind die Layout-Bausteine korrekt angeordnet und könnten einen Listeneintrag repräsentieren.

### Schritt 3: Ein Layout für erledigte Listeneinträge

Erstellen Sie ein weiteres Layout für erledigte Tasks. Kopieren und modifizieren Sie dafür das erstellte Layout für Listeneinträge. Im einfachsten Fall ändern Sie dazu einfach die Farben. Z.B. könnte eine angepasste Schriftfarbe und/oder eine veränderte Hintergrundfarbe über den Status des Tasks Aufschluss bieten.

Befüllen Sie die Elemente zum Testen mit Texten. Überprüfen Sie in der Vorschau der *Split* oder *Design* Ansicht, ob die Layout-Bausteine korrekt angezeigt werden. (Die Texte können Sie danach wieder entfernen.)

**Zwischenziel:** Es gibt ein XML-Layout für erledigte Tasks. In der Vorschau sind die Layout-Bausteine korrekt angeordnet und könnten einen abgeschlossenen Listeneintrag repräsentieren.

### Schritt 4: Adapter für Liste

Erstellen Sie eine Klasse für einen angepassten Adapter, der das vorhandene `ListView` (siehe `activity_main.xml`) mit den Einträgen der vorbereiteten ArrayList oder den vom `TaskManager` verwalteten Inhalten verbindet und dabei das Layout für die Darstellung der einzelnen Aufgaben innerhalb des `ListView` verwendet. Ihr Adapter erbt von `ArrayAdapter` (spezifizieren Sie den Typ der Objekte, die dieser Adapter verwendet über `extends ArrayAdapter<Task>`).

Innerhalb des Adapters werden die aktuell im UI darzustellenden Aufgaben in einer `ArrayList` verwaltet. Implementieren Sie eine öffentliche Methode, die es Ihnen erlaubt, diese durch eine neue Liste an Aufgaben zu ersetzen. Überschreiben Sie anschließende die beiden geerbten Methoden `getCount` (gibt die aktuelle Anzahl der Aufgaben in der `ArrayList` zurück) und `getView` (hier werden auf Anfrage des verknüpften `ListView` die UI-Elemente zur Darstellung der einzelnen Listenelemente erzeugt bzw. zurückgeben). In der `getView`-Methode erstellen Sie einen passenden View, indem Sie das vorbereitet Layout (siehe Schritt 2) über Angabe von `R.layout.<layoutname.xml>` *inflaten* (siehe Hinweise) und anschließend die dortigen `TextView`-Elemente mit den Werte eines der *Task*-Objekte befüllen. Über den Parameter `position` der `getView`-Methode teilt Ihnen das aufrufende `ListView` mit, welches Element der UI-Liste (an welcher Position) ein `View` angefordert wird.

Erzeugen Sie in der `onCreate`-Methode eine Instanz des Adapters. Erstellen Sie außerdem programmatisch eine `ArrayList`, die Sie programmatisch mit Inhalt befüllen. Übergeben Sie diese ArrayList an den Adapter. Loggen Sie die Rückgabe der `getCount`-Methode und den Inhalt der `ArrayList` im Adapter mit `Log.d`.

**Zwischenziel:** Es gibt eine Klasse für einen Adapter, der die interne Liste von Aufgaben mit dem UI-`ListView` verbindet. Dieser hat überschriebene Methoden `getCount` und `getView` und zusätzlich eine öffentliche Methode, um die Liste der Aufgaben im Adapter zu ersetzen. Beim Start der Anwendung werden die Anzahl der Elemente in der programmatisch erzeugten ArrayList und die Task Elemente, die programmatisch hinzugefügt wurden, geloggt.

### Schritt 5: Liste an Adapter "anschließen"

Referenzieren Sie in der *Activity* das `ListView`-UI-Element und erzeugen Sie eine Instanz des *Adapters*. Beim Erzeugen des Adapters müssen Sie diesem einen `TaskListAdapterListener` übergeben. Im einfachsten Fall implementieren Sie dieses Interface in Ihrer *Activity*. Verbinden Sie Adapter und ListView, indem Sie sie die `setAdapter`-Methode des `ListView`-Objekts verwenden. Testen Sie Ihre Anwendung:
  1. Fügen Sie neue Aufgaben zur ArrayList oder zum Manager hinzu
  2. Übergeben Sie dem Adapter die so veränderten Aufgabenliste
  3. Informieren Sie das angeschlossene `ListView`-Element über die Änderungen (Aufruf der Methode `notifyDataSetChanged` des Adapters, welche von der Superklasse geerbt wird).

**Zwischenziel:** Das ListView zeigt nun beim Start der App den Inhalt an, der der veränderten Aufgabenliste entspricht.

### Schritt 6: Hinzufügen von Aufgaben

Verbinden Sie jetzt die losen Enden Ihre Anwendung: Nutzen Sie die vorgegebenen UI-Elemente (EditText und Button), um neue Aufgaben durch die NutzerInnen erstellen zu lassen. Behalten Sie dabei die bereits erprobte Reihenfolge ein:
  1. Die durch die NutzerInnen eingegebene Beschreibung wird an den `TaskManger` übergeben, der eine neue Aufgabe erstellt
  2. Der Adapter erhält dann die veränderten Liste und informiert im Anschluss das `ListView` (wie in Schritt 5)
  3. Das ListView zeigt nun die neuen Elemente an

**Zwischenziel:** Durch Eingabe eines Texts und Drücken des Buttons kann ein neues Element zum `ListView` hinzugefügt werden. Dieses wird ganz oben in der Liste angezeigt.

### Schritt 7:
Auf dem `ListView` registrieren Sie nun einen Listener, der es Ihnen erlaubt lange Klicks auf den Einträgen abzufangen (`setOnItemLongClickListener`). Als Reaktion auf diese *Events* ändern Sie den Status des angeklickten Elements (`OPEN` -> `CLOSED`). Sorgen auch hier wieder mittels des Adapters dafür, dass das `ListView` die Veränderungen des Zustands korrekt anzeigt. Dazu muss bei Tasks deren State nicht mehr `OPEN`, sondern `CLOSED` ist (inneres Enum `TaskState` in `Task`-Klasse) ein anderes Layout *inflatet* werden. Darüber hinaus sollten diese Tasks an das Ende der Liste (ArrayList oder Manager) sortiert werden, so dass Sie auch im `ListView` am Ende angezeigt werden, dazu kann die `sort`-Methode der `Collections`-Klasse verwendet werden, der die ArrayList übergeben werden muss (da die Tasks das `Comparable`-Interface implementieren, die Sortierlogik ist bereits vorgegeben).

**Zwischenziel:** Tasks lassen sich nun durch einen langen Klick als abgeschlossen markieren. Dadurch ändert sich ihr Layout und ihre Position in der Liste.

## Mögliche Erweiterungen


### CardViews und Darstellung des Status einzelner Aufgaben

Aus der Vorlesung kennen Sie bereits das [Material Design](https://material.io/develop/android) als zentrale Designsprache der Android-Plattform. Für die Darstellung von Listen heterogener Daten, wie in unserem Beispiel die ToDo-Einträge, werden [CardViews](https://developer.android.com/jetpack/androidx/releases/cardview) empfohlen, die wir auch in unserem Lösungsvorschlag als Grundlage für die einzelnen Einträge der Liste verwenden. Dafür sind einige Änderungen am Code erforderlich, die auf der verlinkten Seite erklärt werden.

### Umgang mit der internen Aufgabenliste

Ein wesentliches Merkmale guter Software ist der sicherer Umgang mit der jeweiligen Datengrundlage. In unserer bisherigen Lösung missachten wir diesen Grundsatz, in dem wir dem UI-Adapter direkt Zugriff auf die interne Aufgabenliste ermöglichen. Dadurch nehmen wir in Kauf, dass die Inhalte dieser Liste nicht mehr nur innerhalb der Activity (bzw. dem `TaskManager`) manipuliert werden können. Der Adapter kann auf die referenzierten Task-Objekte der Liste zugreifen und direkt deren Zustand ändern. Eine einfache Lösung ist die Verwendung tiefer (*deep*) Kopien der Aufgabenliste. Statt dem Original erhält der Adapter nur eine Kopie der aktuellen Aufgabenliste. Jedes mal, wenn sich die interne Aufgabenliste ändert, übergeben wir eine neue Kopie an den Adapter. Sie können diese Mechanismus leicht in die bereits implementierte Lösung integrieren, in dem Sie an geeigneter Stelle die `copy`-Funktion der `Task`-Klasse verwenden.

### RecyclerView

*ListViews* und *ArrayAdapter* sind valide Möglichkeiten, um strukturierte Daten in Android-Apps anzuzeigen. Mittelfristig sollten für die meisten Anwendungsfälle aber [RecylerViews](https://developer.android.com/guide/topics/ui/layout/recyclerview) in Zusammenarbeit mit den entsprechenden [Adaptern](https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter) verwendet werden. Auch für diese App können die entsprechenden Stellen mit relativ wenig Aufwand durch diese Recycler-Komponenten ersetzt werden.


## Screenshots der Anwendung

[//]: <> (austauschen!)

|  Einfache Lösung   |   Lösung mit CardViews für die Listenelemente    |
|:------:|:-------:|
| ![Screenshots der ToDo-App](./docs/screenshot_possible_results_simple.png)   | ![Screenshots der ToDo-App](./docs/screenshot_possible_result.png)  |

# U04 | Eine einfache ToDo-Liste

## Aufgabenstellung

In dieser Aufgabe implementieren Sie eine einfache, nicht-persistente ToDo-Liste. NutzerInnen können neue Aufgaben zur Liste hinzufügen. Die neuesten Listenelemente werden ganz oben in der Liste angezeigt. Durch einen langen Klick auf einzelne Einträge können Aufgaben als *erledigt* markiert werden. Solche Aufgaben werden dann ans Ende der Liste verschoben und mit einer veränderten Darstellung angezeigt. **Zur Umsetzung der Aufgabe verwenden Sie einen `ArrayAdapter` und ein selbst-erstelltes Layout für die Listeneinträge.**

## Hinweise

**Achtung:** *Spätestens hier beginnen die Übungsaufgaben komplizierter zu werden. Nehmen Sie sich ausreichend Zeit und erarbeiten Sie Schritt für Schritt Ihre eigene Lösung.* Versuchen Sie die Zusammenhänge und Hintergründe zu verstehen. An schwierigen Stellen bieten wir Ihnen in der Aufgabenbeschreibung zwei unterschiedlich komplexe Lösungswege an. Wenn Sie sich für den "einfacheren" Weg entscheiden, können Sie später gerne den alternativen Vorschlag in Ihre funktionierende Lösung integrieren.

### Inflaten des Layouts

Um aus einer XML-Datei im Code ein konkretes View-Objekt zu erzeugen, können Sie sich an diesem Code orientieren. Als `resource` wird dabei die ID des jeweiligen Layouts (XML-Datei) übergeben.

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

**Zwischenziel**: Die im Starterpaket vorgegebene Version der Anwendung lässt sich fehlerfrei auf dem Emulator (oder Ihrem physischen Smartphone) starten.

### Schritt 1: Interne Aufgaben-Liste

Im ersten Schritt kümmern Sie sich darum, dass Ihre App über eine Möglichkeit verfügt, die Liste der relevanten Tasks intern zu verwalten. Für diesen Teil der Aufgabe gibt es *mindestens* zwei mögliche Ansätze, die das Konzept der klaren Trennung zwischen UI und Datengrundlagen unterschiedlich stark umsetzen:
   * **Im einfachen Fall** verfügt die zentrale Activity der App über eine ArrayList, in der beliebig viele Aufgaben (`Task`-Objekte) gespeichert werden können. Ergänzen Sie zwei nicht-öffentliche Methoden über die neue Aufgaben (auf Basis eines übergebenen Beschreibungstextes) hinzugefügt und der Status (*offen* bzw. *erledigt*) bestehender Aufgaben angepasst werden können.
   * **Alternativ** können Sie für die interne Verwaltung der Aufgabenliste auch eine vollständig separate Komponente verwenden. Erstellen Sie dazu eine Klasse `TaskManager`, in der die interne Verwaltung der Aufgaben implementiert wird. Diese Klasse soll: a) eine Liste von `Task` Objekten verwalten, b) eine öffentliche Methode zum Hinzufügen neuer Aufgaben auf Basis einer Aufgabenbeschreibung (`description`) anbieten, c) eine öffentliche Methode zum Umschalten des Status (*offen* zu *erledigt*) einer Aufgabe innerhalb der Liste anbieten und d) über eine öffentliche Methode eine sortierte Kopie der aktuell gespeicherten Aufgaben nach außen geben.
   
Testen Sie die Funktion dieser Komponente, indem Sie die ArrayList oder den Manager in der `onCreate`-Methode Ihrer Activity initialisieren, programmatisch mit Inhalt befüllen und die gespeicherten Elemente per `Log.d`-Befehl ausgeben.

**Zwischenziel:** Die App hat eine Möglichkeit, eine Liste von Task-Objekten zu verwalten. Es existieren Methoden zum Hinzufügen von Tasks und zum Verändern des Status der Tasks. Beim Start der Anwendung werden die programmatisch hinzugefügten Listenelemente im Logcat-Fenster ausgegeben.

### Schritt 2: Adapter für die Liste im UI

Erstellen Sie eine Klasse für einen angepassten Adapter, der das vorhandene `ListView` (siehe `activity_main.xml`) mit den Einträgen der vorbereiteten ArrayList oder den vom `TaskManager` verwalteten Inhalten verbinden soll. Ihr Adapter erbt von `ArrayAdapter` (spezifizieren Sie den Typ der Objekte, die dieser Adapter verwendet über `extends ArrayAdapter<Task>`).

Innerhalb des Adapters werden die aktuell im UI darzustellenden Aufgaben in einer `ArrayList` verwaltet. Implementieren Sie eine öffentliche Methode, die es Ihnen erlaubt, diese durch eine neue Liste an Aufgaben zu ersetzen.

Überschreiben Sie anschließend die beiden geerbten Methoden `getCount` (gibt die aktuelle Anzahl der Aufgaben in der `ArrayList` zurück) und `getView` (hier werden auf Anfrage des verknüpften `ListView` die UI-Elemente zur Darstellung der einzelnen Listenelemente erzeugt bzw. zurückgeben).

In der `getView`-Methode erstellen Sie einen View, indem Sie das von Android vorgegebene Layout für einfache Listeneinträge (`android.R.layout.simple_list_item_1`) *inflaten* (siehe Hinweise) und anschließend das dortige `TextView`-Element, mit der ID `android.R.id.text1`, mit der *Description* des Tasks an der Position `position` in der ArrayList befüllen. Ein View in einem Element erhält man, wenn die `getView` Methode des Layouts, das befüllt werden soll aufgerufen wird.

Erzeugen Sie in der `onCreate`-Methode eine Instanz des Adapters. Erstellen Sie außerdem programmatisch eine `ArrayList`, die Sie mit Inhalt befüllen. Übergeben Sie diese ArrayList an den Adapter. Loggen Sie die Rückgabe der `getCount`-Methode und den Inhalt der `ArrayList` im Adapter mit `Log.d`.

**Zwischenziel:** Es gibt eine Klasse für einen Adapter, der die interne Liste von Aufgaben mit dem UI-`ListView` verbindet. Dieser hat überschriebene Methoden `getCount` und `getView` und zusätzlich eine öffentliche Methode, um die Liste der Aufgaben im Adapter zu ersetzen. Beim Start der Anwendung werden die Anzahl der Elemente in der programmatisch erzeugten ArrayList und die Task Elemente, die programmatisch hinzugefügt wurden, geloggt.

### Schritt 3: Liste an Adapter "anschließen"

Referenzieren Sie in der *Activity* das `ListView`-UI-Element (aus `activity_main.xml`). Verbinden Sie Adapter und ListView, indem Sie sie die `setAdapter`-Methode des `ListView`-Objekts verwenden. Testen Sie Ihre Anwendung:
  1. Fügen Sie neue Aufgaben zur ArrayList oder zum Manager hinzu
  2. Übergeben Sie dem Adapter die so veränderte Aufgabenliste
  3. Informieren Sie das angeschlossene `ListView`-Element über die Änderungen (Aufruf der Methode `notifyDataSetChanged` des Adapters, welche von der Superklasse geerbt wird).

**Zwischenziel:** Das ListView zeigt nun beim Start der App einen Inhalt an, welcher der Aufgabenliste entspricht.

### Schritt 4: Hinzufügen von Aufgaben

Schreiben Sie eine neue Methode, die einen String als Parameter erwartet. Diese Methode soll einen neuen Task zur Liste im UI ergänzen. Fügen Sie dazu einen neuen Task zu Ihrer *ArrayList* oder Ihrem *Manager* hinzu. Ersetzen Sie dann die *ArrayList* im *Adapter* durch den Aufruf der entsprechenden Methode. Stellen Sie sicher, dass nach dem Ersetzen die `notifyDataSetChanged` Methode aufgerufen wird.

Testen Sie Ihre Methode, indem Sie ihr einige Strings übergeben. Entsprechende Tasks sollten dann im UI sichtbar werden.

**Zwischenziel:** Es sollte nun eine Methode geben, die bei Übergabe von Strings neue Elemente zur Liste im UI hinzufügt. Beim Start der Anwendung werden Tasks entsprechend der programmatisch erzeugten Strings angezeigt.

### Schritt 5: Eingabe durch NutzerInnen

Nutzen Sie die vorgegebenen UI-Elemente (*EditText* und *Button*), um neue Aufgaben durch die NutzerInnen erstellen zu lassen. Registrieren Sie dazu einen `OnClickListener` auf den Button. Wird der Button gedrückt soll der Inhalt des EditText-Views ausgelesen und geleert werden. Auf Basis des ausgelesenen Textes soll ein String erzeugt werden, der dann an die in Schritt 4 erstellte Methode übergeben werden kann. Behalten Sie dabei die bereits erprobte Reihenfolge ein:
  1. Die durch die NutzerInnen eingegebene Beschreibung wird an den `TaskManger` übergeben, der eine neue Aufgabe erstellt
  2. Der Adapter erhält dann die veränderten Liste und informiert im Anschluss das `ListView`
  3. Das ListView zeigt nun die neuen Elemente an

**Zwischenziel:** Durch Eingabe eines Texts und Drücken des Buttons kann ein neues Element zum `ListView` hinzugefügt werden. Dieses wird ganz oben in der Liste angezeigt.

### Schritt 6: Ein eigenes Layout für Listeneinträge

Erstellen Sie nun ein eigenes Layout für die Listeneinträge. Erzeugen Sie dazu unter `layout` im `res`-Ordner eine neue XML-Datei. Hier definieren Sie die UI-Elemente (Views), die später Teil eines einzelnen Eintrags des `ListView`  sein sollen. Im einfachsten Fall sorgen Sie dafür, dass in zwei *TextViews* Platz für die Anzeige der Beschreibung und des Erstellungsdatums der einzelnen Aufgaben ist. Vergessen Sie nicht, alle Elemente, die später im Quellcode mit Inhalten gefüllt werden sollen, mit eindeutigen IDs auszustatten.

Befüllen Sie die Elemente zum Testen mit Texten. Überprüfen Sie in der Vorschau der *Split* oder *Design* Ansicht, ob die Layout-Bausteine korrekt angezeigt werden. (Die Texte können Sie danach wieder entfernen.)

**Zwischenziel:** Es gibt ein XML-Layout, das beschreibt wie ein Listeneintrag aussieht. In der Vorschau sind die Layout-Bausteine korrekt angeordnet und könnten einen Listeneintrag repräsentieren.

### Schritt 7: Verwenden des eigenen Layouts

Nun wollen wir erreichen, dass die Listeneinträge, die wir über den Button hinzufügen unser eigenes Layout verwenden. Dazu ersetzen wir bei der Erzeugung des Adapters und in der `getView` Methode des Adapters die Referenzen auf das `simple_list_item_1` mit der Referenz auf unser Layout (`R.layout.<layout_name.xml>`). Um das Layout zu befüllen gehen wir ähnlich vor wie bei dem *TextView* des *simple_list_items*. Referenzieren Sie den TextView für die Taskbeschreibung und befüllen Sie ihn mit der Description des Tasks. Referenzieren Sie dann den TextView für das Erstellungsdatum und befüllen Sie es mit einem entsprechenden String. 

Über die `getCreationDate` Methode des Tasks erhält man ein `LocalDateTime` Objekt, dass den Erstellungszeitpunkt repräsentiert. Mit Hilfe eines Objekts der Klasse `DateTimeFormatter` können Sie daraus einen String in einem von Ihnen gewünschten Datums- oder Zeitformat erhalten. Erzeugen Sie dazu eine Instanz der `DateTimeFormatter` Klasse über den Aufruf der *statischen* Methode `ofPattern` dieser Klasse. Dieser Methode können Sie einen String mit Ihrem gewünschten Format (z.B. "hh:mm" oder "dd.MM.yyyy") übergeben. Dann kann von diesem Objekt die `format` Methode aufgerufen werden, der ein `LocalDateTime` Objekt übergeben werden muss und die einen String im angegebenen Format zurückgibt.

**Zwischenziel:** Beim Klick auf den Button wird ein neuer Task zur Liste hinzugefügt. Die Listenelemente nutzen unser in XML definiertes Layout und zeigen Beschreibung und Erstellungszeitpunkt des Tasks korrekt an.

### Schritt 8: LongClickListener

Auf dem `ListView` registrieren Sie nun einen Listener, der es Ihnen erlaubt lange Klicks auf den Einträgen abzufangen (`setOnItemLongClickListener`). Als Reaktion auf diese *Events* ändern Sie den Status des angeklickten Elements (`OPEN` -> `CLOSED`). Die Position des angeklickten Elements wird der `onItemLongClick` Methode des Listeners im Parameter `i` übergeben. Sorgen auch hier wieder mittels des Adapters dafür, dass das `ListView` die Veränderungen des Zustands korrekt anzeigt. Diese Tasks sollten an das Ende der Liste (ArrayList oder Manager) sortiert werden, so dass Sie auch im `ListView` am Ende angezeigt werden, dazu kann die *statische* `sort`-Methode der `Collections`-Klasse verwendet werden, der die ArrayList übergeben werden muss (funktioniert, da die Tasks das `Comparable`-Interface implementieren; die Sortierlogik ist bereits vorgegeben).

**Zwischenziel:** Tasks lassen sich nun durch einen langen Klick als abgeschlossen markieren. Dadurch ändert sich ihre Position im ListView.

### Schritt 9: Ein Layout für erledigte Listeneinträge

Erstellen Sie ein weiteres Layout für erledigte Tasks. Kopieren und modifizieren Sie dafür das erstellte Layout für Listeneinträge. Im einfachsten Fall ändern Sie nur die Farben. Z.B. könnte eine angepasste Schriftfarbe und/oder eine veränderte Hintergrundfarbe über den Status des Tasks Aufschluss bieten.

Befüllen Sie die Elemente zum Testen mit Texten. Überprüfen Sie in der Vorschau der *Split* oder *Design* Ansicht, ob die Layout-Bausteine korrekt angezeigt werden. (Die Texte können Sie danach wieder entfernen.)

**Zwischenziel:** Es gibt ein XML-Layout für erledigte Tasks. In der Vorschau sind die Layout-Bausteine korrekt angeordnet und könnten einen abgeschlossenen Listeneintrag repräsentieren.

### Schrit 10: Verwendung des angepassten Layouts für abgeschlossene Tasks

Tasks deren Zustand `CLOSED` ist sollen nun das angepasste Layout verwenden. Modifizieren Sie dazu die `getView` Methode des Adapters. Fragen Sie ab, ob der Zustand des Tasks an der Position `position` schon `CLOSED` ist. Falls ja *inflaten* Sie ein das modifizierte Layout anstatt des Standard Layouts für Tasks.

**Zwischenziel:** Beim langen Klick auf ein Task Element wird dieses als abgeschlossen markiert. Dadurch verschiebt es sich im UI an das Ende der Liste und das Aussehen des Tasks im UI verändert sich.

## Mögliche Erweiterungen


### CardViews und Darstellung des Status einzelner Aufgaben

Aus der Vorlesung kennen Sie bereits das [Material Design](https://material.io/develop/android) als zentrale Designsprache der Android-Plattform. Für die Darstellung von Listen heterogener Daten, wie in unserem Beispiel die ToDo-Einträge, werden [CardViews](https://developer.android.com/jetpack/androidx/releases/cardview) empfohlen, die wir auch in unserem Lösungsvorschlag als Grundlage für die einzelnen Einträge der Liste verwenden. Dafür sind einige Änderungen am Code erforderlich, die auf der verlinkten Seite erklärt werden.

### Umgang mit der internen Aufgabenliste

Ein wesentliches Merkmale guter Software ist der sicherer Umgang mit der jeweiligen Datengrundlage. In unserer bisherigen Lösung missachten wir diesen Grundsatz, in dem wir dem UI-Adapter direkt Zugriff auf die interne Aufgabenliste ermöglichen. Dadurch nehmen wir in Kauf, dass die Inhalte dieser Liste nicht mehr nur innerhalb der Activity (bzw. dem `TaskManager`) manipuliert werden können. Der Adapter kann auf die referenzierten Task-Objekte der Liste zugreifen und direkt deren Zustand ändern. Eine einfache Lösung ist die Verwendung tiefer (*deep*) Kopien der Aufgabenliste. Statt dem Original erhält der Adapter nur eine Kopie der aktuellen Aufgabenliste. Jedes mal, wenn sich die interne Aufgabenliste ändert, übergeben wir eine neue Kopie an den Adapter. Sie können diese Mechanismus leicht in die bereits implementierte Lösung integrieren, in dem Sie an geeigneter Stelle die `copy`-Funktion der `Task`-Klasse verwenden.

### RecyclerView

*ListViews* und *ArrayAdapter* sind valide Möglichkeiten, um strukturierte Daten in Android-Apps anzuzeigen. Mittelfristig sollten für die meisten Anwendungsfälle aber [RecylerViews](https://developer.android.com/guide/topics/ui/layout/recyclerview) in Zusammenarbeit mit den entsprechenden [Adaptern](https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter) verwendet werden. Auch für diese App können die entsprechenden Stellen mit relativ wenig Aufwand durch diese Recycler-Komponenten ersetzt werden.

### Unterschiedliche Datumsformate

In vielen Fällen ist es sinnvoll mehrere Formate für das Datum zu verwenden. Wenn Todo Einträge erst kürzlich erstellt wurden, ist eine Anzeige der Stunde und Minute der Erstellung wahrscheinlich am praktischsten. Bei Tasks die älter sind, könnten dagegen Tag, Monat und Jahr interessanter sein. Bei gerade eben erst erstellten Einträgen könnten sogar die vergangenen Sekunden, die relevanteste Zeiteinheit sein. Unsere App könnte also dahingehend erweitert werden, dass abgefragt wird, wie lange ein Erstellungszeitpunkt her ist und dementsprechend unterschiedliche `DateTimeFormatter` verwendet werden. Den Zeitabstand zwischen zwei `LocalDateTime` Objekten erhält man durch Aufruf der `between` Methode der Klasse `Duration`, diese liefert bei Übergabe von zwei `LocalDateTime` Objekten ein `Duration` Objekt zurück. Den Zeitabstand kann man dann in verschiedenen Einheiten (ms, s, min, h, ...) über korrespondierende [Methoden der Duration Klasse](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html) abfragen.


## Screenshots der Anwendung

[//]: <> (austauschen!)

|  Einfache Lösung   |   Lösung mit CardViews für die Listenelemente    |
|:------:|:-------:|
| ![Screenshots der ToDo-App](./docs/screenshot_possible_results_simple.png)   | ![Screenshots der ToDo-App](./docs/screenshot_possible_result.png)  |

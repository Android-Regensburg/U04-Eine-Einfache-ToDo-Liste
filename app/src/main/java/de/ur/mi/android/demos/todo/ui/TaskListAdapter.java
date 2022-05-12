package de.ur.mi.android.demos.todo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.ur.mi.android.demos.todo.R;
import de.ur.mi.android.demos.todo.tasks.Task;

/**
 * Dieser Adapter verbindet eine ArrayList von Task-Objekten mit einem ListView im UserInterface. Der
 * Adapter stellt dem ListView die notwendigen Views zu Darstellung der einzelnen Aufgaben bereit. Dabei
 * unterscheidet der Adpater zwischen offen und bereits erledigten Aufgaben und gibt je nach Zustand der
 * Aufgabe andere Views an das ListView weiter. Dadurch werden die beiden "Typen" von Aufgaben im
 * User Interface auf unterschiedliche Art und Weise dargestellt.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {

    /**
     * Achtung: Dieser Adapter nutzt die gleiche ArrayListe, die in der MainActivity zur Verwaltung
     * der Aufgaben verwendet wird. Das ist kein gutes Software Design, da nun zwei Stellen unserer Anwendung
     * (Activity und Adapter) unabhängig von einander die selbe Datenstruktur (mit für unserer App wichtigen
     * Inhalten) verändern können. Idealerweise würde der Adapter über eine Kopie der Liste verfügen,
     * die bei Änderungen an der Datenbasis (in der Activity) immer mit einer aktualisierten Kopie überschrieben würde.
     */

    // Liste der Aufgaben, die aktuell vom Adapter verwaltet werden
    private ArrayList<Task> tasks;

    public TaskListAdapter(@NonNull Context context) {
        // Aufruf des Super-Konstruktors für den Adapter mit Übergabe eines Standardlayoputs für die Einträge (wir werden später unsere eigenen Layouts verwenden)
        super(context, android.R.layout.simple_list_item_1);
    }

    /**
     * Aktualisierte die Liste der Tasks, die der Adapter verwaltet, in dem die entsprechende Instanzvariable überschriebn wird
     *
     * @param tasks Neue Liste mit Tasks für den Adapter
     */
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        // Wenn sich die Datenbasis im Adapter ändert, informiert dieser den angeschlossenen ListView über die Änderungen
        // Dadurch wird automatisch der Prozess gestartet, in dem das ListView einen neuen Satz an Views zur Darstellung
        // der jetzt aktualisierten Inhalten vom Adapter anfordert.
        this.notifyDataSetChanged();
    }

    /**
     * Ausgelagerte Funktion zum Erstellen eines neuen Views auf Basis einer XML-Datei
     *
     * @param resource Verweis auf die XML-Datei, die als Basis für das neuen View verwendet werden soll
     * @param parent   Elternelement im UI, in das der neue View eingefügt werden soll
     * @return Das erstellte View
     */
    private View inflateViewTask(int resource, ViewGroup parent) {
        // Für das Erstellen des Views aus der XML-Datei ist ein Inflater notwendig, den wir über den "Context" der App beziehen können
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(resource, parent, false);
    }

    /**
     * Wird von dem ListView aufgerufen, wenn ein neuer View zur Darstellung im UI benötigt wird
     *
     * @param position    Die Position des ListViews, für die ein View mit Daten benmötigt wird
     * @param convertView Ein möglicherweise schon vorhandener View, der im Vorfeld für die Darstellung dieser Listenposition verwendet wurde
     * @param parent      Das Elternelement im UI, zu dem der neue View hinzugefügt werden kann, z.B. das ListView selber
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Wir identifzieren über den Positionsparameter, für welchen Datensatz wird einen View erzeugen sollen
        Task task = tasks.get(position);
        View viewForTask = convertView;
        // Wenn es noch keinen View für diese Position gibt, erstellen wir einen neuen auf Basis der XML-Datei für eine "offene" Aufgabe
        if (viewForTask == null) {
            viewForTask = inflateViewTask(R.layout.task_list_item, parent);
        }
        // Wenn es eine Aufgabe gibt, die angezeigt werden soll ...
        if (task != null) {
            // ... prüfen wir, ob der aktuelle Typ von View passt, um den aktuellen Zustand der Aufgabe darzustellen
            if (task.isClosed() && viewForTask.getId() == R.id.list_item_default) {
                // ... falls nicht, erstellen wir einen passenden View
                viewForTask = inflateViewTask(R.layout.task_list_item_done, parent);
            } else if (!task.isClosed() && viewForTask.getId() == R.id.list_item_done) {
                // ... falls nicht, erstellen wir einen passenden View
                viewForTask = inflateViewTask(R.layout.task_list_item, parent);
            }
            // Wenn wir sicher sind, dass Zustand der Aufgabe und Typ von View "überein stimmten", schreiben wir die eigentlichen Eigenschaften (Beschreibung und Datum) der Aufgabe in den View
            bindTaskToView(task, viewForTask);
        }
        return viewForTask;
    }

    /**
     * Überträgt die Eigenschaften der übergebenen Aufgabe in den View
     *
     * @param task Die Aufgabem deren Inhalte im View eingetragen werden sollen
     * @param view Ein View zur Darstellung der Aufgabe (muss auf Basis der XML-Dateien task_list_item.xml bzw. task_list_item_done.xml erstellt worden sein)
     */
    private void bindTaskToView(Task task, View view) {
        // Referenzieren der einzelnen TextViews im übergebenen View
        TextView taskDescription = view.findViewById(R.id.list_item_description);
        TextView taskCreationDate = view.findViewById(R.id.list_item_creationDate);
        // Auslesen der Task-Eigenschaften und übertragen in die jeweiligen TextViews
        taskDescription.setText(task.getDescription());
        taskCreationDate.setText(getFormattedDateForUI(task.getCreationDate()));
    }

    /**
     * Wandelt das übergeben Datum in einen sauber formatiertern String zur Darstellung im UI um.
     * Datumsangaben werden in der Regel im Format Tag. Monat zurückgegeben. Liegt das übergebene Datum
     * weniger als 24h zurück, wird statt dessen das Format  Stunden:Minuten verwendet.
     *
     * @param date Datum, das formatiert werden soll
     * @return Formatierte Stringrepräsentation des übergebenen Datums
     */
    private String getFormattedDateForUI(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date now = new Date();
        long timeDifferenceInMilliseconds = Math.abs(now.getTime() - date.getTime());
        long timeDifferenceInDays = TimeUnit.DAYS.convert(timeDifferenceInMilliseconds, TimeUnit.MILLISECONDS);
        if (timeDifferenceInDays > 0) {
            sdf = new SimpleDateFormat("dd. MMMM", Locale.getDefault());
        }
        return sdf.format(date);
    }

    /**
     * Wird vom ListView aufgerufen, um zu erfahren, wie viele Datensätze im Adapter verwaltet werden
     *
     * @return Anazhl der Einträge/Datensätze im Adapter (hier: Anzahl der Tasks in der ArrayList)
     */
    @Override
    public int getCount() {
        if (tasks == null) {
            return 0;
        }
        return tasks.size();
    }
}

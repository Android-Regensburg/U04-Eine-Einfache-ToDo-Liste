package de.ur.mi.android.demos.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.ui.TaskListAdapter;


/**
 * Diese App erlaubt den Nutzer*innen das Zusammenstellen einer einfachen Aufgabenliste. Dazu können
 * neue Aufgaben erstellt und der Zustand (offen/geschlossen) existierende Aufgaben umgeschaltet werden.
 * In dieser Version der App werden die Einträge der Liste nicht gespeichert, d.h. nach Beenden der
 * App gehen alle erstellten Aufgaben verloren. Diese Variante stellt eine einfache Lösung für die
 * ursprüngliche Aufgabenstellung dar.
 *
 * Diese Activity initialisiert die notwendigen Komponenten des User Interface (Eingabefelder und
 * ListView), fängt Eingabe-Events auf den Elementen und verarbeitet diese. In der Activity wird
 * auch die ArrayList mit Task-Objekten verwaltet, die den inhaltlichen Kern der Anwendung ausmacht.
 */

public class MainActivity extends Activity {

    // Liste mit den aktuelle in der App verwalteten Aufgaben
    private ArrayList<Task> tasks;
    // Adapter zur Verbindung der Datenstruktur (tasks) mit dem ListView, der die Inhalte im UI anzeigt
    private TaskListAdapter taskListAdapter;
    // Eingabefeld für den Beschreibungstext neuer Aufgaben
    private EditText taskDescriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTasks();
        initUI();
    }

    /**
     * Initialisiert eine neue ArrayList zum Speichern der Aufgaben
     */
    private void initTasks() {
        tasks = new ArrayList<>();
    }

    /**
     * Initialisiert (in weiteren Methoden) das User Interface der App
     */
    private void initUI() {
        setContentView(R.layout.activity_main);
        initListView();
        initInputElements();
    }

    /**
     * Initialisiert Adapter und Liste sowie die Interaktionsmöglichkeiten auf der Liste
     */
    private void initListView() {
        // Hier erstellen wir eine Instanz unseres angepassten ArrayAdapters
        taskListAdapter = new TaskListAdapter(this);
        ListView taskList = findViewById(R.id.task_list);
        // ListView und Adapter werden verbunden
        taskList.setAdapter(taskListAdapter);
        // Hier registrieren wir einen Listener, der informiert wird, wenn Einträge des ListViews länger angeklickt werden
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // Diese Callback-Funktion wird immer aufgerufen, wenn ein Listeneintrag länger angeklickt wird
            // Über den Parameter position erhalten wir die Information, welche Element (Position innerhalb des ListViews) angeklickt wurde
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Die Verarbeitung des Events (Umschalten des Zustands des angeklickten "Tasks") erfolgt in einer separaten Methode
                toggleTaskAtPosition(position);
                return true;
            }
        });
    }

    /**
     * Initialisiert die Eingabeelemente und die Interaktionsmöglichkeiten auf diesen
     */
    private void initInputElements() {
        taskDescriptionInput = findViewById(R.id.input_text);
        Button inputButton = findViewById(R.id.input_button);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Wenn der Button zum Hinzufügen einer neuen Aufgabe angeklickt wird, lesen wir den aktuellen Inhalt des Textfelds aus ...
                String currentInput = taskDescriptionInput.getText().toString();
                // ... und versuchen, aus diesem Inhalt einen neuen Task zu erstellen
                onUserInputClicked(currentInput);
            }
        });
    }

    /**
     * Fügt einen neuen Task zur ArrayList hinzu und aktualisiert im Anschluss die Inhalte des Adapters
     *
     * @param description Beschreibung des neuen Tasks
     */
    private void addTask(String description) {
        Task taskToAdd = new Task(description);
        tasks.add(taskToAdd);
        // Nach der Änderung an der Datenbasis wird der Adapter aktualisiert
        updateTasksInAdapter();
    }

    /**
     * Schaltet den Zustand des Tasks an der angegebene Position der List um: Ist der Task aktuell offen, wird
     * er nach dem Aufruf dieser Methode als geschlossen markiert sein
     *
     * @param position Listenposition des Tasks, dessen Zustand geändert werden soll
     */
    private void toggleTaskAtPosition(int position) {
        Task taskToToggle = tasks.get(position);
        // Null-Check, um zu verhindern, dass unsere App abstürzt, wenn eine ungültige Position übergeben wurde
        if (taskToToggle != null) {
            if (taskToToggle.isClosed()) {
                taskToToggle.markAsOpen();
            } else {
                taskToToggle.markAsClosed();
            }
        }
        // Nach der Änderung an der Datenbasis wird der Adapter aktualisiert
        updateTasksInAdapter();
    }

    /**
     * Sortiert die aktuelle Liste der Aufgaben und übergibt diese im Anschluss an den Adapter
     */
    private void updateTasksInAdapter() {
        // Zum Sortieren verwenden wir die Java-Collections-Klasse.
        // Das funktioniert, weil die Objekte in der ArrayList, bzw. deren Klassen, das Comparable-Interface implementieren
        Collections.sort(tasks);
        taskListAdapter.setTasks(tasks);
    }

    /**
     * Prüft, ob sich der übergebene String als Beschreibung einer neuen Aufgabe eignet und, falls passend,
     * erstellt auf dessen Basis eine neue Aufgabe in der ArrayList (dadurch wird auch der Adapter
     * aktualsiert). Im Anschluss wird das UI zurückgesetzt, um die EIngabe der nächsten Aufgabe zu erleichtern.
     *
     * @param input
     */
    private void onUserInputClicked(String input) {
        if (input.length() > 0) {
            addTask(input);
            taskDescriptionInput.setText(""); // Löscht den aktuellen Inhalt aus dem Eingabefeld
            taskDescriptionInput.requestFocus(); // Versuchst, das Eingabefeld zu fokussieren, so dass direkt die nächste Aufabe eingeben werden kann
        }
    }
}
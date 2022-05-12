package de.ur.mi.android.demos.todo.tasks;

import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Repräsentiert eine Aufgabe auf der ToDo-liste.
 *
 * Aufgaben verfügen über eine eindeutige ID, eine textuelle Beschreibung und ein Erstellungs-
 * datum. Diese Eigenschaften sind nach dem Erzeugen einer Aufgabe nicht veränderbar. Zusätzlich
 * wird für jede Aufgabe festgehalten, ob diese offen oder erledigt ist. Der Zustand kann über
 * öffentliche Methoden geändert werden.
 *
 * Kopien
 * Über die copy-Methode können tiefe (deep) Kopien eines Task-Objekts erstellt werden.
 *
 * Sortierung
 * Die Task-Klasse implementiert das Comparable-Interface das die Sortierung von Task-Objekten
 * ermöglicht. Offene Aufgaben werden vor geschlossenen Aufgaben einsportiert. Aufgaben mit
 * gleichem Status werden nach dem Erstellungsdatum sortiert.
 */
public class Task implements Comparable<Task> {

    private final UUID id;
    private final String description;
    private final LocalDateTime createdAt;
    private TaskState currentState;

    public Task(String description) {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now(TimeZone.getDefault().toZoneId());
        this.currentState = TaskState.OPEN;
        this.description = description;
    }

    private Task(String description, UUID id, LocalDateTime createdAt, TaskState currentState) {
        this.id = id;
        this.createdAt = createdAt;
        this.currentState = currentState;
        this.description = description;
    }

    public String getID() {
        return id.toString();
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return getCreationDateCopy();
    }

    public boolean isClosed() {
        return currentState == TaskState.CLOSED;
    }

    public void markAsOpen() {
        currentState = TaskState.OPEN;
    }

    public void markAsClosed() {
        currentState = TaskState.CLOSED;
    }

    public Task copy() {
        LocalDateTime creationDateFromOriginal = getCreationDateCopy();
        return new Task(description, id, creationDateFromOriginal, currentState);
    }

    private LocalDateTime getCreationDateCopy() {
        return LocalDateTime.from(createdAt);
    }

    @Override
    public int compareTo(Task otherTask) {
        if (this.isClosed() && !otherTask.isClosed()) {
            // Diese Aufgabe wird, weil bereits erledigt, nach der anderen sortiert
            return 1;
        }
        if (!this.isClosed() && otherTask.isClosed()) {
            // Diese Aufgabe wird, weil noch nicht erledigt, vor der anderen sortiert
            return -1;
        }
        // Die beiden Aufgaben werden auf Basis des Erstellungsdatums sortiert (neuere vor älteren)
        return -this.createdAt.compareTo(otherTask.createdAt);
    }

    public enum TaskState {
        OPEN,
        CLOSED
    }

}

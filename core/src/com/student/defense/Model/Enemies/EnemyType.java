package com.student.defense.Model.Enemies;

public enum EnemyType {

    PAPERWORK("Paperwork"),
    SADNESS("Sadness"),
    SHARED_PROJECT("Shared Project"),
    EXAM("Exam");

    public final String typeName;

    EnemyType(String typeName) {
        this.typeName = typeName;
    }
}

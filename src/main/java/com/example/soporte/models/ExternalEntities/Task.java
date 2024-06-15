package com.example.soporte.models.ExternalEntities;

public class Task{
    private long id;

    public Task(){}

    public Task(long id){
        this.id = id;
    }

    public Task(long id, String title){
        this.id = id;
    }

    public long getId(){
        return id;
    }
}

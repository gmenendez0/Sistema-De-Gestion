package com.example.soporte.models.ExternalEntities;

public class Task{
    private long id;

    private String title;

    public Task(){}

    public Task(long id){
        this.id = id;
        this.title = null;
    }


    public Task(long id, String title){
        this.id = id;
        this.title = title;
    }

    public long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}

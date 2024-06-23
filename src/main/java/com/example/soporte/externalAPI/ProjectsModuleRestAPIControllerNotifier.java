package com.example.soporte.externalAPI;

import com.example.soporte.services.notifications.Notifier;

public class ProjectsModuleRestAPIControllerNotifier extends RestAPIController implements Notifier {
    public ProjectsModuleRestAPIControllerNotifier(String url){
        super(url);
    }

    @Override
    protected String apiName(){
        return "ProjectsAPI";
    }

    public <T> void sendNotification(T notification, Class<T> aClass){
        postObject(notification, aClass);
    }
}

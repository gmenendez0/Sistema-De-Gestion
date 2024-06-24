package com.example.soporte.externalAPI;

import com.example.soporte.services.notification.Notifier;

public class ProjectsModuleRestAPIControllerNotifier extends RestAPIController implements Notifier {
    public ProjectsModuleRestAPIControllerNotifier(String url){
        super(url);
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String apiName(){
        return "ProjectsAPI";
    }

    /**
     * @inheritDoc
     */
    public <T> void sendNotification(T notification, Class<T> aClass){
        patchObject(notification, aClass);
    }
}

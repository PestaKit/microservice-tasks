package io.pestaKit.tasks.api.spec.helpers;

import io.pestaKit.tasks.api.*;
import java.io.IOException;
import java.util.Properties;
/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private OneStageApi oneStageApi = new OneStageApi();
    private OneTaskApi oneTaskApi   = new OneTaskApi();
    private StagesApi stagesApi     = new StagesApi();
    private TasksApi tasksApi       = new TasksApi();

    private String lastTaskName;
    private String lastStageName;

    public int lastStatusCode;

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("io.pestaKit.tasks.server.url");

    }

    public OneStageApi getOneStageApi() {
        return oneStageApi;
    }

    public OneTaskApi getOneTaskApi() {
        return oneTaskApi;
    }

    public TasksApi getTasksApi() {
        return tasksApi;
    }

    public StagesApi getStagesApi() {
        return stagesApi;
    }

    public void setLastStageName(String lastStageName) {
        this.lastStageName = lastStageName;
    }

    public void setLastTaskName(String lastTaskName) {
        this.lastTaskName = lastTaskName;
    }

    public String getLastStageName() {
        return lastStageName;
    }

    public String getLastTaskName() {
        return lastTaskName;
    }
}

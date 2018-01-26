package io.pestaKit.tasks.api.spec.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestaKit.tasks.ApiException;
import io.pestaKit.tasks.ApiResponse;
import io.pestaKit.tasks.api.OneTaskApi;
import io.pestaKit.tasks.api.TasksApi;
import io.pestaKit.tasks.api.dto.Task;
import io.pestaKit.tasks.api.dto.TaskCreate;
import io.pestaKit.tasks.api.spec.helpers.Environment;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class TasksSteps {

    private Environment environment;
    private TasksApi api;
    private OneTaskApi taskApi;

    private TaskCreate task1, task2, task3;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public TasksSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getTasksApi();
    }

    @Given("^I have 3 tasks payload$")
    public void i_have_3_task_payload() throws Throwable {
        task1 = new TaskCreate();
        task1.setName("task T1");

        task2 = new TaskCreate();
        task2.setName("task T2");

        task3 = new TaskCreate();
        task3.setName("task T3");

        taskApi = environment.getOneTaskApi();
        taskApi.createTaskWithHttpInfo(task1);
        taskApi.createTaskWithHttpInfo(task2);
        taskApi.createTaskWithHttpInfo(task3);
    }
    @When("^I GET the list of Tasks")
    public void i_get_the_list_of_tasks() throws Throwable {


        try {
            lastApiResponse = api.tasksGetWithHttpInfo();
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }

    }
    @Then("^I receive a (\\d+) status code for getting list of Tasks$")
    public void i_receive_a_status_code_for_getting_list_tasks(int arg1) throws Throwable {
        assertEquals(200, lastStatusCode);
    }
    @And("^the list contains the good tasks$")
    public void the_list_contains_good_tasks() throws Throwable {
        List<Task> tasksList = (List<Task>) lastApiResponse.getData();

        assertEquals("task T1", tasksList.get(1).getName());
        assertEquals("task T2", tasksList.get(2).getName());
        assertEquals("task T3", tasksList.get(3).getName());
    }

}

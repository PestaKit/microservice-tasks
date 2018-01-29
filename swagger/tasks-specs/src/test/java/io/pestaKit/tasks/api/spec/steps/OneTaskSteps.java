package io.pestaKit.tasks.api.spec.steps;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestaKit.tasks.ApiException;
import io.pestaKit.tasks.ApiResponse;
import io.pestaKit.tasks.api.OneTaskApi;
import io.pestaKit.tasks.api.TasksApi;
import io.pestaKit.tasks.api.dto.Stage;
import io.pestaKit.tasks.api.dto.Task;
import io.pestaKit.tasks.api.dto.TaskCreate;
import io.pestaKit.tasks.api.spec.helpers.Environment;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class OneTaskSteps {

    private Environment environment;
    private OneTaskApi api;
    private TasksApi tasksApi;

    TaskCreate taskOne, taskTwo;
    Stage stage;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;

    private boolean lastApiCallThrewException;
    private long lastNumTask, numTaskSecondTask;

    private final String NAMETASKONE  = "task one";
    private final String NAMETASKTWO  = "task two";
    private final String NAMESTAGEONE = "stage one";
    private final String NAMESTAGETWO = "stage two";

    public OneTaskSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getOneTaskApi();
        this.tasksApi = environment.getTasksApi();
    }

    @Given("^there is a Tasks server$")
    public void there_is_a_Tasks_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a Task$")
    public void i_have_a_task() throws Throwable {
        taskOne = new io.pestaKit.tasks.api.dto.TaskCreate();
        taskOne.setName(NAMETASKONE);

        postTask(taskOne);

        lastNumTask = tasksApi.tasksGet().get(0).getNumber().intValue();
    }

    @Given("^I have a Task payload$")
    public void i_have_a_task_payload() throws Throwable {
        taskTwo = new io.pestaKit.tasks.api.dto.TaskCreate();
        taskTwo.setName(NAMETASKTWO);
    }
    @Given("^I have a Stage Payload$")
    public void i_have_a_stage_payload() throws Throwable {
        stage = new Stage();
        stage.setName(NAMESTAGEONE);
        stage.setPosition(BigDecimal.valueOf(0));
        stage.setValue("valueOfStageOne");

    }

    @When("^I POST a correct Task payload to the /tasks endpoint$")
    public void i_POST_a_correct_task_payload_to_the_tasks_endpoint() throws Throwable {
        postTask(taskTwo);
        numTaskSecondTask = 2;
    }

    @When("^I POST a correct Task payload but with already used name to the /tasks endpoint$")
    public void i_POST_a_correct_task_payload_but_with_already_used_name_to_the_tasks_endpoint() throws Throwable {
        postTask(taskOne);
    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.lastStatusCode);
    }
    @When("^I GET an existing Task with  /tasks/numTask endpoint$")
    public void i_get_an_existing_task_with_tasks_numTask_endpoint() throws Throwable {
        try {
            lastApiResponse = api.tasksNumTaskGetWithHttpInfo(lastNumTask);
            lastApiCallThrewException = false;
            lastApiException = null;
            environment.lastStatusCode = lastApiResponse.getStatusCode();

        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            environment.lastStatusCode = lastApiException.getCode();
        }
    }
    @When("^I GET an non-existent Task with  /tasks/numTask endpoint$")
    public void i_get_an_non_existent_task_with_tasks_numTask_endpoint() throws Throwable {
       lastNumTask = lastNumTask+100;
       i_get_an_existing_task_with_tasks_numTask_endpoint();
       lastNumTask = 1;
    }

    @When("^I DELETE an existent Task in /tasks/numTask endpoint$")
    public void i_delete_an_existent_task_in_tasks_numTask_endpoint() throws Throwable {
        try {
            lastApiResponse = api.tasksNumTaskDeleteWithHttpInfo(lastNumTask);
            lastApiCallThrewException = false;
            lastApiException = null;
            environment.lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            environment.lastStatusCode = lastApiException.getCode();
        }
    }

    @When("^I DELETE a non-existent Task in /tasks/numTask endpoint$")
    public void i_delete_a_non_existent_task_in_tasks_numTask_endpoint() throws Throwable {
        lastNumTask = 10;
        i_delete_an_existent_task_in_tasks_numTask_endpoint();
        lastNumTask = 1;
    }

    @When("^I GET a deleted Task with  /tasks/numTask endpoint$")
    public void i_get_a_deleted_task_with_tasks_numTask_endpoint() throws Throwable {
        lastNumTask = numTaskSecondTask;
        i_get_an_existing_task_with_tasks_numTask_endpoint();
    }

    @After
    public void deleteBD() throws ApiException {
        List<Task> tasks = tasksApi.tasksGet();
        for(int i = 0; i < tasks.size(); i++)
        {
            api.tasksNumTaskDelete(tasks.get(i).getNumber().longValue());
        }
    }

    private void postTask(TaskCreate currTask)
    {
        try {
            lastApiResponse = api.createTaskWithHttpInfo(currTask);
            lastApiCallThrewException = false;
            lastApiException = null;
            environment.lastStatusCode = lastApiResponse.getStatusCode();

        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            environment.lastStatusCode = lastApiException.getCode();
        }
    }

}

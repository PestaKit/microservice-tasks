package io.pestaKit.tasks.api.spec.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestaKit.tasks.ApiException;
import io.pestaKit.tasks.ApiResponse;
import io.pestaKit.tasks.api.OneTaskApi;
import io.pestaKit.tasks.api.dto.TaskCreate;
import io.pestaKit.tasks.api.spec.helpers.Environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class OneTaskSteps {

    private Environment environment;
    private OneTaskApi api;

    TaskCreate task;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public OneTaskSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getOneTaskApi();
    }

    @Given("^there is a Tasks server$")
    public void there_is_a_Tasks_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a task payload$")
    public void i_have_a_task_payload() throws Throwable {
        task = new io.pestaKit.tasks.api.dto.TaskCreate();
        task.setName("task one");
    }

    @When("^I POST it to the /tasks endpoint$")
    public void i_POST_it_to_the_tasks_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createTaskWithHttpInfo(task);
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

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(201, lastStatusCode);
    }
    @And("^I GET the endpoint /tasks/1 and  verify if it is not empty and that is name is 'task one'$")
    public void i_get_the_endpoint_tasks_1_verify_is_not_empty_and_name_is_task_one() throws Throwable {
        assertEquals(200, api.tasksNumTaskGetWithHttpInfo((long)1).getStatusCode());
        assertEquals("task one", api.tasksNumTaskGetWithHttpInfo((long)1).getData().getName());
    }
    @And("^I DELETE the endpoint /tasks/1 and verify if it is deleted$")
    public void i_delete_the_endpoint_task_1_and_verify_if_deleted() throws Throwable {
        assertEquals(201, api.tasksNumTaskDeleteWithHttpInfo((long)1).getStatusCode());

        try {
            lastApiResponse = api.tasksNumTaskGetWithHttpInfo((long)1);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
        assertEquals(404, lastStatusCode);
    }
}

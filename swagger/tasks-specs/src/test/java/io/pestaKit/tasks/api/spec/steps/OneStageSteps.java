package io.pestaKit.tasks.api.spec.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestaKit.tasks.ApiException;
import io.pestaKit.tasks.ApiResponse;
import io.pestaKit.tasks.api.OneStageApi;
import io.pestaKit.tasks.api.OneTaskApi;
import io.pestaKit.tasks.api.dto.Stage;
import io.pestaKit.tasks.api.dto.TaskCreate;
import io.pestaKit.tasks.api.spec.helpers.Environment;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class OneStageSteps {

    private Environment environment;
    private OneStageApi api;
    private OneTaskApi taskApi;

    private TaskCreate task;
    private Stage stage;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public OneStageSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getOneStageApi();
    }

    @Given("^I have a task payload and a stage payload$")
    public void i_have_a_task_payload_and_a_stage_payload() throws Throwable {
        task = new TaskCreate();
        task.setName("task two");
        OneTaskApi taskApi = environment.getOneTaskApi();
        taskApi.createTaskWithHttpInfo(task);

        stage = new Stage();
        stage.setName("stage one");
        stage.setPosition(BigDecimal.valueOf(1));
        stage.setValue("valueOfStageOne");
    }

    @When("^I POST the stage to the /tasks/2/stages endpoint$")
    public void i_POST_the_stage_to_the_tasks_1_stages_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createStageWithHttpInfo(stage, (long)2);
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
    @Then("^I receive a (\\d+) status code for stage posting$")
    public void i_receive_a_status_code_for_stage_posting(int arg1) throws Throwable {
        assertEquals(201, lastStatusCode);
    }
    @And("^I GET the endpoint /tasks/2/stages/1 and verify if it is not empty and that his name is 'stage one'$")
    public void i_get_the_endpoint_tasks_1_stages_1_verify_is_not_empty_and_name_his_task_one() throws Throwable {
        assertEquals(200, api.tasksNumTaskStagesNumStageGetWithHttpInfo((long)2, (long)1).getStatusCode());
        assertEquals("stage one", api.tasksNumTaskStagesNumStageGetWithHttpInfo((long)2, (long)1).getData().getName());
    }
    @And("^I POST a new Stage at /tasks/2/stages/1 and verify if the shift goes well$")
    public void i_post_a_new_stage_at_tasks_1_stages_1_and_verify_if_shift_good() throws Throwable {
        Stage newStage = new Stage();
        newStage.setValue("value");
        newStage.setPosition(BigDecimal.valueOf(1));
        newStage.setName("stageTwo");
        api.createStageWithHttpInfo(newStage, (long)2);

        assertEquals("stageTwo", api.tasksNumTaskStagesNumStageGetWithHttpInfo((long)2, (long)1).getData().getName());
        assertEquals("stage one", api.tasksNumTaskStagesNumStageGetWithHttpInfo((long)2, (long)2).getData().getName());
    }
    @And("^I DELETE the endpoint /tasks/2/stages/1 and verify if it is deleted$")
    public void i_delete_the_endpoint_task_1_stages_1_and_verify_if_deleted() throws Throwable {
        assertEquals(201, api.tasksNumTaskStagesNumStageDeleteWithHttpInfo((long)2, (long)1).getStatusCode());

        try {
            lastApiResponse = api.tasksNumTaskStagesNumStageGetWithHttpInfo((long)2, (long)1);
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

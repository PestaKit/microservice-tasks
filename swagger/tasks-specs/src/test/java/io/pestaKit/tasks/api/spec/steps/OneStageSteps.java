package io.pestaKit.tasks.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestaKit.tasks.ApiException;
import io.pestaKit.tasks.ApiResponse;
import io.pestaKit.tasks.api.OneStageApi;
import io.pestaKit.tasks.api.OneTaskApi;
import io.pestaKit.tasks.api.TasksApi;
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
    private TasksApi tasksApi;

    private TaskCreate task;
    private Stage stageOne, stageTwo, stageThree;


    private final String NAMETASKONE  = "task one";
    private final String NAMETASKTWO  = "task two";
    private final String NAMESTAGEONE = "stage one";
    private final String NAMESTAGETWO = "stage two";
    private final String NAMESTAGETHREE = "stage three";

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private long lastNumTask;

    public OneStageSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getOneStageApi();
        tasksApi = environment.getTasksApi();
    }

    @Given("^I have a task$")
    public void i_have_a_task() throws Throwable {
        task = new TaskCreate();
        task.setName(NAMETASKONE);

        OneTaskApi taskApi = environment.getOneTaskApi();
        taskApi.createTaskWithHttpInfo(task);

        lastNumTask = tasksApi.tasksGet().get(0).getNumber().longValue();
    }

    @Given("^I have 3 Stages payload$")
    public void i_have_3_stages_payload() {
        stageOne = new Stage();
        stageTwo = new Stage();
        stageThree = new Stage();

        stageOne.setName(NAMESTAGEONE);
        stageTwo.setName(NAMESTAGETWO);
        stageThree.setName(NAMESTAGETHREE);

        stageOne.setPosition(BigDecimal.valueOf(0));
        stageTwo.setPosition(BigDecimal.valueOf(0));
    }

    @When("^I POST a correct stage to the /tasks/numTask/stages endpoint$")
    public void i_POST_a_correct_stage_to_the_tasks_numTask_stages_endpoint() throws Throwable {
        postStage(stageOne);
    }
    @When("^I POST a correct stage but with already used name to the /tasks/numTask/stages endpoint$")
    public void i_POST_a_correct_stage_but_with_already_used_name_to_the_tasks_numTask_stages_endpoint() throws Throwable {
        postStage(stageOne);
        postStage(stageOne);
    }
    @When("^I POST a correct stage in a non-existent Task to the /tasks/numTask/stages endpoint$")
    public void i_POST_a_correct_stage_in_non_existent_stage_to_the_tasks_numTask_stages_endpoint() throws Throwable {
        lastNumTask = tasksApi.tasksGet().get(0).getNumber().longValue() + 10;
        postStage(stageOne);
    }
    @When("^I POST two Stages at the same position$")
    public void i_post_two_stages_at_the_same_position() {
        postStage(stageOne);
        postStage(stageTwo);
    }
    @Then("^the position of first Stage has been switched forward$")
    public void the_position_of_first_stage_has_been_switched_forward() {
        try {
            assertEquals(1, api.tasksNumTaskStagesNumStageGetWithHttpInfo(lastNumTask, (long)1).getData().getPosition().intValue());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
    @When("^I GET an existing Stage at /tasks/numTask/stages/position endpoint$")
    public void i_get_an_existing_stage_at_tasks_numTask_stages_position_endpoint() {
        postStage(stageOne);
        getStage(0);
    }
    @When("^I GET an existing Stage at a non-existent Task /tasks/numTask/stages/position endpoint$")
    public void i_get_an_existing_stage_at_non_existent_task_tasks_numTask_stages_position_endpoint() {
        postStage(stageOne);
        lastNumTask += 10;
        getStage(stageOne.getPosition().intValue());
    }
    @When("^I GET an non-existent Stage at existing Task /tasks/numTask/stages/position endpoint$")
    public void i_get_an_non_existent_stage_at_existing_task_tasks_numTask_stages_position_endpoint() {
        getStage(100);
    }

    @When("^I DELETE an existing Stage /tasks/numTask/stages/position endpoint$")
    public void i_delete_an_existing_stage_tasks_numTask_stages_position_endpoint() {
        postStage(stageOne);
        deleteStage(stageOne.getPosition().intValue());
    }
    @When("^I DELETE a non-existent Stage /tasks/numTask/stages/position endpoint$")
    public void i_delete_a_non_existent_stage_tasks_numTask_stages_position_endpoint() {
        deleteStage(stageOne.getPosition().intValue());
    }
    public void postStage(Stage currStage)
    {
        try {
            lastApiResponse = api.createStageWithHttpInfo(currStage, lastNumTask);
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
    public void getStage(long position)
    {
        try {
            lastApiResponse = api.tasksNumTaskStagesNumStageGetWithHttpInfo(lastNumTask, position);
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
    public void deleteStage(long position)
    {
        try {
            lastApiResponse = api.tasksNumTaskStagesNumStageDeleteWithHttpInfo(lastNumTask, position);
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

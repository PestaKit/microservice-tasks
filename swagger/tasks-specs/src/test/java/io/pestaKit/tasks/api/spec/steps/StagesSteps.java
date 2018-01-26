package io.pestaKit.tasks.api.spec.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestaKit.tasks.ApiException;
import io.pestaKit.tasks.ApiResponse;
import io.pestaKit.tasks.api.OneStageApi;
import io.pestaKit.tasks.api.OneTaskApi;
import io.pestaKit.tasks.api.StagesApi;
import io.pestaKit.tasks.api.dto.Stage;
import io.pestaKit.tasks.api.dto.TaskCreate;
import io.pestaKit.tasks.api.spec.helpers.Environment;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class StagesSteps {

    private Environment environment;
    private StagesApi api;
    private OneStageApi oneStageApi;
    private OneTaskApi oneTaskApi;

    private Stage stage1, stage2, stage3;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public StagesSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getStagesApi();
    }

    @Given("^I have 3 Stages on a Task$")
    public void i_have_3_stages_on_a_task() throws Throwable {

        stage1 = new Stage();
        stage1.setName("stage T1");

        stage2 = new Stage();
        stage2.setName("stage T2");

        stage3 = new Stage();
        stage3.setName("stage T3");

        stage1.setPosition(BigDecimal.valueOf(1));
        stage1.setValue("valueT1");

        stage2.setPosition(BigDecimal.valueOf(1));
        stage2.setValue("valueT2");

        stage3.setPosition(BigDecimal.valueOf(1));
        stage3.setValue("valueT3");

        TaskCreate task = new TaskCreate();
        task.setName("Task S1");
        oneTaskApi = environment.getOneTaskApi();
        oneTaskApi.createTaskWithHttpInfo(task);

        oneStageApi = environment.getOneStageApi();
        oneStageApi.createStage(stage1, (long)6);
        oneStageApi.createStage(stage2, (long)6);
        oneStageApi.createStage(stage3, (long)6);
    }

    @When("^I GET the list of stages with /tasks/1/stages endpoint$")
    public void i_get_list_of_stages_with_1_stages_endpoint() throws Throwable {
        try {
            lastApiResponse = api.tasksNumTaskStagesGetWithHttpInfo((long)6);
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
    @Then("^I receive a (\\d+) status code for getting Stages$")
    public void i_receive_a_status_code_for_getting_stages(int arg1) throws Throwable {
        assertEquals(200, lastStatusCode);
    }
    @And("^the list has the good size$")
    public void the_list_has_the_good_size() throws Throwable {
        List<Stage> stages = (List<Stage>) lastApiResponse.getData();
        assertEquals(3, stages.size());
    }
}

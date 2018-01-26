package io.pestaKit.tasks.api.endpoints;

import io.pestaKit.tasks.api.TasksApi;
import io.pestaKit.tasks.api.model.Stage;
import io.pestaKit.tasks.api.model.Task;
import io.pestaKit.tasks.api.model.TaskCreate;
import io.pestaKit.tasks.entities.StageEntity;
import io.pestaKit.tasks.entities.TaskEntity;
import io.pestaKit.tasks.repositories.StageRepository;
import io.pestaKit.tasks.repositories.TaskRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-18T13:09:34.043Z")

@Controller
public class TasksApiController implements TasksApi {

    @Autowired
    StageRepository stageRepository;

    @Autowired
    TaskRepository taskRepository;


    public ResponseEntity<Void> createStage(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Stage stage,
        @ApiParam(value = "",required=true ) @PathVariable("numTask") Long numTask) {
        TaskEntity task = taskRepository.findById(numTask);

        if(task == null)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        else
        {
            int lastPos = 0;
            for(int i = 0; i < task.getStages().size(); i++)
            {
                if(task.getStages().get(i).getName().equals(stage.getName()))
                    return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            boolean placeFound = false;
            for(int i = 0; i < task.getStages().size(); i++)
            {
                if(placeFound)
                {
                    if(task.getStages().get(i).getPosition() == task.getStages().get(i-1).getPosition())
                    {
                        task.getStages().get(i).setPosition( task.getStages().get(i).getPosition() + 1 );
                    }
                }
                if (stage.getPosition().intValue() >= lastPos &&
                        stage.getPosition().intValue() <= task.getStages().get(i).getPosition() && !placeFound) {
                    StageEntity newStage = new StageEntity(stage.getPosition().intValue(), stage.getName(), stage.getValue());
                    task.getStages().add(i, newStage);
                    stageRepository.save(newStage);

                    placeFound = true;
                }
                lastPos = task.getStages().get(i).getPosition();
            }
            if(task.getStages().size() == 0 || !placeFound)
            {
                StageEntity newStage = new StageEntity(stage.getPosition().intValue(), stage.getName(), stage.getValue());
                task.getStages().add(newStage);
                stageRepository.save(newStage);
            }
            taskRepository.save(task);

            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Void> createTask(@ApiParam(value = "name of the task" ,required=true )  @Valid @RequestBody TaskCreate nameTask) {
        TaskEntity task = taskRepository.findByName(nameTask.getName());
        if(task != null)
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        else
        {
            ArrayList<StageEntity> stages = new ArrayList<>();
            for(Stage stage :  nameTask.getStages())
            {
                stages.add(new StageEntity(stage.getPosition().intValue(), stage.getName(), stage.getValue()));
            }
            TaskEntity newTask = new TaskEntity(nameTask.getName(), stages);
            taskRepository.save(newTask);

            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
    }

    public ResponseEntity<List<Task>> tasksGet() {

        List<Task> allTasks = new ArrayList<>();
        for(TaskEntity task : taskRepository.findAll())
        {
            List<Stage> stages = new ArrayList<>();
            for(StageEntity stage : task.getStages())
            {
                Stage currStage = new Stage();
                currStage.setPosition(BigDecimal.valueOf(stage.getPosition()));
                currStage.setName(stage.getName());
                currStage.setValue(stage.getValue());
                stages.add(currStage);
            }
            Task currTask = new Task();
            currTask.setNumber(BigDecimal.valueOf(task.getId()));
            currTask.setName(task.getName());
            currTask.setStages(stages);
            allTasks.add(currTask);
        }
        return ResponseEntity.ok(allTasks);
    }

    public ResponseEntity<Void> tasksNumTaskDelete(@ApiParam(value = "number of the task",required=true ) @PathVariable("numTask") Long numTask) {
        if(taskRepository.findById(numTask) != null)
            taskRepository.delete(numTask);
        else
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    public ResponseEntity<Task> tasksNumTaskGet(@ApiParam(value = "number of the task",required=true ) @PathVariable("numTask") Long numTask) {
        TaskEntity taskEntity = taskRepository.findById(numTask);
        if(taskEntity == null)
            return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
        else
        {
            ArrayList<Stage> stages = new ArrayList<>();
            for(StageEntity stage : taskEntity.getStages())
            {
                Stage currStage = new Stage();
                currStage.setPosition(BigDecimal.valueOf(stage.getPosition()));
                currStage.setValue(stage.getValue());
                currStage.setName(stage.getName());
                stages.add(currStage);
            }
            Task task = new Task();
            task.setStages(stages);
            task.setName(taskEntity.getName());
            task.setNumber(BigDecimal.valueOf(taskEntity.getId()));
            return ResponseEntity.ok(task);
        }
    }

    public ResponseEntity<List<Stage>> tasksNumTaskStagesGet(@ApiParam(value = "number of the task",required=true ) @PathVariable("numTask") Long numTask) {
        TaskEntity taskEntity = taskRepository.findById(numTask);
        if(taskEntity == null)
            return new ResponseEntity<List<Stage>>(HttpStatus.NOT_FOUND);

        List<Stage> stages = new ArrayList<>();
        for(StageEntity stage : taskEntity.getStages())
        {
            Stage currStage = new Stage();
            currStage.setName(stage.getName());
            currStage.setValue(stage.getValue());
            currStage.setPosition(BigDecimal.valueOf(stage.getPosition()));
            stages.add(currStage);
        }
        return ResponseEntity.ok(stages);
    }

    public ResponseEntity<Void> tasksNumTaskStagesNumStageDelete(@ApiParam(value = "number of the task",required=true ) @PathVariable("numTask") Long numTask,
        @ApiParam(value = "number of the stage",required=true ) @PathVariable("numStage") Long numStage) {
        TaskEntity taskEntity = taskRepository.findById(numTask);
        if(taskEntity == null || taskEntity.getStages().isEmpty())
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

        for(int i = 0; i < taskEntity.getStages().size(); i++)
        {
            if(taskEntity.getStages().get(i).getPosition() == numStage)
            {
                System.out.println("size beofre "+taskEntity.getStages().size());
                taskEntity.getStages().remove(i);
                System.out.println("size after "+taskEntity.getStages().size());
                taskRepository.save(taskEntity);

                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Stage> tasksNumTaskStagesNumStageGet(@ApiParam(value = "number of the task",required=true ) @PathVariable("numTask") Long numTask,
        @ApiParam(value = "number of the stage",required=true ) @PathVariable("numStage") Long numStage) {
        TaskEntity taskEntity = taskRepository.findById(numTask);
        if(taskEntity == null || taskEntity.getStages().isEmpty())
            return new ResponseEntity<Stage>(HttpStatus.NOT_FOUND);
        for(int i = 0; i < taskEntity.getStages().size(); i++)
        {
            if(taskEntity.getStages().get(i).getPosition() == numStage)
            {
                Stage stage = new Stage();
                stage.setPosition(BigDecimal.valueOf(taskEntity.getStages().get(i).getPosition()));
                stage.setValue(taskEntity.getStages().get(i).getValue());
                stage.setName(taskEntity.getStages().get(i).getName());

                return ResponseEntity.ok(stage);
            }
        }
        return new ResponseEntity<Stage>(HttpStatus.NOT_FOUND);
    }

}

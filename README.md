# Microservice-Task
## Introduction
RestApi for microservice Task. This was made for AMT course at HEIG-VD. 
## Task
With this microservice you can create tasks to execute, and all the Stages of this task. This microservice gives you 4 endpoints. 
*OneTask : Allows you to get/delete/post some tasks, one at the time.
*OneStage : Allows you to get/delete/post some stages, at a given position and for a given task
*Stages : Allows you to get list of all Stages of given task
*Tasks : Allows you to get list of all Tasks 
## Start the microservice
Start by cloning the repository. Then in /docker-images/microservice launch the script "build-jar.sh. This will create a jar from the sources files.
Make sur you have docker installed in your machine, and that it is running, then go to topology-amt folder and launch the following commandline : docker-compose up --build
The documentation of your api is at : http://localhost:8080/api
##Start the microservice if you have having issues with the first method (problem with mysql docker's image)
You have to install mysql in your PC, and launch a connection.Then open the spring project with intellj or an other ide, and run the Springboot project 

## Cucumber Tests
If you want to launch the tests make sur the springboot project has been launch and that mysql is running, then go to swagger/tasks-specs and run the command mvn test (make sur you have mvn installed on your PC)
## API's endpoints details

### /tasks/{numTask}/stages
#### POST
Create a stage at given position for the task n° numTask. If an other stage already has this position, it would be switched forward 
#### GET 
get the list of stages of task n° numTask
### /tasks/{numTask}/stages/{numStage}
#### DELETE
Let you delete the stage n° "numStage" of the task n° "numTask"
#### GET
Return the wanted stage of Task n° numTask
### /tasks
#### POST
create a new task
#### GET
get the list of all tasks
### /tasks{numTask}
#### DELETE
delete the task n° numTask
#### GET
get the task n° numTask

## microservice/task creator

* Kevin Moreira [https://github.com/kevinmoreirad] 



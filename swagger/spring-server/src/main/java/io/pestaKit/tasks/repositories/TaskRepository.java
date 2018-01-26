package io.pestaKit.tasks.repositories;

import io.pestaKit.tasks.entities.TaskEntity;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskEntity, Long>{

    public TaskEntity findById(Long idTask);

    public TaskEntity findByName(String name);
}

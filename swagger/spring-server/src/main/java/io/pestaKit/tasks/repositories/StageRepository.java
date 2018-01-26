package io.pestaKit.tasks.repositories;

import io.pestaKit.tasks.entities.StageEntity;
import org.springframework.data.repository.CrudRepository;

public interface StageRepository extends CrudRepository<StageEntity, Long>{

    public StageEntity findById(int idStage);
}

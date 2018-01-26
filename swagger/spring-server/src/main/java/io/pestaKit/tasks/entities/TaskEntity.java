package io.pestaKit.tasks.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TaskEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;


    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("position ASC")
    private List<StageEntity> stages;

    public TaskEntity(){};

    public TaskEntity(String name, ArrayList<StageEntity> stages)
    {
        this.name = name;
        this.stages = stages;
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StageEntity> getStages() {
        return stages;
    }

    public void setStages(ArrayList<StageEntity> stages) {
        this.stages = stages;
    }
}

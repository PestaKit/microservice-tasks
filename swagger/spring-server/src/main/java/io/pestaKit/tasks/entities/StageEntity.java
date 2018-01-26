package io.pestaKit.tasks.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="stage")
public class StageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name, value;
    private int position;
    public StageEntity(){};

    public StageEntity(int num, String n, String val)
    {
        position = num;
        name = n;
        value = val;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
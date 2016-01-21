package main.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by delf on 1/18/16.
 */

@Entity
@Table
//Entity for information about group
public class GroupRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long id;

    @NotNull
    //Group name
    private String name;

    @ManyToMany
    private transient Set<PhonebookRecord> users = new HashSet<>();

    //Default constructor
    public GroupRecord(){}

    //Default constructor with name
    public GroupRecord(String name){
        this.name = name;
    }

    //Getters and setter section
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

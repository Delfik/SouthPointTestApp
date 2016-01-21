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
//Entity for information about contact
public class PhonebookRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private long id;

    @NotNull
    //Contact name
    private String name;

    @NotNull
    //Contact phone
    private String phone;

    @ManyToMany
    @JoinTable(name = "PhonebookRecord_Group",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<GroupRecord> groups = new HashSet<>();

    //Default constructor
    public PhonebookRecord(){}

    //Default constructor with name and phone
    public PhonebookRecord(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    //Getters and setters section
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<GroupRecord> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupRecord> groups) {
        this.groups = groups;
    }
}

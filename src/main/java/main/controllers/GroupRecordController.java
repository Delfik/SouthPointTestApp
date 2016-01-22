package main.controllers;

import com.google.gson.Gson;
import main.models.GroupRecord;
import main.models.GroupRecordDao;
import main.models.PhonebookRecord;
import main.models.PhonebookRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by delf on 1/22/16.
 * Trying to implement RESTful API for groups
 */
@RestController
@RequestMapping("/groups")
public class GroupRecordController {

    //Create new group with JSON representation of RequestBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    GroupRecord newGroup(@RequestBody String newGroup){
        GroupRecord record = null;
        try{
            record = new Gson().fromJson(newGroup, GroupRecord.class);
            groupRecordDao.create(record);
        }catch (Exception e){
        }finally {
            return record;
        }
    }

    //List of all groups
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<GroupRecord> allGroups(){
        return groupRecordDao.getAll();
    }

    //Return list of users which group contains group with id = {id}
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    List<PhonebookRecord> viewGroupUsers(@PathVariable("id") long groupId){
        List<PhonebookRecord> result = new ArrayList<>();
        GroupRecord group = groupRecordDao.getById(groupId);
        result.addAll(phonebookRecordDao.getAll().stream().filter(record -> record.getGroups().contains(group))
                .collect(Collectors.toList()));
        return result;
    }

    //Updates group with JSON representation of RequestBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    GroupRecord updateGroup(@RequestBody String editableGroup){
        GroupRecord record = null;
        try {
            record = new Gson().fromJson(editableGroup, GroupRecord.class);
            groupRecordDao.update(record);
        }catch (Exception e){
        }finally {
            return record;
        }
    }

    //Removes group with id = {id}
    @RequestMapping(method=RequestMethod.DELETE, value="{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        GroupRecord record = groupRecordDao.getById(id);
        if(record!=null){
            //May be there is better way but... ~O(n)... Don't know better way today =(
            phonebookRecordDao.getAll().stream().filter(r -> r.getGroups().contains(record)).forEach(r -> r.getGroups().remove(record));
            groupRecordDao.delete(record);}
    }

    //Removes user with id = {userId} from group with id = {groupId}
    @RequestMapping(value = "/{userId}/{groupId}", method = RequestMethod.DELETE)
    PhonebookRecord excludeUserFromGroup(@PathVariable("userId") long userId,
                                         @PathVariable("groupId") long groupId){
        PhonebookRecord result = phonebookRecordDao.getById(userId);
        GroupRecord group = groupRecordDao.getById(groupId);
        result.getGroups().remove(group);
        phonebookRecordDao.update(result);
        return result;
    }

    @Autowired
    private GroupRecordDao groupRecordDao;

    @Autowired
    private PhonebookRecordDao phonebookRecordDao;
}

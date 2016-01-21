package main.controllers;

import com.google.gson.Gson;
import main.models.GroupRecord;
import main.models.GroupRecordDao;
import main.models.PhonebookRecord;
import main.models.PhonebookRecordDao;
import main.views.ApplicationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by delf on 1/19/16.
 * Пародия на RESTful API с только GET запросами...
 * Понимаю, что это ненормально, но Spring не давал выполнять иные типы запросов =(
 * Комментарии можно в JDoc формате оформить. Но надо ли..
 */
@RestController
public class RequestsController {
    //Return all users from database
    @RequestMapping(value = "/users/all", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    List<PhonebookRecord> allUsers(){
        return phonebookRecordDao.getAll();
    }

    //Return concrete user with id = {id}
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    PhonebookRecord viewUser(@PathVariable("id") long userId){
        return phonebookRecordDao.getById(userId);
    }

    //Return list of users which name contains {name}
    @RequestMapping(value = "/users/filter/{name}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    List<PhonebookRecord> viewUserFilter(@PathVariable("name") String name){
        return phonebookRecordDao.filterByName(name);
    }

    //Return groups of concrete user with id = {id}
    @RequestMapping(value = "/users/groups/{id}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    Set<GroupRecord> viewAllGroups(@PathVariable("id") long userId){
        return phonebookRecordDao.getById(userId).getGroups();
    }

    //Delete user with id = {id}
    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    PhonebookRecord deleteUser(@PathVariable("id") long groupId){
        PhonebookRecord record = phonebookRecordDao.getById(groupId);
        if(record!=null)phonebookRecordDao.delete(record);
        return null;
    }

    //Creates new user with JSON representation {param}
    @RequestMapping(value = "/users/new/{param}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    PhonebookRecord newUser(@PathVariable("param") String param){
        PhonebookRecord record = null;
        try{
            record = new Gson().fromJson(param, PhonebookRecord.class);
            phonebookRecordDao.create(record);
        }catch (Exception e){
        }finally {
            return record;
        }
    }

    //Updates user with JSON representation {param}
    @RequestMapping(value = "/users/edit/{param}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    PhonebookRecord updateUser(@PathVariable("param") String  param){
        PhonebookRecord record = null;
        try{
            record = new Gson().fromJson(param, PhonebookRecord.class);
            phonebookRecordDao.update(record);
        }catch (Exception e){
        }finally {
            return record;
        }
    }

    //Return list of all groups
    @RequestMapping(value = "/groups/all", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    List<GroupRecord> allGroups(){
        return groupRecordDao.getAll();
    }

    //Return concrete group with id = {id}
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    GroupRecord viewGroup(@PathVariable("id") long groupId){
        return groupRecordDao.getById(groupId);
    }

    //Return list of users which group contains group with id = {id}
    @RequestMapping(value = "/groups/users/{id}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    List<PhonebookRecord> viewGroupUsers(@PathVariable("id") long groupId){
        List<PhonebookRecord> result = new ArrayList<>();
        GroupRecord group = groupRecordDao.getById(groupId);
        result.addAll(phonebookRecordDao.getAll().stream().filter(record -> record.getGroups().contains(group))
                .collect(Collectors.toList()));
        return result;
    }

    //Removes user with id = {userId} from group with id = {groupId}
    @RequestMapping(value = "/groups/users/exclude/{userId}/{groupId}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    PhonebookRecord excludeUserFromGroup(@PathVariable("userId") long userId,
                                         @PathVariable("groupId") long groupId){
        PhonebookRecord result = phonebookRecordDao.getById(userId);
        GroupRecord group = groupRecordDao.getById(groupId);
        result.getGroups().remove(group);
        phonebookRecordDao.update(result);
        return result;
    }

    //Create new group with JSON representation {param}
    @RequestMapping(value = "/groups/new/{newGroup}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    GroupRecord newGroup(@PathVariable("newGroup") String newGroup){
        GroupRecord record = null;
        try{
            record = new Gson().fromJson(newGroup, GroupRecord.class);
            groupRecordDao.create(record);
        }catch (Exception e){
        }finally {
            return record;
        }
    }

    //Updates group with JSON representation {param}
    @RequestMapping(value = "/groups/update/{group}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    GroupRecord updateGroup(@PathVariable("group") String group){
        GroupRecord record = null;
        try {
            record = new Gson().fromJson(group, GroupRecord.class);
            groupRecordDao.update(record);
        }catch (Exception e){
        }finally {
            return record;
        }
    }

    //Delete group with id = {id} from database and remove reference to this group from all users
    @RequestMapping(value = "/groups/delete/{id}", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    GroupRecord deleteGroup(@PathVariable("id") long groupId){
        GroupRecord record = groupRecordDao.getById(groupId);
        if(record!=null){
            //May be there is better way but... ~O(n)... Don't know better way today =(
            phonebookRecordDao.getAll().stream().filter(r -> r.getGroups().contains(record)).forEach(r -> r.getGroups().remove(record));
            groupRecordDao.delete(record);}
        return null;
    }

    @Autowired
    private PhonebookRecordDao phonebookRecordDao;

    @Autowired
    private GroupRecordDao groupRecordDao;

    //Мм, да, за такое надо бить по рукам, знаю.
    //Тут основная проблема в том, что я не смог настроить правильный возврат HTML кода =\ из-за этого такой костыль
    @RequestMapping("/")
    public String index(){
        return ApplicationView.mainView();
    }

}

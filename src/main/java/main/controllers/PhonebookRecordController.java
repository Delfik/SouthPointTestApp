package main.controllers;

import com.google.gson.Gson;
import main.models.GroupRecord;
import main.models.PhonebookRecord;
import main.models.PhonebookRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

/**
 * Created by delf on 1/22/16.
 * Trying to implement RESTful API for contacts
 */
@RestController
@RequestMapping("/users")
public class PhonebookRecordController {

    //Creates new user with JSON representation of RequestBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    PhonebookRecord newUser(@RequestBody String param) {
        PhonebookRecord record = null;
        try{
            record = new Gson().fromJson(param, PhonebookRecord.class);
            phonebookRecordDao.create(record);
        }catch (Exception e){
        }finally {
            return record;
        }
    }

    //Return list of all users
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<PhonebookRecord> allUsers(){
        return phonebookRecordDao.getAll();
    }

    //Updates user with JSON representation of RequestBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    PhonebookRecord updateUser(@RequestBody String param){
        PhonebookRecord record = null;
        try{
            record = new Gson().fromJson(param, PhonebookRecord.class);
            phonebookRecordDao.update(record);
        }catch (Exception e){
        }finally {
            return record;
        }
    }

    //Removes user with id = {id}
    @RequestMapping(method=RequestMethod.DELETE, value="{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        PhonebookRecord record = phonebookRecordDao.getById(id);
        if(record!=null)phonebookRecordDao.delete(record);
    }

    //Return list of users which name contains {name}
    @RequestMapping(value = "/filter/{name}", method = RequestMethod.GET)
    List<PhonebookRecord> viewUserFilter(@PathVariable("name") String name){
        return phonebookRecordDao.filterByName(name);
    }

    //if filter contains only whitespaces
    @RequestMapping(value = "/filter/", method = RequestMethod.GET)
    List<PhonebookRecord> viewUserFilterNew(){
        return phonebookRecordDao.getAll();
    }

    //Return groups of concrete user with id = {id}
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
    Set<GroupRecord> viewAllGroups(@PathVariable("id") long userId){
        return phonebookRecordDao.getById(userId).getGroups();
    }

    @Autowired
    private PhonebookRecordDao phonebookRecordDao;
}

package main.models;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by delf on 1/20/16.
 * This class is used to access data for the GroupRecord entity.
 */
@Repository
@Transactional
public class GroupRecordDao {
    //Save new Group in database
    public void create(GroupRecord record) {
        entityManager.persist(record);
        return;
    }

    //Delete group from database
    public void delete(GroupRecord record) {
        if (entityManager.contains(record))
            entityManager.remove(record);
        else
            entityManager.remove(entityManager.merge(record));
    }

    //Return all groups stored in database
    public List<GroupRecord> getAll() {
        return entityManager.createQuery("from GroupRecord").getResultList();
    }

    //Return group by id
    public GroupRecord getById(long id) {
        return entityManager.find(GroupRecord.class, id);
    }

    //Update stored information about group
    public void update(GroupRecord record) {
        entityManager.merge(record);
        return;
    }

    @PersistenceContext
    private EntityManager entityManager;
}

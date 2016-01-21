package main.models;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by delf on 1/20/16.
 * This class is used to access data for the PhonebookRecord entity.
 */
@Repository
@Transactional
public class PhonebookRecordDao {
    //Save new PhonebookRecord in database
    public void create(PhonebookRecord record) {
        entityManager.persist(record);
        return;
    }

    //Delete record from database
    public void delete(PhonebookRecord record) {
        if (entityManager.contains(record))
            entityManager.remove(record);
        else
            entityManager.remove(entityManager.merge(record));
        return;
    }

    //Return all records stored in database
    public List<PhonebookRecord> getAll() {
        return entityManager.createQuery("from PhonebookRecord").getResultList();
    }

    //Return records wich name contains name param
    public List<PhonebookRecord> filterByName(String name) {
        return entityManager.createQuery("from PhonebookRecord where name like :name").setParameter("name", "%"+name+"%").getResultList();
    }

    //Return record by id
    public PhonebookRecord getById(long id) {
        return entityManager.find(PhonebookRecord.class, id);
    }

    //Update record in database
    public void update(PhonebookRecord record) {
        entityManager.merge(record);
        return;
    }

    @PersistenceContext
    private EntityManager entityManager;
}

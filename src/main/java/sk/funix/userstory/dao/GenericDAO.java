package sk.funix.userstory.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Nicolas Milliard
 *
 * @param <T>
 * @param <ID>
 */
public interface GenericDAO<T, ID extends Serializable> {

    /** Save entity.
     * @param entity
     * @return Persistent entity
     */
    T makePersistent(T entity);

    /** Remove entity.
     * @param entity
     */
    void makeTransient(T entity);
    
    /** Remove entity by id.
     * @param entity
     */
    void makeTransientByID(ID id);

    /** Count all entities.
     * @return
     */
    long countAll();

    /** Find an entity by it id.
     * @param id
     * @param lock
     * @return
     */
    T findById(ID id, boolean lock);

    /** Return all entities.
     * @return
     */
    List<T> findAll();
    
    /** Find a range of entities.
     * @param firstResult
     * @param maxResults
     * @return
     */
    List<T> findEntries(int firstResult, int maxResults);
}


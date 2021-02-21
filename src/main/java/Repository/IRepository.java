package Repository;

import java.util.Collection;

//----------------------------------------------------Interface IRepository----------------------------------------------------//
public interface IRepository <T,ID>{
    T add(T elem)throws Exception;
    void delete(T elem)throws Exception;
    void update (T elem, ID id);
    T findById (ID id);
    Iterable<T> findAll();
    Collection<T> getAll();
}

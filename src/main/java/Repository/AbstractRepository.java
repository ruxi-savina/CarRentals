package Repository;

import Model.Identifiable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//----------------------------------------------------Class AbstractRepository----------------------------------------------------//
public abstract class AbstractRepository <T extends Identifiable<ID>,ID> implements IRepository<T, ID>{
    protected Map<ID,T> elem;
    public AbstractRepository(){
        elem = new HashMap<>();
    }

    public T add(T el)throws Exception{
        if(elem.containsKey(el.getId()))
            throw new RuntimeException("\nElement already exists\n");
        elem.put(el.getId(),el);
        return el;
    }

    public void delete(T el)throws Exception{
        if(elem.containsKey(el.getId()))
            elem.remove(el.getId());
        else
            throw new RuntimeException("\nElement not in the database\n");
    }

    public void update(T el,ID id){
        if(elem.containsKey(id))
            elem.put(el.getId(),el);
        else
            throw new RuntimeException("\nElement not in the database\n");
    }

    public T findById(ID id){
        if(elem.containsKey(id))
            return elem.get(id);
        else
            return null;
    }

    public Iterable<T> findAll(){
        return elem.values();
    }

    public Collection<T> getAll(){
        return elem.values();
    }


}
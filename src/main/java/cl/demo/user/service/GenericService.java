package cl.demo.user.service;

import cl.demo.user.domain.exception.ResourceNotFoundException;
import cl.demo.user.repository.IGenericRepo;

public abstract class GenericService<T, ID> implements IGenericService<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T register(T obj) {
        return getRepo().save(obj);
    }

    @Override
    public T modify(T obj) {
        return getRepo().save(obj);
    }

    @Override
    public T getById(ID id) {

        T data = getRepo().findById(id).orElse(null);

        if(data == null){
            throw new ResourceNotFoundException("Resource with id " + id +" not found");
        }

        return data;
    }
}

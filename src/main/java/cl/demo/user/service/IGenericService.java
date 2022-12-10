package cl.demo.user.service;

public interface IGenericService<T, ID> {
    T register(T obj);
    T modify(T obj);
    T getById(ID id);
}

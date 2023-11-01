package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void removeUserById(int id) {
        try {
            em.remove(em.find(User.class, id));
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = em.createQuery("from User", User.class).getResultList();
        return userList;
    }

    @Override
    public void updateUser(User editedUser, int id) {
        try {
            User persistedUser = em.find(User.class, id);
            persistedUser.setName(editedUser.getName());
            persistedUser.setLastName(editedUser.getLastName());
            persistedUser.setAge(editedUser.getAge());
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            return em.find(User.class, id);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException();
        }
    }
}

package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;
import web.utill.UserNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        Optional<User> user = Optional.ofNullable(Optional.ofNullable(em.find(User.class, id))
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found.")));
        em.remove(user.get());
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void updateUser(User editedUser, int id) {
        Optional<User> persistedUser = Optional.ofNullable(Optional.ofNullable(em.find(User.class, id))
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found.")));
        User user = persistedUser.get();
        user.setName(editedUser.getName());
        user.setLastName(editedUser.getLastName());
        user.setAge(editedUser.getAge());
    }

    @Override
    public User getUserById(int id) {
        Optional<User> user = Optional.ofNullable(Optional.ofNullable(em.find(User.class, id))
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found.")));

        return user.get();
    }
}

package ru.swap.orm;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.swap.bean.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional //need to update\delete queries. Don't forget <tx:annotation-driven/>
public class ORMService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> queryFindAllUsersJPA() {
        System.out.println("ORMService queryfindAllUsersJPA is called");
        String query = "from User order by id";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        return typedQuery.getResultList();
    }

    public User queryFindUserById (int id) {
        System.out.println("ORMService queryFindUserById is called");
        return entityManager.find(User.class, id);
    }

    public boolean updateUser(int id, boolean enabled) {
        System.out.println("ORMService updateUser is called");

        String query= "update users set enabled = ? where id = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, enabled);
        nativeQuery.setParameter(2, id);
        int result = nativeQuery.executeUpdate();
        return result > 0; // result show how many rows was updated.
    }

    public boolean insertUser(String username, String password, boolean enabled) {
        System.out.println("ORMExample insertUser is called");

        String qlString = "insert into users (username,password,enabled) values (?,?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, username);
        query.setParameter(2, password);
        query.setParameter(3, enabled);
        int result = query.executeUpdate();

        return result > 0;
    }

    public boolean deleteUser(int id) {
        System.out.println("ORMExample deleteUser is called");

        String qlString = "delete from users where id = ?";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, id);
        int result = query.executeUpdate();

        return result > 0;
    }
}

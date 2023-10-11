package by.javaguru.dao;

import by.javaguru.entity.Course;
import by.javaguru.entity.Trainer;
import by.javaguru.exception.DaoException;
import by.javaguru.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainerDao implements Dao<Long, Trainer> {
    private static final TrainerDao INSTANCE = new TrainerDao();
    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public Optional<Trainer> findById(Long key) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Trainer trainer = session.get(Trainer.class, key);
            session.getTransaction().commit();

            return Optional.ofNullable(trainer);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Trainer> findAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Trainer> trainers = session.createQuery("from Trainer", Trainer.class).getResultList();
            session.getTransaction().commit();

            return trainers;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Trainer save(Trainer trainer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(trainer);
            session.getTransaction().commit();

            return trainer;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Trainer update(Trainer trainer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.merge(trainer);
            session.getTransaction().commit();

            return trainer;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Trainer trainer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.refresh(trainer);

            for (Course cours : trainer.getCourses()) {
                cours.getTrainers().removeIf(trainer::equals);
            }

            session.remove(trainer);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    public static TrainerDao getInstance() {
        return INSTANCE;
    }
}

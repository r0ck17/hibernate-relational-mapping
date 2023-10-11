package by.javaguru.dao;

import by.javaguru.entity.Course;
import by.javaguru.entity.Student;
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
public class CourseDao implements Dao <Long, Course> {
    private static final CourseDao INSTANCE = new CourseDao();
    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public Optional<Course> findById(Long key) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Course course = session.get(Course.class, key);
            session.getTransaction().commit();

            return Optional.ofNullable(course);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Course> findAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Course> courses = session.createQuery("from Course", Course.class).getResultList();
            session.getTransaction().commit();

            return courses;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Course save(Course course) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(course);
            session.getTransaction().commit();

            return course;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Course update(Course course) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.merge(course);
            session.getTransaction().commit();

            return course;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Course course) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.refresh(course);
            for (Student student : course.getStudents()) {
                student.setCourse(null);
                session.merge(student);
            }
            session.remove(course);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    public static CourseDao getInstance() {
        return INSTANCE;
    }
}

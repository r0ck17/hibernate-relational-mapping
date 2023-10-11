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
public class StudentDao implements Dao<Long, Student> {
    private static final StudentDao INSTANCE = new StudentDao();
    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public Optional<Student> findById(Long key) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Student student = session.get(Student.class, key);
            session.getTransaction().commit();

            return Optional.ofNullable(student);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Student> findAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Student> students = session.createQuery("from Student", Student.class).getResultList();
            session.getTransaction().commit();

            return students;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Student save(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(student);
            session.getTransaction().commit();

            return student;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Student update(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.merge(student);
            session.getTransaction().commit();

            return student;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.remove(student);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    public void deleteWithGradeBelow(Course course, Double grade) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.refresh(course);
            List<Student> students = course.getStudents();
            students.removeIf(st -> st.getStudentProfile().getPerformance() < grade);

            //session.merge(course);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    public static StudentDao getInstance() {
        return INSTANCE;
    }
}

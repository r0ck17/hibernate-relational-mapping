package by.javaguru.util;

import by.javaguru.entity.Course;
import by.javaguru.entity.Student;
import by.javaguru.entity.StudentProfile;
import by.javaguru.entity.Trainer;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory buildSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Course.class)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(StudentProfile.class)
                    .addAnnotatedClass(Trainer.class)
                    .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
                    .buildSessionFactory();
        }

        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}

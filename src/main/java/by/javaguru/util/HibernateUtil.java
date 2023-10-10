package by.javaguru.util;

import by.javaguru.entity.Course;
import by.javaguru.entity.Student;
import by.javaguru.entity.StudentProfile;
import by.javaguru.entity.Trainer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@Slf4j
@UtilityClass
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            log.info("Configure session factory");
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Course.class)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(StudentProfile.class)
                    .addAnnotatedClass(Trainer.class)
                    .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
                    .buildSessionFactory();
            log.info("Session factory configured");
        }

        return sessionFactory;
    }

    public static void closeSessionFactory() {
        log.info("Trying to close session factory");
        sessionFactory.close();
        log.info("Session factory closed");
    }

}

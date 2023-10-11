package by.javaguru.dao;

import by.javaguru.entity.Course;
import by.javaguru.entity.EducationType;
import by.javaguru.entity.Student;
import by.javaguru.entity.StudentProfile;
import by.javaguru.entity.Trainer;
import by.javaguru.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DaoTest {
    private static CourseDao courseDao;
    private static StudentDao studentDao;
    private static TrainerDao trainerDao;
    private static SessionFactory sessionFactory;

    @BeforeAll
    static void init() {
        courseDao = CourseDao.getInstance();
        studentDao = StudentDao.getInstance();
        trainerDao = TrainerDao.getInstance();
    }

    @BeforeEach
    void initSessionFactory() {
        sessionFactory = HibernateUtil.buildSessionFactory();
    }

    @AfterAll
    static void closeFactory() {
        sessionFactory.close();
    }

    @Nested
    class TrainerTest {
        @Test
        void findAllTest() {
            List<Trainer> trainers = trainerDao.findAll();
            int expectedListSize = 2;
            assertEquals(expectedListSize, trainers.size());
        }

        @Test
        void findByIdTest() {
            long existingId = 1L;
            Optional<Trainer> existingTrainer = trainerDao.findById(existingId);
            assertNotNull(existingTrainer.get());

            long nonExistingId = 3L;
            Optional<Trainer> nonExistingTrainer = trainerDao.findById(nonExistingId);
            assertEquals(Optional.empty(), nonExistingTrainer);
        }

        @Test
        void saveUpdateDeleteTest() {
            Trainer trainer = generateTrainer();

            Trainer savedTrainer = trainerDao.save(trainer);
            Long savedTrainerId = savedTrainer.getId();
            assertNotNull(savedTrainer);

            String newTrainerName = "Max";
            trainer.setName(newTrainerName);
            trainerDao.update(trainer);

            Optional<Trainer> updatedTrainer = trainerDao.findById(savedTrainerId);
            assertNotNull(updatedTrainer.get());
            assertEquals(newTrainerName, updatedTrainer.get().getName());

            trainerDao.delete(savedTrainer);
            Optional<Trainer> deletedTrainer = trainerDao.findById(savedTrainerId);
            assertEquals(Optional.empty(), deletedTrainer);
        }
    }

    @Nested
    class CourseTest {
        @Test
        void findAllTest() {
            List<Course> courses = courseDao.findAll();
            int expectedListSize = 3;
            assertEquals(expectedListSize, courses.size());
        }

        @Test
        void findByIdTest() {
            Optional<Course> existingCourse = courseDao.findById(1L);
            assertNotNull(existingCourse.get());

            long nonExistingId = 4L;
            Optional<Course> nonExistingCourse = courseDao.findById(nonExistingId);
            assertEquals(Optional.empty(), nonExistingCourse);
        }

        @Test
        void saveUpdateDeleteTest() {
            Course course = new Course("new course");

            Course savedCourse = courseDao.save(course);
            Long savedCourseId = savedCourse.getId();
            assertNotNull(savedCourseId);

            String newCourseName = "new Name";
            savedCourse.setName(newCourseName);
            courseDao.update(savedCourse);

            Optional<Course> updatedCourse = courseDao.findById(savedCourseId);
            assertNotNull(updatedCourse.get());
            assertEquals(newCourseName, updatedCourse.get().getName());

            courseDao.delete(savedCourse);
            Optional<Course> deletedCourse = courseDao.findById(savedCourseId);
            assertEquals(Optional.empty(), deletedCourse);
        }

        @Test
        void addTrainersToCourse() {
            Course course = new Course("Java Hibernate");

            Course savedCourse = courseDao.save(course);
            Long savedCourseId = savedCourse.getId();

            Trainer trainer = generateTrainer();
            trainerDao.save(trainer);

            course.addTrainer(trainer);
            courseDao.update(course);

            Course updatedCourse = courseDao.findById(savedCourseId).get();
            Optional<Trainer> trainerById = trainerDao.findById(trainer.getId());

            assertEquals(List.of(trainer), updatedCourse.getTrainers());
            assertEquals(List.of(course), trainerById.get().getCourses());

            trainerDao.delete(trainer);
            courseDao.delete(course);
        }

        @Test
        void addStudentsToCourse() {
            Course course = new Course("Java Spring");

            Course savedCourse = courseDao.save(course);
            Long savedCourseId = savedCourse.getId();

            Student student = Student.builder()
                    .name("ivan")
                    .surname("ivanov")
                    .educationType(EducationType.FEE_BASED)
                    .studentProfile(StudentProfile.builder()
                            .performance(5.1)
                            .build())
                    .course(course)
                    .build();

            studentDao.save(student);

            course.addStudent(student);
            courseDao.update(course);

            Course updatedCourse = courseDao.findById(savedCourseId).get();
            assertEquals(List.of(student), updatedCourse.getStudents());

            courseDao.delete(course);
            studentDao.delete(student);
        }

        @Test
        void removeStudentsWithGradeBelow6() {
            Optional<Course> course = courseDao.findById(3L);
            assertNotEquals(Optional.empty(), course);

            Course javaEnterpriseCourse = course.get();

            Student studentWithLowGrade = studentDao.findById(4L).get();
            assertNotNull(studentWithLowGrade);

            studentDao.deleteWithGradeBelow(javaEnterpriseCourse, 6.0);
            Optional<Student> deletedStudent = studentDao.findById(studentWithLowGrade.getId());

            assertEquals(Optional.empty(), deletedStudent);
        }
    }

    @Test
    void test() {
    }

    @Nested
    class StudentTest {
        @Test
        void findAllTest() {
            List<Student> courses = studentDao.findAll();
            int expectedListSize = 5;
            assertEquals(expectedListSize, courses.size());
        }

        @Test
        void findByIdTest() {
            Optional<Student> existingStudent = studentDao.findById(1L);
            assertNotNull(existingStudent.get());

            long nonExistingId = 6L;
            Optional<Student> nonExistingStudent = studentDao.findById(nonExistingId);
            assertEquals(Optional.empty(), nonExistingStudent);
        }

        @Test
        void saveUpdateDeleteTest() {
            Course course = new Course("new course");

            Course savedCourse = courseDao.save(course);
            Long savedCourseId = savedCourse.getId();
            assertNotNull(savedCourseId);

            String newCourseName = "new Name";
            savedCourse.setName(newCourseName);
            courseDao.update(savedCourse);

            Optional<Course> updatedCourse = courseDao.findById(savedCourseId);
            assertNotNull(updatedCourse.get());
            assertEquals(newCourseName, updatedCourse.get().getName());

            courseDao.delete(savedCourse);
            Optional<Course> deletedCourse = courseDao.findById(savedCourseId);
            assertEquals(Optional.empty(), deletedCourse);
        }
    }

    private static Trainer generateTrainer() {
        return Trainer.builder()
                .name("Kolya")
                .surname("Rostkov")
                .courses(new ArrayList<>())
                .build();
    }

    private static Student generateStudent() {
        return Student.builder()
                .name("ivan")
                .build();
    }
}

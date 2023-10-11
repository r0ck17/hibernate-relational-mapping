package by.javaguru.dao;

import by.javaguru.entity.Trainer;
import by.javaguru.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        sessionFactory = HibernateUtil.buildSessionFactory();
    }

    @AfterAll
    static void closeFactory() {
        sessionFactory.close();
    }

    @Test
    void trainerTest() {
        List<Trainer> trainers = trainerDao.findAll();
        int expectedListSize = 2;
        assertEquals(expectedListSize, trainers.size());

        long existingId = 1L;
        Optional<Trainer> existingTrainer = trainerDao.findById(existingId);
        assertNotNull(existingTrainer.get());

        long nonExistingId = 3L;
        Optional<Trainer> nonExistingTrainer = trainerDao.findById(nonExistingId);
        assertEquals(Optional.empty(), nonExistingTrainer);

        Trainer trainer = Trainer.builder()
                .name("Kolya")
                .surname("Rostkov")
                .courses(new ArrayList<>())
                .build();

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

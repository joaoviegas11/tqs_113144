package com.example.lab6_1;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ApplicationTests {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
        .withUsername("duke")
        .withPassword("password")
        .withDatabaseName("test");

    @Autowired
    private StudentRepository studentRepository;

    private static int studentId;

    // requires Spring Boot >= 2.2.6
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
        // registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Test
    @Order(1)
    void testInsertStudent() {
        Student student = new Student(10, "Carlos", "EI", "Mestrado");
        Student savedStudent = studentRepository.save(student);

        assertNotNull(savedStudent.getMec(), "The student ID should not be null after insertion");
        studentId = savedStudent.getMec(); // Store the ID for the next tests
        System.out.println("Insertion successful!");
    }

    @Test
    @Order(2)
    void testRetrieveStudent() {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        assertTrue(studentOptional.isPresent(), "The student should exist in the database");

        Student student = studentOptional.get();
        assertEquals("Carlos", student.getName());
        assertEquals("EI", student.getCourse());
        assertEquals("Mestrado", student.getAcademicDegree());

        System.out.println("Retrieval successful!");
    }

    @Test
    @Order(3)
    void testUpdateStudent() {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        assertTrue(studentOptional.isPresent(), "The student should exist before updating");

        Student student = studentOptional.get();
        student.setCourse("Computação");
        studentRepository.save(student);

        // Verify if the update was applied
        Student updatedStudent = studentRepository.findById(studentId).orElseThrow();
        assertEquals("Computação", updatedStudent.getCourse(), "The course should have been updated");

        System.out.println("Update successful!");
    }

    @Test
    @Order(4)
    void testRetrieveUpdatedStudent() {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        assertTrue(studentOptional.isPresent(), "The updated student should exist");

        Student student = studentOptional.get();
        assertEquals("Computação", student.getCourse(), "The course should be updated to 'Computação'");

        System.out.println("Retrieval after update successful!");
    }
}

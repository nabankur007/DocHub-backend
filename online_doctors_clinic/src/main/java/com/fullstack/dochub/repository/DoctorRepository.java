package com.fullstack.dochub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fullstack.dochub.entity.Doctor;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Doctor} entities.
 */
public interface DoctorRepository extends JpaRepository<Doctor, String> {

    /**
     * Finds a doctor by email and password.
     * 
     * @param email the doctor's email
     * @param password the doctor's password
     * @return an Optional containing the found doctor or empty if no doctor is found
     */
    Optional<Doctor> findByEmailAndPassword(String email, String password);
    
    /**
     * give the distinct departments of doctors.
     * 
     * @return list of string 
     */
    @Query("select distinct d.department from Doctor d")
    List<String> getDepartments();
    
    /**
     * give doctor list having the department
     * 
     * @return list of doctors
     */
    List<Doctor> getDoctorByDepartment(String department);
    
}

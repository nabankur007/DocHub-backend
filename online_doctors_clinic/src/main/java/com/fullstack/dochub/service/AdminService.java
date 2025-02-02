package com.fullstack.dochub.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fullstack.dochub.entity.Admin;
import com.fullstack.dochub.entity.Client;
import com.fullstack.dochub.entity.Doctor;
import com.fullstack.dochub.repository.AdminRepository;
import com.fullstack.dochub.repository.AppointmentsRepository;
import com.fullstack.dochub.repository.ClientRepository;
import com.fullstack.dochub.repository.DoctorRepository;

import jakarta.transaction.Transactional;

/**
 * Service class for managing {@link Admin}, {@link Client}, and {@link Doctor}
 * entities.
 */
@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private DoctorRepository doctorRepo;

	@Autowired
	private AppointmentsRepository appointmentsRepo;

	/**
	 * Authenticates an admin using email and password.
	 * 
	 * @param email    the admin's email
	 * @param password the admin's password
	 * @return the authenticated admin
	 * @throws IllegalArgumentException if credentials are invalid
	 */
	public Admin adminLogin(String email, String password) {
		if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Email and password must not be null or empty.");
		}

		return adminRepo.findByEmailAndPassword(email, password)
				.orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
	}

	/**
	 * Retrieves all clients.
	 * 
	 * @return a list of clients
	 */
	public List<Client> getAllClients() {
		return clientRepo.findAll();
	}

	/**
	 * Retrieves all doctors.
	 * 
	 * @return a list of doctors
	 */
	public List<Doctor> getAllDoctors() {
		return doctorRepo.findAll();
	}

	/**
	 * Deletes a client by email.
	 * 
	 * @param email the client's email
	 * @return a message indicating the result of the operation
	 */
	@Transactional
	public String deleteClient(String email) {
		appointmentsRepo.deleteByClientEmail(email);
		clientRepo.deleteById(email);
		return "Client deleted";
	}

	/**
	 * Deletes a doctor by email.
	 * 
	 * @param email the doctor's email
	 * @return a message indicating the result of the operation
	 */
	@Transactional
	public String deleteDoctor(String email) {
		// Delete related appointments first
		appointmentsRepo.deleteByDoctorEmail(email);
		doctorRepo.deleteById(email);
		return "Doctor deleted";

	}
}

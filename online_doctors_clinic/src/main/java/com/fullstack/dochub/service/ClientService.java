package com.fullstack.dochub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fullstack.dochub.entity.Client;
import com.fullstack.dochub.repository.ClientRepository;
import com.fullstack.dochub.repository.DoctorRepository;

/**
 * Service class for managing {@link Client} entities.
 */
@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private DoctorRepository doctorRepo;

	/**
	 * Registers a new client.
	 * 
	 * @param client the client to be registered
	 * @return the registered client
	 */
	public Client addNewClient(Client client) {
		if (client == null || client.getEmail() == null || client.getEmail().isEmpty()) {
			throw new IllegalArgumentException("Client information is missing or incomplete.");
		}
		return clientRepo.save(client);
	}

	/**
	 * Authenticates a client using email and password.
	 * 
	 * @param email    the client's email
	 * @param password the client's password
	 * @return the authenticated client
	 * @throws IllegalArgumentException if credentials are invalid
	 */
	public Client clientLogin(String email, String password) {
		if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Email and password must not be null or empty.");
		}
		return clientRepo.findByEmailAndPassword(email, password)
				.orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
	}

	/**
	 * Retrieves a client by email.
	 * 
	 * @param email the email of the client
	 * @return the client or null if not found
	 */
	public Client findByEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Email must not be null or empty.");
		}
		return clientRepo.findById(email).orElse(null);
	}

	/**
	 * Retrieves list of department.
	 * 
	 * @param email the email of the client
	 * @return the client or null if not found
	 */
	public List<String> getDepartments() {
		return doctorRepo.getDepartments();
	}

	/**
	 * Updates the profile of an existing doctor.
	 * 
	 * @param updatedDoctor the doctor with updated details
	 * @return the updated doctor
	 * @throws IllegalArgumentException if doctor information is invalid
	 */
	public Client updateClientProfile(Client updatedClient) {
		if (updatedClient == null || updatedClient.getEmail() == null || updatedClient.getEmail().isEmpty()) {
			throw new IllegalArgumentException("Invalid doctor information.");
		}
		Client existingClient = clientRepo.findById(updatedClient.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("client not found."));
		// Update fields
		existingClient.setName(updatedClient.getName());
		existingClient.setPhone(updatedClient.getPhone());
		existingClient.setGender(updatedClient.getGender());
		existingClient.setPassword(updatedClient.getPassword());

		return clientRepo.save(existingClient);
	}

}

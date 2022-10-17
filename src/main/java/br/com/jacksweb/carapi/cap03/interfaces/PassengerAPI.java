package br.com.jacksweb.carapi.cap03.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.jacksweb.carapi.cap03.Passenger;
import br.com.jacksweb.carapi.cap03.PassengerRepository;

@Service
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PassengerAPI {

	@Autowired
	PassengerRepository passengerRepository;

	@GetMapping()
	public List<Passenger> listPassengers() {

		return passengerRepository.findAll();
	}

	@GetMapping("/{id}")
	public Passenger findPassenger(@PathVariable("id") Long id) {

		return passengerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	}

	@PostMapping
	public Passenger createDriver(@RequestBody Passenger passenger) {

		return passengerRepository.save(passenger);

	}

	@PutMapping("/{id}")
	public Passenger fullUpdateDriver(@PathVariable("id") Long id, @RequestBody Passenger passenger) {
		Passenger foundPassager = findPassenger(id);
		foundPassager.setName(passenger.getName());
		return passengerRepository.save(foundPassager);

	}

	@PatchMapping("/{id}")
	public Passenger incrementalUpdateDriver(@PathVariable("id") Long id, @RequestBody Passenger passenger) {
		Passenger foundPassager = findPassenger(id);

		foundPassager.setName(Optional.ofNullable(passenger.getName()).orElse(foundPassager.getName()));
		return passengerRepository.save(foundPassager);

	}

	@DeleteMapping("/{id}")
	public void deletPassenger(@PathVariable("id") Long id) {

		passengerRepository.delete(findPassenger(id));

	}

}

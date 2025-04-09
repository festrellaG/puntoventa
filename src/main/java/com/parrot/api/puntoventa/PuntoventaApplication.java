package com.parrot.api.puntoventa;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class PuntoventaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuntoventaApplication.class, args);
	}

}

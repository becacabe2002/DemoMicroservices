package com.mtu.inventoryservice;

import com.mtu.inventoryservice.model.Inventory;
import com.mtu.inventoryservice.repository.InventoryRepository;
import com.netflix.discovery.EurekaNamespace;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("obj 1");
			inventory1.setQuantity(100);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("obj 2");
			inventory2.setQuantity(100);
			List<Inventory> invList = new ArrayList<Inventory>();
			invList.add(inventory1);
			invList.add(inventory2);
			inventoryRepository.saveAll(invList);
		};
	}
}

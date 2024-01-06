package com.batanasov.javaTransportCompanyProjectSPRING;

import com.batanasov.javaTransportCompanyProjectSPRING.DTO.*;
import com.batanasov.javaTransportCompanyProjectSPRING.Enums.Qualification;
import com.batanasov.javaTransportCompanyProjectSPRING.Enums.VehicleType;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.*;
import com.batanasov.javaTransportCompanyProjectSPRING.repository.EmployeeRepository;
import com.batanasov.javaTransportCompanyProjectSPRING.repository.TransportCompanyRepository;
import com.batanasov.javaTransportCompanyProjectSPRING.repository.TransportContractRepository;
import com.batanasov.javaTransportCompanyProjectSPRING.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class JavaTransportCompanyProjectSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaTransportCompanyProjectSpringApplication.class, args);


	}

	@Bean
	public CommandLineRunner testingRunner(TransportCompanyService companyService,
										   ClientService clientService,
										   VehicleService vehicleService,
										   EmployeeService employeeService,
										   TransportContractService contractService,
										   TransportCompanyRepository transportCompanyRepository,
										   EmployeeRepository employeeRepository,
										   TransportContractRepository transportContractRepository) {
		return args -> {
		// Create and save TransportCompany
			TransportCompany savedCompany = companyService.createTransportCompany(
					new TransportCompany().withName("TransCo")
							.withAddress("Sofia")
							.withRevenue(10000.0))
					.withVehicles(new HashSet<>())
					.withContracts(new HashSet<>())
					.withEmployees(new HashSet<>());

			// Create and save Vehicles
			Vehicle vehicle1 = new Vehicle().withLicensePlate("CA1234CA")
					.withType(VehicleType.TRUCK)
					.withCapacity(1000.0)
					.withCompany(savedCompany);
			Vehicle vehicle2 = new Vehicle().withLicensePlate("CA4321CA")
					.withType(VehicleType.TRUCK)
					.withCapacity(500.0)
					.withCompany(savedCompany);
			Vehicle vehicle3 = new Vehicle().withLicensePlate("CA1111CA")
					.withType(VehicleType.TRUCK)
					.withCapacity(1500.0)
					.withCompany(savedCompany);

			Vehicle savedVehicle1 = vehicleService.createVehicle(vehicle1);
			Vehicle savedVehicle2 = vehicleService.createVehicle(vehicle2);
			Vehicle savedVehicle3 = vehicleService.createVehicle(vehicle3);

			// Create and save Employees
			Employee employee1 = new Employee().withName("Ivan")
					.withPosition("Driver")
					.withSalary(BigDecimal.valueOf(1000.0))
					.withQualification(Qualification.HEAVY_VEHICLES)
					.withCompany(savedCompany);
			Employee employee2 = new Employee().withName("Asen")
					.withPosition("Driver")
					.withSalary(BigDecimal.valueOf(1200.0))
					.withQualification(Qualification.HEAVY_VEHICLES)
					.withCompany(savedCompany);
			Employee employee3 = new Employee().withName("Mitko")
					.withPosition("Driver")
					.withSalary(BigDecimal.valueOf(1100.0))
					.withQualification(Qualification.HEAVY_VEHICLES)
					.withCompany(savedCompany);

			Employee savedEmployee1 = employeeService.createEmployee(employee1);
			Employee savedEmployee2 = employeeService.createEmployee(employee2);
			Employee savedEmployee3 = employeeService.createEmployee(employee3);

			// Create and save Clients
			Client client1 = new Client().withName("Nestle")
					.withContactDetails("+359888888888")
					.withNeedsToPay(true)
					.withOutstandingPayments(1000.0);
			Client client2 = new Client().withName("Coca-Cola")
					.withContactDetails("+359888888889")
					.withNeedsToPay(true)
					.withOutstandingPayments(2000.0);
			Client client3 = new Client().withName("Pepsi")
					.withContactDetails("+359888888890")
					.withNeedsToPay(true)
					.withOutstandingPayments(3000.0);

			Client savedClient1 = clientService.createClient(client1);
			Client savedClient2 = clientService.createClient(client2);
			Client savedClient3 = clientService.createClient(client3);

			// Create and save TransportContracts
			TransportContract contract1 = new TransportContract().withDestination("Sofia")
					.withStartDate(LocalDate.of(2025, 1, 1))
					.withPrice(1000.0)
					.withCargoDetails("Food")
					.withStatus(true)
					.withWeight(1000.0)
					.withCompany(savedCompany)
					.withClient(savedClient1)
					.withVehicle(savedVehicle1)
					.withEmployee(savedEmployee1);
			TransportContract contract2 = new TransportContract().withDestination("Varna")
					.withStartDate(LocalDate.of(2025, 2, 1))
					.withPrice(2000.0)
					.withCargoDetails("Metal")
					.withStatus(true)
					.withWeight(2000.0)
					.withCompany(savedCompany)
					.withClient(savedClient2)
					.withVehicle(savedVehicle2)
					.withEmployee(savedEmployee2);
			TransportContract contract3 = new TransportContract().withDestination("Plovdiv")
					.withStartDate(LocalDate.of(2025, 3, 1))
					.withPrice(1500.0)
					.withCargoDetails("Furniture")
					.withStatus(true)
					.withWeight(1500.0)
					.withCompany(savedCompany)
					.withClient(savedClient3)
					.withVehicle(savedVehicle3)
					.withEmployee(savedEmployee3);

			TransportContract savedContract1 = contractService.createTransportContract(contract1);
			TransportContract savedContract2 = contractService.createTransportContract(contract2);
			TransportContract savedContract3 = contractService.createTransportContract(contract3);

			// Update TransportCompany with new relationships
			savedCompany.setVehicles(Set.of(savedVehicle1, savedVehicle2, savedVehicle3));
			savedCompany.setContracts(Set.of(savedContract1, savedContract2, savedContract3));
			savedCompany.setEmployees(Set.of(savedEmployee1, savedEmployee2, savedEmployee3));
			companyService.updateTransportCompany(savedCompany.getCompanyId(), savedCompany);


			// Test custom query methods
			// Get total revenue for a period
			LocalDate startDate = LocalDate.of(2025, 1, 1);
			LocalDate endDate = LocalDate.now();
			Double totalRevenue = contractService.getCompanyRevenueForPeriod(startDate, endDate);
			System.out.println("Total Revenue: " + totalRevenue);

			//-----------------------------------------------------------------------------------------------

			// Test sorting and filtering methods - custom queries

			// TransportCompanyRepository custom queries

			// Find all transport companies ordered by revenue in descending order and then by name in ascending order
			List<TransportCompanyDTO> companiesByRevenueAndName = transportCompanyRepository.findAllByOrderByRevenueDescNameAsc();
			System.out.println("Companies ordered by revenue (desc) and name (asc): " + companiesByRevenueAndName);

			// Find transport companies with revenue greater than or equal to a specified minimum
			List<TransportCompanyDTO> companiesByMinRevenue = transportCompanyRepository.findByRevenueGreaterThanEqual(10000.0);
			System.out.println("Companies with revenue >= 10000: " + companiesByMinRevenue);

			// Find transport companies where the address contains a specific string
			List<TransportCompanyDTO> companiesByAddress = transportCompanyRepository.findByAddressContaining("New York");
			System.out.println("Companies with 'New York' in address: " + companiesByAddress);

			// Find all transport companies ordered by name in descending order
			List<TransportCompanyDTO> companiesByNameDesc = transportCompanyRepository.findAllByOrderByNameDesc();
			System.out.println("Companies ordered by name (desc): " + companiesByNameDesc);

			// EmployeeRepository custom queries

			// Find employees with a specific qualification and a salary greater than a certain amount
			List<EmployeeDTO> employeesByQualificationAndSalary = employeeRepository.findByQualificationAndSalaryGreaterThan(Qualification.HEAVY_VEHICLES, BigDecimal.valueOf(2000));
			System.out.println("Employees with qualification HEAVY_VEHICLES and salary > 2000: " + employeesByQualificationAndSalary);

			// Find employees with a specific position and a salary less than a certain amount
			List<EmployeeDTO> employeesByPositionAndSalary = employeeRepository.findByPositionAndSalaryLessThan("Driver", BigDecimal.valueOf(1500));
			System.out.println("Employees with position 'Driver' and salary < 1500: " + employeesByPositionAndSalary);

			// Find employees with a specific qualification
			List<EmployeeDTO> employeesByQualification = employeeRepository.findByQualification(Qualification.HEAVY_VEHICLES);
			System.out.println("Employees with qualification HEAVY_VEHICLES: " + employeesByQualification);

			// Find employees with a salary within a specific range
			List<EmployeeDTO> employeesBySalaryRange = employeeRepository.findBySalaryBetween(BigDecimal.valueOf(1000), BigDecimal.valueOf(3000));
			System.out.println("Employees with salary between 1000 and 3000: " + employeesBySalaryRange);

			// Find employees ordered by name in ascending order
			List<EmployeeDTO> employeesByNameAsc = employeeRepository.findAllByOrderByNameAsc();
			System.out.println("Employees ordered by name (asc): " + employeesByNameAsc);

			// TransportContractRepository custom queries

			// Find transport contracts with a specific destination
			List<TransportContractDTO> contractsByDestination = transportContractRepository.findByDestination("Sofia");
			System.out.println("Contracts with destination 'Sofia': " + contractsByDestination);

			// Find transport contracts where cargo details contain a specific string
			List<TransportContractDTO> contractsByCargoDetails = transportContractRepository.findByCargoDetailsContaining("Food");
			System.out.println("Contracts with 'Food' in cargo details: " + contractsByCargoDetails);

			// Find transport contracts with a price within a specific range
			List<TransportContractDTO> contractsByPriceRange = transportContractRepository.findByPriceBetween(500.0, 2000.0);
			System.out.println("Contracts with price between 500 and 2000: " + contractsByPriceRange);

			// Find transport contracts with a specific status
			List<TransportContractDTO> contractsByStatus = transportContractRepository.findByStatus(true);
			System.out.println("Contracts with status true: " + contractsByStatus);

			// Find transport contracts ordered by price in ascending order
			List<TransportContractDTO> contractsByPriceAsc = transportContractRepository.findAllByOrderByPriceAsc();
			System.out.println("Contracts ordered by price (asc): " + contractsByPriceAsc);

			// Find by client
			List<TransportContractDTO> contractsByClient = transportContractRepository.findByClient(savedClient1);
			System.out.println("Contracts by client: " + contractsByClient);

			//-----------------------------------------------------------------------------------------------

			// Test file export/import
			String filename = "transport_data.csv";
			contractService.exportTransportContractsToCSV(filename);
			List<TransportContract> importedContracts = contractService.importTransportContractsFromCSV(filename);
			importedContracts.forEach(System.out::println);

			//-----------------------------------------------------------------------------------------------
			// Test report generation

			// Get revenue by employee
			List<EmployeeRevenueDTO> revenueByEmployee = employeeService.getEmployeeRevenue();
			revenueByEmployee.forEach(dto -> System.out.println("Employee ID: " + dto.getEmployeeId() + ", Revenue: " + dto.getTotalRevenue()));

			// Get total number of transport contracts
			long totalContracts = contractService.getTotalNumberOfTransportContracts();
			System.out.println("Total Number of Transport Contracts: " + totalContracts);

			//Sum all revenue from transport contracts
			double totalRevenueFromContracts = contractService.getTotalRevenueFromTransportContracts();
			System.out.println("Total Revenue from Contracts: " + totalRevenueFromContracts);

			// Number of contracts for each employee, calls the EmployeeService and EmployeeRepository
			List<EmployeeContractCountDTO> employeeContractCounts = employeeService.getEmployeeContractCounts();
			employeeContractCounts.forEach(dto ->
					System.out.println("Employee ID: " + dto.getEmployee().getEmployeeId()
							+ ", Contract Count: " + dto.getContractCount()));


		};
	}

}

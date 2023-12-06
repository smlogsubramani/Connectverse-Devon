package gmc.project.connectversev3.userservice.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import gmc.project.connectversev3.userservice.daos.EmployeeDao;
import gmc.project.connectversev3.userservice.daos.JobsDao;
import gmc.project.connectversev3.userservice.daos.ReportsDao;
import gmc.project.connectversev3.userservice.entities.EmployeeEntity;
import gmc.project.connectversev3.userservice.entities.HamletEntity;
import gmc.project.connectversev3.userservice.entities.JobEntity;
import gmc.project.connectversev3.userservice.entities.ReportEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchedulingService {

	@Autowired
	private Environment environment;

	@Autowired
	private JobsDao jobsDao;
	@Autowired
	private UserService userService;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private ReportsDao reportsDao;
	
	@Autowired(required = false)
	private ProphetServiceFeignClient prophetServiceFeignClient;
	@Autowired(required = false)
	private JobServiceFeighClient jobServiceFeighClient;
	

//	@Scheduled(fixedDelay = 860000)
	public void dailyRoutine() {
		log.info("Good Morning!");
		List<EmployeeEntity> unOrganizedWorkers = employeeDao.findByIsTechnicalWorker(false);
		Stream<EmployeeEntity> vettiOfficers = unOrganizedWorkers.stream()
				.filter(employee -> employee.getIsOccupied().equals(false));
		Stream<EmployeeEntity> sam = vettiOfficers.filter(employee -> employee.getIsBlocked().equals(false));
		
		sam.forEach(employee -> {

			if(employee.getWaitingForJobTime() > 3) {
				// apply job through SMS
				applyJob(employee, "SMS");
			} else if(employee.getWaitingForJobTime() > 7) {
				// apply job through PDO
				applyJob(employee, "POST");
			} else {
				// apply through AI
				applyJob(employee, "AI");
			}
			
			if(employee.getInactiveJobSeekTime() > 10) {
				employee.setIsBlocked(true);
			}

			employee.setWaitingForJobTime(employee.getWaitingForJobTime() + 1);
			employee.setInactiveJobSeekTime(employee.getInactiveJobSeekTime() + 1);
			employeeDao.save(employee);
		});
	}

//	@Scheduled(fixedDelay = 60000)
	public void fetchAndAssignJob() {
		log.error("One Min...");
		List<EmployeeEntity> employees = userService.fetchFromEshram();
		employees.forEach(employee -> {
			log.error("Sending SMS to emplpoyee{}.", employee.getFirstName());
			applyJob(employee, "SMS");
		});

	}

	public void applyJob(EmployeeEntity employee, String type) {
		String applyBaseMLUrl = environment.getProperty("applyBaseMLUrl");

		List<JobEntity> foundJobs = jobsDao.findByIsTechnicalJob(false);
		Stream<JobEntity> filteredJobs = foundJobs.stream().filter(job -> job.getIsHidden() == false);

		if(type.equals("AI")) {
			filteredJobs.forEach(job -> {

				Integer payPerHour_input = job.getPayPerHour();
				Integer workHoursPerWeekGreatethan21Hours_input = 0;
				Integer hasDrivingLicense_input = 1;
				Integer hasvehicle_input = 1;
				Integer waitingTime_input = employee.getWaitingForJobTime();
				Integer blokSameAsJob_input = 0;
				Integer districtSameAsJob_input = 0;
				Integer stateSameAsJob_input = 0;
				Integer readyToRelocate_input = 0;

				if (job.getWorkHoursPerWeek() > 21)
					workHoursPerWeekGreatethan21Hours_input = 1;
				if (job.getDrivingLicenceRequired())
					if (!employee.getHasDrivingLicence()) {
						hasDrivingLicense_input = 0;
						hasvehicle_input = 0;
					}
				if (job.getLocation().equals(employee.getAddress())) {
					blokSameAsJob_input = 1;
					districtSameAsJob_input = 1;
					stateSameAsJob_input = 1;
				} else if (job.getLocation().equals(employee.getLocation())) {
					blokSameAsJob_input = 0;
					districtSameAsJob_input = 1;
					stateSameAsJob_input = 1;
				} else if (job.getLocation().equals(employee.getState().toString())) {
					blokSameAsJob_input = 0;
					districtSameAsJob_input = 0;
					stateSameAsJob_input = 1;
				} else {
					log.error("False");
				}

				if (employee.getReadyToRelocate())
					readyToRelocate_input = 1;

				String canApplyJobUrl = applyBaseMLUrl + "/" + payPerHour_input + "/"
						+ workHoursPerWeekGreatethan21Hours_input + "/" + hasDrivingLicense_input + "/"
						+ hasvehicle_input + "/" + waitingTime_input + "/" + blokSameAsJob_input + "/"
						+ districtSameAsJob_input + "/" + stateSameAsJob_input + "/" + readyToRelocate_input + "/";

				log.error(applyBaseMLUrl);

				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> response = restTemplate.getForEntity(canApplyJobUrl, String.class);
				
				if(response.getBody().equals("1")) {
					job.getEmployeesApplied().add(employee);
					jobsDao.save(job);
				}

				log.error("Response: " + response.getBody());
			});
		} else if(type.equals("SMS")) {
			try {
				jobServiceFeighClient.sendTenJobs();
			} catch(Exception e) {
				e.printStackTrace();
				ReportEntity report = new ReportEntity();
				report.setDescription("Failed to send Message about job for user with user Id: " + employee.getId());
				report.setCause("Sms failed");
				report.setSystemLog("Solla mudiyathu");
				report.setSentFrom("User Microservice - gmc/project/connectversev3/userservice/services/SchedulingService");
				report.setIsFixed(false);
				report.setOccuredAt(LocalDateTime.now());
				ReportEntity savedReport = reportsDao.save(report);
				log.error("Failed to send SMS Reported Reference: {}.", savedReport.getId());
			}
//			String msgBody = "";
//			filteredJobs.forEach(job -> {
//
//				Integer payPerHour_input = job.getPayPerHour();
//				Integer workHoursPerWeekGreatethan21Hours_input = 0;
//				Integer hasDrivingLicense_input = 1;
//				Integer hasvehicle_input = 1;
//				Integer waitingTime_input = employee.getWaitingForJobTime();
//				Integer blokSameAsJob_input = 0;
//				Integer districtSameAsJob_input = 0;
//				Integer stateSameAsJob_input = 0;
//				Integer readyToRelocate_input = 0;
//
//				if (job.getWorkHoursPerWeek() > 21)
//					workHoursPerWeekGreatethan21Hours_input = 1;
//				if (job.getDrivingLicenceRequired())
//					if (!employee.getHasDrivingLicence()) {
//						hasDrivingLicense_input = 0;
//						hasvehicle_input = 0;
//					}
//				if (job.getLocation().equals(employee.getAddress())) {
//					blokSameAsJob_input = 1;
//					districtSameAsJob_input = 1;
//					stateSameAsJob_input = 1;
//				} else if (job.getLocation().equals(employee.getLocation())) {
//					blokSameAsJob_input = 0;
//					districtSameAsJob_input = 1;
//					stateSameAsJob_input = 1;
//				} else if (job.getLocation().equals(employee.getState().toString())) {
//					blokSameAsJob_input = 0;
//					districtSameAsJob_input = 0;
//					stateSameAsJob_input = 1;
//				} else {
//					log.error("False");
//				}
//
//				if (employee.getReadyToRelocate())
//					readyToRelocate_input = 1;
//
//				String canApplyJobUrl = applyBaseMLUrl + "/" + payPerHour_input + "/"
//						+ workHoursPerWeekGreatethan21Hours_input + "/" + hasDrivingLicense_input + "/"
//						+ hasvehicle_input + "/" + waitingTime_input + "/" + blokSameAsJob_input + "/"
//						+ districtSameAsJob_input + "/" + stateSameAsJob_input + "/" + readyToRelocate_input + "/";
//
//				log.error(applyBaseMLUrl);
//
//				RestTemplate restTemplate = new RestTemplate();
//				ResponseEntity<String> response = restTemplate.getForEntity(canApplyJobUrl, String.class);
//
//				if(response.getBody().equals("1")) {
//					msgBody.concat(job.getId().toString()).concat("-"+job.getTittle()).concat("-"+job.getPayPerHour()).concat(" ");
//				}
//
//				log.error("Response: " + response.getBody());
//			});
//			
//			log.error(msgBody);
//			
//			try {
//				prophetServiceFeignClient.sendSMS("+91" + employee.getMobileNumber(), msgBody);
//			} catch(Exception e) {
//				ReportEntity report = new ReportEntity();
//				report.setDescription("Failed to send Message about job for user with user Id: " + employee.getId());
//				report.setCause(e.getCause().toString());
//				report.setSystemLog(e.getMessage());
//				report.setSentFrom("User Microservice - gmc/project/connectversev3/userservice/services/SchedulingService");
//				report.setIsFixed(false);
//				report.setOccuredAt(LocalDateTime.now());
//				ReportEntity savedReport = reportsDao.save(report);
//				log.error("Failed to send SMS Reported Reference: {}.", savedReport.getId());
//			}
			
		} else if(type.equals("POST")) {
			log.info("Post successfully sent to: {}.", employee.getFirstName());
		} else {
			log.error("Error!");
		}
	}

}

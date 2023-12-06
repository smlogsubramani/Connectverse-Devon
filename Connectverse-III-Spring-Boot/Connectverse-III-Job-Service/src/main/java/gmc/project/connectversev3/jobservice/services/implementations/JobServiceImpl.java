package gmc.project.connectversev3.jobservice.services.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gmc.project.connectversev3.jobservice.daos.CompanyDao;
import gmc.project.connectversev3.jobservice.daos.EmployeeDao;
import gmc.project.connectversev3.jobservice.daos.EmployerDao;
import gmc.project.connectversev3.jobservice.daos.JobDao;
import gmc.project.connectversev3.jobservice.daos.JobDeclineDao;
import gmc.project.connectversev3.jobservice.daos.ReportsDao;
import gmc.project.connectversev3.jobservice.daos.SchemesDao;
import gmc.project.connectversev3.jobservice.daos.SkillDao;
import gmc.project.connectversev3.jobservice.entities.CompanyEntity;
import gmc.project.connectversev3.jobservice.entities.EmployeeEntity;
import gmc.project.connectversev3.jobservice.entities.EmployerEntity;
import gmc.project.connectversev3.jobservice.entities.JobDeclineEntity;
import gmc.project.connectversev3.jobservice.entities.JobEntity;
import gmc.project.connectversev3.jobservice.entities.SkillEntity;
import gmc.project.connectversev3.jobservice.exceptions.EmployeeNotFoundException;
import gmc.project.connectversev3.jobservice.exceptions.EmployerNotFoundException;
import gmc.project.connectversev3.jobservice.exceptions.JobNotFoundException;
import gmc.project.connectversev3.jobservice.exceptions.JobRulesViolationException;
import gmc.project.connectversev3.jobservice.exceptions.SkillNotFoundException;
import gmc.project.connectversev3.jobservice.exceptions.UserNotFoundException;
import gmc.project.connectversev3.jobservice.models.CreateOrUpdateCompanyModel;
import gmc.project.connectversev3.jobservice.models.JobCreateOrUpdateModel;
import gmc.project.connectversev3.jobservice.models.JobModel;
import gmc.project.connectversev3.jobservice.models.ListJobModel;
import gmc.project.connectversev3.jobservice.services.JobService;
import gmc.project.connectversev3.jobservice.services.ProphetServiceFeignClient;
import gmc.project.connectversev3.jobservice.services.SchemesService;
import gmc.project.connectversev3.jobservice.shared.MailingModel;
import gmc.project.connectversev3.jobservice.entities.ReportEntity;
import gmc.project.connectversev3.jobservice.entities.SchemesEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private Environment environment;

	@Autowired
	private JobDao jobDao;
	@Autowired
	private SkillDao skillDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private EmployerDao employerDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private ReportsDao reportsDao;
	@Autowired
	private JobDeclineDao jobDeclineDao;

	@Autowired(required = false)
	private ProphetServiceFeignClient prophetServiceFeignClient;
	
	@Autowired
	private SchemesService schemeService;
	@Autowired
	private SchemesDao schemesDao;
	
	
	@Override
	public JobEntity findOne(Long jobId) {
		JobEntity returnValue = jobDao.findById(jobId).orElse(null);
		if (returnValue == null)
			throw new JobNotFoundException("Job Id: " + jobId);
		return returnValue;
	}

	@Override
	public List<ListJobModel> getAllJobs() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<ListJobModel> returnValue = new ArrayList<>();
		jobDao.findByIsCompleted(false).forEach(job -> {
			ListJobModel jobModel = modelMapper.map(job, ListJobModel.class);
			if(jobModel.getCompany() == null) {
				CreateOrUpdateCompanyModel companyCreate = new CreateOrUpdateCompanyModel();
				companyCreate.setImageUrl("https://www.betterteam.com/images/betterteam-free-job-posting-sites-5877x3918-20210222.jpg?crop=40:21,smart&width=1200&dpr=2");
				jobModel.setCompany(companyCreate);
			}
			returnValue.add(jobModel);
		});
		return returnValue;
	}

	@Override
	public JobModel getAJob(Long jobId) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		JobEntity foundJob = findOne(jobId);
		JobModel returnValue = modelMapper.map(foundJob, JobModel.class);
//		returnValue.setEmployerId(foundJob.getCompany().getEmployer().getId());
		return returnValue;
	}

	@Override
	public void createOrUpdateJob(JobCreateOrUpdateModel jobCreateOrUpdateModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		if (jobCreateOrUpdateModel.getId() == null) {
			JobEntity detached = modelMapper.map(jobCreateOrUpdateModel, JobEntity.class);
			if (jobCreateOrUpdateModel.getSkillIds() != null) {
				Set<SkillEntity> skills = new HashSet<>();
				jobCreateOrUpdateModel.getSkillIds().forEach(skillId -> {
					SkillEntity reqestedSkill = skillDao.findById(skillId).orElse(null);
					if (reqestedSkill == null)
						throw new SkillNotFoundException("Skill Id: " + skillId);
					skills.add(reqestedSkill);
				});
				detached.setSkills(skills);
			}
			EmployerEntity employer = employerDao.findById(jobCreateOrUpdateModel.getEmployerId()).orElse(null);
			CompanyEntity company = employer.getCompany();
			detached.setCompany(company);
			JobEntity savedJob = jobDao.save(detached);
			company.getJobs().add(savedJob);
			company.setIsEnabled(true);
			companyDao.save(company);
			if (jobCreateOrUpdateModel.getSkillIds() != null) {
				jobCreateOrUpdateModel.getSkillIds().forEach(skillId -> {
					SkillEntity reqestedSkill = skillDao.findById(skillId).orElse(null);
					if (reqestedSkill == null)
						throw new SkillNotFoundException("Skill Id: " + skillId);
					reqestedSkill.getJobs().add(savedJob);
					skillDao.save(reqestedSkill);
				});
			}
		} else {
			JobEntity detached = findOne(jobCreateOrUpdateModel.getId());
			modelMapper.map(jobCreateOrUpdateModel, detached);
			if (jobCreateOrUpdateModel.getSkillIds() != null) {
				Set<SkillEntity> skills = new HashSet<>();
				jobCreateOrUpdateModel.getSkillIds().forEach(skillId -> {
					SkillEntity reqestedSkill = skillDao.findById(skillId).orElse(null);
					if (reqestedSkill == null)
						throw new SkillNotFoundException("Skill Id: " + skillId);
					skills.add(reqestedSkill);
				});
				detached.setSkills(skills);
			}

			EmployerEntity employer = employerDao.findById(jobCreateOrUpdateModel.getEmployerId()).orElse(null);
			CompanyEntity company = employer.getCompany();
			detached.setCompany(company);
			detached.setEmployerId(jobCreateOrUpdateModel.getEmployerId());
			JobEntity savedJob = jobDao.save(detached);
			company.getJobs().add(savedJob);
			companyDao.save(company);
			if (jobCreateOrUpdateModel.getSkillIds() != null) {
				jobCreateOrUpdateModel.getSkillIds().forEach(skillId -> {
					SkillEntity reqestedSkill = skillDao.findById(skillId).orElse(null);
					if (reqestedSkill == null)
						throw new SkillNotFoundException("Skill Id: " + skillId);
					reqestedSkill.getJobs().add(savedJob);
					skillDao.save(reqestedSkill);
				});
			}
		}
	}

	@Override
	public void applyForJob(Long jobId, String employeeId) {
		JobEntity foundJob = findOne(jobId);
		EmployeeEntity foundEmployee = employeeDao.findById(employeeId).orElse(null);
		if (foundEmployee == null)
			throw new EmployeeNotFoundException("Employee Id: " + employeeId);
		foundEmployee.getJobsApplied().add(foundJob);
		foundEmployee.setInactiveJobSeekTime(0);
		foundJob.getEmployeesApplied().add(foundEmployee);
		jobDao.save(foundJob);
	}

	@Override
	public void acceptJoiningRequest(Long jobId, String employeeId) {
		JobEntity foundJob = findOne(jobId);
		EmployeeEntity foundEmployee = employeeDao.findById(employeeId).orElse(null);
		if (foundEmployee == null)
			throw new EmployeeNotFoundException("Employee Id: " + employeeId);
		foundEmployee.getJobsApplied().remove(foundJob);
		foundEmployee.setIsOccupied(true);
		foundEmployee.setWaitingForJobTime(0);
		foundJob.getEmployeesApplied().remove(foundEmployee);
		foundEmployee.setJob(foundJob);
		foundJob.getEmployees().add(foundEmployee);
		
		SchemesEntity scheme = schemesDao.findByWorkType(foundJob.getJobType());
		scheme.getAppliedEmployees().add(foundEmployee);
		foundEmployee.getAppliedSchemes().add(scheme);
		jobDao.save(foundJob);
		schemesDao.save(scheme);
		
		MailingModel mail = new MailingModel();
		mail.setTo(foundEmployee.getEmail());
		mail.setSubject("Stage-1 Passed" + foundJob.getTittle());
		mail.setBody("Your resume have been short listed for next round of filteration in a job " + foundJob.getTittle()
				+ " Posted By " + foundJob.getCompany().getName() + " All The Best!.");

		try {
			prophetServiceFeignClient.sendMail(mail);
		} catch (Exception e) {
			ReportEntity report = new ReportEntity();
			report.setDescription(
					"Failed to send Mail about resume stage 1 passed for user with user Id: " + employeeId);
			report.setCause("Prophet service Feigh client");
			report.setSystemLog("Exception handled");
			report.setSentFrom("Jobs Microservice - gmc/project/connectversev3/jobservice/services/JobServiceImpl");
			report.setIsFixed(false);
			report.setOccuredAt(LocalDateTime.now());
			ReportEntity savedReport = reportsDao.save(report);
			log.error("Failed to send SMS Reported Reference: {}.", savedReport.getId());
		}
	}

	@Override
	public void rejectJoiningRequest(Long jobId, String employeeId, String reason) {
		JobEntity foundJob = findOne(jobId);
		EmployeeEntity foundEmployee = employeeDao.findById(employeeId).orElse(null);
		if (foundEmployee == null)
			throw new EmployeeNotFoundException("Employee Id: " + employeeId);
		foundEmployee.getJobsApplied().remove(foundJob);
		foundJob.getEmployeesApplied().remove(foundEmployee);
		jobDao.save(foundJob);

		MailingModel mail = new MailingModel();
		mail.setTo(foundEmployee.getEmail());
		mail.setSubject("Job Update" + foundJob.getTittle());
		mail.setBody(
				"Your Resuma is wonderful and meets all qualifications that we wanted but unfortunately we won't be moving forward with your application in a job "
						+ foundJob.getTittle() + " Posted By " + foundJob.getCompany().getName() + " Best Of Luck!.");

		try {
			prophetServiceFeignClient.sendMail(mail);
		} catch (Exception e) {
			ReportEntity report = new ReportEntity();
			report.setDescription(
					"Failed to send Mail about Rejection for user with user Id: " + employeeId + " to a job.");
			report.setCause(e.getCause().toString());
			report.setSystemLog(e.getMessage());
			report.setSentFrom("Jobs Microservice - gmc/project/connectversev3/jobservice/services/JobServiceImpl");
			report.setIsFixed(false);
			report.setOccuredAt(LocalDateTime.now());
			ReportEntity savedReport = reportsDao.save(report);
			log.error("Failed to send SMS Reported Reference: {}.", savedReport.getId());
		}

		JobDeclineEntity jobDeclineEntity = new JobDeclineEntity();
		jobDeclineEntity.setReason(reason);
		jobDeclineEntity.setEmployeeId(employeeId);
		jobDeclineEntity.setJobId(jobId.toString());
		jobDeclineEntity.setHappedAt(LocalDateTime.now());
		jobDeclineDao.save(jobDeclineEntity);
	}

	@Override
	public void applyJobThroughSMS(Long jobId, String employeeId) {
		EmployeeEntity foundEmployee = employeeDao.findById(employeeId).orElse(null);
		if (foundEmployee == null)
			throw new UserNotFoundException(employeeId);
		foundEmployee.setKnowsToOperateMobile(true);
		foundEmployee.setKnowsToReadAndWrite(true);
		foundEmployee.setCreditPoints(10);
		employeeDao.save(foundEmployee);
		applyForJob(jobId, employeeId);
	}

	@Override
	public void reportJob(Long jobId, String employeeId, String employerId, String points) {
		JobEntity foundJob = findOne(jobId);
		EmployeeEntity foundEmployee = employeeDao.findById(employeeId).orElse(null);
		if (foundEmployee == null)
			throw new EmployeeNotFoundException("Employee Id: " + employeeId);
		EmployerEntity foundEmployer = employerDao.findById(employerId).orElse(null);
		if (foundEmployer == null)
			throw new EmployerNotFoundException("Employer Id: " + employerId);
		Boolean isEmployer = foundJob.getCompany().getEmployer().equals(foundEmployer);
		if (!isEmployer)
			throw new JobRulesViolationException("Only employer of the job can report the employee");
		EmployeeEntity isEmployee = foundJob.getEmployees().stream().filter(employee -> employee.getId() == employeeId)
				.findFirst().orElse(null);
		if (isEmployee == null)
			throw new JobRulesViolationException("The Employer not regisered for this jobs");
		Integer point = foundEmployee.getReportPoints() + Integer.valueOf(points);
		foundEmployee.setReportPoints(point);
		employeeDao.save(foundEmployee);
	}

	@Override
	public void sendTenJobSuggestions() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<EmployeeEntity> employees = employeeDao.findByIsTechnicalWorker(false);
		List<JobEntity> jobs = jobDao.findByIsTechnicalJob(false);

		employees.forEach(employee -> {
			List<JobEntity> returnValue = new ArrayList<>();
			String msgBody = "Greetings!";

			Integer count = 1;
			for (JobEntity job : jobs) {

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

				String applyBaseMLUrl = environment.getProperty("applyBaseMLUrl");
				String canApplyJobUrl = applyBaseMLUrl + "/" + payPerHour_input + "/"
						+ workHoursPerWeekGreatethan21Hours_input + "/" + hasDrivingLicense_input + "/"
						+ hasvehicle_input + "/" + waitingTime_input + "/" + blokSameAsJob_input + "/"
						+ districtSameAsJob_input + "/" + stateSameAsJob_input + "/" + readyToRelocate_input + "/";

				log.error(applyBaseMLUrl);

				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> response = restTemplate.getForEntity(canApplyJobUrl, String.class);

				if (response.getBody().equals("1")) {
					count += 1;
					returnValue.add(job);
					msgBody += job.getId() + "-" + count + "-" + job.getTittle() + "-"
							+ job.getPayPerHour() + ".";
					
					log.error(msgBody);
					if (returnValue.size() == jobs.size() || returnValue.size() == 9)
						break;
				}

			}

			try {
				prophetServiceFeignClient.sendSMS("+91" + employee.getMobileNumber(), msgBody);
			} catch (Exception e) {
				e.printStackTrace();
				ReportEntity report = new ReportEntity();
				report.setDescription("Failed to send Message about job for user with user Id: " + employee.getId());
				report.setCause("Poda poopkjjb");
				report.setSystemLog("kjhgg");
				report.setSentFrom(
						"User Microservice - gmc/project/connectversev3/userservice/services/SchedulingService");
				report.setIsFixed(false);
				report.setOccuredAt(LocalDateTime.now());
				ReportEntity savedReport = reportsDao.save(report);
				log.error("Failed to send SMS Reported Reference: {}.", savedReport.getId());
			}
		});

	}

	@Override
	public List<ListJobModel> getTenJobForEmployee(String employeeId) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<JobEntity> jobs = jobDao.findByIsTechnicalJob(false);
		EmployeeEntity employee = employeeDao.findById(employeeId).orElse(null);
		if (employee == null)
			throw new EmployeeNotFoundException("Employee Id: " + employeeId);
		List<ListJobModel> returnValue = new ArrayList<>();

		Integer count = 1;
		for (JobEntity job : jobs) {
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

			String applyBaseMLUrl = environment.getProperty("applyBaseMLUrl");
			String canApplyJobUrl = applyBaseMLUrl + "/" + payPerHour_input + "/"
					+ workHoursPerWeekGreatethan21Hours_input + "/" + hasDrivingLicense_input + "/" + hasvehicle_input
					+ "/" + waitingTime_input + "/" + blokSameAsJob_input + "/" + districtSameAsJob_input + "/"
					+ stateSameAsJob_input + "/" + readyToRelocate_input + "/";

			log.error(applyBaseMLUrl);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity(canApplyJobUrl, String.class);

			if (response.getBody().equals("1")) {
				count += 1;
				ListJobModel jobSList = modelMapper.map(job, ListJobModel.class);
				if(jobSList.getCompany() == null) {
					CreateOrUpdateCompanyModel companyC = new CreateOrUpdateCompanyModel();
					companyC.setImageUrl(employeeId);
					companyC.setImageUrl("https://www.betterteam.com/images/betterteam-free-job-posting-sites-5877x3918-20210222.jpg?crop=40:21,smart&width=1200&dpr=2");
					jobSList.setCompany(companyC);
				}
				returnValue.add(jobSList);

				if (returnValue.size() == jobs.size() || returnValue.size() == 9)
					break;
			}
		}
		return returnValue;
	}

}

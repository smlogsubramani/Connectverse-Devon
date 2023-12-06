package gmc.project.connectversev3.jobservice.services;

import java.util.List;

import gmc.project.connectversev3.jobservice.models.ListEmployeeModel;

public interface ContractorService {
	public List<ListEmployeeModel> employeesToReach(String contractorid);
}

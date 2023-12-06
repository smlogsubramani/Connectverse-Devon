package gmc.project.connectversev3.userservice.models;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class EmployeeWrapper implements Serializable {

	private static final long serialVersionUID = 1282502719926290534L;
	
	private List<EmployeeModel> fetchedEmployees;

}

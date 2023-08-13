package dashboard.service;

import dashboard.entity.Employee;
import dashboard.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }

    public List<Employee> findAllEmployeeASC() {
        Sort sort;

        sort = Sort.by(Sort.Direction.ASC, "name");

        return employeeRepository.findAll(sort);
    }
}

package dashboard.service;

import dashboard.entity.Employee;
import dashboard.repository.IEmployeeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Getter
    @Setter
    List<Employee> filterList = new ArrayList<>();

    public void filterTable(Employee employee) {
        filterList.clear();
        setFilterList(employeeRepository.getFilteredTable(employee.getGender(), employee.getUniversity(), employee.getDepartment(), employee.getCountry()));
    }

    @Getter
    @Setter
    List<Employee> searchList = new ArrayList<>();

    public void searchTable(String type, String word) {
        searchList.clear();

        switch (type) {
            case "name" -> searchList = employeeRepository.findByNameContains(word);
            case "surname" -> searchList = employeeRepository.findBySurnameContains(word);
            case "email" -> searchList = employeeRepository.findByEmailContains(word);
            case "age" -> searchList = employeeRepository.findByAge(Integer.parseInt(word));
        }
    }

}

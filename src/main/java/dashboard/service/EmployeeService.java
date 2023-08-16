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

    public void updateEmployee(int id, Employee employee) {
        employeeRepository.updateEmployee(id, employee.getName(), employee.getSurname(), employee.getEmail(), employee.getGender(), employee.getAge(), employee.getUniversity(), employee.getDepartment(), employee.getCountry());
    }

    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }

    public Employee findById(int id) {
        return employeeRepository.findById(id).get();
    }

    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }

    @Getter
    @Setter
    List<Employee> filterList = new ArrayList<>();

    public void filterTable(Employee employee) {
        filterList.clear();
        if(employee.getGender().equals("Other")) {
            setFilterList(employeeRepository.getFilteredTable(employee.getUniversity(), employee.getDepartment(), employee.getCountry()));
        } else if (employee.getGender().isEmpty() && employee.getUniversity().isEmpty() && employee.getDepartment().isEmpty() && employee.getCountry().isEmpty()) {
            setFilterList(employeeRepository.findAll());
        } else {
            setFilterList(employeeRepository.getFilteredTable(employee.getGender(), employee.getUniversity(), employee.getDepartment(), employee.getCountry()));
        }
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

    public int[] getGenderStats() {
        int male, female, other, all;

        all = employeeRepository.findAll().size();
        male = employeeRepository.findByGender("Male").size();
        female = employeeRepository.findByGender("Female").size();

        male = male * 100 / all;
        female = female * 100 / all;
        other = 100 - male - female;

        return new int[]{male, female, other};
    }

    @Getter
    @Setter
    int updateEmployeeId;
}

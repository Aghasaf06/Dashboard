package dashboard.service;

import dashboard.entity.Country;
import dashboard.entity.Department;
import dashboard.entity.Employee;
import dashboard.entity.Gender;
import dashboard.repository.ICountryRepository;
import dashboard.repository.IDepartmentRepository;
import dashboard.repository.IEmployeeRepository;
import dashboard.repository.IGenderRepository;
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

    @Autowired
    private IGenderRepository genderRepository;

    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private ICountryRepository countryRepository;

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

    public double percentageGenderAndEntity(String genderTitle, int column, int all) {
        double percentage = Math.round(((double) (column * 100) / all) * 100.0) / 100.0;
        Gender gender = new Gender(genderTitle, percentage);
        genderRepository.save(gender);
        return percentage;
    }

    public double percentageDepartmentAndEntity(String departmentTitle, int column, int all) {
        double percentage = Math.round(((double) (column * 100) / all) * 100.0) / 100.0;
        Department department = new Department(departmentTitle, percentage);
        departmentRepository.save(department);
        return percentage;
    }

    public double percentageCountryAndEntity(String countryTitle, int column, int all) {
        double percentage = Math.round(((double) (column * 100) / all) * 100.0) / 100.0;
        Country country = new Country(countryTitle, percentage);
        countryRepository.save(country);
        return percentage;
    }

    public double[] getGenderStats() {
        int all;
        double male, female, other;

        genderRepository.deleteAll();
        all = employeeRepository.findAll().size();

        male = percentageGenderAndEntity("Male", employeeRepository.findByGender("Male").size(), all);
        female = percentageGenderAndEntity("Female", employeeRepository.findByGender("Female").size(), all);
        other = percentageGenderAndEntity("Other", (int) (all - male - female), all);

        return new double[] {male, female, other};
    }

    public double[] getDepartmentStats() {
        int all;
        double acc, bd, eng, hr, leg, mark, pm, rd, sales, services, support, tr;

        all = employeeRepository.findAll().size();
        acc = percentageDepartmentAndEntity("Accounting", employeeRepository.findByDepartment("Accounting").size(), all);
        bd = percentageDepartmentAndEntity("Business Development", employeeRepository.findByDepartment("Business Development").size(), all);
        eng = percentageDepartmentAndEntity("Engineering", employeeRepository.findByDepartment("Engineering").size(), all);
        hr = percentageDepartmentAndEntity("Human Resources", employeeRepository.findByDepartment("Human Resources").size(), all);
        leg = percentageDepartmentAndEntity("Legal", employeeRepository.findByDepartment("Legal").size(), all);
        mark = percentageDepartmentAndEntity("Marketing", employeeRepository.findByDepartment("Marketing").size(), all);
        pm = percentageDepartmentAndEntity("Product Management", employeeRepository.findByDepartment("Product Management").size(), all);
        rd = percentageDepartmentAndEntity("Research and Development", employeeRepository.findByDepartment("Research and Development").size(), all);
        sales = percentageDepartmentAndEntity("Sales", employeeRepository.findByDepartment("Sales").size(), all);
        services = percentageDepartmentAndEntity("Services", employeeRepository.findByDepartment("Services").size(), all);
        support = percentageDepartmentAndEntity("Support", employeeRepository.findByDepartment("Support").size(), all);
        tr = percentageDepartmentAndEntity("Training", employeeRepository.findByDepartment("Training").size(), all);

        return new double[] {acc, bd, eng, hr, leg, mark, pm, rd, sales, services, support, tr};
    }

    public double[] getCountryStats() {
        int all;
        double aze, geo, irn, kaz, kgz, rus, tur, ukr, uzb;

        all = employeeRepository.findAll().size();
        aze = percentageCountryAndEntity("Azerbaijan", employeeRepository.findByCountry("Azerbaijan").size(), all);
        geo = percentageCountryAndEntity("Georgia", employeeRepository.findByCountry("Georgia").size(), all);
        irn = percentageCountryAndEntity("Iran", employeeRepository.findByCountry("Iran").size(), all);
        kaz = percentageCountryAndEntity("Kazakhstan", employeeRepository.findByCountry("Kazakhstan").size(), all);
        kgz = percentageCountryAndEntity("Kyrgyzstan", employeeRepository.findByCountry("Kyrgyzstan").size(), all);
        rus = percentageCountryAndEntity("Russia", employeeRepository.findByCountry("Russia").size(), all);
        tur = percentageCountryAndEntity("Turkmenistan", employeeRepository.findByCountry("Turkmenistan").size(), all);
        ukr = percentageCountryAndEntity("Ukraine", employeeRepository.findByCountry("Ukraine").size(), all);
        uzb = percentageCountryAndEntity("Uzbekistan", employeeRepository.findByCountry("Uzbekistan").size(), all);

        return new double[] {aze, geo, irn, kaz, kgz, rus, tur, ukr, uzb};
    }

    @Getter
    @Setter
    int updateEmployeeId;
}

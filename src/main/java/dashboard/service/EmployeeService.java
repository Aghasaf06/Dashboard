package dashboard.service;

import dashboard.entity.Employee;
import dashboard.repository.IEmployeeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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

    public int percentageGender(int column, int all) {
        return column * 100 / all;
    }

    public double percentageDepartmentOrCountry(int column, int all) {
        return Math.round(((double) (column * 100) / all) * 100.0) / 100.0;
    }

    public int[] getGenderStats() {
        int male, female, other, all;

        all = employeeRepository.findAll().size();
        male = percentageGender(employeeRepository.findByGender("Male").size(), all);
        female = percentageGender(employeeRepository.findByGender("Female").size(), all);
        other = 100 - male - female;

        return new int[] {male, female, other};
    }

    public double[] getDepartmentStats() {
        int all;
        double acc, bd, eng, hr, leg, mark, pm, rd, sales, services, support, tr;

        all = employeeRepository.findAll().size();
        acc = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Accounting").size(), all);
        bd = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Business Development").size(), all);
        eng = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Engineering").size(), all);
        hr = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Human Resources").size(), all);
        leg = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Legal").size(), all);
        mark = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Marketing").size(), all);
        pm = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Product Management").size(), all);
        rd = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Research and Development").size(), all);
        sales = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Sales").size(), all);
        services = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Services").size(), all);
        support = percentageDepartmentOrCountry(employeeRepository.findByDepartment("Support").size(), all);
        tr = Math.round((100 - acc - bd - eng - hr - leg - mark - pm - rd - sales - services - support) * 100.0) / 100.0;

        return new double[] {acc, bd, eng, hr, leg, mark, pm, rd, sales, services, support, tr};
    }

    public double[] getCountryStats() {
        int all;
        double aze, geo, irn, kaz, kgz, rus, tur, ukr, uzb;

        all = employeeRepository.findAll().size();
        aze = percentageDepartmentOrCountry(employeeRepository.findByCountry("Azerbaijan").size(), all);
        geo = percentageDepartmentOrCountry(employeeRepository.findByCountry("Georgia").size(), all);
        irn = percentageDepartmentOrCountry(employeeRepository.findByCountry("Iran").size(), all);
        kaz = percentageDepartmentOrCountry(employeeRepository.findByCountry("Kazakhstan").size(), all);
        kgz = percentageDepartmentOrCountry(employeeRepository.findByCountry("Kyrgyzstan").size(), all);
        rus = percentageDepartmentOrCountry(employeeRepository.findByCountry("Russia").size(), all);
        tur = percentageDepartmentOrCountry(employeeRepository.findByCountry("Turkmenistan").size(), all);
        ukr = percentageDepartmentOrCountry(employeeRepository.findByCountry("Ukraine").size(), all);
        uzb = percentageDepartmentOrCountry(employeeRepository.findByCountry("Uzbekistan").size(), all);

        return new double[] {aze, geo, irn, kaz, kgz, rus, tur, ukr, uzb};
    }

    @Getter
    @Setter
    int updateEmployeeId;
}

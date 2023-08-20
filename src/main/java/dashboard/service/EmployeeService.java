package dashboard.service;

import dashboard.entity.Employee;
import dashboard.entity.Gender;
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

    public double percentageAndEntity(int id, String genderTitle, int column, int all) {
        double percentage = Math.round(((double) (column * 100) / all) * 100.0) / 100.0;
        Gender gender = new Gender(id, genderTitle, percentage);
        genderRepository.save(gender);
        return percentage;
    }

    public double percentage(int column, int all) {
        return Math.round(((double) (column * 100) / all) * 100.0) / 100.0;
    }

    public double[] getGenderStats() {
        int all, id = 1;
        double male, female, other;

        genderRepository.deleteAll();
        all = employeeRepository.findAll().size();

        male = percentageAndEntity(id++, "Male", employeeRepository.findByGender("Male").size(), all);
        female = percentageAndEntity(id++, "Female", employeeRepository.findByGender("Female").size(), all);
        other = percentageAndEntity(id++, "Other", (int) (all - male - female), all);

        return new double[] {male, female, other};
    }

    public double[] getDepartmentStats() {
        int all;
        double acc, bd, eng, hr, leg, mark, pm, rd, sales, services, support, tr;

        all = employeeRepository.findAll().size();
        acc = percentage(employeeRepository.findByDepartment("Accounting").size(), all);
        bd = percentage(employeeRepository.findByDepartment("Business Development").size(), all);
        eng = percentage(employeeRepository.findByDepartment("Engineering").size(), all);
        hr = percentage(employeeRepository.findByDepartment("Human Resources").size(), all);
        leg = percentage(employeeRepository.findByDepartment("Legal").size(), all);
        mark = percentage(employeeRepository.findByDepartment("Marketing").size(), all);
        pm = percentage(employeeRepository.findByDepartment("Product Management").size(), all);
        rd = percentage(employeeRepository.findByDepartment("Research and Development").size(), all);
        sales = percentage(employeeRepository.findByDepartment("Sales").size(), all);
        services = percentage(employeeRepository.findByDepartment("Services").size(), all);
        support = percentage(employeeRepository.findByDepartment("Support").size(), all);
        tr = Math.round((100 - acc - bd - eng - hr - leg - mark - pm - rd - sales - services - support) * 100.0) / 100.0;

        return new double[] {acc, bd, eng, hr, leg, mark, pm, rd, sales, services, support, tr};
    }

    public double[] getCountryStats() {
        int all;
        double aze, geo, irn, kaz, kgz, rus, tur, ukr, uzb;

        all = employeeRepository.findAll().size();
        aze = percentage(employeeRepository.findByCountry("Azerbaijan").size(), all);
        geo = percentage(employeeRepository.findByCountry("Georgia").size(), all);
        irn = percentage(employeeRepository.findByCountry("Iran").size(), all);
        kaz = percentage(employeeRepository.findByCountry("Kazakhstan").size(), all);
        kgz = percentage(employeeRepository.findByCountry("Kyrgyzstan").size(), all);
        rus = percentage(employeeRepository.findByCountry("Russia").size(), all);
        tur = percentage(employeeRepository.findByCountry("Turkmenistan").size(), all);
        ukr = percentage(employeeRepository.findByCountry("Ukraine").size(), all);
        uzb = percentage(employeeRepository.findByCountry("Uzbekistan").size(), all);

        return new double[] {aze, geo, irn, kaz, kgz, rus, tur, ukr, uzb};
    }

    @Getter
    @Setter
    int updateEmployeeId;
}

package dashboard.repository;

import dashboard.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> getByGenderOrDepartmentOrUniversityOrCountry(String gender, String university, String department, String country);

}

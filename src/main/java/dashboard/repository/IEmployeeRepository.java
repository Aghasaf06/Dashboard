package dashboard.repository;

import dashboard.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("from Employee where gender = :gender or university = :university or department = :department or country = :country")
    List<Employee> getFilteredTable(String gender, String university, String department, String country);

    @Query(value = "from Employee where gender != 'Male' and gender != 'Female' or university = :university or department = :department or country = :country")
    List<Employee> getFilteredTable(String university, String department, String country);

    List<Employee> findByNameContains(String name);

    List<Employee> findBySurnameContains(String surname);

    List<Employee> findByEmailContains(String email);

    List<Employee> findByAge(int age);

    List<Employee> findByGender(String gender);

}

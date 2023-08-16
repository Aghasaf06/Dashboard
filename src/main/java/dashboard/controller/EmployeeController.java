package dashboard.controller;

import dashboard.entity.Employee;
import dashboard.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/home")
    public String home() {
        return "home";
    }

    @GetMapping(path = "/employeeRegister")
    public String employeeRegister() {
        return "employeeRegister";
    }

    @PostMapping(path = "/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "submit";
    }

    @GetMapping(path = "/employeeTable")
    public ModelAndView employeeTable() {
        List<Employee> employeeList = employeeService.findAllEmployee();
        return new ModelAndView("employeeTable", "employee", employeeList);
    }

    @GetMapping(path = "/updateEmployee/{id}")
    public ModelAndView updateEmployee(@PathVariable("id") int id) {
        employeeService.setUpdateEmployeeId(id);
        Employee employee = employeeService.findById(id);
        return new ModelAndView("updateEmployee", "employee", employee);
    }

    @PostMapping(path = "/update")
    public String update(@ModelAttribute Employee employee) {
        System.out.println(employeeService.getUpdateEmployeeId());
        System.out.println(employee.getAge());
        System.out.println(employeeService.findById(employeeService.getUpdateEmployeeId()).getName());
        employeeService.updateEmployee(employeeService.getUpdateEmployeeId(), employee);
        return "update";
    }

    @GetMapping(path = "/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteById(id);
        return "redirect:/employeeTable";
    }

    @PostMapping(path = "/filterTable")
    public String saveFilter(@ModelAttribute Employee employee) {
        employeeService.filterTable(employee);
        return "redirect:/filteredTable";
    }

    @GetMapping(path = "/filteredTable")
    public ModelAndView filteredTable() {
        List<Employee> employeeList = employeeService.getFilterList();
        return new ModelAndView("employeeTable", "employee", employeeList);
    }

    @PostMapping(path = "/searchBar")
    public String searchBar(@RequestParam(name = "type") String type, @RequestParam(name = "word") String word) {
        employeeService.searchTable(type, word);
        return "redirect:/searchTable";
    }

    @GetMapping(path = "/searchTable")
    public ModelAndView searchTable() {
        List<Employee> employeeList = employeeService.getSearchList();
        return new ModelAndView("employeeTable", "employee", employeeList);
    }

    @GetMapping(path = "/dashboard")
    public ModelAndView dashboard() {
        Map<String, Object> modelGender = new HashMap<>();
        int[] gender = employeeService.getGenderStats();
        modelGender.put("Male", gender[0]);
        modelGender.put("Female", gender[1]);
        modelGender.put("Other", gender[2]);
        System.out.println(gender[0] + " " + gender[1] + " " + gender[2]);
        return new ModelAndView("chart", modelGender);
    }
}

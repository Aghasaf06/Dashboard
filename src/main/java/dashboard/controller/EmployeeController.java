package dashboard.controller;

import dashboard.entity.Employee;
import dashboard.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

}

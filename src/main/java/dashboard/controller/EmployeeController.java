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
        Map<String, Double> modelStats = new HashMap<>();

        //Gender stats
        double[] gender = employeeService.getGenderStats();
        modelStats.put("male", gender[0]);
        modelStats.put("female", gender[1]);
        modelStats.put("other", gender[2]);

        //Department stats
        double[] department = employeeService.getDepartmentStats();
        modelStats.put("acc", department[0]);
        modelStats.put("bd", department[1]);
        modelStats.put("eng", department[2]);
        modelStats.put("hr", department[3]);
        modelStats.put("leg", department[4]);
        modelStats.put("mark", department[5]);
        modelStats.put("pm", department[6]);
        modelStats.put("rd", department[7]);
        modelStats.put("sales", department[8]);
        modelStats.put("services", department[9]);
        modelStats.put("support", department[10]);
        modelStats.put("tr", department[11]);

        //Country stats
        double[] country = employeeService.getCountryStats();
        modelStats.put("aze", country[0]);
        modelStats.put("geo", country[1]);
        modelStats.put("irn", country[2]);
        modelStats.put("kaz", country[3]);
        modelStats.put("kgz", country[4]);
        modelStats.put("rus", country[5]);
        modelStats.put("tur", country[6]);
        modelStats.put("ukr", country[7]);
        modelStats.put("uzb", country[8]);

        System.out.println(modelStats);
        return new ModelAndView("chart", modelStats);
    }
}

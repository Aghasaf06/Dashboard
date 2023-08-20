package dashboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "department")
    private String department;

    @Column(name = "percentage")
    private double percentage;

    public Department(String department, double percentage) {
        this.department = department;
        this.percentage = percentage;
    }
}

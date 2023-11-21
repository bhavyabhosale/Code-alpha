import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Employee {
    private String id;
    private String name;
    private double hourlyRate;

    public Employee(String id, String name, double hourlyRate) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}

class PayrollSystem {
    private List<Employee> employees;

    public PayrollSystem() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public double calculateSalary(Employee employee, int hoursWorked) {
        return employee.getHourlyRate() * hoursWorked;
    }
}

public class PayrollApp extends JFrame {
    private PayrollSystem payrollSystem;

    private JTextField employeeIdField;
    private JTextField employeeNameField;
    private JTextField hourlyRateField;
    private JTextField hoursWorkedField;
    private JTextArea payStubArea;

    public PayrollApp() {
        super("Employee Payroll System");
        payrollSystem = new PayrollSystem();

        // UI components
        employeeIdField = new JTextField(10);
        employeeNameField = new JTextField(10);
        hourlyRateField = new JTextField(10);
        hoursWorkedField = new JTextField(10);
        payStubArea = new JTextArea(10, 30);
        JButton calculateButton = new JButton("Calculate Salary");

        // Layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        add(new JLabel("Employee ID:"));
        add(employeeIdField);
        add(new JLabel("Employee Name:"));
        add(employeeNameField);
        add(new JLabel("Hourly Rate:"));
        add(hourlyRateField);
        add(new JLabel("Hours Worked:"));
        add(hoursWorkedField);
        add(calculateButton);
        add(new JLabel("Pay Stub:"));
        add(new JScrollPane(payStubArea));

        // Calculate button action
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = employeeIdField.getText();
                    String name = employeeNameField.getText();
                    double hourlyRate = Double.parseDouble(hourlyRateField.getText());
                    int hoursWorked = Integer.parseInt(hoursWorkedField.getText());

                    Employee employee = new Employee(id, name, hourlyRate);
                    payrollSystem.addEmployee(employee);

                    double salary = payrollSystem.calculateSalary(employee, hoursWorked);
                    payStubArea.setText("Employee ID: " + id + "\nEmployee Name: " + name + "\nSalary: $" + salary);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PayrollApp.this, "Invalid input. Please enter valid numbers.");
                }
            }
        });

        // Frame settings
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PayrollApp());
    }
}

import java.util.*;

class Employee {
    private String employeeId;
    private String name;
    private int leaveBalance;

    public Employee(String employeeId, String name, int leaveBalance) {
        this.employeeId = employeeId;
        this.name = name;
        this.leaveBalance = leaveBalance;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public int getLeaveBalance() {
        return leaveBalance;
    }

    public void deductLeave(int days) {
        if (leaveBalance >= days) {
            leaveBalance -= days;
            System.out.println("Leave request approved for " + days + " days. Remaining leave balance: " + leaveBalance);
        } else {
            System.out.println("Insufficient leave balance. Leave request denied.");
        }
    }
}

class LeaveRequest {
    private String employeeId;
    private int days;

    public LeaveRequest(String employeeId, int days) {
        this.employeeId = employeeId;
        this.days = days;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public int getDays() {
        return days;
    }
}

class LeaveManagementSystem {
    private List<Employee> employees;
    private List<LeaveRequest> leaveRequests;

    public LeaveManagementSystem() {
        this.employees = new ArrayList<>();
        this.leaveRequests = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void requestLeave(LeaveRequest leaveRequest) {
        leaveRequests.add(leaveRequest);
        System.out.println("Leave request submitted for employee " + leaveRequest.getEmployeeId() + " for " + leaveRequest.getDays() + " days.");
    }

    public void processLeaveRequests() {
        for (LeaveRequest request : leaveRequests) {
            Employee employee = findEmployee(request.getEmployeeId());
            if (employee != null) {
                employee.deductLeave(request.getDays());
            }
        }
        leaveRequests.clear();
    }

    public void displayLeaveBalances() {
        System.out.println("Leave Balances:");
        for (Employee employee : employees) {
            System.out.println(employee.getName() + " (" + employee.getEmployeeId() + "): " + employee.getLeaveBalance() + " days");
        }
    }

    private Employee findEmployee(String employeeId) {
        for (Employee employee : employees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                return employee;
            }
        }
        System.out.println("Employee not found with ID: " + employeeId);
        return null;
    }
}

public class LeaveManagementApp {
    public static void main(String[] args) {
        LeaveManagementSystem leaveManagementSystem = new LeaveManagementSystem();
        Scanner scanner = new Scanner(System.in);

        // Adding employees
        System.out.println("Enter Employee details:");
        System.out.print("Employee ID: ");
        String empId1 = scanner.next();
        System.out.print("Employee Name: ");
        String empName1 = scanner.next();
        System.out.print("Leave Balance: ");
        int empLeaveBalance1 = scanner.nextInt();
        leaveManagementSystem.addEmployee(new Employee(empId1, empName1, empLeaveBalance1));

        System.out.print("\nEmployee ID: ");
        String empId2 = scanner.next();
        System.out.print("Employee Name: ");
        String empName2 = scanner.next();
        System.out.print("Leave Balance: ");
        int empLeaveBalance2 = scanner.nextInt();
        leaveManagementSystem.addEmployee(new Employee(empId2, empName2, empLeaveBalance2));

        // Leave requests
        System.out.println("\nEnter Leave Requests:");
        System.out.print("Employee ID: ");
        String requestEmpId = scanner.next();
        System.out.print("Leave Days: ");
        int leaveDays = scanner.nextInt();
        leaveManagementSystem.requestLeave(new LeaveRequest(requestEmpId, leaveDays));

        // Process leave requests
        leaveManagementSystem.processLeaveRequests();

        // Display leave balances
        leaveManagementSystem.displayLeaveBalances();

        scanner.close();
    }
}

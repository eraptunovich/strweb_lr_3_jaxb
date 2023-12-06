package jaxb.test;

import jaxb.model.Department;
import jaxb.model.Employee;
import jaxb.model.Organization;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TestExample {

    private static final String XML_FILE = "dept-info.xml";

    public static void main(String[] args) {

        Employee emp1 = new Employee("E01", "Tom", null);
        Employee emp2 = new Employee("E02", "Mary", "E01");
        Employee emp3 = new Employee("E03", "John", null);

        List<Employee> list = new ArrayList<>();
        list.add(emp1);
        list.add(emp2);
        list.add(emp3);

        Department dept = new Department("D01", "ACCOUNTING", "NEW YORK");
        dept.setEmployees(list);

        // Additional employees for the ACCOUNTING department
        Employee emp4 = new Employee("E04", "Alice", "E01");
        Employee emp5 = new Employee("E05", "Bob", "E04");
        List<Employee> listAccounting = new ArrayList<>();
        listAccounting.add(emp4);
        listAccounting.add(emp5);
        dept.setEmployees(listAccounting);

        Department dept2 = new Department("D02", "SALES", "LOS ANGELES");
        // Employees for the SALES department
        Employee emp6 = new Employee("E06", "David", null);
        Employee emp7 = new Employee("E07", "Eva", "E06");
        List<Employee> listSales = new ArrayList<>();
        listSales.add(emp6);
        listSales.add(emp7);
        dept2.setEmployees(listSales);

        Department dept3 = new Department("D03", "IT", "SAN FRANCISCO");
        // Employees for the IT department
        Employee emp8 = new Employee("E08", "Fred", null);
        Employee emp9 = new Employee("E09", "Gina", "E08");
        List<Employee> listIT = new ArrayList<>();
        listIT.add(emp8);
        listIT.add(emp9);
        dept3.setEmployees(listIT);

        // Additional departments
        Department dept4 = new Department("D04", "HR", "CHICAGO");
        Department dept5 = new Department("D05", "MARKETING", "BOSTON");

        List<Department> departments = new ArrayList<>();
        departments.add(dept);
        departments.add(dept2);
        departments.add(dept3);
        departments.add(dept4);
        departments.add(dept5);

        Organization organization = new Organization(departments);

        // Marshalling
        try {
            JAXBContext context = JAXBContext.newInstance(Organization.class, Department.class, Employee.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // (1) Marshaller: Java Object to XML content.
            marshaller.marshal(organization, System.out);

            // Write to File
            File outFile = new File(XML_FILE);
            marshaller.marshal(organization, outFile);
            System.err.println("Write to file: " + outFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Unmarshalling
        try {
            JAXBContext context = JAXBContext.newInstance(Organization.class, Department.class, Employee.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // (2) Unmarshaller: Read XML content to Java Object.
            Organization orgFromFile = (Organization) unmarshaller.unmarshal(new FileReader(XML_FILE));

            List<Department> depts = orgFromFile.getDepartments();
            for (Department d : depts) {
                System.out.println("Department: " + d.getDeptName());
                List<Employee> emps = d.getEmployees();
                if (emps != null) {
                    for (Employee emp : emps) {
                        System.out.println("  Employee: " + emp.getEmpName());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





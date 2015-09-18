/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classicmodels;

import entity.Customer;
import entity.Employee;
import entity.Office;
import entity.Orders;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Andreas
 */
public class Facade {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("classicmodelsPU");
        EntityManager em = emf.createEntityManager();
    
    public void createEmploye(Integer employeeNumber, String lastName, String firstName, String extension, String email, String jobTitle) {
        Employee emp = new Employee();
        emp.setEmployeeNumber(employeeNumber);
        emp.setLastName(lastName);
        emp.setFirstName(firstName);
        emp.setExtension(extension);
        emp.setEmail(email);
        emp.setJobTitle(jobTitle);
        emp.setOfficeCode(em.find(Office.class, "1"));

        em.getTransaction().begin();
        em.persist(emp);
        em.getTransaction().commit();
    }
    
    public Customer updateCustomer(Customer cust){
        em.getTransaction().begin();
        em.merge(cust);
        em.getTransaction().commit();
        return cust;
    }
    
    public long getEmployeeCount(){
        String queryString = "SELECT COUNT(e) FROM Employee e";
        Query query = em.createQuery(queryString);
        return (Long) query.getSingleResult();
    }
    
    public List<Customer> getCustomerInCity(String city){
        String queryString = "SELECT c FROM Customer c WHERE c.city =: city";
        Query query = em.createQuery(queryString);
        query.setParameter("city", city);
        return query.getResultList();
    }
    
    public Employee getEmployeeCustomers(){
        Query query = em.createNamedQuery("Employee.findEmployeeMaxCustomer");
        query.setMaxResults(1);
        return (Employee) query.getSingleResult();
    }
    
    public List<Orders> getOrdersOnHold() {
        Query query = em.createNamedQuery("ClassicOrder.findByStatus");
        query.setParameter("status", "On Hold");
        return query.getResultList();
    }
    
    public List<Orders> getOrdersOnHold(int n) {
        Query query = em.createNamedQuery("ClassicOrder.findByStatusSpecificCustomer");
        query.setParameter("customerNumber", n);
        return query.getResultList();
    }
    
}

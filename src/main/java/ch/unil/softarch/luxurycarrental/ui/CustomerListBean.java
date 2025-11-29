package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CustomerClient;
import ch.unil.softarch.luxurycarrental.domain.entities.Customer;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named("customerListBean")
@ViewScoped
public class CustomerListBean implements Serializable {

    private List<Customer> customers;

    private final CustomerClient customerClient = new CustomerClient();

    @PostConstruct
    public void init() {
        try {
            customers = customerClient.getAllCustomers();
            System.out.println("Fetched customers: " + customers.size());
        } catch (Exception e) {
            e.printStackTrace();
            customers = Collections.emptyList();
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
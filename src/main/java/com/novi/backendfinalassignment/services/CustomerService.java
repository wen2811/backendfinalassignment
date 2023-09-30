package com.novi.backendfinalassignment.services;

import com.novi.backendfinalassignment.dtos.CustomerDto;
import com.novi.backendfinalassignment.dtos.InvoiceDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.models.Customer;
import com.novi.backendfinalassignment.models.Invoice;
import com.novi.backendfinalassignment.repositories.CustomerRepository;
import com.novi.backendfinalassignment.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;

    public CustomerService(CustomerRepository customerRepository, InvoiceRepository invoiceRepository) {
        this.customerRepository = customerRepository;
        this.invoiceRepository = invoiceRepository;
    }

    //Read
    public List<CustomerDto> getAllCustomer() {
        List<CustomerDto> customerDto = new ArrayList<>();
        List<Customer> list = customerRepository.findAll();
        for (Customer customer : list) {
            customerDto.add(transferCustomerToDto(customer));
        }

        return customerDto;
    }

    public CustomerDto getCustomerById(Long id) throws RecordNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if(customerOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no customer found with this id " + id);
        }
        Customer customer = customerOptional.get();
        return transferCustomerToDto(customer);
    }

    //Create
    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = transferDtoToCustomer(customerDto);
        customerRepository.save(customer);

        return transferCustomerToDto(customer);
    }

    //Update
    public void updateCustomer(Long id, CustomerDto customerDto) {
        if(!customerRepository.existsById(id)) {
            throw new RecordNotFoundException("No customer found with this id " + id);
        }
        Customer updateCustomer = customerRepository.findById(id).orElse(null);
        updateCustomer.setId(customerDto.getId());
        updateCustomer.setFirstName(customerDto.getFirstName());
        updateCustomer.setLastName(customerDto.getLastName());
        updateCustomer.setEmail(customerDto.getEmail());
        updateCustomer.setPhoneNumber(customerDto.getPhoneNumber());
        updateCustomer.setBookingList(customerDto.getBookingList());
        customerRepository.save(updateCustomer);
    }


    //Delete
    public void deleteCustomer(Long id) throws RecordNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isEmpty()) {
            throw new RecordNotFoundException("There is no booking found with id: " + id);
        }
        Customer customer = optionalCustomer.get();
        customerRepository.delete(customer);
    }



   public CustomerDto getCustomerWithInvoices(Long customerId) throws RecordNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RecordNotFoundException("Customer not found with ID: " + customerId));

        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);
        customer.setInvoice(invoices);

        return transferCustomerToDto(customer);
    }




    public CustomerDto transferCustomerToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();

        customerDto.id = customer.getId();
        customerDto.firstName = customer.getFirstName();
        customerDto.lastName = customer.getLastName();
        customerDto.email = customer.getEmail();
        customerDto.phoneNumber = customer.getPhoneNumber();
        customerDto.bookingList = customer.getBookingList();
        customerDto.invoice = customer.getInvoice();
        return customerDto;
    }

    public Customer transferDtoToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer() {
            @Override
            public boolean isPasswordValid(String password) {
                return false;
            }

            @Override
            public void changePassword(String newPassword) {

            }
        };

        customer.setId(customerDto.id);
        customer.setFirstName(customerDto.firstName);
        customer.setLastName(customerDto.lastName);
        customerDto.setEmail(customerDto.email);
        customerDto.setPhoneNumber(customerDto.phoneNumber);
        customerDto.setBookingList(customerDto.bookingList);
        customerDto.setInvoice(customerDto.invoice);
        return customer;
    }


}

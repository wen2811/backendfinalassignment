package com.novi.backendfinalassignment.services;

import com.novi.backendfinalassignment.dtos.InvoiceDto;
import com.novi.backendfinalassignment.exceptions.RecordNotFoundException;
import com.novi.backendfinalassignment.models.Customer;
import com.novi.backendfinalassignment.models.Invoice;
import com.novi.backendfinalassignment.repositories.CustomerRepository;
import com.novi.backendfinalassignment.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }

    //Read
    public List<InvoiceDto> getAllInvoice() {
        Iterable<Invoice> invoice = invoiceRepository.findAll();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();

        for (Invoice inv: invoice) {
            invoiceDtos.add(transferInvoiceToDto(inv));
        }

        return invoiceDtos;
    }


    public InvoiceDto getInvoice(Long id) throws RecordNotFoundException {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);

        if(invoiceOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no invoice found with this id " + id);
        }
        Invoice invoice = invoiceOptional.get();
        return transferInvoiceToDto(invoice);
    }
    //Create
    public InvoiceDto addInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = transferDtoToInvoice(invoiceDto);
        invoiceRepository.save(invoice);

        return transferInvoiceToDto(invoice);
    }

    //Update
    public void updateInvoice(Long id, InvoiceDto invoiceDto) {
        if(!invoiceRepository.existsById(id)) {
            throw new RecordNotFoundException("There's no Invoice-timeslot found");
        }
        Invoice updatedInvoice = invoiceRepository.findById(id).orElse(null);
        updatedInvoice.setId(invoiceDto.getId());
        updatedInvoice.setAmount(invoiceDto.getAmount());
        updatedInvoice.setInvoicedate(invoiceDto.getInvoicedate());
        updatedInvoice.setBooking(invoiceDto.getBooking());
        invoiceRepository.save(updatedInvoice);

    }
    //Delete
    public void deleteInvoice(Long id) {invoiceRepository.deleteById(id);
    }

    public List<InvoiceDto> getInvoiceForCustomer(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Customer customer = customerOptional.get();
        List<Invoice> invoices = customer.getInvoice();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setId(invoice.getId());
            invoiceDto.setAmount(invoice.getAmount());

            invoiceDtos.add(invoiceDto);
        }
        return invoiceDtos;
    }


    public InvoiceDto transferInvoiceToDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();

        invoiceDto.id = invoice.getId();
        invoiceDto.amount = invoice.getAmount();
        invoiceDto.invoicedate = invoice.getInvoicedate();
        invoiceDto.booking = invoice.getBooking();
        invoiceDto.customer = invoice.getCustomer();
        return invoiceDto;
    }

    public Invoice transferDtoToInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();

        invoice.setId(invoiceDto.id);
        invoice.setAmount(invoiceDto.amount);
        invoice.setInvoicedate(invoiceDto.invoicedate);
        invoice.setBooking(invoiceDto.booking);
        invoice.setCustomer(invoiceDto.customer);
        return invoice;
    }
}

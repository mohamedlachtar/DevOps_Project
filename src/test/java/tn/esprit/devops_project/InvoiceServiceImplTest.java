package tn.esprit.devops_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceDetailRepository;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InvoiceServiceImplTest {
    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private InvoiceDetailRepository invoiceDetailRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllInvoices() {
        // Prepare the mocks
        List<Invoice> mockInvoices = Arrays.asList(new Invoice(), new Invoice());
        when(invoiceRepository.findAll()).thenReturn(mockInvoices);
        // Invoke the method
        List<Invoice> retrievedInvoices = invoiceService.retrieveAllInvoices();
        // Verify the results
        assertEquals(mockInvoices, retrievedInvoices);
        // Verify interactions with mocks (optional but recommended)
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    public void testCancelInvoice() {
        // Given
        Long invoiceId = 1L;
        Invoice mockInvoice = new Invoice();
        mockInvoice.setIdInvoice(invoiceId);
        // Assuming "Active" is a valid status and you have a setStatus or similar method
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(mockInvoice));

        // When
        invoiceService.cancelInvoice(invoiceId);

        // Then
        // Assuming "Cancelled" is the status after cancelling
        assertTrue(mockInvoice.getArchived());
        verify(invoiceRepository, times(1)).save(mockInvoice);
    }

    @Test
    public void testRetrieveInvoice() {
        // Given
        Long invoiceId = 1L;
        Invoice mockInvoice = new Invoice();
        mockInvoice.setIdInvoice(invoiceId);

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(mockInvoice));

        // When
        Invoice retrievedInvoice = invoiceService.retrieveInvoice(invoiceId);

        // Then
        assertEquals(mockInvoice, retrievedInvoice);
    }

    @Test
    public void testGetInvoicesBySupplier() {
        // Given
        Long supplierId = 1L;
        Supplier mockSupplier = new Supplier();

        Set<Invoice> mockInvoicesSet = new HashSet<>(Arrays.asList(new Invoice(), new Invoice()));
        mockSupplier.setInvoices(mockInvoicesSet);

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(mockSupplier));

        // When
        List<Invoice> retrievedInvoices = invoiceService.getInvoicesBySupplier(supplierId);

        // Debugging
        System.out.println("Retrieved invoices: " + retrievedInvoices);

        // Then
        assertNotNull(retrievedInvoices, "Expected a list of invoices but received null");
        assertEquals(new ArrayList<>(mockInvoicesSet), retrievedInvoices);
    }

    @Test
    public void testAssignOperatorToInvoice() {
        // Given
        Long mockOperatorId = 1L;
        Long mockInvoiceId = 2L;

        Operator mockOperator = new Operator();
        mockOperator.setInvoices(new HashSet<>()); // Ensure that the invoices set is initialized

        Invoice mockInvoice = new Invoice();

        // Mock the responses of the repositories
        when(invoiceRepository.findById(mockInvoiceId)).thenReturn(Optional.of(mockInvoice));
        when(operatorRepository.findById(mockOperatorId)).thenReturn(Optional.of(mockOperator));

        // When
        invoiceService.assignOperatorToInvoice(mockOperatorId, mockInvoiceId);

        // Then
        assertTrue(mockOperator.getInvoices().contains(mockInvoice));
        verify(operatorRepository, times(1)).save(mockOperator);
    }


    @Test
    public void testGetTotalAmountInvoiceBetweenDates() {
        Date startDate = new Date();
        Date endDate = new Date();

        float expectedAmount = 100.0f;

        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate)).thenReturn(expectedAmount);

        float result = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);

        assertEquals(expectedAmount, result);
    }
}

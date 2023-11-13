package tn.esprit.devops_project;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.services.OperatorServiceImpl;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class OperatorServiceImplTest {

    @InjectMocks
    OperatorServiceImpl operatorService;

    @Mock
    OperatorRepository operatorRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllOperators() {
        // Créez une liste fictive d'opérateurs pour simuler le comportement du repository.
        List<Operator> operatorList = new ArrayList<>();
        operatorList.add(new Operator(1L, "John", "Doe", "password", null));
        operatorList.add(new Operator(2L, "Jane", "Smith", "secret", null));

        // Définissez le comportement simulé du repository.
        Mockito.when(operatorRepository.findAll()).thenReturn(operatorList);

        List<Operator> result = operatorService.retrieveAllOperators();

        // Vérifiez que le service renvoie la liste d'opérateurs simulée.
        assertEquals(operatorList, result);
    }

    @Test
    public void testAddOperator() {
        Operator operatorToAdd = new Operator(3L, "Alice", "Johnson", "newPassword", null);

        // Définissez le comportement simulé du repository pour renvoyer l'opérateur ajouté.
        Mockito.when(operatorRepository.save(operatorToAdd)).thenReturn(operatorToAdd);

        Operator result = operatorService.addOperator(operatorToAdd);

        // Vérifiez que le service renvoie l'opérateur ajouté.
        assertEquals(operatorToAdd, result);
    }

    @Test
    public void testDeleteOperator() {
        Long operatorId = 1L;

        // Aucune simulation nécessaire car la méthode deleteById n'a pas de résultat de retour.

        operatorService.deleteOperator(operatorId);

        // Vérifiez que la méthode deleteById a été appelée avec l'ID correct.
        Mockito.verify(operatorRepository).deleteById(operatorId);
    }

    @Test
    public void testUpdateOperator() {
        Operator operatorToUpdate = new Operator(1L, "Updated", "Name", "newPassword", null);

        // Définissez le comportement simulé du repository pour renvoyer l'opérateur mis à jour.
        Mockito.when(operatorRepository.save(operatorToUpdate)).thenReturn(operatorToUpdate);

        Operator result = operatorService.updateOperator(operatorToUpdate);

        // Vérifiez que le service renvoie l'opérateur mis à jour.
        assertEquals(operatorToUpdate, result);
    }

    @Test
    public void testRetrieveOperator() {
        Long operatorId = 1L;
        Operator operator = new Operator(operatorId, "John", "Doe", "password", null);

        // Définissez le comportement simulé du repository pour renvoyer l'opérateur par ID.
        Mockito.when(operatorRepository.findById(operatorId)).thenReturn(java.util.Optional.of(operator));

        Operator result = operatorService.retrieveOperator(operatorId);

        // Vérifiez que le service renvoie l'opérateur simulé.
        assertEquals(operator, result);
    }

    @Test
    public void testRetrieveOperator_NotFound() {
        Long operatorId = 999L;

        // Définissez le comportement simulé du repository pour renvoyer une option vide.
        Mockito.when(operatorRepository.findById(operatorId)).thenReturn(java.util.Optional.empty());

        // Vérifiez que le service génère une exception lorsqu'il ne trouve pas l'opérateur.
        assertThrows(NullPointerException.class, () -> operatorService.retrieveOperator(operatorId));
    }
}

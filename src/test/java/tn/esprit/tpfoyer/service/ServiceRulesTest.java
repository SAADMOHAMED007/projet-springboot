package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.domain.Agence;
import tn.esprit.tpfoyer.domain.Client;
import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.domain.Employe;
import tn.esprit.tpfoyer.domain.Equipement;
import tn.esprit.tpfoyer.domain.Maintenance;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IAgenceRepository;
import tn.esprit.tpfoyer.repository.IClientRepository;
import tn.esprit.tpfoyer.repository.IContratRepository;
import tn.esprit.tpfoyer.repository.IEmployeRepository;
import tn.esprit.tpfoyer.repository.IEquipementRepository;
import tn.esprit.tpfoyer.repository.IMaintenanceRepository;
import tn.esprit.tpfoyer.repository.IReservationRepository;
import tn.esprit.tpfoyer.repository.IVehiculeRepository;
import tn.esprit.tpfoyer.service.impl.AgenceServiceImpl;
import tn.esprit.tpfoyer.service.impl.ClientServiceImpl;
import tn.esprit.tpfoyer.service.impl.ContratServiceImpl;
import tn.esprit.tpfoyer.service.impl.EmployeServiceImpl;
import tn.esprit.tpfoyer.service.impl.EquipementServiceImpl;
import tn.esprit.tpfoyer.service.impl.MaintenanceServiceImpl;
import tn.esprit.tpfoyer.service.impl.ReservationServiceImpl;
import tn.esprit.tpfoyer.service.impl.VehiculeServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceRulesTest {

    @Mock
    private IContratRepository contratRepository;

    @Mock
    private IVehiculeRepository vehiculeRepository;

    @Mock
    private IMaintenanceRepository maintenanceRepository;

    @Mock
    private IAgenceRepository agenceRepository;

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private IEmployeRepository employeRepository;

    @Mock
    private IEquipementRepository equipementRepository;

    @Mock
    private IReservationRepository reservationRepository;

    @Test
    void contratCreationRejectsPresetIdAndNegativeAmount() {
        ContratServiceImpl service = new ContratServiceImpl(contratRepository);
        Contrat withId = new Contrat();
        withId.setIdContrat(5L);

        assertThrows(IllegalArgumentException.class, () -> service.create(withId));
        verify(contratRepository, never()).save(withId);

        Contrat negative = new Contrat();
        negative.setMontantTotal(new BigDecimal("-0.01"));
        assertThrows(IllegalArgumentException.class, () -> service.create(negative));
        verify(contratRepository, never()).save(negative);
    }

    @Test
    void contratUpdateCopiesAllowedFieldsAndPreservesPaymentsAndId() {
        ContratServiceImpl service = new ContratServiceImpl(contratRepository);
        Contrat existing = new Contrat();
        existing.setIdContrat(7L);
        existing.setPaiements(new LinkedHashSet<>());
        Contrat updates = new Contrat();
        updates.setIdContrat(999L);
        updates.setMontantTotal(new BigDecimal("125.50"));
        updates.setValide(true);
        when(contratRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(contratRepository.save(existing)).thenReturn(existing);

        Contrat result = service.update(7L, updates);

        assertEquals(7L, result.getIdContrat());
        assertEquals(new BigDecimal("125.50"), result.getMontantTotal());
        assertTrue(result.isValide());
        verify(contratRepository).save(existing);
    }

    @Test
    void missingContratUsesDomainNotFoundException() {
        ContratServiceImpl service = new ContratServiceImpl(contratRepository);
        when(contratRepository.findById(44L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(44L));
    }

    @Test
    void vehicleRejectsNegativeDailyRate() {
        VehiculeServiceImpl service = new VehiculeServiceImpl(vehiculeRepository);
        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(new BigDecimal("-1.00"));

        assertThrows(IllegalArgumentException.class, () -> service.create(vehicule));
        verify(vehiculeRepository, never()).save(vehicule);
    }

    @Test
    void maintenanceRejectsEndDateBeforeStartDate() {
        MaintenanceServiceImpl service = new MaintenanceServiceImpl(maintenanceRepository);
        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(LocalDate.of(2026, 10, 10));
        maintenance.setDateFin(LocalDate.of(2026, 10, 9));

        assertThrows(IllegalArgumentException.class, () -> service.create(maintenance));
        verify(maintenanceRepository, never()).save(maintenance);
    }

    @Test
    void agenceRejectsBlankName() {
        AgenceServiceImpl service = new AgenceServiceImpl(agenceRepository);

        assertThrows(IllegalArgumentException.class, () -> service.create(new Agence()));
    }

    @Test
    void employeRejectsBlankName() {
        EmployeServiceImpl service = new EmployeServiceImpl(employeRepository);

        assertThrows(IllegalArgumentException.class, () -> service.create(new Employe()));
    }

    @Test
    void equipementRejectsBlankLabel() {
        EquipementServiceImpl service = new EquipementServiceImpl(equipementRepository);

        assertThrows(IllegalArgumentException.class, () -> service.create(new Equipement()));
    }

    @Test
    void equipementRejectsNegativeQuantity() {
        EquipementServiceImpl service = new EquipementServiceImpl(equipementRepository);
        Equipement equipement = new Equipement();
        equipement.setLibelle("GPS");
        equipement.setQuantite(-1);

        assertThrows(IllegalArgumentException.class, () -> service.create(equipement));
    }

    @Test
    void clientRequiresNameAndEmail() {
        ClientServiceImpl service = new ClientServiceImpl(clientRepository);
        Client client = new Client();
        client.setNom("Client");

        assertThrows(IllegalArgumentException.class, () -> service.create(client));
    }

    @Test
    void reservationRejectsEndDateBeforeStartDate() {
        ReservationServiceImpl service = new ReservationServiceImpl(reservationRepository);
        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2026, 10, 10));
        reservation.setDateFin(LocalDate.of(2026, 10, 9));

        assertThrows(IllegalArgumentException.class, () -> service.create(reservation));
    }

    @Test
    void maintenanceRejectsNegativeCost() {
        MaintenanceServiceImpl service = new MaintenanceServiceImpl(maintenanceRepository);
        Maintenance maintenance = new Maintenance();
        maintenance.setCout(new BigDecimal("-0.01"));

        assertThrows(IllegalArgumentException.class, () -> service.create(maintenance));
    }
}

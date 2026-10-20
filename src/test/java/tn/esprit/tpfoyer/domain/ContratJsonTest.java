package tn.esprit.tpfoyer.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

class ContratJsonTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void deserializingContractPaymentsSetsBackReferenceWithoutSerializingCycle() throws Exception {
        String json = """
                {
                  "dateSignature": "2026-11-02",
                  "montantTotal": 480.00,
                  "valide": true,
                  "paiements": [
                    { "montant": 150.00, "datePaiement": "2026-11-02", "modePaiement": "CARTE" }
                  ]
                }
                """;

        Contrat contrat = objectMapper.readValue(json, Contrat.class);
        Paiement paiement = contrat.getPaiements().iterator().next();

        assertSame(contrat, paiement.getContrat());
        assertFalse(objectMapper.writeValueAsString(contrat).contains("\"contrat\""));
    }
}

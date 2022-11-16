package edu.uky.pml.epic;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.util.BundleUtil;
import edu.uky.pml.epic.api.EpicAPI;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3 || args.length > 4)
            printUsage();
        String epicURL = args[0];
        String clientId = args[1];
        String privateKeyFile = args[2];
        EpicAPI epicAPI;
        if (args.length == 3)
            epicAPI = new EpicAPI(epicURL, clientId, privateKeyFile);
        else
            epicAPI = new EpicAPI(epicURL, clientId, privateKeyFile, args[3]);
        IGenericClient client = epicAPI.getFhirClient();
        Bundle bundle = client
                .search()
                .forResource(Patient.class)
                .where(
                        Patient.IDENTIFIER.exactly().systemAndValues("MRN", "203713")
                )
                .returnBundle(Bundle.class).execute();

        List<IBaseResource> patients = new ArrayList<>(BundleUtil.toListOfResources(epicAPI.getFhirContext(), bundle));
        String string = epicAPI.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(patients.get(0));
        System.out.println(string);
    }

    private static void printUsage() {
        System.err.println("required parameters: epic_url backend_service_client_id private_key_file_path [public_certificate_file_path]");
        System.exit(1);
    }
}
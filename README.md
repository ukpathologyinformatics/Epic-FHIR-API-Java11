# Epic FHIR [Backend Service](https://fhir.epic.com/Documentation?docId=oauth2&section=BackendOAuth2Guide) Authentication Wrapper for [HAPI FHIR](https://hapifhir.io/) client

A Java wrapper implementation to enable Epic FHIR 
[Backend Service](https://fhir.epic.com/Documentation?docId=oauth2&section=BackendOAuth2Guide) authentication
with the [HAPI FHIR](https://hapifhir.io/) Java FHIR implementation with minimal setup.

### Creating an EpicAPI wrapper instance
Creating a basic EpicAPI wrapper instance:
```java
// Using the Epic FHIR Sandbox as an example
String epicURL = "https://fhir.epic.com/interconnect-fhir-oauth";

// This should be the 'Client ID' associated with your Epic FHIR or AppMarket app
String clientId = "client_id";

// This should be the private key used to sign JWT requests and generate the
// publickey509.pem you submitted to Epic FHIR or AppMarket
String privateKeyFile = "path/to/privatekey.pem";

// Instantiate a simple EpicAPI wrapper instance
EpicAPI epicAPI = new EpicAPI(epicURL, clientId, privateKeyFile);
```

Creating an EpicAPI wrapper instance with authorization request JWT validation:
```java
// Using the Epic FHIR Sandbox as an example
String epicURL = "https://fhir.epic.com/interconnect-fhir-oauth";

// This should be the 'Client ID' associated with your Epic FHIR or AppMarket app
String clientId = "client_id";

// This should be the private key used to sign JWT requests and generate the
// publickey509.pem you submitted to Epic FHIR or AppMarket
String privateKeyFile = "path/to/privatekey.pem";

// This should be the public certificate file you uploaded to Epic FHIR or AppMarket
String publicCertFile = "path/to/publicCert509.pem";

// Instantiate a simple EpicAPI wrapper instance
EpicAPI epicAPI = new EpicAPI(epicURL, clientId, privateKeyFile, publicCertFile);
```

### Using your EpicAPI wrapper instance
```java
// Instantiate a generic restful client
IGenericClient client = epicAPI.getFhirClient();

// Search for a patient by MRN
Bundle bundle = client
    .search()
    .forResource(Patient.class)
    .where(
        Patient.IDENTIFIER.exactly().systemAndValues("MRN", "some_mrn")
    )
    .returnBundle(Bundle.class).execute();

// Process the search results
List<IBaseResource> patients = new ArrayList<>(
    BundleUtil.toListOfResources(
        epicAPI.getFhirContext(), bundle
    )
);
```
package tech.gabrieljbo.lab.hubspotta.contacts.features.retrievecontacts.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class RetrieveContactsFromCRMHubSpotAdapter implements RetrieveContactsFromCRM {

    @Value( "${hubspot.contacts.endpoint}" )
    private String hubspotContactsEndpoint;
    private final RestTemplate restClient;

    @Override
    public RetrieveContactsResponse retrieveContacts(RetrieveContactsQuery retrieveContactsQuery) {
        Integer limit = retrieveContactsQuery.getLimit();
        Integer offset = retrieveContactsQuery.getOffset();
        String properties = retrieveContactsQuery.getProperties();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(hubspotContactsEndpoint);
        if (limit != null) uriBuilder.queryParam("limit", limit);
        if (offset != null) uriBuilder.queryParam("offset", offset);
        if (properties != null) uriBuilder.queryParam("properties", properties);

        ResponseEntity<RetrieveContactsHubSpotResponse> hubspotResponse = restClient.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RetrieveContactsHubSpotResponse>() {}
        );
        var hubspotResponseResults = hubspotResponse.getBody().getResults();

        var response = RetrieveContactsResponse.builder().contacts(hubspotResponseResults).build();
        return response;
    }

}

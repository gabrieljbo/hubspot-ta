package tech.gabrieljbo.lab.hubspotta.contacts.features.retrievecontacts.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.gabrieljbo.lab.hubspotta.crosscutting.commons.responsemodel.SuccessResponse;
import tech.gabrieljbo.lab.hubspotta.crosscutting.commons.routing.BaseEndpoint;

@RestController
@RequestMapping("/api/v1")
public class RetrieveContactsEndpoint extends BaseEndpoint {

    @GetMapping("/contacts")
    public SuccessResponse<RetrieveContactsResponse> retrieveContacts(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String properties) {

        RetrieveContactsQuery retrieveContactsQuery = RetrieveContactsQuery.builder()
                .limit(limit)
                .offset(offset)
                .properties(properties)
                .build();
        RetrieveContactsResponse retrieveContactsResponse = mediator.send(retrieveContactsQuery);

        SuccessResponse<RetrieveContactsResponse> response = SuccessResponse.<RetrieveContactsResponse>builder()
                .data(retrieveContactsResponse)
                .build();
        return response;
    }

}

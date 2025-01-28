package tech.gabrieljbo.lab.hubspotta.contacts.features.createcontact.v1;

import org.springframework.web.bind.annotation.*;
import tech.gabrieljbo.lab.hubspotta.crosscutting.commons.responsemodel.SuccessResponse;
import tech.gabrieljbo.lab.hubspotta.crosscutting.commons.routing.BaseEndpoint;

@RestController
@RequestMapping("/api/v1")
public class CreateContactEndpoint extends BaseEndpoint {

    @PostMapping("/contacts")
    public SuccessResponse<CreateContactResponse> createContact(@RequestBody CreateContactCommand createContactCommand) {
        CreateContactResponse createContactResponse = mediator.send(createContactCommand);

        SuccessResponse<CreateContactResponse> response = SuccessResponse.<CreateContactResponse>builder()
                .data(createContactResponse)
                .build();
        return response;
    }

}

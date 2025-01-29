package tech.gabrieljbo.lab.hubspotta.contacts.features.createcontact.v1;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.gabrieljbo.lab.hubspotta.crosscutting.commons.datavalidation.Validator;

@Slf4j
@Component
@AllArgsConstructor
public class CreateContactCommandHandler implements Command.Handler<CreateContactCommand, CreateContactResponse> {

    private CreateContactInCRM createContactInCRM;

    @Override
    public CreateContactResponse handle(CreateContactCommand createContactCommand) {
        log.info("Creating contact with the command -> {}", createContactCommand);

        Validator.validate(createContactCommand);

        CreateContactResponse createContactResponse = createContactInCRM.createContact(createContactCommand);
        return createContactResponse;
    }

}

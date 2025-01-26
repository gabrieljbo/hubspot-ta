package tech.gabrieljbo.lab.hubspotta.contacts.features.retrievecontacts.v1;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RetrieveContactsQueryHandler implements Command.Handler<RetrieveContactsQuery, RetrieveContactsResponse> {

    private final RetrieveContactsFromCRM retrieveContactsFromCRM;

    @Override
    public RetrieveContactsResponse handle(RetrieveContactsQuery retrieveContactsQuery) {
        var response = retrieveContactsFromCRM.retrieveContacts(retrieveContactsQuery);
        return response;
    }
}

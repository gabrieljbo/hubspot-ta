package tech.gabrieljbo.lab.hubspotta.contacts.features.retrievecontacts.v1;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RetrieveContactsQueryHandler implements Command.Handler<RetrieveContactsQuery, RetrieveContactsResponse> {

    private final RetrieveContactsFromCRM retrieveContactsFromCRM;

    @Override
    public RetrieveContactsResponse handle(RetrieveContactsQuery retrieveContactsQuery) {
        log.info("Retrieving contacts with the query -> {}", retrieveContactsQuery);

        var response = retrieveContactsFromCRM.retrieveContacts(retrieveContactsQuery);
        return response;
    }
}

package tech.gabrieljbo.lab.hubspotta.contacts.features.retrievecontacts.v1;

import an.awesome.pipelinr.Command;
import lombok.*;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class RetrieveContactsQuery implements Command<RetrieveContactsResponse> {

    private Integer limit;
    private Integer offset;
    private String properties;

}


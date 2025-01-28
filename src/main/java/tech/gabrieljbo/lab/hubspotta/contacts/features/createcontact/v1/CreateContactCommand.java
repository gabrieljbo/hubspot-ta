package tech.gabrieljbo.lab.hubspotta.contacts.features.createcontact.v1;

import an.awesome.pipelinr.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CreateContactCommand implements Command<CreateContactResponse> {

    @NotNull(message = "firstName cannot be null")
    @Size(min = 2, max = 40, message
            = "firstName must be between 2 and 40 characters")
    private String firstName;
    @NotNull(message = "lastName cannot be null")
    @Size(min = 2, max = 40, message
            = "lastName must be between 2 and 40 characters")
    private String lastName;
    @Email(message = "email should be valid")
    private String email;

}

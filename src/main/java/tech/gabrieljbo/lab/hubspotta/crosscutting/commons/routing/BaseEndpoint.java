package tech.gabrieljbo.lab.hubspotta.crosscutting.commons.routing;

import an.awesome.pipelinr.Pipeline;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseEndpoint {

    @Autowired
    protected Pipeline mediator;

}

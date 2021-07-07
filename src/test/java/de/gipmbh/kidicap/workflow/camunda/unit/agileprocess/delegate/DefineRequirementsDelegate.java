/*
 * Copyright © by GiP mbH
 */
package de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.delegate;

import de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.ProcessConstants;
import de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.model.Context;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefineRequirementsDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefineRequirementsDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Context context = (Context) execution.getVariable(ProcessConstants.PROCESS_VAR_CONTEXT);
        if (context == null) {
            context = new Context();
            context.setRequirementsFit(new Random().nextBoolean());
            execution.setVariable(ProcessConstants.PROCESS_VAR_CONTEXT, context);
            LOGGER.info("Context created: '{}'", context);
        } else {
            LOGGER.info("Context exists: '{}'", context);
        }
    }

}

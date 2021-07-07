/*
 * Copyright © by GiP mbH
 */
package de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.delegate;

import de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.ProcessConstants;
import de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.model.Context;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcceptRejectUserStoryDelegate implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptRejectUserStoryDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Context context = (Context) execution.getVariable(ProcessConstants.PROCESS_VAR_CONTEXT);
        if (context.isMustRedefineRequirements()) {
            // remove story from backlog
            LOGGER.info("Story rejected! Will remove from backlog...");
        }

        if (context.isRequirementsFit()) {
            // move story from backlog
            LOGGER.info("Story accepted! Will move to backlog for sprint planing!");
        }
    }

}

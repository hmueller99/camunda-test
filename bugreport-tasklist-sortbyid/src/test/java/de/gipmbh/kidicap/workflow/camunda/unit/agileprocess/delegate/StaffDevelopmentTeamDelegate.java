/*
 * Copyright © by GiP mbH
 */
package de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.delegate;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffDevelopmentTeamDelegate implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaffDevelopmentTeamDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<String> assigneeList = new ArrayList<>();
        if (execution.hasVariable("sprint_started") && (boolean) execution.getVariable("sprint_started")) {
            assigneeList.add("cipher");
            assigneeList.add("dozer");
            LOGGER.info("Sprint-Devteam staffffffed with '{}'", assigneeList);
        } else {
            assigneeList.add("jondoe");
            assigneeList.add("maryx");
            assigneeList.add("kermit");
            LOGGER.info("Refinement-Devteam staffffffed with '{}'", assigneeList);
        }
        execution.setVariable("assigneeList", assigneeList);
    }

}

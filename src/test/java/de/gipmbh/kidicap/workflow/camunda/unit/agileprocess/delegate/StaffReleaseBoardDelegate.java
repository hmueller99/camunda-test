/*
 * Copyright © by GiP mbH
 */
package de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.delegate;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffReleaseBoardDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffReleaseBoardDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<String> assigneeList = new ArrayList<>();
        assigneeList.add("Estbourg");
        assigneeList.add("Delta");
        assigneeList.add("He-Man");
        LOGGER.info("Release Board stafffed with '{}'", assigneeList);
        execution.setVariable("assigneeList", assigneeList);
    }

}

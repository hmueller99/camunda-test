package de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.delegate;

import de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.ProcessConstants;
import de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.model.Context;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * This is an easy adapter implementation illustrating how a Java Delegate can be used from within a BPMN 2.0 Service Task.
 */
public class LoggerDelegate implements JavaDelegate, org.camunda.bpm.engine.delegate.ExecutionListener {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        final Context context = (Context) execution.getVariable(ProcessConstants.PROCESS_VAR_CONTEXT);
        if (context == null) {
            throw new IllegalStateException("Context not found!");
        }

        LOGGER.info("\n\n  ... LoggerDelegate invoked by " + "activtyName='" + execution.getCurrentActivityName() + "'"
                + ", activtyId=" + execution.getCurrentActivityId() + ", customBusinessKey= " + context.getId()
                + ", processDefinitionId=" + execution.getProcessDefinitionId() + ", processInstanceId="
                + execution.getProcessInstanceId() + ", businessKey=" + execution.getProcessBusinessKey() + ", executionId="
                + execution.getId() + ", variables=" + execution.getVariables() + " \n\n");
    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        execute(execution);
    }

}

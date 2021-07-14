package de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.listener;

import de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.delegate.LoggerDelegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;

/**
 * This is an easy adapter implementation illustrating how a Java Delegate can be used from within a BPMN 2.0 Service Task.
 */
public class MultiInstanceTaskStartListener implements org.camunda.bpm.engine.delegate.ExecutionListener {

    private org.camunda.bpm.engine.delegate.Expression injectedFieldValue;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        new LoggerDelegate().execute(execution);
    }

    public void setInjectedFieldValue(org.camunda.bpm.engine.delegate.Expression injectedFieldValue) {
        this.injectedFieldValue = injectedFieldValue;
    }
}

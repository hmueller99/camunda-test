package de.gipmbh.kidicap.workflow.camunda.unit.agileprocess;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

import de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.model.Context;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.SignalEventReceivedBuilder;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class ProcessUnitTest {

    static {
        LogFactory.useSlf4jLogging(); // MyBatis
    }

    @ClassRule
    @Rule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    @Before
    public void setup() {
        init(rule.getProcessEngine());
    }

    @Test
    @Deployment(resources = "agileProcess.bpmn")
    public void testHappyPath() {
        HashMap<String, Object> variables = new HashMap<>();
        Context value = new Context();
        value.setRequirementsFit(true);
        variables.put("context", value);

        TaskService taskService = ProcessUnitTest.rule.getTaskService();
        RuntimeService runtimeService = ProcessUnitTest.rule.getRuntimeService();

        ExecutionQuery activeExecutions = runtimeService.createExecutionQuery().active();
        TaskQuery activeTasks = taskService.createTaskQuery().active().taskAssigned().orderByTaskId().asc();

        // start
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstants.PROCESS_DEFINITION_KEY,
                variables);

        List<Execution> activeExecutionsList = activeExecutions.list();
        assertThat(activeExecutionsList).isNotEmpty();
        assertThat(processInstance).isNotEnded();

        // check for open user tasks
        List<Task> refinementTasks = activeTasks.list();
        assertThat(refinementTasks).extracting("assignee").containsExactly("jondoe", "maryx", "kermit");

        // complete open user tasks in order and check that 'main' process instance has stopped
        refinementTasks.forEach(t -> taskService.complete(t.getId()));
        refinementTasks = activeTasks.list();
        assertThat(refinementTasks).isEmpty();
        assertThat(processInstance).isEnded();

        // issue new event 'sprint started'
        SignalEventReceivedBuilder signalEvent = runtimeService.createSignalEvent("SprintStart");
        variables.put("sprint_started", true);
        signalEvent.setVariables(variables);
        signalEvent.send();

        // check for exactly ONE new, active process instance
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().active().list();
        assertThat(list).hasSize(1);
        ProcessInstance sprintProcessInstance = list.get(0);
        assertThat(sprintProcessInstance).isNotEnded();

        // check for open user tasks
        List<Task> developmentTasks = activeTasks.list();
        assertThat(developmentTasks).extracting("assignee").containsExactly("cipher", "dozer");

        // complete open user tasks in order and check that 'auxillary' process instance has stopped
        developmentTasks.forEach(t -> taskService.complete(t.getId()));
        developmentTasks = activeTasks.list();
        assertThat(developmentTasks).isEmpty();
        assertThat(sprintProcessInstance).isEnded();
    }

    @Test
    @Deployment(resources = "agileProcess.bpmn")
    public void testPittyPath() {
        HashMap<String, Object> variables = new HashMap<>();
        Context value = new Context();
        value.setRequirementsFit(false);
        variables.put("context", value);

        ProcessInstance processInstance = processEngine().getRuntimeService()
                .startProcessInstanceByKey(ProcessConstants.PROCESS_DEFINITION_KEY, variables);

        ExecutionQuery activeExecutions = ProcessUnitTest.rule.getRuntimeService().createExecutionQuery().active();
        List<Execution> activeExecutionsList = activeExecutions.list();
        assertThat(activeExecutionsList).isNotEmpty();

        TaskQuery activeTasks = ProcessUnitTest.rule.getTaskService().createTaskQuery().active();
        List<Task> activeTasksList = activeTasks.list();
        assertThat(activeTasksList).extracting("assignee").containsExactly("Estbourg", "Delta", "He-Man");
        assertThat(processInstance).isNotEnded();
    }

}

/*
 * Copyright © by GiP mbH
 */
package de.gipmbh.kidicap.workflow.camunda.unit.agileprocess.model;

import java.util.Date;
import java.util.UUID;

public class Context {

    private UUID id = UUID.randomUUID();

    private String name = "test";
    private Date created = new Date();
    private boolean requirementsFit;
    private boolean mustRedefineRequirements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public UUID getId() {
        return id;
    }

    public boolean isRequirementsFit() {
        return requirementsFit;
    }

    public void setRequirementsFit(boolean requirementsFit) {
        this.requirementsFit = requirementsFit;
    }

    public boolean isMustRedefineRequirements() {
        return mustRedefineRequirements;
    }

    public void setMustRedefineRequirements(boolean mustRedefineRequirements) {
        this.mustRedefineRequirements = mustRedefineRequirements;
    }

    @Override
    public String toString() {
        return "Context [id=" + id + ", name=" + name + ", created=" + created + ", requirementsFit=" + requirementsFit
                + ", mustRedefineRequirements=" + mustRedefineRequirements + "]";
    }

}


package com.company.util;

import javax.ws.rs.core.Response.Status;

/**
 *
 * @author santiago.mamani
 */
public class ObjectResponce {

    private Status status;
    private ObjectModel objectModel;

    public ObjectResponce() {
    }

    public ObjectResponce(Status status) {
        this.status = status;
    }

    public ObjectResponce(Status status, ObjectModel objectModel) {
        this.status = status;
        this.objectModel = objectModel;
    }

    public Status getStatus() {
        return status;
    }

    public ObjectModel getObjectModel() {
        return objectModel;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setObjectModel(ObjectModel objectModel) {
        this.objectModel = objectModel;
    }
}

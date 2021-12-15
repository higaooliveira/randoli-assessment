package com.higor.randoliassessment.exceptions;

import java.util.UUID;

public class ResourceNotFound extends CustomException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFound(UUID id) {
        super("Resource not Found. Id: " + id, 404);
    }

}

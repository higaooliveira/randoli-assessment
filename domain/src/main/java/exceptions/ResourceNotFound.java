package exceptions;

import java.util.UUID;

public class ResourceNotFound extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ResourceNotFound(UUID id) {
        super("Resource not Found. Id: " + id);
    }
}

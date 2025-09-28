package fr.rougeux.projet.auction.exception;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private List<String> clefsExternalisations;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public List<String> getClefsExternalisations() {
        return clefsExternalisations;
    }

    public void add(String clef) {
        if (clefsExternalisations == null) {
            clefsExternalisations = new ArrayList<>();
        }
        clefsExternalisations.add(clef);
    }
}
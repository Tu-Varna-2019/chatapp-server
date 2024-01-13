package model.dataclass;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ClientResponse {
    public String status;
    public String message;
    public DataResponse data;

    public ClientResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}

package model.dataclass;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ClientResponse {
    public String eventType;
    public DataResponse data;
    // private String filter;

}

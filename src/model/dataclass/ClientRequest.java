package model.dataclass;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ClientRequest {
    public String eventType;
    public DataRequest data;
    // private String filter;

}

package koanruler.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hose on 2017/8/16.
 */
public class ServiceResult {

    private String result;
    private Object data;
    private final Logger logger = LoggerFactory.getLogger(ServiceResult.class);

    ObjectMapper objectMapper = new ObjectMapper();

    public ServiceResult(boolean success, Object data){
        this.result = success ? "success" : "failed";
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJson(){
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }
}

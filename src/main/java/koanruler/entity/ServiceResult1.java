package koanruler.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hose on 2017/8/16.
 */
public class ServiceResult1 {

    private Map<String, Object> result = new HashMap();
    private final Logger logger = LoggerFactory.getLogger(ServiceResult1.class);

    ObjectMapper objectMapper = new ObjectMapper();

    public ServiceResult1(boolean success, Long timeUsed, String dataKey, Object data){
        put("result", success ? "success" : "failed");
        put("time_used", timeUsed);
        put(dataKey, data);
    }

    public void put(String key, Object value) {
        result.put(key, value);
    }

    public String toJson(){
        try {
            return objectMapper.writeValueAsString(this.result);
        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }
}

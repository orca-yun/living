package ag.orca.living.util;

import ag.orca.living.errors.OrcaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class JsonUtil {

    public static <T> String beanToJson(T t) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw OrcaException.error("转换错误", e);
        }
    }

    public static <T> T jsonToBean(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        T t = null;
        try {
            t = mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw OrcaException.error("转换错误", e);
        }
        return t;
    }


    public static String prettyJson(String json) {
        Object o = jsonToBean(json, Object.class);
        try {
            ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw OrcaException.error("转换错误", e);
        }
    }

    public static String compressJson(String json) {
        Object o = jsonToBean(json, Object.class);
        return beanToJson(o);
    }


}

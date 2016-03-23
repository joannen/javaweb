package se.threegorillas.provider.webparser;

import com.google.gson.*;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import se.threegorillas.provider.WebUser;
import se.threegorillas.provider.WebWorkItem;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public final class WorkItemProvider implements MessageBodyReader<WebWorkItem>, MessageBodyWriter<WebWorkItem> {


    private static final Gson gson = new GsonBuilder().registerTypeAdapter(WebWorkItem.class, new WebWorkItemAdapter()).create();

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return false;
    }

    @Override
    public WebWorkItem readFrom(Class<WebWorkItem> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        return null;
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return false;
    }

    @Override
    public long getSize(WebWorkItem webWorkItem, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(WebWorkItem webWorkItem, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {

    }

    private final static class WebWorkItemAdapter implements JsonSerializer<WebWorkItem>, JsonDeserializer<WebWorkItem> {

        @Override
        public WebWorkItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            JsonObject workItemJson = jsonElement.getAsJsonObject();
            Long id = workItemJson.get("id").getAsLong();
            String description = workItemJson.get("description").getAsString();

            return new WebWorkItem(id, description);
        }
    }

    @Override
    public JsonElement serialize(WebUser webUser, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();

        json.addProperty("id",webUser.getId());
        json.addProperty("firstName", webUser.getFirstName());
        json.addProperty("lastName", webUser.getLastName());
        json.addProperty("username", webUser.getUsername());
        json.addProperty("password", webUser.getPassword());
        json.addProperty("userNumber", webUser.getUserNumber());

        return json;
    }
}

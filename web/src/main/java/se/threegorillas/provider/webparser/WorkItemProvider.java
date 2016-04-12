package se.threegorillas.provider.webparser;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import se.threegorillas.model.WebWorkItem;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class WorkItemProvider implements MessageBodyReader<WebWorkItem>, MessageBodyWriter<WebWorkItem> {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(WebWorkItem.class, new WebWorkItemAdapter()).create();

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.isAssignableFrom(WebWorkItem.class);
    }

    @Override
    public WebWorkItem readFrom(Class<WebWorkItem> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        return gson.fromJson(new InputStreamReader(inputStream), WebWorkItem.class);
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.isAssignableFrom(WebWorkItem.class);
    }

    @Override
    public void writeTo(WebWorkItem webWorkItem, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream))) {
            gson.toJson(webWorkItem, WebWorkItem.class, writer);
        }
    }

    @Override
    public long getSize(WebWorkItem webWorkItem, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    private final static class WebWorkItemAdapter implements JsonSerializer<WebWorkItem>, JsonDeserializer<WebWorkItem> {

        @Override
        public WebWorkItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject workItemJson = jsonElement.getAsJsonObject();

            Long id = workItemJson.get("id").getAsLong();
            String description = workItemJson.get("description").getAsString();
            String status = null;
            if (workItemJson.has("status")){
                status = workItemJson.get("status").getAsString();
            }
            return new WebWorkItem.Builder(id, description).withStatus(status).build();
        }

        @Override
        public JsonElement serialize(WebWorkItem webWorkItem, Type type, JsonSerializationContext jsonSerializationContext) {
            String issue = webWorkItem.getIssueDescription();
            JsonObject json = new JsonObject();
            json.addProperty("id", webWorkItem.getId());
            json.addProperty("description", webWorkItem.getDescription());
            json.addProperty("assignedUsername", webWorkItem.getAssignedUsername());
            json.addProperty("status", webWorkItem.getStatus());
            json.addProperty("issueDescription", issue);

            return json;
        }
    }

}

package se.threegorillas.provider.webparser;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import se.threegorillas.provider.WebWorkItem;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

@Provider
public final class ArrayListWorkItemProvider implements MessageBodyWriter<ArrayList<WebWorkItem>> {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Collection.class, new WebWorkItemAdapter()).create();

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.isAssignableFrom(ArrayList.class);
    }

    @Override
    public long getSize(ArrayList<WebWorkItem> webWorkItems, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(ArrayList<WebWorkItem> webWorkItems, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream))) {
            gson.toJson(webWorkItems, ArrayList.class, writer);
        }
    }


    private final static class WebWorkItemAdapter implements JsonSerializer<ArrayList<WebWorkItem>>, JsonDeserializer<ArrayList<WebWorkItem>> {

        @Override
        public ArrayList<WebWorkItem> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            JsonObject workItemJson = jsonElement.getAsJsonObject();
            Long id = workItemJson.get("id").getAsLong();
            String description = workItemJson.get("description").getAsString();

            return null;
        }

        /* how to serialize standard webworkitem object
        public JsonElement serialize(WebWorkItem webWorkItem, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();

            json.addProperty("id", webWorkItem.getId());
            json.addProperty("description", webWorkItem.getDescription());

            return json;
        } */

        @Override
        public JsonElement serialize(ArrayList<WebWorkItem> webWorkItems, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();
            JsonArray workitems = new JsonArray();
            webWorkItems.forEach(webwi -> workitems.add(serializeWorkItem(webwi)));
            json.add("workitems", workitems);

            return json;
        }

        private JsonElement serializeWorkItem(WebWorkItem webWorkItem) {
            JsonObject json = new JsonObject();

            json.addProperty("id", webWorkItem.getId());
            json.addProperty("description", webWorkItem.getDescription());
            json.addProperty("assignedUser", webWorkItem.getAssignedUsername());

            return json;
        }
    }
}

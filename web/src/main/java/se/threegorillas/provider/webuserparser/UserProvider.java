package se.threegorillas.provider.webuserparser;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import se.threegorillas.provider.WebUser;

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
public final class UserProvider implements MessageBodyReader<WebUser>, MessageBodyWriter<WebUser> {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(WebUser.class, new WebUserAdapter()).create();


    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.isAssignableFrom(WebUser.class);
    }

    @Override
    public WebUser readFrom(Class<WebUser> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        return gson.fromJson(new InputStreamReader(inputStream), WebUser.class);
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.isAssignableFrom(WebUser.class);
    }

    @Override
    public long getSize(WebUser webUser, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(WebUser webUser, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream))) {
            gson.toJson(webUser, WebUser.class, writer);
        }
    }

    private static final class WebUserAdapter implements JsonSerializer<WebUser>, JsonDeserializer<WebUser> {

        @Override
        public WebUser deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject userJson = jsonElement.getAsJsonObject();
            String username = userJson.get("username").getAsString();
            String password = userJson.get("password").getAsString();

            return new WebUser(username, password);
        }

        @Override
        public JsonElement serialize(WebUser webUser, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();

            json.addProperty("username", webUser.getUsername());
            json.addProperty("password", webUser.getPassword());

            return json;
        }
    }
}

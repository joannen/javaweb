package se.threegorillas.provider.webparser;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import se.threegorillas.model.WebTeam;

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

/**
 * Created by joanne on 23/03/16.
 */

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamProvider implements MessageBodyReader<WebTeam>, MessageBodyWriter<WebTeam> {

    Gson gson = new GsonBuilder().registerTypeAdapter(WebTeam.class, new WebTeamAdapter()).create();


    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.isAssignableFrom(WebTeam.class);
    }

    @Override
    public WebTeam readFrom(Class<WebTeam> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        return gson.fromJson(new InputStreamReader(inputStream), WebTeam.class);
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return  aClass.isAssignableFrom(WebTeam.class);
    }

    @Override
    public long getSize(WebTeam webTeam, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(WebTeam webTeam, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        try(JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream))){
            gson.toJson(webTeam, WebTeam.class, writer);
        }
    }

    private static final class WebTeamAdapter implements JsonSerializer<WebTeam>, JsonDeserializer<WebTeam>{

        @Override
        public WebTeam deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject teamJson = jsonElement.getAsJsonObject();

            Long id = teamJson.get("id").getAsLong();
            String teamName = teamJson.get("teamName").getAsString();

            return new WebTeam(id, teamName);
        }

        @Override
        public JsonElement serialize(WebTeam webTeam, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();

            json.addProperty("id", webTeam.getId());
            json.addProperty("teamName", webTeam.getTeamName());
            json.addProperty("teamStatus", webTeam.getTeamStatus());

            return json;
        }
    }

}

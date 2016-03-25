package se.threegorillas.provider.webparser;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import se.threegorillas.provider.WebTeam;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by TheYellowBelliedMarmot on 2016-03-24.
 */

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArrayListTeamProvider implements MessageBodyWriter<ArrayList<WebTeam>>{

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(ArrayListTeamAdapter.class, new ArrayListTeamAdapter()).create();

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.isAssignableFrom(ArrayList.class);
    }

    @Override
    public void writeTo(ArrayList<WebTeam> webTeams, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        try(JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream))){
            gson.toJson(webTeams, ArrayList.class, writer);
        }
    }

    @Override
    public long getSize(ArrayList<WebTeam> webTeams, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    private static final class ArrayListTeamAdapter implements JsonSerializer<ArrayList<WebTeam>> {


        @Override
        public JsonElement serialize(ArrayList<WebTeam> webTeams, Type type, JsonSerializationContext jsonSerializationContext) {

            JsonArray jsonArray = new JsonArray();

            for(WebTeam webTeam : webTeams){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", webTeam.getId());
                jsonObject.addProperty("teamName", webTeam.getTeamName());
                jsonObject.addProperty("teamStatus", webTeam.getTeamStatus());
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        }
    }

}

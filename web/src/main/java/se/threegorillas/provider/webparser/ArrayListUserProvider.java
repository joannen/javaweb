package se.threegorillas.provider.webparser;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import se.threegorillas.model.WebUser;

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
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Created by joanne on 24/03/16.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArrayListUserProvider implements  MessageBodyWriter<ArrayList<WebUser>>, MessageBodyReader<ArrayList<WebUser>> {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(ArrayListUserAdapter.class, new ArrayListUserAdapter()).create();

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.isAssignableFrom(ArrayList.class);
    }

    @Override
    public long getSize(ArrayList<WebUser> webUsers, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(ArrayList<WebUser> webUsers, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        try(JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream))) {
            gson.toJson(webUsers, ArrayList.class, writer);
        }
    }

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.isAssignableFrom(ArrayList.class);
    }

    @Override
    public ArrayList<WebUser> readFrom(Class<ArrayList<WebUser>> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        return gson.fromJson(new InputStreamReader(inputStream), ArrayList.class);
    }

    private static final class ArrayListUserAdapter implements JsonSerializer<ArrayList<WebUser>>, JsonDeserializer<ArrayList<WebUser>>{

        @Override
        public JsonElement serialize(ArrayList<WebUser> webUsers, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonArray jsonArray = new JsonArray();

            for (WebUser webUser:webUsers) {
                JsonObject json2 = new JsonObject();
                json2.addProperty("id",webUser.getId());
                json2.addProperty("firstName", webUser.getFirstName());
                json2.addProperty("lastName", webUser.getLastName());
                json2.addProperty("username", webUser.getUsername());
                json2.addProperty("password", webUser.getPassword());
                json2.addProperty("userNumber", webUser.getUserNumber());
                jsonArray.add(json2);
            }
            return jsonArray;
        }

        @Override
        public ArrayList<WebUser> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonArray jArray = jsonElement.getAsJsonArray();
            Iterator<JsonElement> iterator = jArray.iterator();
            ArrayList<WebUser> webUsers = new ArrayList<>();

            while (iterator.hasNext()){
                JsonElement json = iterator.next();
                WebUser u = gson.fromJson(json, (Class<WebUser>)type);
                webUsers.add(u);
            }
            return webUsers;
        }
    }

}

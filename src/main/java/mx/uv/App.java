package mx.uv;

import static spark.Spark.*;
import com.google.gson.*;
import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App 
{
    public static Gson gson = new Gson();
    
    public static void main(String[] args) {
            port(getHerokuAssignedPort());

            // Aqui va el CORS

            get("/usuarios", (req, res) -> gson.toJson(DAO.dameUsuarios()) );

            post("/", (req, res) -> {
                String datosCliente = req.body();
                String id = UUID.randomUUID().toString();
                Usuario u = gson.fromJson(datosCliente, Usuario.class);
                u.setId(id);

                // devolver una respuesta JSON
                JsonObject objetoJson = new JsonObject();
                objetoJson.addProperty("status", DAO.crearUsuario(u));
                objetoJson.addProperty("id", id);
                return objetoJson; 
            });
        }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}

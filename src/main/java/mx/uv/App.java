package mx.uv;

import static spark.Spark.*;
import com.google.gson.*;

import java.util.List;
import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App {
    public static Gson gson = new Gson();

    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        // Aqui va el CORS
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            System.out.println(accessControlRequestHeaders);
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            System.out.println(accessControlRequestMethod);
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        before((req, res)-> res.header("Access-Control-Allow-Origin", "*"));


        get("/usuarios", (req, res) -> {
            res.type("application/json");
            return gson.toJson(DAO.dameUsuarios());
        });

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

        post("/existe", (req, res) -> {
            String datosCliente = req.body();
            Usuario u = gson.fromJson(datosCliente, Usuario.class);

            // devolver una respuesta JSON
            JsonObject objetoJson = new JsonObject();

            List<Usuario> x = DAO.dameUsuarios();
            for (Usuario xUsuario : DAO.dameUsuarios()) {
                if (xUsuario.getId().equals(u.getId())) {
                    objetoJson.addProperty("status", true);
                    objetoJson.addProperty("usuario", gson.toJson(xUsuario));
                    return objetoJson;
                }
            }
            objetoJson.addProperty("status", false);
            return objetoJson;
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
    }
}

package mx.uv;

public class Usuario {
    String id;
    String nombre;
    String password;

    public Usuario(String id, String usuario, String password) {
        this.id = id;
        this.nombre = usuario;
        this.password = password;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String usuario) {
        this.nombre = usuario;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

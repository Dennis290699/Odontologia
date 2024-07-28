package model;

public class Paciente {
    private String cedula;
    private String nombre;
    private String telefono;
    private String apellido;

    private Paciente(PacienteBuilder builder) {
        this.cedula = builder.cedula;
        this.nombre = builder.nombre;
        this.telefono = builder.telefono;
        this.apellido = builder.apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getApellido() {
        return apellido;
    }

    @Override
    public String toString() {
        return "Paciente{ cedula='" + cedula + "', nombre='" + nombre + "', apellido='" + apellido + "', telefono='" + telefono + "'}";
    }

    public static class PacienteBuilder {
        private String cedula;
        private String nombre;
        private String telefono;
        private String apellido;

        public PacienteBuilder setCedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public PacienteBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public PacienteBuilder setTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public PacienteBuilder setApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Paciente build() {
            return new Paciente(this);
        }
    }
    
    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.apellido = apellido;
    }
}

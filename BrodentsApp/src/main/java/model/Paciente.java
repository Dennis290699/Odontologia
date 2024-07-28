package model;

public class Paciente {
    private String cedula;
    private String nombre;
    private String telefono;
    private String apellido;

    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.apellido = apellido;
    }

    public Paciente() {
    	
    }
	

    public String getCedula() {
		return cedula;
	}



	public void setCedula(String cedula) {
		this.cedula = cedula;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public String getApellido() {
		return apellido;
	}



	public void setApellido(String apellido) {
		this.apellido = apellido;
	}



	@Override
    public String toString() {
        return "Paciente{ cedula='" + cedula + "', nombre='" + nombre + "', apellido='" + apellido + "', teefono ='" + telefono + "'}";
    }
}

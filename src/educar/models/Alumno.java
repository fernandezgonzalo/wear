package educar.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import educar.db.JPA;

public class Alumno {

    public Alumno(String dni, String name, String lastName, String fN,
	    String age, String dir, String tel) {
	Person(dni, name, lastName, fN, age, dir, tel);
	// TODO Auto-generated constructor stub
    }

    public boolean save() {
	String[] columns = { "dni", "apellido_a", "nombre_a", "fechaNac_a",
		"edad_a", "telefono_a", "direccion_a" };
	return save("Alumnos", columns);
    }

    public boolean destroy() {
	return destroy("Alumnos");
    }

    public static Alumno getAlumno(String dni) {
	return getPerson(dni, "Alumnos");
    }

    private String name;
    private String lastName;
    private String fN;
    private String age;
    private String dni;
    private String tel;
    private String dir;
    private static JPA jpa = new JPA();

    private void Person(String dni, String name, String lastName, String fN,
	    String age, String dir, String tel) {
	this.name = name;
	this.lastName = lastName;
	this.fN = fN;
	this.dni = dni;
	this.tel = tel;
	this.dir = dir;
	this.age = age;
    }

    /*
     * Guarda una persona dar como parametro la tabla donde se guarda (docente ,
     * alumno) Return True ssi el borrado fues exitoso
     */
    private boolean save(String tableName, String[] nameColumns) {
	try {
	    Alumno.getPersonByDni(dni, tableName);
	    return false;
	} catch (PersonNotFound e1) {
	    String[] columns = nameColumns;

	    PreparedStatement stm = jpa.newRecord("educar_dev." + tableName,
		    columns);

	    try {
		stm.setString(1, dni);
		stm.setString(2, name);
		stm.setString(3, lastName);
		stm.setString(4, fN);
		stm.setString(5, age);
		stm.setString(6, tel);
		stm.setString(7, dir);
	    } catch (SQLException e) {
		e.printStackTrace();
		return false;
	    }

	    jpa.create(stm);
	    return true;
	}

    }

    /*
     * dni a buscar, nombre de la tabla, nombre de la columna
     */

    private static Alumno getPersonByDni(String dni2, String tableName)
	    throws PersonNotFound {
	// TODO Auto-generated method stub
	ResultSet rs = null;
	Alumno u = null;
	rs = jpa.getByField(tableName, "dni", dni2);

	try {
	    if (rs.next()) {
		u = new Alumno(rs.getString(1), rs.getString(2), rs
			.getString(3), rs.getString(4), rs.getString(5), rs
			.getString(6), rs.getString(7));
	    } else {
		throw new PersonNotFound();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}

	return u;
    }

    /*
     * borra una persona entera por dni ,Return True ssi el borrado fues exitoso
     */
    private boolean destroy(String tableName) {
	// try {
	// alumno = Alumno.getPersonByDni(this.getDni(), tableName);
	// jpa.destroy(tableName, "dni_a", this.getDni());
	// return true;
	// } catch (PersonNotFound e1) {
	// return false;
	// }
	jpa.destroy(tableName, "dni", this.getDni());
	return true;
    }

    /*
     * Return una persona buscada por dni null en el caso que no exista;
     */

    private static Alumno getPerson(String dni, String tableName) {
	Alumno person = null;
	try {
	    person = Alumno.getPersonByDni(dni, tableName);
	    return person;
	} catch (PersonNotFound e) {
	    // TODO Auto-generated catch block
	    return person;
	}
    }

    public String getDni() {
	// TODO Auto-generated method stub
	return dni;
    }

    public String getAge() {
	return age;
    }

    public String getDir() {
	return dir;
    }

    public String getfN() {
	return fN;
    }

    public void setTel(String tel) {
	this.tel = tel;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setfN(String fN) {
	this.fN = fN;
    }

    public void setDni(String dni) {
	this.dni = dni;
    }

    public void setDir(String dir) {
	this.dir = dir;
    }

    public void setAge(String age) {
	this.age = age;
    }

    public String getTel() {
	return tel;
    }

    public String getName() {
	return name;
    }

    public String getLastName() {
	return lastName;
    }

}
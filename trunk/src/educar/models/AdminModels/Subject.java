package educar.models.AdminModels;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import educar.db.JPA;

public class Subject {

	private static String subjectCode;
	private String subjectName;
	private String responsableTeacher;
	private static JPA jpa = new JPA();

	public Subject(String code, String name, String responsable) {
		this.subjectCode = code;
		this.subjectName = name;
		this.responsableTeacher = responsable;
	}

	// busca en la base de datos la materia ingresada, si no existe, la inserta
	// y retorna
	// true, de lo contrario, retorna false sin ingresar nada (materia ya
	// existente) 
	public boolean save(String tableName) {
		try {
			Subject.getSubjectByCode(tableName);
			return false;
		} catch (SubjectNotFound e1) {
			String[] columns = { "cod_materia", "nombre_mat", "dni_docente" };

			PreparedStatement stm = jpa.newRecord("educar_dev." + tableName,
					columns);

			try {
				stm.setString(1, subjectCode);
				stm.setString(2, subjectName);
				stm.setString(3, responsableTeacher);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

			jpa.create(stm);
			return true;
		}

	}

	/*
	 * retorna una materia que buscada en la BD en caso de que no exista,
	 * devuelve null
	 */

	public Subject getSubject() {
		Subject subject = null;
		try {
			subject = Subject.getSubjectByCode("Materia");
			return subject;
		} catch (SubjectNotFound e) {
			// TODO Auto-generated catch block
			return subject;
		}

	}


	/*
	 * busca en la BD una materia por su c�digo (es �nico)
	 */
	private static Subject getSubjectByCode(String tableName)
			throws SubjectNotFound {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		Subject u = null;
		rs = jpa.getByField(tableName, "cod_materia", getCode());

		try {
			if (rs.next()) {
				u = new Subject(rs.getString(1), rs.getString(2),
						rs.getString(3));
			} else {
				throw new SubjectNotFound();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}




	/*
	 * borra una materia por su c�digo, retorna true ssi el borrado fue exitoso 
	 * si la materia no existe, retorna false
	 */
	public boolean deleteSubject() {
		return this.destroy("cod_materia");
	}


	private boolean destroy(String tableName) {
		try {
			this.getSubjectByCode(tableName);
			jpa.destroy(tableName, "cod_materia", this.getCode());
			return true;
		} catch (SubjectNotFound error) {
			return false;
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return false;
	}

	// ////////
	public void setCode(String code) {
		this.subjectCode = code;
	}

	public static String getCode() {
		return subjectCode;
	}

	public void setName(String name) {
		this.subjectName = name;
	}

	public String getName() {
		return this.subjectName;
	}

	public void setRTeacher(String teacher) {
		this.responsableTeacher = teacher;
	}

	public String getRTeacher() {
		return this.responsableTeacher;
	}

}

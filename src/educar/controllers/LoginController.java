package educar.controllers;

import educar.gui.IView;
import educar.gui.LoginView;
import educar.gui.RootView;
import educar.gui.AdminViews.administrador;
import educar.gui.AlumnoView.AlumnoView;
import educar.gui.DocenteView.DocenteView;
import educar.models.Session;
import educar.models.User;

public class LoginController implements IController {
    private LoginView view;

    public void process(String model) {
	if (null != view)
	    if (User.authenticate(((LoginView) view).getUsername(),
		    ((LoginView) view).getPassword())) {
		view.present("You are logged in as: "
			+ Session.getCurrentUser().getUsername());
		view.close();
		openView(Session.getCurrentUser());

	    } else {
		view.present("Wrong username/password");
	    }
    }

    private void openView(User currentUser) {
	String role = Session.getCurrentUser().getRole();
	if (role.equals("root")) {
	    // view.close();
	    new RootView();
	}
	if (role.equals("admin")) {
	    new administrador();
	}
	if (role.equals("Docente")) {
	    new DocenteView();
	}
	if (role.equals("Alumno")) {
	    new AlumnoView();
	}
    }

    @Override
    public void setView(IView view) {
	this.view = (LoginView) view;
    }
}

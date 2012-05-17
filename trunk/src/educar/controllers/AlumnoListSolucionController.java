package educar.controllers;

import educar.gui.IView;
import educar.gui.AlumnoView.AlumnoView;
import educar.models.Actividad;
import educar.models.ActividadNotFound;

public class AlumnoListSolucionController implements IListController,
		IController {
	private AlumnoView view;

	@Override
	public void processItemList(String item) {
		view.setCodActividad(item);
		if (!(view.getCodActividad().compareTo("")==0)){
		try {
			view.setDescripcionActividad(Actividad.getActividad(view.getCodActividad()).getDescripcionActividad());
		    view.setNota(AlumnoControllers.getNota(view.getCodActividad()));
		} catch (ActividadNotFound e) {
			// TODO Auto-generated catch block
			view.present("NO HA SELECCIONADO UNA ACTIVIDAD");
		}
	}}

	@Override
	public void process(String model) {

	}

	@Override
	public void setView(IView view) {
		this.view = (AlumnoView) view;
	}

}

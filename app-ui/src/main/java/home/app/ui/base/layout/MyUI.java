package home.app.ui.base.layout;

import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import home.app.ui.base.menubar.MenuUI;

// @Push(value = PushMode.AUTOMATIC, transport = Transport.LONG_POLLING)
// @BodySize(height = "100vh", width = "100vw")
//@Theme(value = Material.class,
//        variant = Material.LIGHT)
@Theme(value = Lumo.class,
        variant = Lumo.LIGHT)
public class MyUI extends MenuUI {

    private static final long serialVersionUID = 3084286820068870880L;

}

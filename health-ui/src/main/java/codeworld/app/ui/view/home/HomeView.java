package codeworld.app.ui.view.home;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import codeworld.app.ui.HealthUI;

@Route(value = HomeView.VIEW_NAME,
        layout = HealthUI.class)
@PageTitle("home")
public class HomeView extends Div implements AfterNavigationObserver {

    private static final long serialVersionUID = -8390939296006799571L;

    public static final String VIEW_NAME = "";

    public HomeView() {
        setSizeFull();
        Image image = new Image("https://cdn-b.medlife.com/2020/03/coronavirus-dos-donts.jpg", "corona");
        image.setSizeFull();
        add(image);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // TODO Auto-generated method stub

    }

}

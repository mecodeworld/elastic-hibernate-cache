package codeworld.app.ui.view.billing;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import codeworld.app.ui.HealthUI;

@Route(value = BillingView.VIEW_NAME,
        layout = HealthUI.class)
@PageTitle("billing")
public class BillingView extends Div implements AfterNavigationObserver {

    private static final long serialVersionUID = 6387296276964609126L;
    public static final String VIEW_NAME = "billing";

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // TODO Auto-generated method stub

    }
}

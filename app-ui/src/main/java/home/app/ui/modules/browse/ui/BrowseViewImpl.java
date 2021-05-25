package home.app.ui.modules.browse.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import home.app.ui.base.layout.MyUI;

@Route(value = BrowseViewImpl.VIEW_NAME,
        layout = MyUI.class)
@PageTitle("browse")
public class BrowseViewImpl extends Div implements AfterNavigationObserver {

    private static final long serialVersionUID = 1487637169453754776L;

    public static final String VIEW_NAME = "";

    public BrowseViewImpl() {
        createActionMenuBar();
        setSizeFull();
    }

    private void createActionMenuBar() {

        MenuBar menuBar = new MenuBar();
        menuBar.setOpenOnHover(true);

        Text selected = new Text("");

        MenuItem project = menuBar.addItem("Project");
        MenuItem account = menuBar.addItem("Account");
        menuBar.addItem("Sign Out", e -> selected.setText("Sign Out"));

        SubMenu projectSubMenu = project.getSubMenu();
        MenuItem users = projectSubMenu.addItem("Users");
        MenuItem billing = projectSubMenu.addItem("Billing");

        SubMenu usersSubMenu = users.getSubMenu();
        usersSubMenu.addItem("List", e -> selected.setText("List"));
        usersSubMenu.addItem("Add", e -> selected.setText("Add"));

        SubMenu billingSubMenu = billing.getSubMenu();
        billingSubMenu.addItem("Invoices", e -> selected.setText("Invoices"));
        billingSubMenu.addItem("Balance Events", e -> selected.setText("Balance Events"));

        account.getSubMenu()
                .addItem("Edit Profile", e -> selected.setText("Edit Profile"));
        account.getSubMenu()
                .addItem("Privacy Settings", e -> selected.setText("Privacy Settings"));

        add(menuBar);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        Notification.show("navigate to BrowseViewImpl");
    }

}

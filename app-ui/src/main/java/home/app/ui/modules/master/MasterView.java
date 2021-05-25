package home.app.ui.modules.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import home.app.api.model.Film;
import home.app.api.service.FilmService;
import home.app.ui.base.layout.MyUI;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route(value = MasterView.VIEW_NAME,
        layout = MyUI.class)
@PageTitle("master-screen")
public class MasterView extends Div implements AfterNavigationObserver {

    private static final long serialVersionUID = 5210018881247210736L;

    public static final String VIEW_NAME = "master";

    private MenuBar menuBar = new MenuBar();
    private Grid<Film> filmGrid = new Grid<>();
    private ListDataProvider<Film> dataProvider;

    @Autowired
    private FilmService filmService;

    public MasterView() {
        setSizeFull();
        createActionMenuBar();
        initFilmGrid();

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setPadding(true);
        mainLayout.add(menuBar);
        mainLayout.add(filmGrid);

        add(mainLayout);
    }

    private void createActionMenuBar() {

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
    }

    private void initFilmGrid() {
        filmGrid = new Grid<>();
        filmGrid.setSizeFull();
        filmGrid.setColumnReorderingAllowed(true);
        filmGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        filmGrid.addColumn(Film::getId)
                .setHeader("ID")
                .setSortable(true);

        filmGrid.addColumn(Film::getTitle)
                .setHeader("TITLE")
                .setSortable(true);

        filmGrid.addColumn(Film::getDescription)
                .setHeader("DESCRIPTION")
                .setSortable(true);

        filmGrid.addColumn(Film::getFeatures)
                .setHeader("FEATURE")
                .setSortable(true);

        filmGrid.addColumn(Film::getLanguage)
                .setHeader("LANGUAGE")
                .setSortable(true);

        filmGrid.addColumn(Film::getRating)
                .setHeader("RATING")
                .setSortable(true);

        filmGrid.addColumn(Film::getRating)
                .setHeader("RATING")
                .setSortable(true);

        filmGrid.addColumn(Film::getReleaseDate)
                .setHeader("REALEASE_DATE")
                .setSortable(true);

        // createFilter();

    }

    // private void createFilter() {
    // HeaderRow filterRow = filmGrid.appendHeaderRow();
    //
    // TextField title = new TextField();
    // title.addValueChangeListener(event -> dataProvider.addFilter(bean -> StringUtils.containsIgnoreCase(bean.getTitle(), title.getValue())));
    // title.setValueChangeMode(ValueChangeMode.EAGER);
    // title.setSizeFull();
    // title.setPlaceholder("Filter");
    // filterRow.getCell(titleColumn)
    // .setComponent(title);
    //
    // }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        Notification.show("navigate to MasterView");

        List<Film> data = this.filmService.findAll();
        log.info("film found " + data.size());

        dataProvider = new ListDataProvider<>(data);
        filmGrid.setDataProvider(dataProvider);
    }
}

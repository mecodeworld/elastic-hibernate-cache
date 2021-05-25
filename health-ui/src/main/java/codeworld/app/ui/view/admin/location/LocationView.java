package codeworld.app.ui.view.admin.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import codeworld.app.ui.HealthUI;
import codeworld.app.ui.common.enums.DialogMode;
import codeworld.app.ui.view.admin.location.dialog.city.CityDialogViewImpl;
import codeworld.app.ui.view.admin.location.dialog.country.CountryDialogViewImpl;
import codeworld.app.ui.view.admin.location.dialog.state.StateDialogViewImpl;
import codeworld.app.ui.view.admin.location.events.AdminLocationGridRefreshEvent;
import codeworld.elastic.service.api.ECountryService;
import codeworld.health.service.api.CityService;
import codeworld.health.service.api.CountryService;
import codeworld.health.service.api.StateService;
import codeworld.health.service.model.CityDto;
import codeworld.health.service.model.CountryDto;
import codeworld.health.service.model.StateDto;

@Route(value = LocationView.VIEW_NAME,
        layout = HealthUI.class)
@PageTitle("admin")
public class LocationView extends Div implements AfterNavigationObserver {

    private static final long serialVersionUID = 7742926542080271963L;
    public static final String VIEW_NAME = "admin";

    @Autowired
    private ApplicationContext applicationContext;
    @Resource
    private CountryService countryService;
    @Resource
    private StateService stateService;
    @Resource
    private CityService cityService;

    @Autowired
    private ECountryService eCountryService;

    @Resource
    private EventBus.ApplicationEventBus applicationEventBus;

    private Grid<CountryDto> countryGrid = new Grid<>();
    private Grid<StateDto> stateGrid = new Grid<>();
    private Grid<CityDto> cityGrid = new Grid<>();

    @PostConstruct
    public void postConstruct() {
        applicationEventBus.subscribe(this, false);
    }

    public LocationView() {
        setSizeFull();
        initCountryGrid();
        initStateGrid();
        initCityGrid();

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setPadding(true);

        HorizontalLayout row1 = new HorizontalLayout();
        row1.setSizeFull();
        row1.add(countryGrid);
        row1.add(stateGrid);

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setSizeFull();
        row2.add(cityGrid);

        mainLayout.add(row1);
        mainLayout.add(row2);
        add(mainLayout);
    }

    private void initCityGrid() {
        cityGrid = new Grid<>();
        cityGrid.setSizeFull();
        cityGrid.setColumnReorderingAllowed(true);
        cityGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        cityGrid.addColumn(CityDto::getId)
                .setHeader("CITY_ID")
                .setSortable(true);

        cityGrid.addColumn(CityDto::getName)
                .setHeader("NAME")
                .setSortable(true);

        cityGrid.addColumn(CityDto::getPopulation)
                .setHeader("POPULATION")
                .setSortable(true);

        cityGrid.addColumn(value -> {
            return value.getStateDto() != null && StringUtils.isNotBlank(value.getStateDto()
                    .getName()) ? value.getStateDto()
                            .getName() : "";
        })
                .setHeader("STATE")
                .setSortable(true);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(cityGrid);
        contextMenu.addItem("ADD", event -> {
            openCityDialog(DialogMode.ADD);
        });
        contextMenu.addItem("EDIT", event -> {
            openCityDialog(DialogMode.EDIT);
        });
        contextMenu.addItem("DELETE", event -> {
            if (getSelectedState().isPresent()) {
                cityService.delete(getSelectedCity().get()
                        .getId());
                refreshCityGrid();
                Notification.show("Deleted Successfully");
            }
            else {
                Notification.show("Error in deleting");
            }
        });
    }

    private void initStateGrid() {
        stateGrid = new Grid<>();
        stateGrid.setSizeFull();
        stateGrid.setColumnReorderingAllowed(true);
        stateGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        stateGrid.addColumn(StateDto::getId)
                .setHeader("STATE_ID")
                .setSortable(true);

        stateGrid.addColumn(StateDto::getName)
                .setHeader("NAME")
                .setSortable(true);

        stateGrid.addColumn(StateDto::getPopulation)
                .setHeader("POPULATION")
                .setSortable(true);

        stateGrid.addColumn(value -> {
            return value.getCountryDto() != null && StringUtils.isNotBlank(value.getCountryDto()
                    .getName()) ? value.getCountryDto()
                            .getName() : "";
        })
                .setHeader("COUNTRY")
                .setSortable(true);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(stateGrid);
        contextMenu.addItem("ADD", event -> {
            openStateDialog(DialogMode.ADD);
        });
        contextMenu.addItem("EDIT", event -> {
            openStateDialog(DialogMode.EDIT);
        });
        contextMenu.addItem("DELETE", event -> {
            if (getSelectedState().isPresent()) {
                stateService.delete(getSelectedState().get()
                        .getId());
                refreshStateGrid();
                Notification.show("Deleted Successfully");
            }
            else {
                Notification.show("Error in deleting");
            }
        });
    }

    private void initCountryGrid() {
        countryGrid = new Grid<>();
        countryGrid.setSizeFull();
        countryGrid.setColumnReorderingAllowed(true);
        countryGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        countryGrid.setSelectionMode(SelectionMode.SINGLE);

        countryGrid.addColumn(CountryDto::getId)
                .setHeader("COUNTRY_ID")
                .setSortable(true);

        countryGrid.addColumn(CountryDto::getName)
                .setHeader("NAME")
                .setSortable(true);

        countryGrid.addColumn(CountryDto::getRegion)
                .setHeader("REGION")
                .setSortable(true);

        countryGrid.addColumn(CountryDto::getPopulation)
                .setHeader("POPULATION")
                .setSortable(true);

        ContextMenu countryContextMenu = new ContextMenu();
        countryContextMenu.setTarget(countryGrid);
        countryContextMenu.addItem("ADD", event -> {
            openCountryDialog(DialogMode.ADD);
        });
        countryContextMenu.addItem("EDIT", event -> {
            openCountryDialog(DialogMode.EDIT);
        });
        countryContextMenu.addItem("DELETE", event -> {
            if (getSelectedCountry().isPresent()) {
                countryService.delete(getSelectedCountry().get()
                        .getId());
                refreshCountryGrid();
                Notification.show("Deleted Successfully");
            }
            else {
                Notification.show("Error in deleting");
            }
        });
    }

    private Optional<CountryDto> getSelectedCountry() {
        if (countryGrid.getSelectedItems()
                .iterator()
                .hasNext()) {
            return Optional.ofNullable(countryGrid.getSelectedItems()
                    .iterator()
                    .next());
        }
        return Optional.empty();
    }

    private void openCountryDialog(DialogMode mode) {
        CountryDialogViewImpl countryDialog = applicationContext.getBean(CountryDialogViewImpl.class);
        if (countryDialog != null) {
            countryDialog.setData(DialogMode.ADD.equals(mode) ? new CountryDto() : getSelectedCountry().get());
            countryDialog.open();
        }
        else {
            Notification.show("Error in opening dialog");
        }
    }

    private Optional<StateDto> getSelectedState() {
        if (stateGrid.getSelectedItems()
                .iterator()
                .hasNext()) {
            return Optional.ofNullable(stateGrid.getSelectedItems()
                    .iterator()
                    .next());
        }
        return Optional.empty();
    }

    private void openStateDialog(DialogMode mode) {
        StateDialogViewImpl stateDialog = applicationContext.getBean(StateDialogViewImpl.class);
        if (stateDialog != null) {
            stateDialog.setData(DialogMode.ADD.equals(mode) ? new StateDto() : getSelectedState().get());
            stateDialog.open();
        }
        else {
            Notification.show("Error in opening dialog");
        }
    }

    private Optional<CityDto> getSelectedCity() {
        if (cityGrid.getSelectedItems()
                .iterator()
                .hasNext()) {
            return Optional.ofNullable(cityGrid.getSelectedItems()
                    .iterator()
                    .next());
        }
        return Optional.empty();
    }

    private void openCityDialog(DialogMode mode) {
        CityDialogViewImpl dialog = applicationContext.getBean(CityDialogViewImpl.class);
        if (dialog != null) {
            dialog.setData(DialogMode.ADD.equals(mode) ? new CityDto() : getSelectedCity().get());
            dialog.open();
        }
        else {
            Notification.show("Error in opening dialog");
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        refreshCountryGrid();
        refreshStateGrid();
        refreshCityGrid();
    }

    private void refreshCountryGrid() {
        // List<CountryDto> countries = countryService.findAll();
        List<CountryDto> countries = eCountryService.findAll();
        countryGrid.setItems(new ArrayList<CountryDto>());
        countryGrid.setItems(countries);
    }

    private void refreshStateGrid() {
        List<StateDto> states = stateService.findAll();
        stateGrid.setItems(states);
    }

    private void refreshCityGrid() {
        List<CityDto> cities = cityService.findAll();
        cityGrid.setItems(cities);
    }

    @EventBusListenerMethod
    public void onAdminGridRefreshEventHandler(AdminLocationGridRefreshEvent event) {

        switch (event.getGridType()) {
            case COUNTRY:
                refreshCountryGrid();
                break;
            case STATE:
                refreshStateGrid();
                break;
            case CITY:
                refreshCityGrid();
                break;
            default:
                break;
        }
    }

}

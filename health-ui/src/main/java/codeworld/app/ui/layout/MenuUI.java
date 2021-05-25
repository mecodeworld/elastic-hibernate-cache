package codeworld.app.ui.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;

import codeworld.app.ui.view.admin.hospital.HospitalViewImpl;
import codeworld.app.ui.view.admin.location.LocationView;
import codeworld.app.ui.view.home.HomeView;
import codeworld.app.ui.view.patient.PatientView;

public class MenuUI extends AppLayout {

    private static final long serialVersionUID = -8332403492240770497L;
    private static final String PATIENT = "PATIENT";
    private static final String ADMIN = "ADMIN";
    private static final String HOME = "HOME";

    private static final String LOCATION = "LOCATION";
    private static final String HOSPTIAL = "HOSPTIAL";

    private final Tabs menu = new Tabs();

    public MenuUI() {
        createMenubar();
        setDrawerOpened(false);
    }

    private void createMenubar() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, new DrawerToggle());
        addToNavbar(true, createTab(HOME, new RouterLink(HOME, HomeView.class)));
        addToNavbar(true, createTab(LOCATION, new RouterLink(LOCATION, LocationView.class)));
        addToNavbar(true, createTab(HOSPTIAL, new RouterLink(HOSPTIAL, HospitalViewImpl.class)));
        addToNavbar(true, createTab(PATIENT, new RouterLink(PATIENT, PatientView.class)));
        // createMenuTabs();
        // addToDrawer(menu);
    }

    private void createMenuTabs() {
        menu.setOrientation(Tabs.Orientation.VERTICAL);
        menu.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        menu.setId("tabs");
        menu.add(getAdminTabs());
    }

    private Tab[] getAdminTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab(LOCATION, new RouterLink(LOCATION, LocationView.class)));
        tabs.add(createTab(HOSPTIAL, new RouterLink(HOSPTIAL, HospitalViewImpl.class)));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    @SuppressWarnings("unused")
    private Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab(HOME, new RouterLink(HOME, HomeView.class)));
        tabs.add(createTab(ADMIN, new RouterLink(ADMIN, LocationView.class)));
        tabs.add(createTab(PATIENT, new RouterLink(PATIENT, PatientView.class)));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private Tab createTab(String tabId, RouterLink link) {
        final Tab tab = new Tab();
        tab.setId(tabId);
        tab.add(link);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope()
                .getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren()
                .filter(tab -> {
                    Component child = tab.getChildren()
                            .findFirst()
                            .get();
                    return child instanceof RouterLink && ((RouterLink) child).getHref()
                            .equals(target);
                })
                .findFirst();
        tabToSelect.ifPresent(tab -> {
            Tab selectedTab = (Tab) tab;
            menu.setSelectedTab(selectedTab);
        });
    }

}

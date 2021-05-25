package home.app.ui.base.menubar;

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

import home.app.ui.modules.admin.ui.AdminViewImpl;
import home.app.ui.modules.browse.ui.BrowseViewImpl;
import home.app.ui.modules.master.MasterView;

//@UIScope
//@Component
public class MenuUI extends AppLayout {

    private static final long serialVersionUID = -8332403492240770497L;

    private final Tabs menu = new Tabs();

    public MenuUI() {
        createMenubar();
    }

    private void createMenubar() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, new DrawerToggle());
        createMenuTabs();
        addToDrawer(menu);
    }

    private void createMenuTabs() {
        menu.setOrientation(Tabs.Orientation.VERTICAL);
        menu.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        menu.setId("tabs");
        menu.add(getAvailableTabs());
    }

    private Tab[] getAvailableTabs() {

        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab(new RouterLink("admin", AdminViewImpl.class)));
        tabs.add(createTab(new RouterLink("browse", BrowseViewImpl.class)));
        tabs.add(createTab(new RouterLink("master", MasterView.class)));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private Tab createTab(RouterLink link) {
        final Tab tab = new Tab();
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
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }

}

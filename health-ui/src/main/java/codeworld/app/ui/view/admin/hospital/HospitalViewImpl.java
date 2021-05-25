package codeworld.app.ui.view.admin.hospital;

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
import codeworld.app.ui.view.admin.hospital.dialog.department.DepartmentDialogViewImpl;
import codeworld.app.ui.view.admin.hospital.dialog.hospital.HospitalDialogViewImpl;
import codeworld.app.ui.view.admin.hospital.dialog.staff.StaffDialogViewImpl;
import codeworld.app.ui.view.admin.hospital.events.AdminHospitalGridRefreshEvent;
import codeworld.health.service.api.DepartmentService;
import codeworld.health.service.api.HospitalService;
import codeworld.health.service.api.StaffService;
import codeworld.health.service.model.DepartmentDto;
import codeworld.health.service.model.HospitalDto;
import codeworld.health.service.model.StaffDto;

@Route(value = HospitalViewImpl.VIEW_NAME,
        layout = HealthUI.class)
@PageTitle("hospital-view")
public class HospitalViewImpl extends Div implements AfterNavigationObserver {

    private static final long serialVersionUID = 7742926542080271963L;
    public static final String VIEW_NAME = "hospital";

    @Autowired
    private ApplicationContext applicationContext;
    @Resource
    private EventBus.ApplicationEventBus applicationEventBus;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private StaffService staffService;

    private Grid<HospitalDto> hospitalGrid = new Grid<>();
    private Grid<DepartmentDto> departmentGrid = new Grid<>();
    private Grid<StaffDto> staffGrid = new Grid<>();

    @PostConstruct
    public void postConstruct() {
        applicationEventBus.subscribe(this, false);
    }

    public HospitalViewImpl() {
        setSizeFull();
        initHospitalGrid();
        initDepartmentGrid();
        initStaffGrid();

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setPadding(true);

        HorizontalLayout row1 = new HorizontalLayout();
        row1.setSizeFull();
        row1.add(hospitalGrid);

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setSizeFull();
        row2.add(departmentGrid);
        row2.add(staffGrid);

        mainLayout.add(row1);
        mainLayout.add(row2);
        add(mainLayout);
    }

    private void initHospitalGrid() {
        hospitalGrid = new Grid<>();
        hospitalGrid.setSizeFull();
        hospitalGrid.setColumnReorderingAllowed(true);
        hospitalGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        hospitalGrid.addColumn(HospitalDto::getId)
                .setHeader("HOSPITAL_ID")
                .setSortable(true);

        hospitalGrid.addColumn(HospitalDto::getName)
                .setHeader("NAME")
                .setSortable(true);

        hospitalGrid.addColumn(HospitalDto::getLicenseNo)
                .setHeader("LICENSE_NO")
                .setSortable(true);

        hospitalGrid.addColumn(HospitalDto::getType)
                .setHeader("TYPE")
                .setSortable(true);

        hospitalGrid.addColumn(value -> {
            return value.getCityDto() != null && StringUtils.isNotBlank(value.getCityDto()
                    .getName()) ? value.getCityDto()
                            .getName() : "";
        })
                .setHeader("CITY")
                .setSortable(true);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(hospitalGrid);
        contextMenu.addItem("ADD", event -> {
            openHospitalDialog(DialogMode.ADD);
        });
        contextMenu.addItem("EDIT", event -> {
            openHospitalDialog(DialogMode.EDIT);
        });
        contextMenu.addItem("DELETE", event -> {
            if (getSelectedHospital().isPresent()) {
                hospitalService.delete(getSelectedHospital().get()
                        .getId());
                refreshHospitalGrid();
                Notification.show("Deleted Successfully");
            }
            else {
                Notification.show("Error in deleting");
            }
        });

    }

    private Optional<HospitalDto> getSelectedHospital() {
        if (hospitalGrid.getSelectedItems()
                .iterator()
                .hasNext()) {
            return Optional.ofNullable(hospitalGrid.getSelectedItems()
                    .iterator()
                    .next());
        }
        return Optional.empty();
    }

    private void openHospitalDialog(DialogMode mode) {
        HospitalDialogViewImpl dialog = applicationContext.getBean(HospitalDialogViewImpl.class);
        if (dialog != null) {
            dialog.setData(DialogMode.ADD.equals(mode) ? new HospitalDto() : getSelectedHospital().get());
            dialog.open();
        }
        else {
            Notification.show("Error in opening dialog");
        }
    }

    private void initDepartmentGrid() {
        departmentGrid = new Grid<>();
        departmentGrid.setSizeFull();
        departmentGrid.setColumnReorderingAllowed(true);
        departmentGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        departmentGrid.setSelectionMode(SelectionMode.SINGLE);

        departmentGrid.addColumn(DepartmentDto::getId)
                .setHeader("DEPARTMENT_ID")
                .setSortable(true);

        departmentGrid.addColumn(DepartmentDto::getName)
                .setHeader("NAME")
                .setSortable(true);

        departmentGrid.addColumn(DepartmentDto::getDetail)
                .setHeader("DETAIL")
                .setSortable(true);

        departmentGrid.addColumn(DepartmentDto::isDeactive)
                .setHeader("ACTIVE")
                .setSortable(true);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(departmentGrid);
        contextMenu.addItem("ADD", event -> {
            openDepartmentDialog(DialogMode.ADD);
        });
        contextMenu.addItem("EDIT", event -> {
            openDepartmentDialog(DialogMode.EDIT);
        });
        contextMenu.addItem("DELETE", event -> {
            if (getSelectedDepartment().isPresent()) {
                departmentService.delete(getSelectedDepartment().get()
                        .getId());
                refreshDepartmentGrid();
                Notification.show("Deleted Successfully");
            }
            else {
                Notification.show("Error in deleting");
            }
        });

    }

    private Optional<DepartmentDto> getSelectedDepartment() {
        if (departmentGrid.getSelectedItems()
                .iterator()
                .hasNext()) {
            return Optional.ofNullable(departmentGrid.getSelectedItems()
                    .iterator()
                    .next());
        }
        return Optional.empty();
    }

    private void openDepartmentDialog(DialogMode mode) {
        DepartmentDialogViewImpl dialog = applicationContext.getBean(DepartmentDialogViewImpl.class);
        if (dialog != null) {
            dialog.setData(DialogMode.ADD.equals(mode) ? new DepartmentDto() : getSelectedDepartment().get());
            dialog.open();
        }
        else {
            Notification.show("Error in opening dialog");
        }
    }

    private void initStaffGrid() {
        staffGrid = new Grid<>();
        staffGrid.setSizeFull();
        staffGrid.setColumnReorderingAllowed(true);
        staffGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        staffGrid.setSelectionMode(SelectionMode.SINGLE);

        staffGrid.addColumn(StaffDto::getId)
                .setHeader("STAFF_ID")
                .setSortable(true);

        staffGrid.addColumn(StaffDto::getName)
                .setHeader("NAME")
                .setSortable(true);

        staffGrid.addColumn(StaffDto::getJobType)
                .setHeader("JOB_TYPE")
                .setSortable(true);

        staffGrid.addColumn(StaffDto::getAddress)
                .setHeader("ADDRESS")
                .setSortable(true);

        staffGrid.addColumn(StaffDto::isDeactive)
                .setHeader("ACTIVE")
                .setSortable(true);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(staffGrid);
        contextMenu.addItem("ADD", event -> {
            openStaffDialog(DialogMode.ADD);
        });
        contextMenu.addItem("EDIT", event -> {
            openStaffDialog(DialogMode.EDIT);
        });
        contextMenu.addItem("DELETE", event -> {
            if (getSelectedStaff().isPresent()) {
                departmentService.delete(getSelectedStaff().get()
                        .getId());
                refreshStaffGrid();
                Notification.show("Deleted Successfully");
            }
            else {
                Notification.show("Error in deleting");
            }
        });

    }

    private Optional<StaffDto> getSelectedStaff() {
        if (staffGrid.getSelectedItems()
                .iterator()
                .hasNext()) {
            return Optional.ofNullable(staffGrid.getSelectedItems()
                    .iterator()
                    .next());
        }
        return Optional.empty();
    }

    private void openStaffDialog(DialogMode mode) {
        StaffDialogViewImpl dialog = applicationContext.getBean(StaffDialogViewImpl.class);
        if (dialog != null) {
            dialog.setData(DialogMode.ADD.equals(mode) ? new StaffDto() : getSelectedStaff().get());
            dialog.open();
        }
        else {
            Notification.show("Error in opening dialog");
        }

    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        refreshHospitalGrid();
        refreshDepartmentGrid();
        refreshStaffGrid();
    }

    private void refreshHospitalGrid() {
        List<HospitalDto> hospitals = hospitalService.findAll();
        hospitalGrid.setItems(hospitals);
    }

    private void refreshDepartmentGrid() {
        List<DepartmentDto> departments = departmentService.findAll();
        departmentGrid.setItems(departments);
    }

    private void refreshStaffGrid() {
        List<StaffDto> staffs = staffService.findAll();
        staffGrid.setItems(staffs);
    }

    @EventBusListenerMethod
    public void onAdminGridRefreshEventHandler(AdminHospitalGridRefreshEvent event) {

        switch (event.getGridType()) {
            case HOSPITAL:
                refreshHospitalGrid();
                break;
            case DEPARTMENT:
                refreshDepartmentGrid();
                break;
            case STAFF:
                refreshStaffGrid();
                break;
            default:
                break;
        }
    }

}

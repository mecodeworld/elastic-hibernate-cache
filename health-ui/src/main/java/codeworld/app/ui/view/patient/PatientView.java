package codeworld.app.ui.view.patient;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import codeworld.app.ui.HealthUI;
import codeworld.app.ui.common.enums.DialogMode;
import codeworld.app.ui.view.patient.dialog.PatientDialogViewImpl;
import codeworld.app.ui.view.patient.events.PatientGridRefreshEvent;
import codeworld.elastic.service.api.EPatientService;
import codeworld.health.service.api.PatientService;
import codeworld.health.service.model.PatientDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route(value = PatientView.VIEW_NAME,
        layout = HealthUI.class)
@PageTitle("patient")
public class PatientView extends Div implements AfterNavigationObserver {

    private static final long serialVersionUID = 8404019409309694529L;
    public static final String VIEW_NAME = "patient";

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private PatientService patientService;

    @Resource
    private EPatientService ePatientService;

    @Resource
    private EventBus.ApplicationEventBus applicationEventBus;

    private Grid<PatientDto> mainGrid = new Grid<>();
    private List<PatientDto> allPatients;

    @PostConstruct
    public void postConstruct() {
        applicationEventBus.subscribe(this, false);
    }

    public PatientView() {
        setSizeFull();
        initMainGrid();

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setPadding(true);
        mainLayout.add(mainGrid);
        add(mainLayout);
    }

    private void initMainGrid() {
        mainGrid.setSizeFull();
        mainGrid.setColumnReorderingAllowed(true);
        mainGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        addGridColumn(PatientDto::getId, "PATIENT_ID");
        addGridColumn(PatientDto::getName, "PATIENT_NAME");
        addGridColumn(e -> {
            return e.getHospitalDto() == null ? ""
                    : e.getHospitalDto()
                            .getName();
        }, "HOSPITAL_NAME");
        addGridColumn(PatientDto::getAadharNo, "AADHAR_NO");
        addGridColumn(PatientDto::getAddress, "ADDRESS");
        addGridColumn(PatientDto::getAmount, "BILL_AMOUNT");
        addGridColumn(PatientDto::getReport, "MEDICAL_REPORT");
        // addGridColumn(PatientDto::getAdmitDate, "ADMITDATE");
        // addGridColumn(PatientDto::getReleaseDate, "RELEASEDATE");
        mainGrid.addColumn(e -> {
            return e.getAdmitDate() == null ? ""
                    : e.getAdmitDate()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
        })
                .setHeader("ADMIT_DATE")
                .setSortable(true);

        mainGrid.addColumn(e -> {
            return e.getReleaseDate() == null ? ""
                    : e.getReleaseDate()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
        })
                .setHeader("RELEASE_DATE")
                .setSortable(true);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(mainGrid);
        contextMenu.addItem("ADD", event -> {
            openDialog(DialogMode.ADD);
        });
        contextMenu.addItem("EDIT", event -> {
            openDialog(DialogMode.EDIT);
        });
        contextMenu.addItem("DELETE", event -> {
            if (getSelectedItem().isPresent()) {
                patientService.delete(getSelectedItem().get()
                        .getId());
                Notification.show("Deleted Successfully");
            }
            else {
                Notification notification = new Notification("ERROR IN DELETE", 2000, Position.MIDDLE);
                notification.setThemeName(NotificationVariant.LUMO_ERROR.getVariantName());
                notification.open();
            }
        });

    }

    private void addGridColumn(ValueProvider<PatientDto, ?> valueProvider, String name) {
        mainGrid.addColumn(valueProvider)
                .setHeader(name)
                .setSortable(true);
    }

    private void openDialog(DialogMode mode) {
        PatientDialogViewImpl dialog = applicationContext.getBean(PatientDialogViewImpl.class);
        if (dialog != null) {
            dialog.setData(DialogMode.ADD.equals(mode) ? new PatientDto() : getSelectedItem().get());
            dialog.open();
        }
        else {
            Notification notification = new Notification("ERROR IN OPENING DIALOG", 2000, Position.MIDDLE);
            notification.setThemeName(NotificationVariant.LUMO_ERROR.getVariantName());
            notification.open();
        }

    }

    private Optional<PatientDto> getSelectedItem() {
        if (mainGrid.getSelectedItems()
                .iterator()
                .hasNext()) {
            return Optional.ofNullable(mainGrid.getSelectedItems()
                    .iterator()
                    .next());
        }
        return Optional.empty();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        refreshMainGrid();
    }

    @EventBusListenerMethod
    public void onPatientGridRefreshEventHandler(PatientGridRefreshEvent event) {
        refreshMainGridById(event.getId());
    }

    private void refreshMainGridById(Long id) {
        log.info("requesting recently saved patient data from UI " + id);
        Optional<PatientDto> saved = ePatientService.findById(id);

        if (saved.isPresent() && !allPatients.contains(saved.get())) {
            allPatients.add(saved.get());
            mainGrid.setItems(allPatients);
        }

    }

    private void refreshMainGrid() {
        allPatients = ePatientService.findAll();
        mainGrid.setItems(allPatients);
    }

}

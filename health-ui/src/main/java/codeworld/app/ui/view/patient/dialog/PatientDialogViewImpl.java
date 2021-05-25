package codeworld.app.ui.view.patient.dialog;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;

import codeworld.app.ui.layout.AbstractDialog;
import codeworld.health.service.api.HospitalService;
import codeworld.health.service.model.HospitalDto;
import codeworld.health.service.model.PatientDto;
import lombok.Getter;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PatientDialogViewImpl extends AbstractDialog implements PatientView {

    private static final long serialVersionUID = -5569864030212319467L;

    @Getter
    @Autowired
    private PatientDialogPresenter presenter;

    @Resource
    private HospitalService hospitalService;

    private Binder<PatientDto> binder = new Binder<PatientDto>();
    private PatientDto bean = new PatientDto();

    public PatientDialogViewImpl() {
        setCloseOnOutsideClick(false);
        setDraggable(true);
        setResizable(true);
        setSizeUndefined();
    }

    public void setData(PatientDto dto) {
        if (dto != null) {
            bean = dto;
            binder.setBean(bean);
        }
    }

    @Override
    protected void initialize() {
        presenter.setView(this);

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add("PATIENT_DIALOG");

        ComboBox<HospitalDto> cmbBoxHospital = new ComboBox<HospitalDto>("HOSPITAL");
        cmbBoxHospital.setItemLabelGenerator(HospitalDto::getName);
        cmbBoxHospital.setClearButtonVisible(true);
        cmbBoxHospital.setSizeFull();

        List<HospitalDto> hospitals = hospitalService.findAll();
        cmbBoxHospital.setItems(hospitals);
        binder.forField(cmbBoxHospital)
                .asRequired()
                .bind(PatientDto::getHospitalDto, PatientDto::setHospitalDto);

        TextField nameTextField = createTextField("PATIENT_NAME");
        binder.forField(nameTextField)
                .asRequired()
                .withNullRepresentation("")
                .bind(PatientDto::getName, PatientDto::setName);

        TextField aadharTextField = createTextField("AADHAR_NO");
        binder.forField(aadharTextField)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Only Number allowed"))
                .bind(PatientDto::getAadharNo, PatientDto::setAadharNo);

        TextField addressTextField = createTextField("ADDRESS");
        binder.forField(addressTextField)
                .withNullRepresentation("")
                .bind(PatientDto::getAddress, PatientDto::setAddress);

        TextField reportTextField = createTextField("MEDICAL_REPORT");
        binder.forField(reportTextField)
                .withNullRepresentation("")
                .bind(PatientDto::getReport, PatientDto::setReport);

        DatePicker admitDate = new DatePicker("ADMIT_DATE");
        admitDate.setClearButtonVisible(true);
        binder.forField(admitDate)
                .asRequired()
                .withConverter(new LocalDateToDateConverter())
                .bind(PatientDto::getAdmitDate, PatientDto::setAdmitDate);

        DatePicker releaseDate = new DatePicker("RELEASE_DATE");
        releaseDate.setClearButtonVisible(true);
        binder.forField(releaseDate)
                .withConverter(new LocalDateToDateConverter())
                .bind(PatientDto::getReleaseDate, PatientDto::setReleaseDate);

        TextField amountTextField = createTextField("BILL_AMOUNT");
        binder.forField(amountTextField)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Only Number allowed"))
                .bind(PatientDto::getAmount, PatientDto::setAmount);

        Button save = new Button("SAVE");
        save.setSizeFull();
        save.addClickListener(event -> {
            if (binder.isValid()) {
                getPresenter().save(binder.getBean());
            }
            else {
                Notification notification = new Notification("Validation Failed", 2000, Position.MIDDLE);
                notification.setThemeName(NotificationVariant.LUMO_ERROR.getVariantName());
                notification.open();
            }
        });

        Button cancel = new Button("CANCEL");
        cancel.setSizeFull();
        cancel.addClickListener(event -> {
            Notification.show("Cancelled");
            close();
        });

        HorizontalLayout actions = new HorizontalLayout();
        actions.setSizeFull();
        actions.add(save, cancel);

        FormLayout columnLayout = new FormLayout();
        columnLayout.setSizeFull();
        // Setting the desired responsive steps for the columns in the layout
        columnLayout.setResponsiveSteps(new ResponsiveStep("25em", 1), new ResponsiveStep("32em", 2), new ResponsiveStep("40em", 3));
        columnLayout.add(cmbBoxHospital, nameTextField, aadharTextField, amountTextField, admitDate, releaseDate, addressTextField, reportTextField);
        columnLayout.setColspan(reportTextField, 2);

        mainLayout.add(columnLayout);
        mainLayout.add(actions);

        add(mainLayout);
    }

    private TextField createTextField(String string) {
        TextField textField = new TextField();
        textField.setLabel(string);
        textField.setSizeFull();
        textField.setClearButtonVisible(true);
        textField.setPlaceholder(string);
        return textField;
    }

}

package codeworld.app.ui.view.admin.hospital.dialog.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import codeworld.app.ui.layout.AbstractDialog;
import codeworld.health.service.model.StaffDto;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StaffDialogViewImpl extends AbstractDialog implements StaffDialogView {

    private static final long serialVersionUID = -5569864030212319467L;

    @Autowired
    private StaffDialogPresenter presenter;

    private Binder<StaffDto> binder = new Binder<StaffDto>();
    private StaffDto departmentDto = new StaffDto();

    public StaffDialogViewImpl() {
        setCloseOnOutsideClick(false);
        setDraggable(true);
        setResizable(true);
        setSizeUndefined();
    }

    public void setData(StaffDto dto) {
        if (dto != null) {
            departmentDto = dto;
            binder.setBean(departmentDto);
        }
    }

    @Override
    protected void initialize() {
        presenter.setView(this);

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add("STAFF_DIALOG");

        TextField nameTextField = createTextField("NAME");
        binder.forField(nameTextField)
                .asRequired()
                .withNullRepresentation("")
                .bind(StaffDto::getName, StaffDto::setName);

        TextField jobTypeTextField = createTextField("JOB_TYPE");
        binder.forField(jobTypeTextField)
                .withNullRepresentation("")
                .bind(StaffDto::getJobType, StaffDto::setJobType);

        TextField addressTextField = createTextField("ADDRESS");
        binder.forField(addressTextField)
                .withNullRepresentation("")
                .bind(StaffDto::getAddress, StaffDto::setAddress);

        Checkbox deactive = new Checkbox("IS_DEACTIVE");
        deactive.setSizeFull();
        binder.forField(deactive)
                .bind(StaffDto::isDeactive, StaffDto::setDeactive);

        Button save = new Button("SAVE");
        save.setSizeFull();
        save.addClickListener(event -> {
            if (binder.isValid()) {
                presenter.save(binder.getBean());
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
        columnLayout.setResponsiveSteps(new ResponsiveStep("30em", 1), new ResponsiveStep("30em", 2));
        columnLayout.add(nameTextField, jobTypeTextField, addressTextField, deactive);
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

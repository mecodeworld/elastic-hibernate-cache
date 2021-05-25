package codeworld.app.ui.view.admin.location.dialog.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;

import codeworld.app.ui.layout.AbstractDialog;
import codeworld.health.service.model.CountryDto;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CountryDialogViewImpl extends AbstractDialog implements CountryView {

    private static final long serialVersionUID = -5569864030212319467L;

    @Autowired
    private CountryDialogPresenter presenter;

    private Binder<CountryDto> binder = new Binder<CountryDto>();
    private CountryDto countryDto = new CountryDto();

    public CountryDialogViewImpl() {
        setCloseOnOutsideClick(false);
        setDraggable(true);
        setResizable(true);
        setSizeUndefined();
    }

    public void setData(CountryDto dto) {
        if (dto != null) {
            countryDto = dto;
            binder.setBean(countryDto);
        }
    }

    @Override
    protected void initialize() {

        presenter.setView(this);
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add("COUNTY");

        TextField nameTextField = createTextField("COUNTRY_NAME");
        binder.forField(nameTextField)
                .asRequired()
                .withNullRepresentation("")
                .bind(CountryDto::getName, CountryDto::setName);

        TextField regionTextField = createTextField("COUNTRY_REGION");
        binder.forField(regionTextField)
                .asRequired()
                .withNullRepresentation("")
                .bind(CountryDto::getRegion, CountryDto::setRegion);

        TextField populationTextField = createTextField("POPULATION");
        binder.forField(populationTextField)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Only Number allowed"))
                .bind(CountryDto::getPopulation, CountryDto::setPopulation);

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
        columnLayout.add(nameTextField, regionTextField, populationTextField);
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

package codeworld.app.ui.view.admin.location.dialog.state;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;

import codeworld.app.ui.layout.AbstractDialog;
import codeworld.health.service.api.CountryService;
import codeworld.health.service.model.CountryDto;
import codeworld.health.service.model.StateDto;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StateDialogViewImpl extends AbstractDialog implements StateView {

    private static final long serialVersionUID = -5569864030212319467L;

    @Autowired
    private StateDialogPresenter presenter;

    @Autowired
    private CountryService countryService;

    private Binder<StateDto> binder = new Binder<StateDto>();
    private StateDto stateDto = new StateDto();

    public StateDialogViewImpl() {
        setCloseOnOutsideClick(false);
        setDraggable(true);
        setResizable(true);
        setSizeUndefined();
    }

    public void setData(StateDto dto) {
        if (dto != null) {
            stateDto = dto;
            binder.setBean(stateDto);
        }
    }

    @Override
    protected void initialize() {

        presenter.setView(this);
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add("STATE");

        ComboBox<CountryDto> comboBox = createComboBox("COUNTRY");
        binder.forField(comboBox)
                .asRequired()
                .bind(StateDto::getCountryDto, StateDto::setCountryDto);

        TextField nameTextField = createTextField("STATE_NAME");
        binder.forField(nameTextField)
                .asRequired()
                .withNullRepresentation("")
                .bind(StateDto::getName, StateDto::setName);

        TextField populationTextField = createTextField("POPULATION");
        binder.forField(populationTextField)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Only Number allowed"))
                .bind(StateDto::getPopulation, StateDto::setPopulation);

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
        columnLayout.add(comboBox, nameTextField, populationTextField);
        mainLayout.add(columnLayout);
        mainLayout.add(actions);

        add(mainLayout);
    }

    private ComboBox<CountryDto> createComboBox(String label) {
        ComboBox<CountryDto> comboBox = new ComboBox<CountryDto>(label);
        comboBox.setItemLabelGenerator(CountryDto::getName);
        comboBox.setClearButtonVisible(true);
        comboBox.setSizeFull();

        List<CountryDto> countryDtos = countryService.findAll();
        comboBox.setItems(countryDtos);
        return comboBox;
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

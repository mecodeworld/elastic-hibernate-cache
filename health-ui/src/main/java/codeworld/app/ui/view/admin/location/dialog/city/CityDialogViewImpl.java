package codeworld.app.ui.view.admin.location.dialog.city;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;

import codeworld.app.ui.layout.AbstractDialog;
import codeworld.health.service.api.CountryService;
import codeworld.health.service.api.StateService;
import codeworld.health.service.model.CityDto;
import codeworld.health.service.model.CountryDto;
import codeworld.health.service.model.StateDto;
import lombok.Getter;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CityDialogViewImpl extends AbstractDialog implements CityView {

    private static final long serialVersionUID = -5569864030212319467L;

    @Getter
    @Autowired
    private CityDialogPresenter presenter;

    @Resource
    private CountryService countryService;

    @Resource
    private StateService stateService;

    private Binder<CityDto> binder = new Binder<CityDto>();
    private CityDto bean = new CityDto();

    private ComboBox<StateDto> cmbBoxState = new ComboBox<StateDto>();
    private ComboBox<CountryDto> cmbBoxCountry = new ComboBox<CountryDto>();

    public CityDialogViewImpl() {
        setCloseOnOutsideClick(false);
        setDraggable(true);
        setResizable(true);
        setSizeUndefined();
    }

    public void setData(CityDto dto) {
        if (dto != null) {
            bean = dto;
            binder.setBean(bean);
        }
    }

    @Override
    protected void initialize() {

        presenter.setView(this);
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add("CITY");

        createCountryComboBox("COUNTRY");

        createStateComboBox("STATE");
        binder.forField(cmbBoxState)
                .asRequired()
                .bind(CityDto::getStateDto, CityDto::setStateDto);

        TextField nameTextField = createTextField("CITY_NAME");
        binder.forField(nameTextField)
                .asRequired()
                .withNullRepresentation("")
                .bind(CityDto::getName, CityDto::setName);

        TextField populationTextField = createTextField("POPULATION");
        binder.forField(populationTextField)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Only Number allowed"))
                .bind(CityDto::getPopulation, CityDto::setPopulation);

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
        columnLayout.setResponsiveSteps(new ResponsiveStep("30em", 1), new ResponsiveStep("30em", 2));
        columnLayout.add(cmbBoxCountry, cmbBoxState, nameTextField, populationTextField);

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

    private void createCountryComboBox(String label) {
        cmbBoxCountry.setLabel(label);
        cmbBoxCountry.setItemLabelGenerator(CountryDto::getName);
        cmbBoxCountry.setClearButtonVisible(true);
        cmbBoxCountry.setSizeFull();

        List<CountryDto> countryDtos = countryService.findAll();
        cmbBoxCountry.setItems(countryDtos);

        cmbBoxCountry.addValueChangeListener(e -> {
            if (e.isFromClient()) {
                if (e.getValue() != null) {
                    List<StateDto> stateDtos = stateService.findAll();
                    Set<StateDto> validStates = stateDtos.stream()
                            .filter(data -> data.getCountryDto()
                                    .equals(e.getValue()))
                            .collect(Collectors.toSet());
                    cmbBoxState.setItems(validStates);
                }
                else {
                    cmbBoxState.setItems(new HashSet<StateDto>());
                }
            }
        });
    }

    private void createStateComboBox(String label) {
        cmbBoxState.setLabel(label);
        cmbBoxState.setItemLabelGenerator(StateDto::getName);
        cmbBoxState.setClearButtonVisible(true);
        cmbBoxState.setSizeFull();
    }

}

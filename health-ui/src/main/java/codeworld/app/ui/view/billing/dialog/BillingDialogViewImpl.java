package codeworld.app.ui.view.billing.dialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;

import codeworld.app.ui.layout.AbstractDialog;
import codeworld.health.service.model.CityDto;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BillingDialogViewImpl extends AbstractDialog implements BillingView {

    private static final long serialVersionUID = -5569864030212319467L;

    @Autowired
    private BillingDialogPresenter presenter;

    private Binder<CityDto> binder = new Binder<CityDto>();
    private CityDto bean = new CityDto();

    public BillingDialogViewImpl() {
        setCloseOnEsc(true);
        setDraggable(true);
        setResizable(true);
        setHeight("400px");
        setWidth("400px");
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
        mainLayout.add("STATE");

        TextField nameTextField = createTextField("COUNTRY_NAME");
        binder.forField(nameTextField)
                .asRequired()
                .withNullRepresentation("");
        // .bind(CountryDto::getName, CountryDto::setName);

        TextField regionTextField = createTextField("COUNTRY_REGION");
        binder.forField(regionTextField)
                .asRequired()
                .withNullRepresentation("");
        // .bind(CountryDto::getRegion, CountryDto::setRegion);

        TextField populationTextField = createTextField("POPULATION");
        binder.forField(populationTextField)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Only Number allowed"));
        // .bind(CountryDto::getPopulation, CountryDto::setPopulation);

        Button save = new Button("SAVE");
        save.setSizeFull();
        save.addClickListener(event -> {
            if (binder.isValid()) {
                // TODO
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

        mainLayout.add(nameTextField, regionTextField, populationTextField, actions);
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

package codeworld.app.ui.view.admin.hospital.dialog.hospital;

import java.util.ArrayList;
import java.util.List;

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

import codeworld.app.ui.layout.AbstractDialog;
import codeworld.app.ui.model.HospitalVO;
import codeworld.app.ui.view.admin.hospital.DepartmentStaffComponent;
import codeworld.app.ui.view.admin.hospital.DepartmentStaffComponent.DeptStaffComponentBean;
import codeworld.health.service.api.CityService;
import codeworld.health.service.api.DepartmentService;
import codeworld.health.service.api.StaffService;
import codeworld.health.service.model.CityDto;
import codeworld.health.service.model.DepartmentDto;
import codeworld.health.service.model.DepartmentStaffDto;
import codeworld.health.service.model.HospitalDepartmentDto;
import codeworld.health.service.model.HospitalDto;
import codeworld.health.service.model.StaffDto;
import lombok.Getter;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HospitalDialogViewImpl extends AbstractDialog implements HospitalView {

    private static final long serialVersionUID = -5569864030212319467L;

    @Getter
    @Autowired
    private HospitalDialogPresenter presenter;
    @Autowired
    private CityService cityService;
    @Resource
    private StaffService staffService;
    @Resource
    private DepartmentService departmentService;

    private Binder<HospitalVO> binder = new Binder<HospitalVO>();
    private HospitalDto bean = new HospitalDto();
    private DepartmentStaffComponent deptStaffComponent = new DepartmentStaffComponent();

    public HospitalDialogViewImpl() {
        setCloseOnOutsideClick(false);
        setDraggable(true);
        setResizable(true);

        setWidth("1200px");
        setHeight("600px");
    }

    public void setData(HospitalDto dto) {
        if (dto != null) {
            bean = dto;
            binder.setBean(convertToBinderBean(dto));
        }
    }

    private HospitalVO convertToBinderBean(HospitalDto dto) {
        HospitalVO vo = HospitalVO.builder()
                .id(dto.getId())
                .name(dto.getName())
                .licenseNo(dto.getLicenseNo())
                .type(dto.getType())
                .cityDto(dto.getCityDto())
                .build();
        return vo;
    }

    private HospitalDto convertToDto(HospitalVO vo, List<DeptStaffComponentBean> list) {
        List<HospitalDepartmentDto> hospitalDepartmentDtos = new ArrayList<HospitalDepartmentDto>();

        HospitalDto hospitalDto = HospitalDto.builder()
                .id(vo.getId())
                .name(vo.getName())
                .licenseNo(vo.getLicenseNo())
                .type(vo.getType())
                .cityDto(vo.getCityDto())
                .build();

        for (DeptStaffComponentBean deptStaffBean : list) {

            HospitalDepartmentDto hospitalDepartmentDto = HospitalDepartmentDto.builder()
                    .hospitalDto(HospitalDto.builder()
                            .id(vo.getId())
                            .build())
                    .departmentDto(DepartmentDto.builder()
                            .id(deptStaffBean.getDepartmentDto()
                                    .getId())
                            .build())
                    .build();

            List<DepartmentStaffDto> departmentStaffDtos = new ArrayList<DepartmentStaffDto>();
            for (StaffDto staffBean : deptStaffBean.getStaffDtos()) {
                DepartmentStaffDto departmentStaffDto = DepartmentStaffDto.builder()
                        .staffDto(staffBean)
                        .build();
                departmentStaffDtos.add(departmentStaffDto);
            }

            hospitalDepartmentDto.setDepartmentStaffDtos(departmentStaffDtos);
            hospitalDepartmentDtos.add(hospitalDepartmentDto);
        }

        hospitalDto.setHospitalDepartmentDtos(hospitalDepartmentDtos);
        return hospitalDto;
    }

    @Override
    protected void initialize() {
        presenter.setView(this);

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add("HOSPITAL");

        ComboBox<CityDto> cmbBoxCity = new ComboBox<CityDto>("CITY");
        cmbBoxCity.setItemLabelGenerator(CityDto::getName);
        cmbBoxCity.setClearButtonVisible(true);
        cmbBoxCity.setSizeFull();

        List<CityDto> cities = cityService.findAll();
        cmbBoxCity.setItems(cities);

        binder.forField(cmbBoxCity)
                .asRequired()
                .bind(HospitalVO::getCityDto, HospitalVO::setCityDto);

        TextField nameTextField = createTextField("HOSPITAL_NAME");
        binder.forField(nameTextField)
                .asRequired()
                .withNullRepresentation("")
                .bind(HospitalVO::getName, HospitalVO::setName);

        TextField licenseTextField = createTextField("LICENSE_NO");
        binder.forField(licenseTextField)
                .asRequired()
                .withNullRepresentation("")
                .bind(HospitalVO::getLicenseNo, HospitalVO::setLicenseNo);

        TextField typeTextField = createTextField("HOSPITAL_TYPE");
        binder.forField(typeTextField)
                .withNullRepresentation("")
                .bind(HospitalVO::getType, HospitalVO::setType);

        Button save = new Button("SAVE");
        save.setSizeFull();
        save.addClickListener(event -> {
            if (binder.isValid() && deptStaffComponent.isValid()) {
                getPresenter().save(convertToDto(binder.getBean(), deptStaffComponent.getValues()));
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
        columnLayout.add(cmbBoxCity, nameTextField, licenseTextField, typeTextField);

        deptStaffComponent.setData(departmentService.findAll(), staffService.findAll());

        mainLayout.add(columnLayout);
        mainLayout.add(deptStaffComponent);
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

package codeworld.app.ui.view.admin.hospital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.vaadin.gatanaso.MultiselectComboBox;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import codeworld.health.service.model.DepartmentDto;
import codeworld.health.service.model.StaffDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DepartmentStaffComponent extends Composite<Div> {

    private static final long serialVersionUID = 262586639545698634L;
    private List<DepartmentDto> departmentDtos = new ArrayList<DepartmentDto>();
    private List<StaffDto> staffDtos = new ArrayList<StaffDto>();

    // LAYOUT
    private HorizontalLayout mainLayout = new HorizontalLayout();
    private FormLayout formLayout = new FormLayout();
    private List<RowComponent> components = new ArrayList<DepartmentStaffComponent.RowComponent>();

    private Map<String, RowComponent> rowComponentByBtnRemoveMap = new HashMap<String, DepartmentStaffComponent.RowComponent>();

    public DepartmentStaffComponent() {
        mainLayout.setSizeFull();
        formLayout.setSizeFull();
        getContent().setSizeFull();

        formLayout.setResponsiveSteps(new ResponsiveStep("30em", 1), new ResponsiveStep("30em", 2), new ResponsiveStep("10em", 3),
                new ResponsiveStep("10em", 4));

        mainLayout.add(formLayout);

        getContent().add(mainLayout);
    }

    public void setData(List<DepartmentDto> departments, List<StaffDto> staffs) {
        departmentDtos = departments;
        staffDtos = staffs;
        updateRow();
    }

    private ComboBox<DepartmentDto> getDeptComboBox() {
        ComboBox<DepartmentDto> cmbBoxDepartment = new ComboBox<DepartmentDto>("DEPARTMENT");
        cmbBoxDepartment.setItemLabelGenerator(DepartmentDto::getName);
        cmbBoxDepartment.setClearButtonVisible(true);
        cmbBoxDepartment.setSizeFull();
        cmbBoxDepartment.setItems(departmentDtos);
        return cmbBoxDepartment;
    }

    private MultiselectComboBox<StaffDto> getStaffComboBox() {
        MultiselectComboBox<StaffDto> cmbBox = new MultiselectComboBox<StaffDto>("STAFF");
        cmbBox.setItemLabelGenerator(StaffDto::getName);
        cmbBox.setClearButtonVisible(true);
        cmbBox.setSizeFull();
        cmbBox.setItems(staffDtos);
        return cmbBox;
    }

    private Button createAddButtonLayout() {
        Button btnAdd = new Button("Add");
        btnAdd.setId(UUID.randomUUID()
                .toString());
        btnAdd.setSizeFull();
        btnAdd.addClickListener(e -> {
            updateRow();
        });
        return btnAdd;
    }

    private Button createRemoveButtonLayout() {
        Button btnRemove = new Button("Remove");
        btnRemove.setId(UUID.randomUUID()
                .toString());
        btnRemove.setSizeFull();

        btnRemove.addClickListener(e -> {
            if (components.size() == 1) {
                Notification notification = new Notification("Not Allowed to Delete", 2000, Position.MIDDLE);
                notification.setThemeName(NotificationVariant.LUMO_ERROR.getVariantName());
                notification.open();
            }
            else if (rowComponentByBtnRemoveMap.containsKey(btnRemove.getId()
                    .get())) {
                components.remove(rowComponentByBtnRemoveMap.get(btnRemove.getId()
                        .get()));
                rowComponentByBtnRemoveMap.remove(btnRemove.getId()
                        .get());
                refreshComponentLayout();
            }
        });
        return btnRemove;
    }

    private void updateRow() {
        Button btnRemove = createRemoveButtonLayout();
        RowComponent rowComponent = RowComponent.builder()
                .id(UUID.randomUUID()
                        .toString())
                .cmbBoxDepartment(getDeptComboBox())
                .multiCmbBoxStaff(getStaffComboBox())
                .btnAdd(createAddButtonLayout())
                .btnRemove(btnRemove)
                .build();

        rowComponentByBtnRemoveMap.put(btnRemove.getId()
                .get(), rowComponent);

        addToComponent(rowComponent);
    }

    private void addToComponent(RowComponent rowComponent) {
        components.add(rowComponent);
        refreshComponentLayout();
    }

    private void refreshComponentLayout() {
        formLayout.removeAll();
        components.forEach(component -> {
            formLayout.add(component.getCmbBoxDepartment(), component.getMultiCmbBoxStaff(), component.getBtnAdd(), component.getBtnRemove());
        });

        mainLayout.removeAll();
        mainLayout.add(formLayout);
        getContent().add(mainLayout);
    }

    public List<DeptStaffComponentBean> getValues() {
        if (components.isEmpty()) {
            return new ArrayList<DepartmentStaffComponent.DeptStaffComponentBean>();
        }

        List<DeptStaffComponentBean> values = components.stream()
                .filter(deptComponent -> deptComponent.getCmbBoxDepartment()
                        .getOptionalValue()
                        .isPresent())
                .filter(staffComponent -> staffComponent.getMultiCmbBoxStaff()
                        .getOptionalValue()
                        .isPresent())
                .map(component -> {
                    return DeptStaffComponentBean.builder()
                            .departmentDto(component.getCmbBoxDepartment()
                                    .getValue())
                            .staffDtos(component.getMultiCmbBoxStaff()
                                    .getValue())
                            .build();
                })
                .collect(Collectors.toList());
        return values;
    }

    public boolean isValid() {
        if (components.isEmpty()) {
            return false;
        }
        return components.stream()
                .anyMatch(component -> component.getCmbBoxDepartment()
                        .getOptionalValue()
                        .isPresent()
                        && component.getMultiCmbBoxStaff()
                                .getOptionalValue()
                                .isPresent());
    }

    @Builder
    @Getter
    @Setter
    @EqualsAndHashCode(of = "id")
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RowComponent {

        private String id;
        private ComboBox<DepartmentDto> cmbBoxDepartment;
        private MultiselectComboBox<StaffDto> multiCmbBoxStaff;
        private Button btnAdd;
        private Button btnRemove;

    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeptStaffComponentBean {

        private DepartmentDto departmentDto;
        private Set<StaffDto> staffDtos;

    }

}

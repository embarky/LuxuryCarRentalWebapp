package ch.unil.softarch.luxurycarrental.ui;

import ch.unil.softarch.luxurycarrental.client.CarTypeClient;
import ch.unil.softarch.luxurycarrental.domain.entities.CarType;
import ch.unil.softarch.luxurycarrental.domain.enums.DriveType;
import ch.unil.softarch.luxurycarrental.domain.enums.Transmission;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Named("carTypeFormBean")
@ViewScoped
public class CarTypeFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CarTypeClient carTypeClient;

    private CarType carType;           // 当前表单对象
    private boolean editing;           // 是否编辑模式
    private String featuresAsString;   // 用于输入框的字符串

    private UUID carTypeId;            // URL 参数

    private List<DriveType> driveTypes;        // 下拉选项
    private List<Transmission> transmissions;  // 下拉选项

    // --- 初始化 ---
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.isPostback()) return;

        // 下拉选项
        driveTypes = Arrays.asList(DriveType.values());
        transmissions = Arrays.asList(Transmission.values());

        // 尝试从 URL 读取 carTypeId
        String idParam = fc.getExternalContext().getRequestParameterMap().get("carTypeId");
        if (idParam != null && !idParam.isBlank()) {
            try {
                carTypeId = UUID.fromString(idParam);
                carType = carTypeClient.getCarType(carTypeId);
                if (carType.getFeatures() == null) carType.setFeatures(new ArrayList<>());
                featuresAsString = String.join(", ", carType.getFeatures());
                editing = true;
            } catch (Exception e) {
                // 无效 UUID 或无法加载 → 新增模式
                setupNewCarType();
            }
        } else {
            setupNewCarType();
        }
    }

    private void setupNewCarType() {
        carType = new CarType();
        carType.setFeatures(new ArrayList<>());
        featuresAsString = "";
        carType.setDriveType(DriveType.FRONT_WHEEL_DRIVE);
        carType.setTransmission(Transmission.MANUAL);
        editing = false;
    }

    public String save() {
        if (featuresAsString != null && !featuresAsString.isBlank()) {
            carType.setFeatures(Arrays.asList(featuresAsString.split("\\s*,\\s*")));
        } else {
            carType.setFeatures(new ArrayList<>());
        }

        if (editing) {
            carTypeClient.updateCarType(carType.getId(), carType);
        } else {
            carTypeClient.addCarType(carType);
        }

        return "/pages/admin/car_type.xhtml?faces-redirect=true";
    }

    // --- Getter/Setter ---
    public CarType getCarType() { return carType; }
    public void setCarType(CarType carType) { this.carType = carType; }

    public boolean isEditing() { return editing; }

    public String getFeaturesAsString() { return featuresAsString; }
    public void setFeaturesAsString(String featuresAsString) { this.featuresAsString = featuresAsString; }

    public UUID getCarTypeId() { return carTypeId; }
    public void setCarTypeId(UUID carTypeId) { this.carTypeId = carTypeId; }

    public List<DriveType> getDriveTypes() { return driveTypes; }
    public List<Transmission> getTransmissions() { return transmissions; }
}
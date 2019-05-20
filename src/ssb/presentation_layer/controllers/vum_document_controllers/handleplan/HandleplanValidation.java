package ssb.presentation_layer.controllers.vum_document_controllers.handleplan;

import java.util.Collection;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ssb.presentation_layer.controllers.vum_document_controllers.VumDocumentController;
import static ssb.presentation_layer.controllers.vum_document_controllers.VumDocumentController.findChildInTab;

public class HandleplanValidation {

    private final TabPane tabPane;
    private Label addressValidatorLabel;
    private ImageView addressValidateErrorImage;
    private Label phoneNumberValidatorLabel;
    private ImageView phoneNumberValidatorErrorImage;
    private Label mailValidatorLabel;
    private ImageView mailValidatorErrorImage;

    public HandleplanValidation(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setAddressValidation(String labelID, String imageErrorID, TextField addresseTxtF) {
        addressValidatorLabel = VumDocumentController.findChildInTab(labelID, tabPane.getTabs().get(0), Label.class);
        addressValidateErrorImage = VumDocumentController.findChildInTab("addressValidateErrorImage", tabPane.getTabs().get(0), ImageView.class);
        addresseTxtF.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!addressIsValid(addresseTxtF.getText())) {
                    setError(addressValidatorLabel, addressValidateErrorImage);
                } else {
                    clearError(addressValidatorLabel, addressValidateErrorImage);
                }
            }
        });
    }

    public boolean addressIsValid(String addresseTxt) {
        if (addresseTxt == null) {
            return false;
        } else {
            String[] addressElements = addresseTxt.split(" ");
            if (addressElements.length >= 4) { // Needs to follow this form: [roadname] [houseNumber] [zip code] [city]
                for (Character charas : addressElements[0].toCharArray()) { // roadname check
                    if (Character.isDigit(charas)) {
                        return false;
                    }
                }
                try {
                    // houseNumber can contain both numbers and characters so no need to check this field
                    Integer.parseInt(addressElements[2]); // zip code check
                } catch (NumberFormatException e) {
                    return false;
                }
                for (Character charas : addressElements[3].toCharArray()) { // city check
                    if (Character.isDigit(charas)) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public void setPhoneNumberValidation(String labelID, String imageErrorID, TextField telefonTxtF) {
        phoneNumberValidatorLabel = findChildInTab(labelID, tabPane.getTabs().get(0), Label.class);
        phoneNumberValidatorErrorImage = findChildInTab(imageErrorID, tabPane.getTabs().get(0), ImageView.class);
        telefonTxtF.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!phoneNumberIsValid(telefonTxtF.getText())) {
                    setError(phoneNumberValidatorLabel, phoneNumberValidatorErrorImage);
                } else {
                    clearError(phoneNumberValidatorLabel, phoneNumberValidatorErrorImage);
                }
            }
        });
    }

    public boolean phoneNumberIsValid(String telefonTxt) {
        if (telefonTxt == null) {
            return false;
        }
        if (telefonTxt.length() != 8) {
            return false;
        } else {
            try {
                Double.parseDouble(telefonTxt);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public void setMailValidation(String labelID, String imageErrorID, TextField mailTxtF) {
        mailValidatorLabel = findChildInTab(labelID, tabPane.getTabs().get(0), Label.class);
        mailValidatorErrorImage = findChildInTab(imageErrorID, tabPane.getTabs().get(0), ImageView.class);
        mailTxtF.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!mailIsValid(mailTxtF.getText())) {
                    setError(mailValidatorLabel, mailValidatorErrorImage);
                } else {
                    clearError(mailValidatorLabel, mailValidatorErrorImage);
                }
            }
        });
    }

    public boolean mailIsValid(String mailTxt) {
        if (mailTxt == null) {
            return false;
        }
        return mailTxt.contains("@");
    }

    private void clearError(Label label, ImageView imageView) {
        label.setVisible(false);
        imageView.setVisible(false);
    }

    private void setError(Label label, ImageView imageView) {
        label.setVisible(true);
        imageView.setVisible(true);
    }

    public void setErrors(Collection<TextField> textFields) {
        for (TextField txtF : textFields) {
            switch (txtF.getId()) {
                case "addresseTxtF":
                    setError(addressValidatorLabel, addressValidateErrorImage);
                    break;
                case "telefonTxtF":
                    setError(phoneNumberValidatorLabel, phoneNumberValidatorErrorImage);
                    break;
                case "mailTxtF":
                    setError(mailValidatorLabel, mailValidatorErrorImage);
                    break;
            }
        }
    }

    public void clearAllErrors() {
        addressValidatorLabel.setVisible(false);
        addressValidateErrorImage.setVisible(false);

        phoneNumberValidatorLabel.setVisible(false);
        phoneNumberValidatorErrorImage.setVisible(false);

        mailValidatorLabel.setVisible(false);
        mailValidatorErrorImage.setVisible(false);
    }
}

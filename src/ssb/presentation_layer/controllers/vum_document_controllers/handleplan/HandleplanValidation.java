package ssb.presentation_layer.controllers.vum_document_controllers.handleplan;

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
                    reverseError(addressValidatorLabel, addressValidateErrorImage);
                }
            }
        });
    }

    public boolean addressIsValid(String addresseTxt) {
        if (addresseTxt != null) {
            String[] addressElements = addresseTxt.split(" ");
            if (addressElements.length >= 4) { // Skal følge formen [roadname] [houseNumber] [zip code] [city]
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
                    reverseError(phoneNumberValidatorLabel, phoneNumberValidatorErrorImage);
                }
            }
        });
    }

    public boolean phoneNumberIsValid(String telefonTxt) {
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
                    reverseError(mailValidatorLabel, mailValidatorErrorImage);
                }
            }
        });
    }

    public boolean mailIsValid(String mailTxt) {
        return mailTxt.contains("@");
    }

    private void reverseError(Label label, ImageView imageView) {
        if (label.isVisible() && imageView.isVisible()) {
            label.setVisible(false);
            imageView.setVisible(false);
        } else {
            label.setVisible(true);
            imageView.setVisible(true);
        }
    }

    public void setErrors() {
        reverseError(addressValidatorLabel, addressValidateErrorImage);
        reverseError(phoneNumberValidatorLabel, phoneNumberValidatorErrorImage);
        reverseError(mailValidatorLabel, mailValidatorErrorImage);
    }
}

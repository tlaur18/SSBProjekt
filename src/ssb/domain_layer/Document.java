package ssb.domain_layer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class Document implements Serializable {

    public enum type {
        SAGSÅBNING, UDREDNING, FAGLIGVURDERING, INDSTILLING, HANDLEPLAN, AFGØRELSE, BESTILLING, STATUSNOTAT, OPFØLGNING
    }

    private long id = 0;
    private String documentName;
    private String residentName;
    private final type type;
    private Date editDate;
    private Date creationDate;

    public Document(type type) {
        this.type = type;
        setEditDate();
        setCreationDate();
    }

    public Document(type documentType, Date creationDate, Date editDate) {
        this.type = documentType;
        this.creationDate = creationDate;
        this.editDate = editDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private void setCreationDate() {
        this.creationDate = new Date(new Random().nextInt(100000000) + 1000000);
    }

    private void setEditDate() {
        this.editDate = new Date();
    }

    public String getDocumentName() {
        return generateDocumentName();
    }

    private String generateDocumentName() {
        return documentName = residentName.substring(0, 3) + type.toString().substring(0, 3) + " Test";
    }

    public type getType() {
        return type;
    }

    public Date getEditDate() {
        return editDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    void setResidentName(String firstName) {
        residentName = firstName;
    }

    public String getResidentName() {
        if (residentName != null) {
            return residentName;
        } else {
            return "No associated resident";
        }
    }

    public String encodeDocument() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "documentName=" + documentName
            + "\nassociatedResident=" + residentName
            + "\ntype=" + type
            + "\neditDate=" + editDate
            + "\ncreationDate=" + creationDate;
    }

}

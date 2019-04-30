package ssb.domain_layer;

import java.util.Date;

public class Notification {
    
    private int id;
    private String message;
    private String authorName;
    private String creationDate;

    public Notification(String message, String authorName, String creationDate) {
        this.message = message;
        this.authorName = authorName;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getMessage() {
        return message;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getCreationDate() {
        return creationDate;
    }
}

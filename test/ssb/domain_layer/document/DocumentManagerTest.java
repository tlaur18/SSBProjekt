package ssb.domain_layer.document;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ssb.domain_layer.person.Resident;

public class DocumentManagerTest {
    
    private DocumentManager dm = DocumentManager.getInstance();
    private Resident r1;
    private Resident r2;
    
    public DocumentManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        r1 = new Resident("Anders", "And", "08080808", "000000-1111");
        r2 = new Resident("Børge", "Bisse", "09090909", "000000-2222");
        
        dm.clearDocuments();
        
        Document d1 = new Document(Document.type.SAGSÅBNING);
        d1.setResidentName(r1.getFirstName());
        Document d2 = new Document(Document.type.HANDLEPLAN);
        d2.setResidentName(r1.getFirstName());
        Document d3 = new Document(Document.type.SAGSÅBNING);
        d3.setResidentName(r2.getFirstName());
        
        dm.getAllDocuments().add(d1);
        dm.getAllDocuments().add(d2);
        dm.getAllDocuments().add(d3);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSearchSubList method, of class DocumentManager.
     */
    @Test
    public void testGetSearchSubList() {
        System.out.println("getSearchSubList");
        String keyWordResidentName = "anders";
        String keyWordDocumentName = "sag";
        
        int expSize = 1;
        String expResName = "Anders";
        String expDocName = "AndSAG Test";
        
        ObservableList<Document> resultList = dm.getSearchSubList(keyWordResidentName, keyWordDocumentName);
        assertEquals(expSize, resultList.size());
        assertEquals(expResName, resultList.get(0).getResidentName());
        assertEquals(expDocName, resultList.get(0).getDocumentName());
    }
}

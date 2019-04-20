package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import ssb.data_layer.contracts.EmployeeContract;
import ssb.data_layer.contracts.PersonsContract;

class EmployeeWorkData {

    private final DatabaseManager db = DatabaseManager.getInstance();

    HashMap<String, String> getEmployeeData(String employeeCpr) {
        String sql = "SELECT * FROM " + EmployeeContract.TABLE_NAME + " NATURAL JOIN " + PersonsContract.TABLE_NAME
            + " WHERE " + EmployeeContract.TABLE_NAME + "." + EmployeeContract.COLUMN_CPR + " = ?";

        HashMap<String, String> columnData = new HashMap<>();
        try (Connection connection = db.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCpr);
            ResultSet result = statement.executeQuery();
            columnData.put(EmployeeContract.COLUMN_CPR, result.getString(EmployeeContract.COLUMN_CPR));
            columnData.put(EmployeeContract.COLUMN_ROLE, result.getString(EmployeeContract.COLUMN_ROLE));
            columnData.put(PersonsContract.COLUMN_FIRST_NAME, result.getString(PersonsContract.COLUMN_FIRST_NAME));
            columnData.put(PersonsContract.COLUMN_LAST_NAME, result.getString(PersonsContract.COLUMN_LAST_NAME));
            columnData.put(PersonsContract.COLUMN_PHONE, result.getString(PersonsContract.COLUMN_PHONE));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return columnData;
    }

//    public HashMap<String, List<String>> loadResidents() {
//        try (Scanner scanner = new Scanner(RESIDENTS_DATA_FILE).useDelimiter(",|\n")) {
//            while (scanner.hasNext()) {
//                String residentID = scanner.next();
//                String residentFirstname = scanner.next();
//                String residentLastName = scanner.next();
//                String residentPhoneNr = scanner.next();
//                String residentCPR = scanner.next();
//                String associatedEmloyee = scanner.next();
//                List<String> residentInfo = new ArrayList<>(Arrays.asList(residentFirstname, residentLastName, residentPhoneNr, residentCPR, associatedEmloyee));
//                residentData.put(residentID, residentInfo);
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("The file you want to open doesn't exist");
//        }
//        return residentData;
//    }
//    public List<Pair<String, List<String>>> loadDocuments() {
    //
//        try (Scanner scanner = new Scanner(DOCUMENTS_DATA_FILE).useDelimiter(",|\n")) {
//            while (scanner.hasNext()) {
//                String documentType = scanner.next();
//                String documentCreationDate = scanner.next();
//                String documentEditDate = scanner.next();
//                String associatedResident = scanner.next();
//                documentData.add(new Pair(associatedResident, new ArrayList<>(Arrays.asList(documentType, documentCreationDate, documentEditDate))));
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("The file you want to open doesn't exist");
//        }
//        return documentData;
//    }
}

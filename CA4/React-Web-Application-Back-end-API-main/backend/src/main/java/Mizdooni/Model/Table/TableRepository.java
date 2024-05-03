package Mizdooni.Model.Table;

import java.util.ArrayList;

public class TableRepository {
    private static TableRepository instance;
    private ArrayList<Table> tables = new ArrayList<>();

    public TableRepository() throws Exception {
        TableDAO dao = new TableDAO();
        tables = dao.getFromAPI();
    }

    public static TableRepository getInstance() throws Exception {
        if(instance == null)
            return new TableRepository();
        else return instance;
    }

    public ArrayList<Table> getAll() {
        return tables;
    }
}

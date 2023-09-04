package dat.data.dao;

import dat.data.dao.boilerplate.DAO;
import dat.model.Hobby;

public class HobbyDAO extends DAO<Hobby> {

    private static HobbyDAO instance;

    private HobbyDAO() { }

    public static HobbyDAO getInstance() {
        if (instance == null) {
            instance = new HobbyDAO();
        }

        return instance;
    }
}
package pl.zajacp;

import pl.zajacp.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

            Connection cnt = DatabaseConnection.getConnection();

    }
}


//metody do implementacji
//save()
//update()
//delete()

//statyczne:
//loadAll()
//loadById(id)
//deleteAll(

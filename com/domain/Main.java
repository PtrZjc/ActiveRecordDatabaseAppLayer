package com.domain;

import com.domain.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Connection cnt = DatabaseConnection.getConnection();






        } catch (SQLException e) {
            e.printStackTrace();
        }
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

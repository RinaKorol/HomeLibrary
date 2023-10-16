package com.example.labwithbase.services;

import com.example.labwithbase.Creation;
import com.example.labwithbase.DataLoader;
import com.example.labwithbase.Jenre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JenreService {

    public List<Creation> creationsByJenre(DataLoader loader, Jenre jenre){
        try {
            String query = "SELECT * FROM creation_jenre WHERE jenre_id = "+ jenre.getId();
            ResultSet rs = loader.getRS(query);
            Creation creation;
            List<Creation> creations = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    int auId = rs.getInt(3);
                    creation = new Creation(id,name,auId);
                    creations.add(creation);

                }
                return creations;
            }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return null;
    }
    public void deleteJenre(DataLoader loader, Jenre jenre){
        String query = "DELETE FROM jenre WHERE id = "+ jenre.getId();
        ResultSet rs = loader.getRS(query);
    }
    public void addJenre(DataLoader loader, Jenre jenre){
        String query = "INSERT INTO jenre (id, name) VALUES ( "+ jenre.getId() + " , '"+ jenre.getName()+"' )";
        ResultSet rs = loader.getRS(query);
    }
    public List<Jenre> findAllJenres(DataLoader loader){
        try{
            Jenre jenre;
            List<Jenre> jenres = new ArrayList<>();
            ResultSet rs = loader.getRS("SELECT * FROM JENRE order by id");
            while (rs.next()) {
                int count = rs.getInt(1);
                String name = rs.getString(2);
                jenre = new Jenre(count,name);
                jenres.add( jenre);

            }
            return jenres;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }


        return null;
    }
}

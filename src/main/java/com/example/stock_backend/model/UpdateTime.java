package com.example.stock_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document; 

@Document(collection = "update_time")
public class UpdateTime {

    @Id
    private String id = "timeId"; 
    private long nextUpdateTime; 
    
    public String getId(){
        return id; 
    }    

    public void setId(String id){
        this.id = id;
    }
    public long getNextUpdateTime(){
        return nextUpdateTime; 
    }

    public void setNextUpdateTime(long nextUpdateTime){
        this.nextUpdateTime = nextUpdateTime;
    }
}

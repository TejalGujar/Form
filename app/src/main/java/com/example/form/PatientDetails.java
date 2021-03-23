package com.example.form;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PatientDetails implements Serializable {

    String pFirstName;
    String pLastName;
    String pGender;
    String pSymptoms;
    String pId;

   public PatientDetails(String pFirstName,String pLastName, String pGender, String pSymptoms){
       this.pFirstName = pFirstName;
       this.pLastName = pLastName;
       this.pGender = pGender;
       this.pSymptoms = pSymptoms;
   }

   public PatientDetails(){
   }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpFirstName(){
       return pFirstName;
   }

   public String getpLastName(){
       return pLastName;
   }

   public String getpGender(){
       return pGender;
   }

   public String getpSymptoms(){
       return pSymptoms;
   }

   public Map<String,Object> toMap(){
       Map<String,Object> mMap = new HashMap<>();
       mMap.put("pFirstName",this.pFirstName);
       mMap.put("pLastName",this.pLastName);
       mMap.put("pGender",this.pGender);
       mMap.put("pSymptoms",this.pSymptoms);

       return mMap;
   }

}

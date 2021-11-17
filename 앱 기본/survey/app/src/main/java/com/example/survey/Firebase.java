package com.example.survey;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Firebase {
    public Long que;
    public String ans;

    public Firebase() {
    }
    public Firebase(Long que, String ans){
        this.que = que;
        this.ans = ans;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("que", que);
        result.put("ans", ans);
        return result;
    }
}

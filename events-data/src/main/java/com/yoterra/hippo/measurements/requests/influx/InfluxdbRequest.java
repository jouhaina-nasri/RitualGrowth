package com.yoterra.hippo.measurements.requests.influx;


import com.yoterra.hippo.measurements.requests.form.EventRequest;
import org.springframework.util.StringUtils;

public class InfluxdbRequest {

    public static String toInfluxdbQuery(EventRequest request){
        boolean first = true;
        String query ="SELECT * FROM events_screens";

        if(StringUtils.hasText(request.getEventId())){
                query = query+ " where eventId ='"+request.getEventId()+"'";
                first = false;
        }

        if(StringUtils.hasText(request.getUserId())){
            if(first) {
                query = query + " where userId ='" + request.getUserId() + "'";
                first = false;
            }
            else {
                query = query + " and userId ='" + request.getUserId() + "'";
            }
        }

        if(StringUtils.hasText(request.getScreenName())){
            if(first)
                query = query+ " where screenName ='"+request.getScreenName()+"'";
            else
                query = query+ " and screenName ='"+request.getScreenName()+"'";
        }

        return query;
    }

    public static String toInfluxdbQuery(String userId){
        boolean first = true;
        String query ="SELECT * FROM events_screens";

        if(StringUtils.hasText(userId))
            query = query + " where userId ='" +userId+ "'";

        return query;
    }


}

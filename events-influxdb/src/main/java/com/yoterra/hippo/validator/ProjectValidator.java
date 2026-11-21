package com.yoterra.hippo.validator;


import com.yoterra.hippo.jpa.entities.Project;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProjectValidator {

    public static List<String> validate(Project project)
    {
        List<String> errors = new ArrayList<>();

        if(project==null) {
            errors.add("Please enter project name !");
            errors.add("Please enter project website !");
            return errors;
        }

        if(project.getProjectName()==null) {
            errors.add("Please enter project name !");
        }

        if(project.getProjectWebsite()==null) {
            errors.add("Please enter project website !");
        }

        if(!isValid(project.getProjectWebsite())) {

            errors.add("your website not available !");
        }

        return errors;
    }


    public static boolean isValid(String url)
    {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}

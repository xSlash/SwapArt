/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package swapart.martin.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

import java.security.Key;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.martin.swapart", ownerName = "backend.martin.swapart", packagePath = ""))
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "nameToX")
    public MyBean nameToX(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("x" + name + "x");

        return response;
    }

    @ApiMethod(name = "storeObject")
    public MyBean storeObject(@Named("username") String username, @Named("password") String password, @Named("name") String name, @Named("city") String city, @Named("phone") String phone) throws EntityNotFoundException {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        Entity newuser = new Entity("Users", username);

        newuser.setProperty("userName", username);
        newuser.setProperty("password", password);
        newuser.setProperty("fullname", name);
        newuser.setProperty("city", city);
        newuser.setProperty("phone", phone);

        //com.google.appengine.api.datastore.Key newuserKey = newuser.getKey();

        //Date hireDate = new Date();
        //employee.setProperty("hireDate", hireDate);

        //employee.setProperty("attendedHrTraining", true);


        datastore.put(newuser);

        Entity newlyCreatedUser = datastore.get(newuser.getKey());


        String createdUserName = (String) newlyCreatedUser.getProperty("username");


        MyBean response = new MyBean();
        response.setData(createdUserName + " created!");

        return response;
    }

}

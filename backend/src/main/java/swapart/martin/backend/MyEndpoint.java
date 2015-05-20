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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.*;



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

    @ApiMethod(name = "checkLogin")
    public MyBean checkLogin(@Named("username") String username, @Named("password") String password) throws EntityNotFoundException {

        MyBean response = new MyBean();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        Entity createUser = new Entity("Users", username);
        Key tempKey = createUser.getKey();


        Entity newlyCreatedUser = datastore.get(tempKey);


        String createdUserName = (String) newlyCreatedUser.getProperty("userName");
        String createdPassWord = (String) newlyCreatedUser.getProperty("passWord");
        String fetchedfullname = (String) newlyCreatedUser.getProperty("fullname");
        String fetchedphone = (String) newlyCreatedUser.getProperty("phone");
        String fetchedcity = (String) newlyCreatedUser.getProperty("city");

        if (!createdPassWord.equals(password)) {
            response.setData("Password not correct");
        }
        else {
            response.setData(createdUserName + ":" + fetchedfullname + ":" + fetchedphone + ":" + fetchedcity);
        }


        return response;

    }

    @ApiMethod(name = "storeObject")
    public MyBean storeObject(@Named("username") String username, @Named("password") String password, @Named("name") String name, @Named("city") String city, @Named("phone") String phone) throws EntityNotFoundException {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        //Create user
        Entity createUser = new Entity("Users", username);

        createUser.setProperty("userName", username);
        createUser.setProperty("passWord", password);
        createUser.setProperty("fullname", name);
        createUser.setProperty("city", city);
        createUser.setProperty("phone", phone);

        datastore.put(createUser);

        Key tempKey = createUser.getKey();

        Entity newlyCreatedUser = datastore.get(tempKey);

        //Retrieve user data
        String createdUserName = (String) newlyCreatedUser.getProperty("userName");
        String createdPassWord = (String) newlyCreatedUser.getProperty("passWord");
        String createdFullName = (String) newlyCreatedUser.getProperty("fullname");
        String createdCity = (String) newlyCreatedUser.getProperty("city");
        String createdPhone = (String) newlyCreatedUser.getProperty("phone");

        MyBean response = new MyBean();

        response.setData("User created - " + createdUserName);

        return response;

    }

    @ApiMethod(name = "updateUser")
    public MyBean updateUser(@Named("username") String username, @Named("password") String password, @Named("name") String name, @Named("city") String city, @Named("phone") String phone) throws EntityNotFoundException {
        //public MyBean storeObject(@Named("username") String username, @Named("password") String password) throws EntityNotFoundException {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        Entity createUser = new Entity("Users", username);
        createUser.setProperty("passWord", password);
        createUser.setProperty("userName", username);
        createUser.setProperty("fullname", name);
        createUser.setProperty("city", city);
        createUser.setProperty("phone", phone);


        datastore.put(createUser);

        Key tempKey = createUser.getKey();

        Entity newlyCreatedUser = datastore.get(tempKey);


        /*String createdUserName = (String) newlyCreatedUser.getProperty("userName");

        String createdFullName = (String) newlyCreatedUser.getProperty("fullname");
        String createdCity = (String) newlyCreatedUser.getProperty("city");
        String createdPhone = (String) newlyCreatedUser.getProperty("phone");*/

        MyBean response = new MyBean();

        response.setData("User data updated");

        return response;

    }


}

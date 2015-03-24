package swapart.martin.backend;

/**
 * Created by Martin on 24-03-2015.
 */

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

@Api(name = "myApi2", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.martin.swapart", ownerName = "backend.martin.swapart", packagePath = ""))
public class Xname {

    @ApiMethod(name = "swag")
    public MyBean swag(@Named("thename") String thename) {

        MyBean newname = new MyBean();
        newname.setData("Swag to " + thename);

        return newname;
    }
}


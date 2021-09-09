package com.rf.inventory.webapps;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rf.inventory.backend.InventoryDAO;
import com.rf.inventory.backend.InventoryDAOJDBCImpl;
import com.rf.inventory.backend.Item;
import com.rf.inventory.backend.ItemList;
import com.sun.research.ws.wadl.Request;

/**
 * RESTful service implemented with JAX-RS 2.0
 * 
 * See https://jersey.java.net
 * See the Jersey User Guide http://jersey.java.net/documentation/latest/user-guide.html
 * See JSR 339 Javadocs http://jcp.org/aboutJava/communityprocess/final/jsr339/index.html
 * @author Mike Woinoski
 */
// TODO: Specify that request URLs will all begin with 
//       http://localhost:8080/inventory/rs/item  
// HINT: Part of this is fixed by the name of the war file and the web.xml URL mapping
//       See slide 6-14
@Path("/item")
public class InventoryEndpointImpl {
    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(InventoryEndpointImpl.class);

    private InventoryDAO dao = new InventoryDAOJDBCImpl();

    /**
     * Handles HTTP GET. Sends the complete inventory.
     * Request URL will be http://localhost:8080/inventory/rs/item/all
     */
    // TODO: Add the appropriate annotations here.  
    // HINTS: 
    // 1. Specify that this resource method will handle HTTP GET requests
    // 2. Method will be invoked with URL http://localhost:8080/inventory/rs/item/all 
    // 3. Method will produce content of MIME type "application/xml"
    // See slide 6-15
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public ItemList doGet() {
        // TODO: Implement this method by calling the DAO getItems() method
        //       method and returning its result
    	ItemList itemList=getDao().getItems();
        return itemList;
    }

    /**
     * Handles HTTP Delete. The request URI (e.g. http://.../item/3012) identifies
     * the productId of the item to be deleted from inventory
     * Request URL will be http://localhost:8080/inventory/rs/item/3012
     */
    // TODO: Add the appropriate annotations here (there are 3).  
    // HINTS: 
    // 1. Specify that this resource method will handle HTTP DELETE requests
    // 2. Method will be invoked with URL http://localhost:8080/inventory/rs/item/3012 
    // 3. One of the annotations goes before the first parameter of the method
    // See slide 6-16
    @DELETE
    @Path("/{id}")
    public Response doDelete(@PathParam("id") int id ) {
        if (id <= 0) {
            // TODO: Return an HTTP Internal Server Error status (500)
        	//       See slide 6-29
        	throw new WebApplicationException(500);
        }
        getDao().removeItem(id);
        // TODO: return an HTTP OK status (200)
        Response response=Response.accepted().entity(id).build();
        return response;
    }

    /**
     * Handles HTTP POST. Creates a new Item with a new unique id.
     * Request URL: http://localhost:8080/inventory/rs/item
     * POST request body:
     *   <item>
     *       <productId>3012</productId>
     *       <quantity>33</quantity>
     *   </item>
     */
    // TODO: Add the appropriate annotations here (there are 3)  
    // HINTS: 
    // 1. Specify that this resource method will handle HTTP POST requests
    // 2. Method will be invoked with URL http://localhost:8080/inventory/rs/item
    // 3. Method will consume content of MIME type "application/xml"
    // 4. Method will produce content of MIME type "application/xml"
    // See slide 6-24
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public String doPost(Item item) throws WebApplicationException {
        if (item.getProductId() <= 0 || item.getQuantity() < 0) {
            // TODO: return an HTTP Bad Request status (400)
            // HINT: see slide 6-29
        	throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        // TODO: Invoke the DAO addItem() method
        getDao().addItem(item.getProductId(), item.getQuantity());
        
        // TODO: Return "<ok/>"
        return "<ok />" ;
    }

    /**
     * Handles HTTP PUT. Updates the quantity of Item with the given productId. 
     * Request URL (e.g. http://.../item/3012) ends with productId
     * Request URL: http://localhost:8080/inventory/rs/item/3012
     * PUT request body:
     *   <item>
     *       <quantity>44</quantity>
     *   </item>
     */
    // TODO: Add the appropriate annotations here (there are 4)  
    // HINTS: 
    // 1. Specify that this resource method will handle HTTP PUT requests
    // 2. Method will be invoked with URL http://localhost:8080/inventory/rs/item/3012
    // 3. Method will consume content of MIME type "application/xml"
    // 4. Method will return an HTTP response (the response will not have a body)
    // The last component of the URL should be assigned to the parameter id, 
    // and the body of the HTTP request is assigned to the parameter item.
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response doPut(@PathParam("id") int id, Item item )
                    throws WebApplicationException {
        // ignore the productId in the XML and use the URL parameter
    	item.setProductId(id);
        if (item.getProductId() <= 0 || item.getQuantity() < 0) {
            // TODO: return an HTTP status of BAD_REQUEST (400)
            // HINT: see slide 6-29
        	throw new WebApplicationException(400);
        }
        // TODO: use the dao to update the stock count for this item
        getDao().updateStockCount(id, item.getQuantity());
        // TODO: return an HTTP status of Accepted (202)
        // HINT: see slide 6-28
       
        return Response.accepted().build();
    }
    
    private InventoryDAO getDao() {
    	return dao;
    }
}

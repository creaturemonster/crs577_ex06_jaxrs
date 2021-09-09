package com.rf.inventory.backend;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

/**
 * Data type corresponding to XML exchanged with the server. The XML looks like this:
 * <items>
 *    <item productId="3212" quantity="4" />
 *    <item productId="3204" quantity="7" />
 * </items>
 * 
 * @author v.lakshmanan
 *
 */

// TODO: Add annotation to indicate that we will send/receive XML documents 
//       consisting of just one ItemList.
//       The name of the XML element should be "items"
// HINT: See slide 6-19
@XmlRootElement(name="items")
public class ItemList {
    
    private List<Item> items = new ArrayList<Item>();

    // TODO: Add annotation to indicate that it will have child elements named "item" 
    //       whose contents come out of this array list
    @XmlElement(name="item")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

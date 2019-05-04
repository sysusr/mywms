/*
 * StorageLocationQueryRemote.java
 *
 * Created on 14. September 2006, 06:59
 *
 * Copyright (c) 2006 LinogistiX GmbH. All rights reserved.
 *
 *<a href="http://www.linogistix.com/">browse for licence information</a>
 *
 */

package de.linogistix.los.inventory.pick.query;


import javax.ejb.Remote;

import de.linogistix.los.inventory.pick.model.PickReceiptPosition;
import de.linogistix.los.query.BusinessObjectQueryRemote;


/**
 *
 * @author <a href="http://community.mywms.de/developer.jsp">Andreas Trautmann</a>
 */
@Remote
public interface PickReceiptPositionQueryRemote extends BusinessObjectQueryRemote<PickReceiptPosition>{ 
      
}

package com.ensf480.model.user;

import com.ensf480.model.property.Property;

public  interface Observer {
    public String searchCriteria = null;
    public void update(Property property);
}

package src.main.model.user;

import src.main.model.property.Property;

public  interface Observer {
    public String searchCriteria = null;
    public void update(Property property);
}

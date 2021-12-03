package User;

public  interface Observer {
    public String searchCriteria = null;
    public void update(Property property);
}

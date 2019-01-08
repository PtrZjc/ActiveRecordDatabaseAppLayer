package pl.zajacp.db;

public interface ActiveRecord {
    public void save();
    public void update();
    public void delete();
}

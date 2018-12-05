package com.domain.model;

public interface ActiveRecord {
    public void save();
    public void update();
    public void delete();
}

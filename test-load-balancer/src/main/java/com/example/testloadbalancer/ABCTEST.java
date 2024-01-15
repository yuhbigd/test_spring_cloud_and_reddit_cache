package com.example.testloadbalancer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ABCE_TEST")
public class ABCTEST {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ABCTEST [id=" + id + ", path=" + path + "]";
    }

}

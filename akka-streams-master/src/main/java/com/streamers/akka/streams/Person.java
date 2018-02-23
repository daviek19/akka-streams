/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.streamers.akka.streams;

/**
 *
 * @author david
 */
public class Person{

    private String id;
    private String lastName;
    private int modified;
    private String dateModifid;

    public Person(String id, String lastName, int modified, String dateModifid) {
        this.id = id;
        this.lastName = lastName;
        this.modified = modified;
        this.dateModifid = dateModifid;
    }

    @Override
    public String toString() {
        return this.id + " " + this.lastName + " " + this.dateModifid + "";
    }

}

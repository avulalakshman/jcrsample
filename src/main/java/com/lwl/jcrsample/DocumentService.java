package com.lwl.jcrsample;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jcr.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class DocumentService {
    private Repository jcrRepository;
    private Session session;

    @Autowired
    public DocumentService(RepositoryImpl jcrRepository) throws Exception {
        this.jcrRepository = jcrRepository;

    }
    public void login()throws Exception {
        try {
            session = getSession();
            String user = session.getUserID();
            String name = jcrRepository.getDescriptor(Repository.REP_NAME_DESC);
            System.out.println(
                    "Logged in as " + user + " to a " + name + " repository.");
        } finally {
            session.logout();
        }
    }
    public void createAndDeleteNode()throws Exception{
        Session session = getSession();
        try {
            Node root = session.getRootNode();

            // Store content
            Node hello = root.addNode("hello");
            Node world = hello.addNode("world");
            world.setProperty("message", "Hello, World!");
            session.save();

            // Retrieve content
            Node node = root.getNode("hello/world");
            System.out.println(node.getPath());
            System.out.println(node.getProperty("message").getString());

            // Remove content
            root.getNode("hello").remove();
            session.save();
        } finally {
            session.logout();
        }
    }

    public  void saveFile(String tenantId) throws  Exception {
        session = getSession();
        Node root = session.getRootNode();
        if (root.hasNode(tenantId) ){
            Node node = root.getNode(tenantId);
            long count = node.getProperty("count").getLong();
            node.setProperty("count", count + 1);
            try {
                Binary binary = session.getValueFactory().createBinary( new FileInputStream("C:\\Users\\learn\\cypress-ws\\cypress.json"));
                node.setProperty("file",binary );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Found the "+tenantId+" node, count = " + count);
        } else {
            System.out.println("Creating the "+tenantId+" node");
            root.addNode(tenantId).setProperty("count", 1);
            Node node = root.getNode(tenantId);
            try {
                Binary binary = session.getValueFactory().createBinary( new FileInputStream("C:\\Users\\learn\\cypress-ws\\cypress.json"));
                node.setProperty("file",binary );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        session.save();
    }


    private Session getSession()throws  Exception {
        return jcrRepository.login(
                new SimpleCredentials("admin", "admin".toCharArray()));
    }

}

package com.java.test.ProjectTest.repository;

import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public interface ChatGPTClientRepository {

    String getResponse(String prompt) throws IOException;

    void getResponseAndWriteToCSV(String prompt) throws IOException;
}

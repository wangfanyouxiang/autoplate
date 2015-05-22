package com.qeautos.newman_examples;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class qeExecutor extends DefaultExecutor {
    int cmdExitCode;
    String cmdOutput;
    static Logger log = Logger.getLogger(qeExecutor.class.getName());

    public int runBash(String cmd) {
        PropertyConfigurator.configure("log4j.properties");

        log.info("entering runBash(\"" + cmd + "\")");
        cmdExitCode = -1;
        cmdOutput = null;
        String command_string = cmd;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor exec = new DefaultExecutor();
        exec.setStreamHandler(streamHandler);

        log.debug("Bash Command: " + command_string);
        CommandLine final_command = CommandLine.parse(command_string);

        try {
            cmdExitCode = exec.execute(final_command);
            cmdOutput = outputStream.toString();
            log.debug("Bash Comand Output: " + cmdOutput);
            log.debug("Bash Command ExitCode: " + cmdExitCode);
        } catch (IOException e) {
            e.printStackTrace();
            cmdExitCode = -1;
        } finally {
            log.info("exiting runBash with " + cmdExitCode);
            return cmdExitCode;
        }
    }

    public int runCollection(String collection_name) {
        PropertyConfigurator.configure("log4j.properties");

        log.info("entering runCollection(\"" + collection_name + "\")");
        cmdExitCode = -1;
        cmdOutput = null;

        String work_dir = System.getProperty("user.dir");
        String node_cmd = "node ";
        String newman_path = "node_modules/newman/bin/newman";
        String newman_arg = " -x -C ";
        String collection_dir = "src/test/resources/postman_collections/";
        String collection_data = collection_name + ".data";
        String collection_file_arg = " -c " + collection_dir + collection_name;
        String command_string = node_cmd + newman_path + newman_arg;

        if (new File(collection_dir + collection_name).exists()) {
            log.debug("Collection File: " + collection_name);
            command_string = command_string + collection_file_arg;
        } else {
            log.error("Collection File Not Found: " + collection_dir + collection_name);
            log.info("exiting runCollection with " + cmdExitCode);
            return cmdExitCode;
        }

        if (new File(collection_dir + collection_data + ".csv").exists()) {
            log.debug("Collection Data: " + collection_data + ".csv");
            command_string = command_string + " -d " + collection_dir + collection_data + ".csv";
        } else if (new File(collection_dir + collection_data + ".json").exists()) {
            log.debug("Collection Data: " + collection_data + ".json");
            command_string = command_string + " -d " + collection_dir + collection_data + ".json";
        } else {
            log.debug("Collection Data: none");
        }

        log.debug("Newman Command: " + command_string);

        try {
            cmdExitCode = runBash(command_string);
        } catch (Exception e) {
            e.printStackTrace();
            cmdExitCode = 1;
        } finally {
            log.info("exiting runCollection with " + cmdExitCode);
            return cmdExitCode;
        }
    }
}

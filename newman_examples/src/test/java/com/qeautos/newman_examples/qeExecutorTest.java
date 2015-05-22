package com.qeautos.newman_examples;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.Assert.*;

public class qeExecutorTest {

    @Test
    public void testRunBash_pwd() throws Exception {
        qeExecutor e = new qeExecutor();
        e.runBash("pwd");
        Assert.assertEquals(e.cmdExitCode, 0);
        System.out.println("testRunBash_pwd: " + e.cmdOutput);
    }
    @Test
    public void GoogleMap() throws Exception {
        qeExecutor e = new qeExecutor();
        String collection_name = "GoogleMap.json";
        e.runCollection(collection_name);
        Assert.assertEquals(e.cmdExitCode, 0);
        System.out.println(e.cmdOutput);
        if (e.cmdOutput.contains(
                "    ✔ Status code is 200\n" +
                "    ✔ Content-Type is image/png\n" +
                "    ✔ Server is staticmap\n" +
                "\n" +
                "Summary:\n" +
                "\n" +
                "Parent                   \tPass Count\t FailCount\n" +
                "-------------------------------------------------------------\n" +
                "Collection GoogleMap     \t         3\t         0\n" +
                "\n" +
                "Total                    \t         3\t         0\n"
        )){
            assert true;
        }
        else {
            assert false;
        }
    }
    @Test
    public void Walkthrough_csvDatafile() throws Exception {
        qeExecutor e = new qeExecutor();
        String collection_name = "Walkthrough_csvDatafile.json";
        e.runCollection(collection_name);
        Assert.assertEquals(e.cmdExitCode, 0);
        System.out.println(e.cmdOutput);
    }
    @Test
    public void Walkthrough_jsonDatafile() throws Exception {
        qeExecutor e = new qeExecutor();
        String collection_name = "Walkthrough_jsonDatafile.json";
        e.runCollection(collection_name);
        Assert.assertEquals(e.cmdExitCode, 0);
    }
}

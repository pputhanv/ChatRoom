package edu.udacity.java.nano.chat;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {
    Message message;

    public MessageTest() {
        Message message = new Message("SPEAK","Pradeep","Hi, how are you",3);
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void toString1() {
        Message message = new Message("SPEAK","Pradeep","Hi, how are you",3);
        String msgActual = message.toString();
        String msgJsonExpected  = "{\"msg\":\"Hi, how are you\",\"onlineCount\":3,\"type\":\"SPEAK\",\"username\":\"Pradeep\"}";

        Assert.assertEquals(msgJsonExpected,msgActual);
    }

    @Test
    public void setType() {
        Message message = new Message("SPEAK","Pradeep","Hi, how are you",3);
        message.setType("BYE");
        Assert.assertEquals("BYE",message.getType());

    }

    @Test
    public void getUsername() {
        Message message = new Message("SPEAK","Pradeep","Hi, how are you",3);
        Assert.assertEquals("Pradeep",message.getUsername());
    }

    @Test
    public void setUsername() {
        Message message = new Message("SPEAK","Pradeep","Hi, how are you",3);
        message.setUsername("ABCDEF");
        Assert.assertEquals("ABCDEF",message.getUsername());

    }

    @Test
    public void getMsg() {
        Message message = new Message("SPEAK","Pradeep","Hi, how are you",3);
        Assert.assertEquals("Hi, how are you",message.getMsg());
    }

    @Test
    public void setMsg() {
        Message message = new Message("SPEAK","Pradeep","Hi, how are you",3);
        message.setMsg("Bye, cu later");
        Assert.assertEquals("Bye, cu later",message.getMsg());
    }

    @Test
    public void getOnlineCount() {
        Message message = new Message("SPEAK","Pradeep","Hi, how are you",3);
        Assert.assertEquals(3,message.getOnlineCount());
    }
}
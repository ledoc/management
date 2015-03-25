package fr.treeptik.model.assembler;

import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EnregistreurAssembler_Should {

    private EnregistreurAssembler enregistreurAssembler = new EnregistreurAssembler();

    @Test
    public void fillValidTrue(){
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcByDefault()).getValid(), is(true));
    }

    @Test
    public void fillValidFalse(){
        HashMap<String, Object> xmlRpcHashMap = xmlRpcByDefault();
        xmlRpcHashMap.put("valid", false);
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcHashMap).getValid(), is(false));
    }

    @Test
    public void fillPeriod(){
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcByDefault()).getPeriod(), is(34));
    }

    @Test
    public void fillLocalizableStatus(){
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcByDefault()).getLocalizableStatus(), is(76));
    }

    @Test
    public void fillClientName(){
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcByDefault()).getClientName(), is("anyClientName"));
    }
    @Test
    public void fillMid(){
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcByDefault()).getMid(), is("anyMid"));
    }
    @Test
    public void fillUntil(){
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcByDefault()).getUntil(), is(54));
    }
    @Test
    public void fillPid(){
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcByDefault()).getPid(), is("anyPid"));
    }
    @Test
    public void fillComment(){
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcByDefault()).getComment(), is("anyComment"));
    }

    @Test
    public void fillType(){
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcByDefault()).getType(), is("anyType"));
    }

    @Test
    public void fillUserName(){
        assertThat(enregistreurAssembler.fromXmlrpcHashMap(xmlRpcByDefault()).getUserName(), is("anyUserName"));
    }

    private HashMap<String, Object> xmlRpcByDefault() {
        HashMap<String, Object> xmlRpcHashMap = new HashMap<String, Object>();
        xmlRpcHashMap.put("valid", true);
        xmlRpcHashMap.put("period", 34);
        xmlRpcHashMap.put("localizableStatus", 76);
        xmlRpcHashMap.put("clientName", "anyClientName");
        xmlRpcHashMap.put("mid", "anyMid");
        xmlRpcHashMap.put("until", 54);
        xmlRpcHashMap.put("pid", "anyPid");
        xmlRpcHashMap.put("comment", "anyComment");
        xmlRpcHashMap.put("type", "anyType");
        xmlRpcHashMap.put("userName", "anyUserName");
        return xmlRpcHashMap;
    }

}
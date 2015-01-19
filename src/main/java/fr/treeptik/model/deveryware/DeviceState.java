package fr.treeptik.model.deveryware;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DeviceState {
	private String mid;
	private int date;
	private int input1;
	private int input2;
	private int input3;
	private int input4;
	private int output1;
	private int output2;
	private int output3;
	private int output4;
	private float inputV1;
	private float inputV2;
	private float inputV3;
	private float inputV4;
	private int devState;
	private int comstate;
	private int batLevel;
	private String stream1;
	private String stream2;
	private String stream3;
	private String stream4;

	public DeviceState(HashMap<String, Object> xmlrpcHashMap) {
		this.mid = (String) xmlrpcHashMap.get("mid");
		this.date = (int) xmlrpcHashMap.get("date");
		this.input1 = (int) xmlrpcHashMap.get("input1");
		this.input2 = (int) xmlrpcHashMap.get("input2");
		this.input3 = (int) xmlrpcHashMap.get("input3");
//		this.input4 = (int) xmlrpcHashMap.get("input4");
//		this.output1 = (int) xmlrpcHashMap.get("output1");
//		this.output2 = (int) xmlrpcHashMap.get("output2");
//		this.output3 = (int) xmlrpcHashMap.get("output3");
//		this.output4 = (int) xmlrpcHashMap.get("output4");
//		this.inputV1 = (float) xmlrpcHashMap.get("inputV1");
//		this.inputV2 = (float) xmlrpcHashMap.get("inputV2");
//		this.inputV3 = (float) xmlrpcHashMap.get("inputV3");
//		this.inputV4 = (float) xmlrpcHashMap.get("inputV4");
//		this.devState = (int) xmlrpcHashMap.get("devState");
//		this.comstate = (int) xmlrpcHashMap.get("comstate");
//		this.batLevel = (int) xmlrpcHashMap.get("batLevel");
//		this.stream1 = (String) xmlrpcHashMap.get("stream1");
//		this.stream2 = (String) xmlrpcHashMap.get("stream2");
//		this.stream3 = (String) xmlrpcHashMap.get("stream3");
//		this.stream4 = (String) xmlrpcHashMap.get("stream4");
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getInput1() {
		return input1;
	}

	public void setInput1(int input1) {
		this.input1 = input1;
	}

	public int getInput2() {
		return input2;
	}

	public void setInput2(int input2) {
		this.input2 = input2;
	}

	public int getInput3() {
		return input3;
	}

	public void setInput3(int input3) {
		this.input3 = input3;
	}

	public int getInput4() {
		return input4;
	}

	public void setInput4(int input4) {
		this.input4 = input4;
	}

	public int getOutput1() {
		return output1;
	}

	public void setOutput1(int output1) {
		this.output1 = output1;
	}

	public int getOutput2() {
		return output2;
	}

	public void setOutput2(int output2) {
		this.output2 = output2;
	}

	public int getOutput3() {
		return output3;
	}

	public void setOutput3(int output3) {
		this.output3 = output3;
	}

	public int getOutput4() {
		return output4;
	}

	public void setOutput4(int output4) {
		this.output4 = output4;
	}

	public float getInputV1() {
		return inputV1;
	}

	public void setInputV1(float inputV1) {
		this.inputV1 = inputV1;
	}

	public float getInputV2() {
		return inputV2;
	}

	public void setInputV2(float inputV2) {
		this.inputV2 = inputV2;
	}

	public float getInputV3() {
		return inputV3;
	}

	public void setInputV3(float inputV3) {
		this.inputV3 = inputV3;
	}

	public float getInputV4() {
		return inputV4;
	}

	public void setInputV4(float inputV4) {
		this.inputV4 = inputV4;
	}

	public int getDevState() {
		return devState;
	}

	public void setDevState(int devState) {
		this.devState = devState;
	}

	public int getComstate() {
		return comstate;
	}

	public void setComstate(int comstate) {
		this.comstate = comstate;
	}

	public int getBatLevel() {
		return batLevel;
	}

	public void setBatLevel(int batLevel) {
		this.batLevel = batLevel;
	}

	public String getStream1() {
		return stream1;
	}

	public void setStream1(String stream1) {
		this.stream1 = stream1;
	}

	public String getStream2() {
		return stream2;
	}

	public void setStream2(String stream2) {
		this.stream2 = stream2;
	}

	public String getStream3() {
		return stream3;
	}

	public void setStream3(String stream3) {
		this.stream3 = stream3;
	}

	public String getStream4() {
		return stream4;
	}

	public void setStream4(String stream4) {
		this.stream4 = stream4;
	}

	/**
	 * TODO :String la date
	 */
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Long dateLong =  (long) date;
		String dateString = dateFormat.format(new Date ( dateLong *1000));
		return "DeviceState [mid=" + mid + ", date=" + dateString + ", input1="
				+ input1 + ", input2=" + input2 + ", input3=" + input3
				+ ", input4=" + input4 + ", output1=" + output1 + ", output2="
				+ output2 + ", output3=" + output3 + ", output4=" + output4
				+ ", inputV1=" + inputV1 + ", inputV2=" + inputV2
				+ ", inputV3=" + inputV3 + ", inputV4=" + inputV4
				+ ", devState=" + devState + ", comstate=" + comstate
				+ ", batLevel=" + batLevel + ", stream1=" + stream1
				+ ", stream2=" + stream2 + ", stream3=" + stream3
				+ ", stream4=" + stream4 + "]";
	}

}

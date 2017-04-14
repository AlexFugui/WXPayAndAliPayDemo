package cn.leancloud.demo.todo.WXPayTools;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Random;


public class WXPayUtil {

    //生成随机字符串
    public static String getNonce_str() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //map转xml 加上签名信息
    public static String map2Xml(
            Map<String, String> map
    ) throws UnsupportedEncodingException {
        String xmlResult = "";
        StringBuffer sb = new StringBuffer();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("<xml>");
        for (String key : map.keySet()) {
//            System.out.println(key + "========" + map.get(key));
            sb.append(key);
            sb.append('=');
            sb.append(map.get(key));
            sb.append('&');
            // sb2是用来做请求的xml参数
            sb2.append("<" + key + ">");
//            sb2.append("<![CDATA[" + map.get(key) + "]]>");
            sb2.append(map.get(key));
            sb2.append("</" + key + ">");
        }
        sb.append("key=ShiguangyuYangchao11223344556677");
        String sign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        sb2.append("<sign>");
        sb2.append(sign);
        sb2.append("</sign>");
        sb2.append("</xml>");
        return sb2.toString();
    }

    //解析微信返回return_code SUCCESS或FILE
    //根据微信返回resultXml再次生成签名
    public static String getSign(Map<String, Object> map) {
        String xmlResult = "";
        StringBuffer sb = new StringBuffer();
        for (String key : map.keySet()) {
//            System.out.println(key + "========" + map.get(key));
            sb.append(key);
            sb.append('=');
            sb.append(map.get(key));
            sb.append('&');
        }
        sb.append("key=ShiguangyuYangchao11223344556677");
        return MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
    }

    //解析微信返回return_code SUCCESS或FILE
    public static String getReturnCode(String resultXml) {
        String nonceStr;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = dbf.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(resultXml.getBytes());
            Document doc = builder.parse(inputStream); //
            // 下面开始读取
            Element root = doc.getDocumentElement(); // 获取根元素
            NodeList nl = root.getElementsByTagName("return_code");
            Element el = (Element) nl.item(0);
            Node nd = el.getFirstChild();
            nonceStr = nd.getNodeValue();
            return nonceStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //解析微信返回return_msg
    public static String getReturn_msg(String resultXml) {
        String nonceStr;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = dbf.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(resultXml.getBytes());
            Document doc = builder.parse(inputStream); //
            // 下面开始读取
            Element root = doc.getDocumentElement(); // 获取根元素
            NodeList nl = root.getElementsByTagName("return_msg");
            Element el = (Element) nl.item(0);
            Node nd = el.getFirstChild();
            nonceStr = nd.getNodeValue();
            return nonceStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //解析微信返回appid
    public static String getAppId(String resultXml) {
        String nonceStr;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = dbf.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(resultXml.getBytes());
            Document doc = builder.parse(inputStream); //
            // 下面开始读取
            Element root = doc.getDocumentElement(); // 获取根元素
            NodeList nl = root.getElementsByTagName("appid");
            Element el = (Element) nl.item(0);
            Node nd = el.getFirstChild();
            nonceStr = nd.getNodeValue();
            return nonceStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //解析微信返回mch_id
    public static String getMchId(String resultXml) {
        String nonceStr;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = dbf.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(resultXml.getBytes());
            Document doc = builder.parse(inputStream); //
            // 下面开始读取
            Element root = doc.getDocumentElement(); // 获取根元素
            NodeList nl = root.getElementsByTagName("mch_id");
            Element el = (Element) nl.item(0);
            Node nd = el.getFirstChild();
            nonceStr = nd.getNodeValue();
            return nonceStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //解析微信返回nonce_str
    public static String getNonceStr(String resultXml) {
        String nonceStr;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = dbf.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(resultXml.getBytes());
            Document doc = builder.parse(inputStream); //
            // 下面开始读取
            Element root = doc.getDocumentElement(); // 获取根元素
            NodeList nl = root.getElementsByTagName("nonce_str");
            Element el = (Element) nl.item(0);
            Node nd = el.getFirstChild();
            nonceStr = nd.getNodeValue();
            return nonceStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //解析微信返回prepay_id
    public static String getPrepayId(String resultXml) {
        String nonceStr;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = dbf.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(resultXml.getBytes());
            Document doc = builder.parse(inputStream); //
            // 下面开始读取
            Element root = doc.getDocumentElement(); // 获取根元素
            NodeList nl = root.getElementsByTagName("prepay_id");
            Element el = (Element) nl.item(0);
            Node nd = el.getFirstChild();
            nonceStr = nd.getNodeValue();
            return nonceStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}

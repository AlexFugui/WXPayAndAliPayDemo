package cn.leancloud.demo.todo;

import cn.leancloud.EngineFunction;
import cn.leancloud.EngineFunctionParam;
import cn.leancloud.demo.todo.WXPayTools.HttpRequest;
import cn.leancloud.demo.todo.WXPayTools.WXPayUtil;
import cn.leancloud.demo.todo.aliPayTools.OrderInfoUtil2_0;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import org.dom4j.DocumentException;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Cloud {

    /**
     * @param orderId    APP后台生成唯一订单号订单号
     * @param orderTotal 订单总价
     * @param orderBody  订单内容 Json格式
     * @return orderInfo 前端可直接用来调起支付SDK的串
     */
    @EngineFunction("payForAli")
    public static String payForAli(
            @EngineFunctionParam("orderId") String orderId,
            @EngineFunctionParam("orderTotal") String orderTotal,
            @EngineFunctionParam("orderBody") String orderBody
    ) {
        String RSA2_PRIVATE = System.getenv("RSA2_PRIVATE");
        String RSA_PRIVATE = System.getenv("RSA_PRIVATE");

        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("out_trade_no", orderId);
        orderMap.put("body", orderBody);
        orderMap.put("total_amount", orderTotal);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(System.getenv("APPID"), rsa2, orderMap);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        System.out.print(orderInfo);
        return orderInfo;
    }

    /**
     * @param body             订单内容
     * @param total_fee        订单总价
     * @param spbill_create_ip 请求端IP地址
     * @param out_trade_no     后台唯一订单号
     * @return 返回JSON格式 前端解析后可直接调起api
     * @throws AVException
     * @throws UnsupportedEncodingException
     * @throws DocumentException
     */
    @EngineFunction("PayForWX")
    public static String PayForWX(
            @EngineFunctionParam("body") String body,
            @EngineFunctionParam("total_fee") String total_fee,
            @EngineFunctionParam("spbill_create_ip") String spbill_create_ip,
            @EngineFunctionParam("out_trade_no") String out_trade_no
    ) throws AVException, UnsupportedEncodingException, DocumentException {
        Map<String, String> reqMap = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
//                        return obj2.compareTo(obj1);
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
        reqMap.put("appid", System.getenv("appid"));
        reqMap.put("mch_id", System.getenv("mch_id"));
        reqMap.put("nonce_str", WXPayUtil.getNonce_str());
        reqMap.put("body", body);
        reqMap.put("out_trade_no", out_trade_no);
        reqMap.put("total_fee", total_fee); //订单总金额，单位为分
        reqMap.put("spbill_create_ip", spbill_create_ip); //用户端实际ip
        reqMap.put("notify_url", System.getenv("notify_url")); //通知地址
        reqMap.put("trade_type", System.getenv("trade_type")); //交易类型  JSAPI
//        String reqStr = new String(WXPayUtil.map2Xml(reqMap).getBytes("UTF-8"));
        String reqStr = WXPayUtil.map2Xml(reqMap);
        System.out.println("=======XML=======:" + reqStr);
//        String retStr = new String(HttpRequest.sendPost(reqStr).getBytes("UTF-8"));
        String resultXml = HttpRequest.sendPost(reqStr);
        System.out.println("微信请求返回:" + resultXml);
        //解析微信返回串
        if (WXPayUtil.getReturnCode(resultXml).equals("SUCCESS") && WXPayUtil.getReturnCode(resultXml) != null) {
            //成功
            Map<String, Object> resultMap = new TreeMap<>(
                    new Comparator<String>() {
                        public int compare(String obj1, String obj2) {
                            // 降序排序
//                        return obj2.compareTo(obj1);
                            // 升序排序
                            return obj1.compareTo(obj2);
                        }
                    });
            resultMap.put("appid", System.getenv("appid"));
            resultMap.put("partnerid", System.getenv("mch_id"));
            resultMap.put("timeStamp", String.valueOf(System.currentTimeMillis()));
            resultMap.put("nonceStr", WXPayUtil.getNonceStr(resultXml));
            resultMap.put("package", WXPayUtil.getPrepayId(resultXml));
            resultMap.put("prepayid", WXPayUtil.getPrepayId(resultXml));
            resultMap.put("paySign", WXPayUtil.getSign(resultMap));
            resultMap.put("signType", "MD5");
            JSON json = new JSONObject(resultMap);
            return json.toJSONString();
        } else {
            throw new AVException(1, "微信请求支付失败");
        }
    }
}

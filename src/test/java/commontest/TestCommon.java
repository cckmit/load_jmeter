package commontest;

import com.alibaba.fastjson.JSONObject;
import com.hualala.app.crm.service.grpc.*;
import com.hualala.grpc.client.GrpcClient;
import com.hualala.shop.wechat.service.grpc.WechatCardCouponTemplateData;
import com.hualala.shop.wechat.service.grpc.WechatCardCouponTemplateServiceGrpc;
import common.GetGrpcClient;
import org.junit.Test;
import java.util.Date;

import static common.GetGrpcClient.setFIieds;


public class TestCommon {
    CardInfoServiceGrpc.CardInfoServiceFutureStub futureStub;
    CrmConsumeServiceGrpc.CrmConsumeServiceFutureStub futureStub2;
    CrmGiftServiceGrpc.CrmGiftServiceFutureStub crmGiftServiceFutureStub;
    GrpcClient client = new GetGrpcClient().getClient();

    @Test
    public void Test01() throws Exception {

        // 连接grpc
        futureStub = (CardInfoServiceGrpc.CardInfoServiceFutureStub) client.getFutureStub(CardInfoServiceGrpc.class);
        // 接口请求信息填充
        CardInfoData.CardInfoReq req = CardInfoData.CardInfoReq.newBuilder()
                .setGroupID(121955)
                .setGroupName("pre测试072407")
                .setSourceWay("1")
                .setSourceType("30")
//                .setCustomerID(new Date().getTime())
                .setCustomerBirthday("2019-02-02")
                .setCustomerSex("1")
                .setCustomerMobile("17600")
                .setCustomerName("自动化")
                .setShopID("76160789")
                .setShopName("maxsu测试店铺")
                .setShopWeixinID("456789754")
                .setScenes("1010")
                .setUnionID("4565565211")
                .setWeixinID("456789754")
                .setMpID("test")
                .setCardNO("56666")
                .setCardTypeID(6712356429883247715L)
                .setCardLevelID(6712356429883255907L)
                .setOperator("maxsu|maxsu")
                .build();
        // 获取返回信息
        CardInfoData.CardInfoRes res = futureStub.openCard(req).get();


    }

    @Test
    public void Test_cardConsume() throws Exception {
        futureStub2 = (CrmConsumeServiceGrpc.CrmConsumeServiceFutureStub)client.getFutureStub(CrmConsumeServiceGrpc.class);
        CrmConsumeData.CrmConsumeReq infoReq =  CrmConsumeData.CrmConsumeReq.newBuilder()
                .setGroupID(121955)
                .setCardID(6753863925848087651L)
                .setShopID("76160789")
                .setShopName("maxsu测试店铺")
                .setConsumptionAmount("1")
                .setConsumptionPointAmount("0")
                .setDeductMoneyAmount("0")
                .setDeductPointAmount("0")
                .setDiscountAmount("0")
                .setTransWay("1")
                .setTransType(30)
                .setPosOrderNo("123456")
                .build();
        futureStub2.cardConsume(infoReq).get();
    }

    @Test
    public void Test02() throws Exception {
        String ss = "{\"groupID\":121955,\n" +
                "\"cardTypeID\":6712356429883247715,\n" +
                "\"cardNO\":\"100123\"}";
        String json = "{\n" +
                "    \"groupID\":121955,\n" +
                "    \"createShopID\":0,\n" +
                "    \"cardNO\":\"\",\n" +
                "    \"customerMobile\":\"17600370152\",\n" +
                "    \"cardTypeID\":6712356429883247715,\n" +
                "    \"cardLevelID\":6712356429883251811,\n" +
                "    \"shopID\":\"76160789\",\n" +
                "    \"sourceWay\":\"0\",\n" +
                "    \"sourceType\":\"60\",\n" +
                "}";

        // 查询会员卡
        CardInfoData.CardInfoReq cardInfoReq = cardInfo(json);
        futureStub.queryCardInfo(cardInfoReq).get();
    }

    @Test
    public void Test_03() {
        String ss = "{\"groupID_\":121955,\n" +
                "\"cardTypeID_\":6712356429883247715,\n" +
                "\"cardNO_\":\"100123\",\n " +
                "\"bitField0_\":0}";
        // 将json文本转换成json对象
        JSONObject jsonObject = JSONObject.parseObject(ss);
        jsonObject.keySet().forEach(s -> System.out.println(s+ ":" + jsonObject.get(s).getClass().getTypeName()));

    }

    @Test
    public void Test_04() throws Exception {
        WechatCardCouponTemplateServiceGrpc.WechatCardCouponTemplateServiceFutureStub stup;
        String wechatAddr = "pre.shop.wechat.service.hualala.com";
        GrpcClient wechatClient = new GetGrpcClient().getClient(wechatAddr);
        stup = (WechatCardCouponTemplateServiceGrpc.WechatCardCouponTemplateServiceFutureStub) wechatClient.getFutureStub(WechatCardCouponTemplateServiceGrpc.class);
        WechatCardCouponTemplateData.WechatCardEventHandlerReq req1 = WechatCardCouponTemplateData.WechatCardEventHandlerReq.newBuilder()
                .setGroupID("89447")
                .setCardTemplateID("pbgsTw8HZRS15s8i")
                .build();
        stup.queryCardTemplateByCardTemplateID(req1).get();

    }

    @Test
    public void Test_05() throws Exception {
        CrmGiftServiceGrpc.CrmGiftServiceFutureStub crmGiftServiceFutureStub;
        crmGiftServiceFutureStub = (CrmGiftServiceGrpc.CrmGiftServiceFutureStub) client.getFutureStub(CrmGiftServiceGrpc.class);
        CrmGiftData.CrmGiftRequest request = CrmGiftData.CrmGiftRequest.newBuilder().setGroupID(89447)
                .setPageNo("1")
                .setPageSize("0")
                .setPageCount("0")
                .setTotalSize("0")
                .setSourceWay("1")
                .setSourceType("30")
                .setSourceOpenID("obgsTw8k9LvxiQqYbIxgGlNwfyV0")
                .setMpID("lNxAHFA1BX7e10af")
                .setAppID("wx10809aa4e842a143")
                .addGiftDetailList(CrmGiftData.GiftDetailListModel.newBuilder().setGiftItemID(1030036607121805312L)
                        .setWechatGiftCode("459391622799")
                        .setWechatGiftCardID("6746832936013663591").build())
                .build();
        crmGiftServiceFutureStub.getGiftFromHll(request).get();

    }

    @Test
    public void Test_06() throws Exception {
        String json = "{\n" +
                "  \"groupID\": 89447,\n" +
                "  \"pageNo\": \"0\",\n" +
                "  \"pageSize\": \"0\",\n" +
                "  \"pageCount\": \"0\",\n" +
                "  \"totalSize\": \"0\",\n" +
                "  \"sourceWay\": \"true\",\n" +
                "  \"sourceType\": \"30\",\n" +
                "  \"sourceOpenID\": \"obgsTw8k9LvxiQqYbIxgGlNwfyV0\",\n" +
                "  \"mpID\": \"lNxAHFA1BX7e10af\",\n" +
                "  \"giftDetailList\": [\n" +
                "    {\n" +
                "      \"giftItemID\": 1030036607121805312,\n" +
                "      \"wechatGiftCode\": \"459391622799\",\n" +
                "      \"wechatGiftCardID\": \"6746832936013663591\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"appID\": \"wx10809aa4e842a143\"\n" +
                "}";
        CrmGiftData.CrmGiftRequest request = giftRequest(json);
        crmGiftServiceFutureStub.getGiftFromHll(request).get();
    }


    public CrmGiftData.CrmGiftRequest giftRequest(String ss) throws Exception {
        client = new GetGrpcClient().getClient();
        crmGiftServiceFutureStub = (CrmGiftServiceGrpc.CrmGiftServiceFutureStub) client.getFutureStub(CrmGiftServiceGrpc.class);
        Class cla = CrmGiftData.CrmGiftRequest.class;
        Object obj = setFIieds(ss, cla);
        CrmGiftData.CrmGiftRequest request= (CrmGiftData.CrmGiftRequest)obj;
//        System.out.println(request.toString());
        return request;
    }

    public CardInfoData.CardInfoReq cardInfo(String ss) throws Exception {
        futureStub = (CardInfoServiceGrpc.CardInfoServiceFutureStub)client.getFutureStub(CardInfoServiceGrpc.class);
        Class cla = CardInfoData.CardInfoReq.class;
        Object obj =setFIieds(ss, cla);
        CardInfoData.CardInfoReq infoReq = (CardInfoData.CardInfoReq)obj ;
        System.out.println(infoReq.toString());
        return infoReq;
    }

}

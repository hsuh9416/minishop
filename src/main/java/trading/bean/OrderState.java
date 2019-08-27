package trading.bean;

public enum OrderState {
 ORDERPLACED,
 PAYMENTDONE,
 PROCESSED,
 INDELIVERY,
 REFUNDPROCESSING,
 DELIVERED,
 REFUNDED,
 ORDERCOMPLETED,
 ORDERCANCELED
}
//주문완료:0,입금완료:1,배송대기중:2,배송중:3,환불진행중:4,배송완료:5,환불완료:6,수취완료:7,주문취소:8

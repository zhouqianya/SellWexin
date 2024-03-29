<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#include  "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <#--订单master-->
                <div class="col-md-6 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>订单总金额</th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.orderId}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <#--订单列表-->
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>商品名称</th>
                            <th>价格</th>
                            <th>数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTO.orderDetailList as orderDetail>
                            <tr>
                                <td>${orderDetail.productId}</td>
                                <td>${orderDetail.productName}</td>
                                <td>${orderDetail.productPrice}</td>
                                <td>${orderDetail.productQuantity}</td>
                                <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>

                    <#--操作-->
                    <div class="col-md-12 column">
                        <#if orderDTO.getOrderStatusEnum().msg == "新订单">
                            <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button"
                               class="btn btn-default btn-primary">完结订单</a>
                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button"
                               class="btn btn-default btn-danger">取消订单</a>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
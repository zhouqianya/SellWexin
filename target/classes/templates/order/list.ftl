<html>
<#include "../common/header.ftl">
<body>


<div id="wrapper" class="toggled">
    <#include  "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTOPage.content as orderDTO>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.buyerName}</td>
                                <td>${orderDTO.buyerPhone}</td>
                                <td>${orderDTO.buyerAddress}</td>
                                <td>${orderDTO.orderAmount}</td>
                                <td>${orderDTO.getOrderStatusEnum().msg}</td>
                                <td>${orderDTO.getPayStatusEnum().msg}</td>
                                <#--${(orderDTO.createTime?string('yyyy-MM-dd HH:mm:ss'))}-->
                                <td>${(orderDTO.createTime)}</td>
                                <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                <td>
                                    <#if orderDTO.getOrderStatusEnum().msg!="已取消">
                                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a></td>
                                </#if>
                            </tr>
                        </#list>
                        </tbody>
                    </table>

                    <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">

                            <#--currentPage < 1-->
                            <#if currentPage lte 1 >
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else >
                                <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                            </#if>


                            <#--0.. 0到几  1到几  打印出来-->
                            <#list 1..orderDTOPage.getTotalPages() as index >
                                <#if currentPage==index>
                                <#--如果等于当前页，置灰不可以点击-->
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else >
                                    <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>

                            </#list>

                            <#--currentPage >= orderDTOPage.getTotalPages()  -->
                            <#if currentPage gte orderDTOPage.getTotalPages() >
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else >
                                <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

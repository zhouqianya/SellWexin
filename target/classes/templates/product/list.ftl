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
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>介绍</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list productInfo.content as productInfo>
                            <tr>
                                <td>${productInfo.productId}</td>
                                <td>${productInfo.productName}</td>
                                <td><img src="${productInfo.productIcon}" width="50px" height="50px" alt=""></td>
                                <td>${productInfo.productPrice}</td>
                                <td>${productInfo.productStock}</td>
                                <td>${productInfo.productDescription}</td>
                                <td>${productInfo.getProductStatusEnum().msg}</td>
                                <td>${productInfo.createTime}</td>
                                <td>${productInfo.updateTime}</td>
                                <#--${(orderDTO.createTime?string('yyyy-MM-dd HH:mm:ss'))}-->
                                <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a></td>

                                <#if productInfo.getProductStatusEnum().msg=='上架' >
                                    <td><a href="/sell/seller/product/offsale?productId=${productInfo.productId}">下架</a></td>
                                <#else >
                                    <td><a href="/sell/seller/product/onsale?productId=${productInfo.productId}">上架</a></td>
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
                            <#list 1..productInfo.getTotalPages() as index >
                                <#if currentPage==index>
                                <#--如果等于当前页，置灰不可以点击-->
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else >
                                    <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>

                            </#list>

                            <#--currentPage >= orderDTOPage.getTotalPages()  -->
                            <#if currentPage gte productInfo.getTotalPages() >
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

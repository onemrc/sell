<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--侧边栏sidebar-->
<#include "../common/nav.ftl">
<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label>名称</label><input class="form-control" type="text" name="productName"
                                                    value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label><input class="form-control" type="text" name="productPrice"
                                                    value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label><input class="form-control" type="number" name="productStock"
                                                    value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label><input class="form-control" type="text" name="productDescription"
                                                    value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img src="${(productInfo.productIcon)!''}" alt="" height="100">
                            <input class="form-control" type="text" name="productIcon"
                                   value="${(productInfo.productIcon)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                            <#list productCategoryList as category>
                                <option value="${category.categoryType}"
                                    <#if (productInfo.categoryType)?? && productInfo.categoryType==category.categoryType>
                                        selected</#if>
                                >

                                ${category.categoryName}
                                </option>
                            </#list>
                            </select>
                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button class="btn btn-default" type="submit">提交</button>
                    </form>
                </div>


            </div>
        </div>
    </div>
</div>

</body>
</html>
<#--<#list orderDTOPage.content as orderDTO>-->
<#--${orderDTO.orderId}<br>-->
<#--</#list>-->
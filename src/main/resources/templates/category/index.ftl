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
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>名称</label><input class="form-control" type="text" name="categoryName" value="${(productCategory.categoryName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>type</label><input class="form-control" type="text" name="categoryType" value="${(productCategory.categoryType)!''}"/>
                        </div>
                        <input hidden type="text" name="categoryId" value="${(productCategory.categoryId)!''}">
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
<html>
<head>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <button class="close" aria-hidden="true" type="button" data-dismiss="alert">×</button>
                <h4>
                    成功!
                </h4> <strong>${msg!""}</strong><a class="alert-link" href="${url}">3s 后自动跳转</a>
            </div>
        </div>
    </div>
</div>
<script>
    setTimeout('location.href="${url}"', 3000);
</script>
</body>
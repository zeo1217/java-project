$(function(){
    utils.ajaxCallback({
        url:"http://localhost/shop-sso/sso/isLogin",
        success:function(data){
            if(data.status=='success'){
                $("#showUser").html("欢迎【"+data.data.username+"】登录，<a href='javascript:void(0)' onclick='logout()'>点击注销</a>");
            }else{
                $("#showUser").html("你还没有登录，请先<a onclick='toLogin()' href='javascript:void(0)'>登录</a>");
            }
        }
    });
})


function logout(){
    localStorage.removeItem("token");
    location.reload();
}

function  toLogin() {

    // 1.获取当前的url
    var url = location.href;

    // 2.跳转到登录页面
    location.href="http://localhost/shop-sso/toLoginPage?returnUrl="+url;
}



function initMenuZtree(title,url,w,h){

    if (title == null || title == '') {
        title=false;
    };
    if (url == null || url == '') {
        url="404.html";
    };
    if (w == null || w == '') {
        w=800;
    };
    if (h == null || h == '') {
        h=($(window).height() - 50);
    };


    layer.open({
        type: 2,
        area: [w+'px', h +'px'],
        fix: false, //不固定
        maxmin: true,
        shade:0.4,
        title: title,
        content: url,
        end:function(){
            // 1.从session中获取
            var pid = $.session.get("p_id");
            var pname = $.session.get("p_name");

            // 2.写到表单中
            $("#per_pid").val(pid);
            $("#per_pname").val(pname);
        }
    });
}
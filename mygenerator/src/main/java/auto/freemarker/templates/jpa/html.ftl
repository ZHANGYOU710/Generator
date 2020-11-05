<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <div th:replace="common/header::head"></div>
</head>
<body class="layui-layout-body">
<div class="layui-layout-admin">
    <div th:replace="common/header::headerhtml"></div>
    <div th:replace="common/menu::menuhtml"></div>
    <div class="layui-body site-content">
        <div class="site-text">
            <div class="layui-btn-container">
                <button type="button" class="layui-btn" data-method="add">增加</button>
                <button type="button" class="layui-btn" data-method="deit">编辑</button>
                <button type="button" class="layui-btn" data-method="del">删除</button>
            </div>
            <div class="layui-layout-right">
                <div class="layui-inline">
                    <input class="layui-input" name="id" id="demoReload" placeholder="搜索ID" autocomplete="off">
                </div>
                <button class="layui-btn" data-method="reload">搜索</button>
            </div>
            <table class="layui-hide" id="table" lay-filter="table"></table>
        </div>
    </div>

    <div th:replace="common/footer::footer"></div>
</div>
<script th:replace="common/footer::script"></script>
<script>
    layui.use(['table', 'form', 'util'], function () {
        var table = layui.table,
            util = layui.util,
            $ = layui.jquery,
            form = layui.form;
        table.render({
            elem: '#table'
            , id: "dataTable"
            , url: '../${Controller}/pageList'
            , method: 'post'
            , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , cols: [
                [
                    {type: 'checkbox', fixed: 'left'}
                    <#list newMember as m>
                    ,{field: '<#if underline==true>${m.humpConlumName? uncap_first}<#else>${m.conlumName? uncap_first}</#if>',title:'${m.commentName}'}
                    </#list>
                ]
            ]
            , page: true
            , response: {
                statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.status, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.total, //解析数据长度
                    "data": res.data //解析数据列表
                };
            }
        });

        //重新加载列表数据
        function reload(param){
            let where=$.extend({},param||{});
            let page={
                page: {
                    curr: 1 //重新从第 1 页开始
                }, where: where
            };
            //执行重载
            table.reload('dataTable',page,'data');
        }

        var active = {
            reload: function () {
                reload({<#list newMember as m><#if m.key == true><#if underline==true>${m.humpConlumName? uncap_first}<#else>${m.conlumName? uncap_first}</#if></#if></#list>: $("#demoReload").val()});
            }
            , add: function () {
                //重置表单
                $('#formDom')[0].reset();
                layer.open({
                    type: 1
                    , title: "添加用户"
                    , area: '600px'
                    , shade: 0.2
                    , id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    , btn: ['提交', '取消']
                    , btnAlign: 'c'
                    , moveType: 1 //拖拽模式，0或者1
                    , content: $("#formDom") /** 注意：DOM一定要放在body之外的地方，否则你会崩溃！！！！！**/
                    , success: function (layero) {
                        //获取新窗口对
                        //form.render();
                    }
                    , yes: function () {
                        form.verify();
                        var data = form.val('example');
                        console.log(data);
                        $.ajax({
                            url: '../${Controller}/save',
                            type: 'post',
                            dataType: 'json',
                            data: data,
                            success: function (result) {
                                if (result.status != 200) {
                                    layer.alert(result.msg);
                                }
                                layer.closeAll();
                                layer.alert("保存成功");
                            }
                        });
                    }
                });
            },
            deit: function () {
                layer.open({
                    type: 1
                    , title: "添加用户"
                    , area: '600px'
                    , shade: 0.2
                    , id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    , btn: ['提交', '取消']
                    , btnAlign: 'c'
                    , moveType: 1 //拖拽模式，0或者1
                    , content: $("#formDom")
                    , success: function (layero, index) {
                        //获取新窗口对
                        form.render();
                        var checkStatus = table.checkStatus('dataTable');
                        var datas = checkStatus.data;
                        if (datas.length !== 1) {
                            layer.close(index);
                            layer.alert("请选择一条数据");
                            return;
                        }
                        var data = datas[0];
                        //获取数据
                        $.ajax({
                            url: '../${Controller}/getById',
                            type: 'post',
                            dataType: 'json',
                            data: {id: data.<#list newMember as m><#if m.key == true><#if underline==true>${m.humpConlumName? uncap_first}<#else>${m.conlumName? uncap_first}</#if></#if></#list>},
                            success: function (result) {
                                if (result.status !== 200) {
                                    layer.close(index);
                                    layer.alert(result.msg);
                                    return;
                                }
                                form.val('example', result.data);
                            }
                        })
                    }
                    , yes: function () {

                    }
                });
            },
            del: function () {
                var checkStatus = table.checkStatus('dataTable');
                var datas = checkStatus.data;
                if (datas.length !== 1) {
                    layer.alert("请选择一条数据");
                    return;
                }
                var data = datas[0];
                //获取数据
                $.ajax({
                    url: '../${Controller}/del',
                    type: 'post',
                    dataType: 'json',
                    data: {id: data.userId},
                    success: function (result) {
                        if (result.status !== 200) {
                            layer.close(index);
                            layer.alert(result.msg);
                            result;
                        }
                        form.val('example', result.data);
                    }
                })
            }
        };
        $('.layui-body .layui-btn').on('click', function () {
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });
    });
</script>
</body>
<form class="layui-form" action="" lay-filter="example" id="formDom" style="display: none">
    <#list newMember as m>
        <#if m.key==true>
    <div class="layui-form-item layui-margin-top">
        <#else>
    <div class="layui-form-item">
        </#if>
        <label class="layui-form-label">${m.commentName}</label>
        <div class="layui-input-block">
            <input type="text" name="<#if underline==true>${m.humpConlumName? uncap_first}<#else>${m.conlumName? uncap_first}</#if>" lay-verify="required" lay-reqtext="不能为空！" placeholder="请输入"
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    </#list>
</form>
</html>
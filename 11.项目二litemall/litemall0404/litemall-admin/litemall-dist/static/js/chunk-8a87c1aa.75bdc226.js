(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-8a87c1aa"],{"153d":function(l,t,a){"use strict";a.r(t);var e=function(){var l=this,t=l.$createElement,a=l._self._c||t;return a("div",{staticClass:"app-container"},[a("el-form",{ref:"dataForm",attrs:{rules:l.rules,model:l.dataForm,"status-icon":"","label-width":"300px"}},[a("el-form-item",{attrs:{label:"商场名称",prop:"litemall_mall_name"}},[a("el-input",{model:{value:l.dataForm.litemall_mall_name,callback:function(t){l.$set(l.dataForm,"litemall_mall_name",t)},expression:"dataForm.litemall_mall_name"}})],1),l._v(" "),a("el-form-item",{attrs:{label:"商场地址",prop:"litemall_mall_address"}},[a("el-input",{model:{value:l.dataForm.litemall_mall_address,callback:function(t){l.$set(l.dataForm,"litemall_mall_address",t)},expression:"dataForm.litemall_mall_address"}})],1),l._v(" "),a("el-form-item",{attrs:{label:"地理坐标"}},[a("el-col",{attrs:{span:11}},[a("el-input",{attrs:{placeholder:"经度"},model:{value:l.dataForm.litemall_mall_longitude,callback:function(t){l.$set(l.dataForm,"litemall_mall_longitude",t)},expression:"dataForm.litemall_mall_longitude"}})],1),l._v(" "),a("el-col",{staticStyle:{"text-align":"center"},attrs:{span:2}},[l._v("-")]),l._v(" "),a("el-col",{attrs:{span:11}},[a("el-input",{attrs:{placeholder:"纬度"},model:{value:l.dataForm.litemall_mall_latitude,callback:function(t){l.$set(l.dataForm,"litemall_mall_latitude",t)},expression:"dataForm.litemall_mall_latitude"}})],1)],1),l._v(" "),a("el-form-item",{attrs:{label:"联系电话",prop:"litemall_mall_phone"}},[a("el-input",{model:{value:l.dataForm.litemall_mall_phone,callback:function(t){l.$set(l.dataForm,"litemall_mall_phone",t)},expression:"dataForm.litemall_mall_phone"}})],1),l._v(" "),a("el-form-item",{attrs:{label:"联系QQ",prop:"litemall_mall_qq"}},[a("el-input",{model:{value:l.dataForm.litemall_mall_qq,callback:function(t){l.$set(l.dataForm,"litemall_mall_qq",t)},expression:"dataForm.litemall_mall_qq"}})],1),l._v(" "),a("el-form-item",[a("el-button",{on:{click:l.cancel}},[l._v("取消")]),l._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:l.update}},[l._v("确定")])],1)],1)],1)},r=[],n=a("da71"),i={name:"ConfigMail",data:function(){return{dataForm:{litemall_mall_name:"",litemall_mall_address:"",litemall_mall_phone:"",litemall_mall_qq:"",litemall_mall_longitude:"",litemall_mall_latitude:""},rules:{litemall_mall_name:[{required:!0,message:"不能为空",trigger:"blur"}],litemall_mall_address:[{required:!0,message:"不能为空",trigger:"blur"}],litemall_mall_phone:[{required:!0,message:"不能为空",trigger:"blur"}],litemall_mall_qq:[{required:!0,message:"不能为空",trigger:"blur"}]}}},created:function(){this.init()},methods:{init:function(){var l=this;Object(n["b"])().then((function(t){l.dataForm=t.data.data}))},cancel:function(){this.init()},update:function(){var l=this;this.$refs["dataForm"].validate((function(t){if(!t)return!1;l.doUpdate()}))},doUpdate:function(){var l=this;Object(n["f"])(this.dataForm).then((function(t){l.$notify.success({title:"成功",message:"商场配置成功"})})).catch((function(t){l.$notify.error({title:"失败",message:t.data.errmsg})}))}}},o=i,m=a("cba8"),u=Object(m["a"])(o,e,r,!1,null,null,null);t["default"]=u.exports},da71:function(l,t,a){"use strict";a.d(t,"b",(function(){return r})),a.d(t,"f",(function(){return n})),a.d(t,"a",(function(){return i})),a.d(t,"e",(function(){return o})),a.d(t,"c",(function(){return m})),a.d(t,"g",(function(){return u})),a.d(t,"d",(function(){return d})),a.d(t,"h",(function(){return c}));var e=a("b775");function r(){return Object(e["a"])({url:"/config/mall",method:"get"})}function n(l){return Object(e["a"])({url:"/config/mall",method:"post",data:l})}function i(){return Object(e["a"])({url:"/config/express",method:"get"})}function o(l){return Object(e["a"])({url:"/config/express",method:"post",data:l})}function m(){return Object(e["a"])({url:"/config/order",method:"get"})}function u(l){return Object(e["a"])({url:"/config/order",method:"post",data:l})}function d(){return Object(e["a"])({url:"/config/wx",method:"get"})}function c(l){return Object(e["a"])({url:"/config/wx",method:"post",data:l})}}}]);
(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["page"],{"0ce0":function(t,e,s){},"15c5e":function(t,e,s){"use strict";s.r(e);var n=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"lock-container pull-height"},[s("div",{staticClass:"lock-form animated bounceInDown"},[s("div",{staticClass:"animated",class:{shake:t.passwdError,bounceOut:t.pass}},[s("h3",{staticClass:"title"},[t._v(t._s(t.userInfo.username))]),s("el-input",{staticClass:"input-with-select animated",attrs:{placeholder:"请输入登录密码",type:"password"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleLogin(e)}},model:{value:t.passwd,callback:function(e){t.passwd=e},expression:"passwd"}},[s("el-button",{attrs:{slot:"append",icon:"icon-bofangqi-suoping"},on:{click:t.handleLogin},slot:"append"}),s("el-button",{attrs:{slot:"append",icon:"icon-tuichu"},on:{click:t.handleLogout},slot:"append"})],1)],1)])])},a=[],r=s("cebc"),o=s("5880"),i={name:"lock",data:function(){return{passwd:"",passwdError:!1,pass:!1}},created:function(){},mounted:function(){},computed:Object(r["a"])({},Object(o["mapState"])({userInfo:function(t){return t.user.userInfo}}),Object(o["mapGetters"])(["tag","lockPasswd"])),props:[],methods:{handleLogout:function(){var t=this;this.$confirm("是否退出系统, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$store.dispatch("LogOut").then(function(){t.$router.push({path:"/login"})})})},handleLogin:function(){var t=this;if(this.passwd!=this.lockPasswd)return this.passwd="",this.$message({message:"解锁密码错误,请重新输入",type:"error"}),this.passwdError=!0,void setTimeout(function(){t.passwdError=!1},1e3);this.pass=!0,setTimeout(function(){t.$store.commit("CLEAR_LOCK"),t.$router.push({path:t.$router.$avueRouter.getPath({src:t.tag.value})})},1e3)}},components:{}},c=i,l=(s("70dc"),s("2877")),u=Object(l["a"])(c,n,a,!1,null,null,null);e["default"]=u.exports},"30eb":function(t,e,s){"use strict";var n=s("c6f3"),a=s.n(n);a.a},5596:function(t,e,s){"use strict";s.r(e);var n=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"login-container"},[s("div",{staticClass:"login-weaper animated bounceInDown"},[t._m(0),s("p",{staticClass:"login-tip"},[t._v("Kong-网关服务管理平台 v1.2.1")]),s("div",{staticClass:"login-border"},[s("div",{staticClass:"login-main"},["user"===t.activeName?s("userLogin"):t._e()],1)]),s("div",{staticClass:"login-copyright"},[t._v("Copyright © 2020 raoxiaoyan. All rights reserved.")])])])},a=[function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("img",{staticClass:"img",attrs:{src:"/svg/logo.png",alt:""}})])}],r=s("cebc"),o=(s("d4d0"),s("7c05")),i=s("44b2"),c=s("37cf"),l=s("5880"),u=s("c54a"),d={name:"login",mixins:[Object(c["a"])()],components:{topColor:i["default"],userLogin:o["default"]},data:function(){return{activeName:"user"}},watch:{$route:function(){var t=this.$route.query;if(this.socialForm.state=t.state,this.socialForm.code=t.code,!Object(u["c"])(this.socialForm.state)){var e=this.$loading({lock:!0,text:"".concat("WX"===this.socialForm.state?"微信":"QQ","登录中,请稍后。。。"),spinner:"el-icon-loading"});setTimeout(function(){e.close()},2e3)}}},created:function(){},mounted:function(){},computed:Object(r["a"])({},Object(l["mapGetters"])(["website"])),props:[],methods:{}},p=d,m=s("2877"),f=Object(m["a"])(p,n,a,!1,null,null,null);e["default"]=f.exports},"611b":function(t,e,s){"use strict";s.r(e);var n=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"error500"},[s("div",{staticClass:"error500-body-con"},[s("el-card",{staticClass:"box-card"},[s("div",{staticClass:"error500-body-con-title"},[t._v("\n        5\n        "),s("span",{staticClass:"error500-0-span"},[s("i",{staticClass:"icon-debug"})]),s("span",{staticClass:"error500-0-span"},[s("i",{staticClass:"icon-debug"})])]),s("p",{staticClass:"error500-body-con-message"},[t._v("Oops! the server is wrong")]),s("div",{staticClass:"error500-btn-con"},[s("el-button",{staticStyle:{width:"200px"},attrs:{size:"large",type:"text"},on:{click:t.goHome}},[t._v("返回首页")]),s("el-button",{staticStyle:{width:"200px","margin-left":"40px"},attrs:{size:"large",type:"primary"},on:{click:t.backPage}},[t._v("返回上一页")])],1)])],1)])},a=[],r={name:"Error500",methods:{backPage:function(){this.$router.go(-1)},goHome:function(){this.$router.push({path:"/"})}}},o=r,i=(s("f2a4"),s("2877")),c=Object(i["a"])(o,n,a,!1,null,"606040a7",null);e["default"]=c.exports},"70dc":function(t,e,s){"use strict";var n=s("758e"),a=s.n(n);a.a},"758e":function(t,e,s){},"7c05":function(t,e,s){"use strict";s.r(e);var n=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("el-form",{ref:"loginForm",staticClass:"login-form",attrs:{"status-icon":"",rules:t.loginRules,model:t.loginForm,"label-width":"0"}},[s("el-form-item",{attrs:{prop:"username"}},[s("el-input",{ref:"userName",attrs:{size:"small","auto-complete":"off",placeholder:"请输入用户名"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleLogin(e)}},model:{value:t.loginForm.username,callback:function(e){t.$set(t.loginForm,"username",e)},expression:"loginForm.username"}},[s("i",{staticClass:"icon-yonghu",attrs:{slot:"prefix"},slot:"prefix"})])],1),s("el-form-item",{attrs:{prop:"password"}},[s("el-input",{attrs:{size:"small",type:t.passwordType,"auto-complete":"off",placeholder:"请输入密码"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleLogin(e)}},model:{value:t.loginForm.password,callback:function(e){t.$set(t.loginForm,"password",e)},expression:"loginForm.password"}},[s("i",{staticClass:"el-icon-view el-input__icon",attrs:{slot:"suffix"},on:{click:t.showPassword},slot:"suffix"}),s("i",{staticClass:"icon-mima",attrs:{slot:"prefix"},slot:"prefix"})])],1),t.codeDisplay?s("el-form-item",{attrs:{prop:"code"}},[s("el-row",{attrs:{span:24}},[s("el-col",{attrs:{span:16}},[s("el-input",{attrs:{size:"small",maxlength:t.code.len,"auto-complete":"off",placeholder:"请输入验证码"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleLogin(e)}},model:{value:t.loginForm.code,callback:function(e){t.$set(t.loginForm,"code",e)},expression:"loginForm.code"}},[s("i",{staticClass:"icon-yanzhengma",attrs:{slot:"prefix"},slot:"prefix"})])],1),s("el-col",{attrs:{span:8}},[s("div",{staticClass:"login-code"},[s("el-button",{attrs:{disabled:t.btnDisabled},on:{click:t.refreshCode}},[t._v(t._s(t.btnName))]),s("i",{staticClass:"icon-shuaxin login-code-icon",on:{click:t.refreshCode}})],1)])],1)],1):t._e(),s("el-form-item",[s("el-button",{staticClass:"login-submit",attrs:{type:"primary",size:"small"},nativeOn:{click:function(e){return e.preventDefault(),t.handleLogin(e)}}},[t._v("登录")])],1)],1)},a=[],r=s("cebc"),o=(s("0e0b"),s("a161")),i=s("5880"),c={name:"userlogin",data:function(){return{loginForm:{username:"admin",password:"123456",code:"",redomStr:""},codeDisplay:!1,isFirst:!0,times:60,btnDisabled:!1,btnName:"获取验证码",checked:!1,code:{src:"",value:"",len:6,type:"text"},loginRules:{username:[{required:!0,message:"请输入用户名",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"},{min:6,message:"密码长度最少为6位",trigger:"blur"}],code:[{required:!0,message:"请输入验证码",trigger:"blur"},{min:6,max:6,message:"验证码长度为6位",trigger:"blur"},{required:!0,trigger:"blur"}]},passwordType:"password"}},created:function(){},mounted:function(){this.$nextTick(function(){this.$refs.userName.focus()})},computed:Object(r["a"])({},Object(i["mapGetters"])(["tagWel"])),props:[],methods:{refreshCode:function(){var t=this;Object(o["c"])(this.loginForm).then(function(e){var s=e.data,n=s.status;if(t.times=60,t.btnDisabled=!1,"1"==n)t.$message({showClose:!0,message:s.errmsg,type:"error"});else{var a=window.setInterval(function(){t.times--,t.times<0?(t.btnName="获取验证码",t.btnDisabled=!1,window.clearInterval(a)):(t.btnName=t.times+"秒后重新获取",t.btnDisabled=!0)},1e3);t.$message({showClose:!0,message:s.errmsg,type:"success"})}})},showPassword:function(){""==this.passwordType?this.passwordType="password":this.passwordType=""},handleLogin:function(){var t=this;this.$refs.loginForm.validate(function(e){e&&t.$store.dispatch("LoginByUsernameDis",t.loginForm).then(function(){t.$router.push({path:t.tagWel.value})},function(e){t.$message({showClose:!0,message:e.errmsg,type:"error"})})})}}},l=c,u=s("2877"),d=Object(u["a"])(l,n,a,!1,null,null,null);e["default"]=d.exports},8033:function(t,e,s){"use strict";var n=s("882e"),a=s.n(n);a.a},"882e":function(t,e,s){},"9d31":function(t,e,s){},a5fa:function(t,e,s){"use strict";s.r(e);var n=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"error404"},[s("div",{staticClass:"error404-body-con"},[s("el-card",{staticClass:"box-card"},[s("div",{staticClass:"error404-body-con-title"},[t._v("4\n        "),s("span",[t._v("0")]),t._v("4")]),s("p",{staticClass:"error404-body-con-message"},[t._v("YOU  LOOK  LOST")]),s("div",{staticClass:"error404-btn-con"},[s("el-button",{staticStyle:{width:"200px"},attrs:{size:"large",type:"text"},on:{click:t.goHome}},[t._v("返回首页")]),s("el-button",{staticStyle:{width:"200px","margin-left":"40px"},attrs:{size:"large",type:"primary"},on:{click:t.backPage}},[t._v("返回上一页")])],1)])],1)])},a=[],r={name:"Error404",methods:{backPage:function(){this.$router.go(-1)},goHome:function(){this.$router.push({path:"/"})}}},o=r,i=(s("c894"),s("2877")),c=Object(i["a"])(o,n,a,!1,null,"5864062f",null);e["default"]=c.exports},a8d0:function(t,e,s){"use strict";s.r(e);var n=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"error403"},[s("div",{staticClass:"error403-body-con"},[s("el-card",{staticClass:"box-card"},[s("div",{staticClass:"error403-body-con-title"},[t._v("4\n        "),s("span",{staticClass:"error403-0-span"},[s("i",{staticClass:"icon-quanxian"})]),s("span",{staticClass:"error403-key-span"},[s("i",{staticClass:"icon-iconset0216"})])]),s("p",{staticClass:"error403-body-con-message"},[t._v("You don't have permission")]),s("div",{staticClass:"error403-btn-con"},[s("el-button",{staticStyle:{width:"200px"},attrs:{size:"large",type:"text"},on:{click:t.goHome}},[t._v("返回首页")]),s("el-button",{staticStyle:{width:"200px","margin-left":"40px"},attrs:{size:"large",type:"primary"},on:{click:t.backPage}},[t._v("返回上一页")])],1)])],1)])},a=[],r={name:"Error403",methods:{backPage:function(){this.$router.go(-1)},goHome:function(){this.$router.push({path:"/"})}}},o=r,i=(s("8033"),s("2877")),c=Object(i["a"])(o,n,a,!1,null,"4d643359",null);e["default"]=c.exports},c1a1:function(t,e,s){"use strict";s.r(e);var n=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("basic-container",[t.$route.query.src?s("iframe",{ref:"iframe",staticClass:"iframe",attrs:{src:t.$route.query.src}}):s("iframe",{ref:"iframe",staticClass:"iframe",attrs:{src:t.urlPath}})])],1)},a=[],r=(s("a481"),s("6b54"),s("cebc")),o=s("5880"),i=s("323e"),c=s.n(i),l=(s("a5d8"),{name:"AvueIframe",data:function(){return{urlPath:this.getUrlPath()}},created:function(){c.a.configure({showSpinner:!1})},mounted:function(){this.load(),this.resize()},props:["routerPath"],watch:{$route:function(){this.load()},routerPath:function(){this.urlPath=this.getUrlPath()}},components:Object(r["a"])({},Object(o["mapGetters"])(["screen"])),methods:{show:function(){c.a.start()},hide:function(){c.a.done()},resize:function(){var t=this;window.onresize=function(){t.iframeInit()}},load:function(){var t=this;this.show();var e=!0;-1==this.$route.query.src.indexOf("?")&&(e=!1);var s=[];for(var n in this.$route.query)"src"!=n&&"name"!=n&&s.push("".concat(n,"= this.$route.query[key]"));s=s.join("&").toString(),this.$route.query.src=e?"".concat(this.$route.query.src).concat(s.length>0?"&list":""):"".concat(this.$route.query.src).concat(s.length>0?"?list":"");var a=3,r=setInterval(function(){a--,0==a&&(t.hide(),clearInterval(r))},1e3);this.iframeInit()},iframeInit:function(){var t=this,e=this.$refs.iframe,s=document.documentElement.clientHeight-(screen>1?200:130);e.style.height="".concat(s,"px"),e.attachEvent?e.attachEvent("onload",function(){t.hide()}):e.onload=function(){t.hide()}},getUrlPath:function(){var t=window.location.href;return t=t.replace("/myiframe",""),t}}}),u=l,d=(s("30eb"),s("2877")),p=Object(d["a"])(u,n,a,!1,null,null,null);e["default"]=p.exports},c6f3:function(t,e,s){},c894:function(t,e,s){"use strict";var n=s("9d31"),a=s.n(n);a.a},d4d0:function(t,e,s){},f2a4:function(t,e,s){"use strict";var n=s("0ce0"),a=s.n(n);a.a}}]);
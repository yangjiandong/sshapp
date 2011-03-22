$.fn.pwdInputSwap=function(b){var e=$.extend({},$.fn.pwdInputSwap.defaults,b);
var c=$(this);
var a=$(e.inputTemp);
var d=$(e.inputPass);
a.removeClass("hidden");
d.addClass("hidden");
a.focus(function(){var f=$(this);
f.addClass("hidden");
d.removeClass("hidden").addClass("userInput").focus()
});
d.blur(function(){var f=$(this);
if(f.val().length==0){f.addClass("hidden");
a.removeClass("hidden")
}})
};
$.fn.pwdInputSwap.defaults={inputTemp:"#switchfoo",inputPass:"#passwordfoo"};
$.fn.labelToValue=function(c){var e=$.extend({},$.fn.labelToValue.defaults,c);
var a=$(this);
var b=a.prev("label").html();
a.attr("value",b).prev("label");
var d=function(){var f=$(this);
if(f.val()==b){f.addClass(e.focusClass).val("")
}};
a.unbind().bind(e.clearTextOn,d);
a.blur(function(){var f=$(this);
if(f.val().length==0){f.removeClass(e.focusClass).attr("value",b).prev("label")
}})
};
$.fn.labelToValue.defaults={focusClass:"userInput",clearTextOn:"focus"};
$.fn.pseudoHover=function(a){var b=$.extend({},$.fn.pseudoHover.defaults,a);
$(this).hover(function(){$(this).addClass(b.addClass)
},function(){$(this).removeClass(b.addClass)
})
};
$.fn.pseudoHover.defaults={addClass:"hover"};
$.fn.listHighLight=function(b){var d=$.extend({},$.fn.listHighLight.defaults,b);
var c=$(this);
var a=c.find(".controls");
c.hover(function(){$(this).find("ul.controls").css("visibility","visible").animate({opacity:1},d.fadeIn,function(){$(this).parents("li:eq(0)").addClass("hilight")
})
},function(){var e=0;
if($(this).find("ul.controls").hasClass("noFade")){e=1
}if($(this).find("div.reportThis").hasClass("open")){$(this).find("ul.nested").slideUp(50);
$(this).find("div.reportThis").removeClass("open")
}$(this).find("ul.controls").animate({opacity:e},d.fadeOut,function(){$(this).parents("li:eq(0)").removeClass("hilight")
})
})
};
$.fn.listHighLight.defaults={fadeIn:0,fadeOut:0};
$.fn.clickListener=function(a){var b=$.extend({},$.fn.clickListener.defaults,a);
var c=$(this);
c.click(function(x){var s=$(x.target);
var i=function(N,I,C){var K=N;
var e="/ajax/edit/comment";
var F={review:"",tags:"",title:"",keep_date:false,sticky_post:false,commentid:0,publicid:"",syndicate_tw:"",syndicate_fb:"",token:""};
if(K.find("textarea[name='review']").length){var M=K.find("textarea[name='review']").val()
}else{var M=K.find("textarea").val()
}F.review=M.replace(/^\s+|\s+$/g,"");
if(K.find("input[name='title']").length){F.title=K.find("input[name='title']").val()
}if(K.find("input[name='tags']").length){F.tags=K.find("input[name='tags']").val()
}if(K.find("input[name='keep_date']").length){F.keep_date=K.find("input[name='keep_date']").attr("checked")
}if(K.find("input[name='sticky_post']").length){F.sticky_post=K.find("input[name='sticky_post']").attr("checked")
}if(K.find("input[name='syndicate_tw']").attr("checked")){F.syndicate_tw=K.find("input[name='syndicate_tw']").val()
}if(K.find("input[name='syndicate_fb']").attr("checked")){F.syndicate_fb=K.find("input[name='syndicate_fb']").val()
}var D=s.parents("li:parent(ul.listStumble)");
if(D.find("var").length){F.commentid=D.find("var").attr("id");
F.publicid=D.find("var").attr("class")
}F.token=$("#wrapperContent").attr("class");
F.keep_date=+F.keep_date;
F.sticky_post=+F.sticky_post;
if(I){for(var H in I){F[H]=I[H]
}}var G=function(P){if(P.success==true){if(K.parents(".text").find(".review").length!=0){K.parents(".text").find(".review").html(P.review)
}else{var O='<p class="review">'+P.review+"</p>";
K.parents(".text").find(".showThumbUp").after(O)
}l()
}};
if(C&&!K.find("input[name='post_fb']").attr("checked")){var L=C
}else{var L=G
}if((F.review.length>0||F.commentid>0)||F.tags.length>0){$.post(e,F,L,"json")
}else{alert("Please write a review for this URL to help describe it to the StumbleUpon community.");
return false
}if(K.find("input[name='post_fb_setting']").val()!=K.find("input[name='post_fb']").attr("checked")){var J=K.find("input[name='post_fb']").attr("checked")?1:0;
$.ajax({type:"POST",url:"/ajax/publishsettings",data:{update_publish:J},dataType:"json",success:function(O){}})
}if(K.find("input[name='post_fb']").attr("checked")&&F.review.length>0){if(F.url){var E=F.url
}else{var E=null
}fb_dologin=false;
FB.init({appId:"8ab252785ffd2ebc69f34b48c78a931d",status:true,cookie:true,xfbml:true});
FB.login(function(O){if(O.session){$.ajax({type:"POST",url:"/ajax/checkfbmapping",data:{fb_userid:O.session.uid,update_mapping:true},dataType:"json",success:function(P){if(P.passed){$.ajax({type:"POST",url:"/ajax/syndicate_fb",data:{access_token:O.session.access_token,fb_uid:O.session.uid,review:F.review,publicid:F.publicid,url:E,extra_params:F},dataType:"json",success:function(Q){if(Q.go_to){window.location=Q.go_to
}else{if(C){window.location.reload()
}}}})
}else{SUJS.suWindow.show()
}}})
}else{alert("facebook post cancelled!")
}},{perms:"publish_stream"})
}};
function l(){var e=$(".listStumble .active");
e.each(function(){$(".open").slideUp(200,function(){setTimeout(function(){e.removeClass("active")
},300)
})
});
$("#inflator.open").slideUp(200).removeClass("open");
$("#addContent.open").slideUp(200).removeClass("open")
}if(s.is(".filter a.controlPanel")){}if(s.is(".controls a.edit")){var w=s.parents("li:parent(ul.listStumble)");
var A=w.children("var").attr("id");
if(A==""){A=0
}var o=w.children("var").attr("class");
var h=$("#wrapperContent").attr("class");
var r=w.find("div.editReview");
var j,p;
if(s.hasClass("active")){s.removeClass("active");
w.removeClass("active");
r.slideUp(200).removeClass("open").unbind()
}else{l();
s.addClass("active");
w.addClass("active");
r.slideDown(300).addClass("open");
j=r.find("textarea").val();
p=r.find("input[name='tags']").attr("value")
}r.unbind("click").click(function(E){var C=$(E.target);
if(C.is("a.cancel")){var D=C.parents("fieldset:eq(0)");
r.find("textarea").val(j);
r.find("input[name='tags']").attr("value",p);
l();
return false
}if(C.is("input[type='submit']")){var D=C.parents("fieldset:eq(0)");
var F=false;
if(D.find("input[name='newUrlReview']").length){F=function(e){window.location.reload()
}
}i(D,false,F)
}})
}if(s.is(".controls a.helpfulYes")||s.is(".controls a.helpfulNo")){var w=s.parents("li:eq(0)");
var f="/ajax/flag/comment";
var h=$("#wrapperContent").attr("class");
var y=w.children("var").attr("id");
var z={action:"helpful",helpful:0,commentid:y,token:h};
var m={yes:w.find("div a.helpfulYes"),no:w.find("div a.helpfulNo")};
if(s.is("a.helpfulYes")){z.helpful=1
}m.yes.css("font-weight","normal");
m.no.css("font-weight","normal");
if(z.helpful){m.yes.css("font-weight","bold")
}else{m.no.css("font-weight","bold")
}$.post(f,z,function(e){},"json")
}if(s.is(".controls a.blockUser")){var w=s.parents("li:eq(0)");
var k=w.children("var").attr("id");
var h=$("#wrapperContent").attr("class");
$.post("/ajax/block/stumbler",{stumbler:k,action:"block",token:h},function(C,e){if(e=="success"){window.location.reload()
}},"json")
}function u(E){var C=s.parents("li:parent(ul.listStumble)");
if(!window.viewPanes){window.viewPanes=[]
}if(!window.viewPanes.sharepanel){window.viewPanes.sharepanel=new SUJS.View.ResourceViewPane("sharepanel",true);
window.viewPanes.sharepanel.setDisplayStyle({position:"static",margintop:"-285px","z-index":"100",width:"100%",height:"285px"}).setCloseButtonStyle(null).setLoadingStyle("none")
}else{window.viewPanes.sharepanel.handle=null
}var D=C.find("var").attr("class");
var e="/share/"+D+"/?src=website&mode="+E;
window.viewPanes.sharepanel.setResource(SUJS.Model.InternalResource.initFromUrl(e));
window.viewPanes.sharepanel.show()
}if(s.is(".controls a.share")){u("toStumbler")
}if(s.is(".controls a.emailshare")){u("email")
}if(s.is(".shareUrl")||s.is(".controls a.markShareRead")){var w=s.parents("li:parent(ul.listStumble)");
if(w.is(".unseen")){w.removeClass("unseen");
x.preventDefault();
if(s.is(".shareUrl")){var v=w.attr("id")
}else{var v=s.attr("id")
}$.get("/viewshare.php",{referralid:v,ajax:1},function(e){if(s.is(".shareUrl")){document.location.href=s.attr("href")
}},"json")
}}if(s.is(".controls a.delete")){var w=s.parents("li:eq(1)");
var f="/ajax/delete/favorite";
var o=w.children("var").attr("class");
var h=$("#wrapperContent").attr("class");
var A=w.children("var").attr("id");
if(A==""){A=0
}var B=w.find(".deleteThis");
function q(){s.parent().removeClass("active");
B.animate({width:"hide"},0).removeClass("open")
}function g(){s.parent().addClass("active");
B.animate({width:"show"},200).addClass("open")
}s.closest("li.listLi").hover(function(){clearTimeout(t)
},function(){t=setTimeout(function(){q()
},10)
});
if(s.parent().hasClass("active")){$.post(f,{commentid:A,publicid:o,token:h},function(e){if(e.success==true){w.fadeOut(900,function(){$(this).remove()
})
}},"json")
}else{l();
g()
}}if(s.is(".headerControls a.addSite")){var n=$("#addContent");
n.click(function(I){var F=$(I.target);
if(F.is("a.cancel")){n.children("form").clearForm();
n.removeClass("open").slideUp(200);
n.unbind();
return false
}if(F.is(".blogMode")){var L=F.parents("fieldset:eq(0)");
var N=F.attr("checked");
if(N){L.find("label[for='url']").html("Title");
L.find("a.submit").html("Add a blog");
L.find("#syndicatePost").hide()
}else{L.find("label[for='url']").html("URL");
L.find("a.submit").html("Add a site");
L.find("#syndicatePost").show()
}}if(F.is("a.submit")){var L=F.parents("fieldset:eq(0)");
var J="";
var C=L.find("input[name='url']").val();
var H=L.find("input[name='blog_mode']").attr("checked");
var E=L.find("input[name='post_fb']").attr("checked");
var K=0;
var D=1;
if(H){J=C;
C="";
K=1
}var G={title:J,url:C,new_post:D,blog_post:K};
var M=function(e,O){if(e.go_to){window.location=e.go_to
}else{window.location="/favorites/"
}};
i(L,G,M)
}});
if(n.hasClass("open")){n.removeClass("open").slideUp(200);
n.unbind();
return false
}else{l();
n.slideDown(300).addClass("open");
return false
}}if(s.is("#stats .seeMore")){s.next("dl").slideToggle(250)
}if(s.is(".tags.seeMore")){s.next("dl").slideToggle(250)
}if(s.is(".userSubscribe")){var h=$("#wrapperContent").attr("class");
$.post("/ajax/user/subscribe",{act:"subscribe",id:s.attr("href").substr(1),fauth:h},function(){},"json");
s.fadeOut()
}if(s.is(".userSubscribeAndDS")){var h=$("#wrapperContent").attr("class");
var d;
if(s.hasClass("pro")){d="subscribe"
}else{d="subscribe_and_ds"
}if(s.html()=="Follow"){$.post("/ajax/user/subscribe",{act:d,id:s.attr("href").substr(1),fauth:h},function(){s.html("Following (undo)")
},"json")
}else{$.post("/ajax/user/subscribe",{act:"unsubscribe",id:s.attr("href").substr(1),fauth:h},function(){s.html("Follow")
},"json")
}}if(s.is(".userSuggestRemove")){var h=$("#wrapperContent").attr("class");
$.post("/ajax/user/subscribe",{act:"suggest_remove",id:s.attr("href").substr(1),fauth:h},function(){s.parents("li").fadeOut()
},"json")
}if(s.is(".searchlink")){s.hover(function(){s.find("ul").fadeIn(100)
},function(){s.find("ul").fadeOut(200)
})
}})
};
$.fn.clickListener.defaults={};
$.fn.swapThis=function(a){var b=$.extend({},$.fn.swapThis.defaults,a);
var c=$(this);
if(b.event=="click"){c.click(function(){$(b.swapThis).addClass("hidden");
$(b.forThat).removeClass("hidden");
return false
})
}};
$.fn.swapThis.defaults={swapThis:".this",forThat:".that",event:"click"};
$.fn.focusElement=function(a){var b=$.extend({},$.fn.focusElement.defaults,a);
var c=$(this);
if(b.event=="click"){c.click(function(){$(b.element).focus().select()
})
}};
$.fn.focusElement.defaults={element:false,event:"click"};
$.fn.clearForm=function(){return this.each(function(){var b=this.type,a=this.tagName.toLowerCase();
if(a=="form"){return $(":input",this).clearForm()
}if(b=="text"||b=="password"||a=="textarea"){this.value=""
}else{if(b=="checkbox"||b=="radio"){this.checked=false
}else{if(a=="select"){this.selectedIndex=-1
}}}})
};
$.fn.stumbleInfoPopup=function(a){var b="userid="+a.userid;
jQuery.ajax({type:"POST",url:"/ajax/user/info",data:b,error:function(){},success:function(){}})
};
$.fn.imageMaxWidth=function(a){var b=$.extend({},$.fn.imageMaxWidth.defaults,a);
$(this).each(function(){var h=$(this);
if(h.attr("width")>=b.width){h.removeAttr("height").removeAttr("width");
var g=h.width();
var c=h.height();
if(g>=b.width){var f=(c/g);
var d=b.width;
var e=(d*f);
h.height(e).width(b.width)
}}})
};
$.fn.imageMaxWidth.defaults={width:700};
$.fn.installAddon=function(a){var c=$.extend({},$.fn.installAddon.defaults,a);
var b=$(this);
if(c.user_agent=="ie"){b.attr("href",c.ie_download_url)
}else{if(c.user_agent=="firefox"){b.attr("href",c.ff_download_url);
b.click(function(){$("#fadeout",top.document).show();
$("#ff-install-helper",top.document).show()
});
$("#close-button",top.document).click(function(){$("#ff-install-helper",top.document).hide();
$("#fadeout",top.document).hide()
})
}else{if(c.user_agent=="chrome"){b.attr("href",c.chrome_download_url)
}else{b.hide()
}}}};
$.fn.installAddon.defaults={ff_download_url:"#",ie_download_url:"#",chrome_download_url:"#",user_agent:false};
$.fn.showHide=function(a){var b=$.extend({},$.fn.showHide.defaults,a);
$this=$(this);
$this.click(function(d){var c=$(d.target);
if(c.is(b.showHideSwitch)){if(c.hasClass("closed")){c.removeClass("closed").children(".showSwitch, .hideSwitch").toggle();
c.next(b.targ).show()
}else{c.addClass("closed").children(".showSwitch, .hideSwitch").toggle();
c.next(b.targ).hide()
}}})
};
$.fn.showHide.defaults={showHideSwitch:".showHideSwitch",targ:".showHideThis"};
$.fn.charCount=function(a){var c=$.extend({},$.fn.charCount.defaults,a);
function b(f){var d=$(f).val().length;
var e=c.allowed-d;
if(e<=c.warning&&e>=0){$(f).next().addClass(c.cssWarning)
}else{$(f).next().removeClass(c.cssWarning)
}if(e<0){$(f).next().addClass(c.cssExceeded)
}else{$(f).next().removeClass(c.cssExceeded)
}$(f).next().html(c.counterText+e)
}this.each(function(){$(this).after("<"+c.counterElement+' class="'+c.css+'">'+c.counterText+"</"+c.counterElement+">");
b(this);
$(this).keyup(function(){b(this)
});
$(this).change(function(){b(this)
})
})
};
$.fn.charCount.defaults={allowed:140,warning:25,css:"counter",counterElement:"span",cssWarning:"warning",cssExceeded:"exceeded",counterText:""};
$.fn.delegate=function(a,b){return this.bind(a,function(f){var d=$(f.target);
for(var c in b){if(d.is(c)){return b[c].apply(this,arguments)
}}})
};
$.fn.makeCols=function(o,n,p){var a=$.extend({},$.fn.makeCols.defaults,p);
var m=$(this),l=$(o),e=l.length,h=m.width(),k=l.eq(0).height(),g=l.eq(0).width(),d,j,c,b,f;
if(h/n<g){n=n-1
}d=Math.ceil(e/n);
j=a.liHeight*d;
m.children("li").removeClass("col1 col2 col3").hide();
for(f=1;
f<=(e);
f++){c=0;
if(e<=a.colMin){b=1
}else{b=Math.ceil(f/d);
if(f%d==0&&f>1){c=-1*j
}}l.eq(f-1).addClass("col"+b).css({height:a.liHeight+"px"}).show();
l.eq(f).css({"margin-top":c});
l.parent("ul").css("height",j)
}};
$.fn.makeCols.defaults={liHeight:22,topicsLimit:127,colMin:10};
$.fn.openWindow=function(d,c,a,e,f){var b=f?",scrollbars=1":"";
var g=window.open(d,c,"height="+a+",width="+e+b);
if(g&&!g.opener){g.opener=window
}if(window.focus){g.focus()
}return false
};
$.fn.suCenter=function(){var a=$(window);
this.css("position","absolute");
this.css("top",(a.height()-this.height()-40)/2+a.scrollTop()+"px");
this.css("left",(a.width()-this.width())/2+a.scrollLeft()+"px");
return this
};
$.fn.goAway=function(a){var b=$.extend({},$.goAway,a);
$this=$(this);
$this.animate({opacity:0},b.fadeTime,function(){$this.css({left:"-999em"})
})
};
$.fn.goAway.defaults={fadeTime:300};
$.fn.comeBack=function(a){var b=$.extend({},$.comeBack,a);
$this=$(this);
$this.animate({opacity:1},b.fadeTime,function(){if(b.reposition){$this.css({left:optLeft,top:b.top})
}})
};
$.fn.comeBack.defaults={fadeTime:300,reposition:false,left:0,top:0};
var SUJS={};
SUJS.extend=function(c,a){var b=function(){};
b.prototype=a.prototype;
c.prototype=new b();
c.prototype.constructor=c;
c.superclass=a.prototype;
if(a.prototype.constructor==Object.prototype.constructor){a.prototype.constructor=a
}};
SUJS.suWindow=function(){var b=false,d=$(window),c=$(document),a=function(e){return $('<div id="'+e+'"/>')
};
return{init:function(i){var h=$(i),f=h.width(),g=h.height(),e='<a href="javascript:void(0);" id="suBoxClose" style="display: block;">close</a>';
$overlay=a("suOverlay").css({position:"absolute",width:"100%",height:$(document).height(),top:0,left:0,opacity:0.7,background:"black no-repeat 50% 50%",zIndex:7000}).hide(),$contentWindow=a("suWindow").append(h.html()).css({position:"absolute",background:"#fff",zIndex:8000}).append(e).hide();
h.html("");
$("body").append($overlay,$contentWindow);
$("#suBoxClose").click(function(){SUJS.suWindow.hide()
})
},show:function(){if(b==true){SUJS.suWindow.hide()
}var e=$("#suWindow"),f=$("#suOverlay");
f.fadeIn(200,function(){e.suCenter().fadeIn(100,function(){});
$(window).resize(function(){f.height(c.height()).width(d.width());
e.suCenter()
})
});
b=true
},hide:function(){$("#suWindow").fadeOut(100,function(){$("#suOverlay").fadeOut(100)
});
b=false
}}
}();
SUJS.topicsList=function(){var h="#topicsList",b=$("#topicsList"),c,a=22,f=3;
function e(i,j){j=j+1;
i.addClass("selectedTopic");
return j
}function d(i,j){j=j-1;
i.removeClass("selectedTopic");
return j
}function g(){$interestsForm.animate({opacity:0},190,function(){$interestsForm.removeClass("mini");
b.makeCols("Popular_Topics",3);
$("#topicsSeeMore").fadeOut();
$("#filterList").fadeIn(200);
$("#Popular_Topics").click();
$(".moduleHomeStumblePromo").fadeOut(120,function(){$interestsForm.animate({opacity:1},300)
})
})
}this.Filter=function(){this.filters={age:"",gender:""};
this.setFilter=function(i,j){var j=j.substring(6).toLowerCase();
if(this.filters[i]==j){this.filters[i]=""
}else{this.filters[i]=j
}$("#age").val(this.filters.age);
$("#gender").val(this.filters.gender)
};
this.getFilter=function(){return this.filters.gender+this.filters.age
}
};
return{init:function(l,j,k,i){l=l;
b=$(l);
j=j;
k=k;
topic_filter=new Filter();
$interestsForm=$("#interestsForm");
if((typeof i!="undefined")&&i!=22){a=i
}b.makeCols(j,k,{liHeight:a})
},nav:function(p,o,n,k){var j=$(p),i=$("#topicsSeeMore"),m,l=$(k);
if(o.length){m=$(o);
m.parent("li:eq(0)").addClass("current")
}if(n!="expandOnClick"){$(k).show()
}j.click(function(w){var v=$(w.target),r=topic_filter.getFilter(),x=(v.attr("id")=="Popular_Topics"&&r)?r:v.attr("id"),z="#topicsList ."+x,s=$(z),u=s.width(),q=s.width(),y=Math.floor(u/q);
j.children("li").removeClass("current");
v.parent("li").addClass("current");
$("#topicsList > li").hide();
$(z).show();
b.makeCols(z,f,{liHeight:a})
});
l.click(function(u){var r=$(u.target),s=r.attr("id"),q=(r.hasClass("age"))?"age":"gender",v=!r.hasClass("active");
if(!(r.attr("id"))||!(r.attr("class"))){return
}$("."+q).removeClass("active");
if(v){$("#"+s).addClass("active")
}topic_filter.setFilter(q,s);
$("#Popular_Topics").click()
});
b.delegate("click",{input:function(w){var r=$(w.target),s=r.parents("li:eq(0)"),v=$("#selectedTopic"),y=v.children(".count"),u=parseInt(y.html());
var q=s.hasClass("selectedTopic")?d(s,u):e(s,u);
y.html(q);
if(n=="expandOnClick"&&$("#interestsForm").hasClass("mini")){g()
}$("#start_stumble_button").fadeIn(200)
}});
i.click(function(){g()
})
}}
}();
SUJS.Model={};
SUJS.View={};
SUJS.Controller={};
SUJS.Event=function(a){this._sender=a;
this._subscribers=[]
};
SUJS.Event.prototype={attach:function(b,c,a){this._subscribers.push({"function":b,args:c||{},scope:a||false})
},detach:function(b){for(var a=0;
a<this._subscribers.length;
a++){if(b==this._subscribers[a]["function"]){this._subscribers.splice(a,1)
}}},notify:function(b){for(var a=0;
a<this._subscribers.length;
a++){$.extend(b,this._subscribers[a]["args"]);
if(this._subscribers[a]["scope"]){this._subscribers[a]["function"].call(this._subscribers[a]["scope"],this._sender,b)
}else{this._subscribers[a]["function"](this._sender,b)
}}}};
SUJS.Model.TopicsList=function(a){this._topics=a;
this._category={category:"",filter:""};
this._displayMode={prev:"",current:""};
this._selectedTopics=false;
this._selectedTopicsCount=false;
this._suggestedTopics={};
this._adultLevelOption="G";
this.displayModeChanged=new SUJS.Event(this);
this.categoryChanged=new SUJS.Event(this);
this.selectedTopicsChanged=new SUJS.Event(this);
this.adultTopicSelected=new SUJS.Event(this);
this.showSelectedTopics=new SUJS.Event(this)
};
SUJS.Model.TopicsList.prototype.getAdultLevelOption=function(){if(this._adultLevelOption!="X"){var c="G";
for(var b=0,a=this._selectedTopicsCount;
b<a;
++b){c=this._selectedTopics[b].adult;
if(c>0&&this._adultLevelOption!="X"){this._adultLevelOption=(c==1)?"R":"X"
}}}return this._adultLevelOption
};
SUJS.Model.TopicsList.prototype.setAdultLevelOption=function(a,b){this._adultLevelOption=a;
if(typeof(b)=="object"){this.setSelectedTopics(this._topics.topics,b)
}};
SUJS.Model.TopicsList.prototype.getTopics=function(){return this._topics
};
SUJS.Model.TopicsList.prototype.setTopics=function(a){this._topics=a;
this.topicsChanged.notify()
};
SUJS.Model.TopicsList.prototype.getCategory=function(){return this._category
};
SUJS.Model.TopicsList.prototype.getAdultRating=function(c){var a=(typeof(c)=="object")?c.target.value:c;
var b=this._topics.topics[a].adult;
if(b>0){adultRatingString=(b==1)?"R":"X"
}else{adultRatingString="G"
}return adultRatingString
};
SUJS.Model.TopicsList.prototype.getTopicName=function(b){var a="";
if(typeof b!="undefined"&&b in this._topics.topics){a=this._topics.topics[b].name
}return a
};
SUJS.Model.TopicsList.prototype.setCategory=function(b,a){this._category.category=b;
if(a!=undefined){this._category.filter=a
}this.categoryChanged.notify()
};
SUJS.Model.TopicsList.prototype.getSelectedTopics=function(){return this._selectedTopics
};
SUJS.Model.TopicsList.prototype.getSelectedTopicsCount=function(){return this._selectedTopicsCount
};
SUJS.Model.TopicsList.prototype.setSelectedTopics=function(c,b){var g=[];
var f=this;
var d="G";
if(typeof b!="undefined"){d=f.getAdultRating(b)
}if(d=="G"||this.getAdultLevelOption()==d||this._adultLevelOption=="X"){for(var a in c){if(c[a].selected){g.push(c[a])
}}this._selectedTopics=g;
this._selectedTopicsCount=g.length;
this.selectedTopicsChanged.notify(b)
}else{if(typeof b!="undefined"&&$(b.target).attr("checked")){this.adultTopicSelected.notify(b)
}}};
SUJS.Model.TopicsList.prototype.getDisplayMode=function(){return this._displayMode
};
SUJS.Model.TopicsList.prototype.setDisplayMode=function(a){this._displayMode.prev=this._displayMode.current;
this._displayMode.current=a;
this.displayModeChanged.notify()
};
SUJS.View.WebTbTopicsList=function(b,a,c){this._model=b;
this._controller=a;
this._elements=c;
this._topics=this._model.getTopics();
var d=this;
this._model.categoryChanged.attach(function(){d.buildTopicsList()
})
};
SUJS.View.WebTbTopicsList.prototype.init=function(){this._controller.selectTopic(this._topics)
};
SUJS.View.WebTbTopicsList.prototype.buildTopicsList=function(){var j=this._elements.topicsList;
var h=this._topics.topics;
var k=this._model.getCategory();
var e="<table><tr><td>";
var q="</td></tr></table>";
var r="</td><td>";
var l=9;
if(k.category=="video_channels"){l=8
}function d(B,w){var x=B;
var z=m.length;
var A=Math.ceil(z/l);
var v=Math.ceil(z/A);
var u=v+1;
for(var y=1;
y<A;
y++){x.splice(v,0,w);
v=v+u
}return x
}var m=[];
var a=[];
j.html("");
if(k.category=="your_topics"){m=h.ordering.personal
}else{if(k.category=="Popular_Topics"){m=h.ordering.popular
}else{if(k.category=="stumblethru_channel"){m=h.ordering.stumblethru
}else{if(k.category=="video_channels"){m=h.ordering.videochannels
}else{m=h.ordering.alltopics
}}}}for(var n=0,g=m.length;
n<g;
n++){var p={};
if(k.category=="stumblethru_channel"){p=h.stumblethru[m[n]];
p.id=m[n];
p.className="stumbleThru";
a.push(this.buildThruList(p,p.tb_desc))
}else{if(k.category=="video_channels"){p=h.videochannels[m[n]];
p.id=h.videochannels[m[n]].id;
p.className="videoTopic";
p.title=m[n];
a.push(this.buildThruList(p,p.name))
}else{p=h.alltopics[m[n]];
p.id=m[n];
a.push(this.buildList(p))
}}}a=d(a,r);
if(k.category=="your_topics"){var b=[];
b.push('<fieldset id="keywordFieldset"><span>beta</span><label for="keyword">Explore a new interest</label><input id="keyword" name="keyword" /><input type="image" alt="Stumble by Keyword" id="stumbleByKeywordSubmit" src="http://cdn.stumble-upon.com/i/btn/btnExploreInterestsToolbar.png" /></fieldset>');
b.push('<a class="channel" id="channelAll" href="#">All Interests</a>');
b.push('<a class="channel" id="channelFollowing" href="#">Friends</a>');
b.push('<a class="channel" id="channelPhotos" href="#">Photos</a>');
b.push('<a class="channel" id="channelVideo" href="#">Videos</a>');
b.push('<a class="channel" id="channelNews" href="#">News</a>');
b.push("</td><td>");
a.unshift(b.join(""));
if(personalized){a.push('<a class="edit" id="editTopics" href="/settings/interests/" target="_top">Edit Interests</a>')
}}else{if(k.category=="video_channels"){m=h.ordering.videoproviders;
var b=[];
var s=1;
b.push('<a href="#" class="channel" id="channelVideo">All Videos</a>');
if(personalized){b.push('<a href="#" class="channel" id="channelVideoFriendsFavorites">Friends\' Favorites</a>')
}for(var n=0,g=m.length;
n<g;
n++){var p={};
p=h.videoproviders[m[n]];
p.id=m[n];
p.className="videoChannel "+h.videoproviders[m[n]].sname;
b.push(this.buildThruList(p,p.tb_desc));
if(n>0&&(n+3)%l==0){b.push(r);
s++
}}b.push(r);
if(personalized){a.push('<a class="edit" id="editTopics" href="/settings/interests/?pre=video" target="_top">Edit Topics</a>')
}var f=1;
for(var n=0,g=a.length;
n<g;
n++){if(a[n]==r){f++
}}a.unshift(b.join(""));
var c="Stumble videos by topic...";
if(h.ordering.videochannels.length==0){c=""
}a.splice(0,0,"Stumble through...</td>",'<td class="subtitle" colspan="'+f+'">'+c+"</td></tr><tr><td>");
e='<table><tr><td class="subtitle" colspan="'+s+'">'
}}a.push(q);
a.splice(0,0,e);
j.html(a.join(" "));
if(is_su_team){var o=$("#keywordFieldset");
o.show();
$("#keyword").labelToValue();
$("#keyword").keydown(function(u){var i=$("#keyword").val();
if(u.keyCode=="13"&&i.length>0){if(topicSelector&&topicSelector.handleTagStumblingStart){topicSelector.handleTagStumblingStart(i)
}}});
$("#stumbleByKeywordSubmit").click(function(){var i=$("#keyword").val();
if(i.length>0){if(topicSelector&&topicSelector.handleTagStumblingStart){topicSelector.handleTagStumblingStart(i)
}}})
}};
SUJS.View.WebTbTopicsList.prototype.buildList=function(e){var d=[];
var c=e.popular;
var a=e.id;
var b=e.topic;
d.push('<a href="#" class="topic');
if(c){d.push("popular ")
}d.push('" id="');
d.push(a);
d.push('">');
d.push(b);
d.push("</a>");
return d.join("")
};
SUJS.View.WebTbTopicsList.prototype.buildThruList=function(g,c){var f=[];
var e=g.metatopics;
var b=g.id;
var d=c;
var a=g.className;
f.push('<a href="#" class="topic ');
if(typeof a!="undefined"){f.push(a)
}f.push('" id="');
f.push(b);
f.push('" title="');
f.push(g.title);
f.push('"');
if(typeof _topicImg!="undefined"){f.push('style="background-image: url(http://cdn.stumble-upon.com/images/');
f.push(_topicImg);
f.push(');"')
}f.push(">");
f.push(d);
f.push("</a>");
return f.join("")
};
SUJS.View.TopicsList=function(b,a,c){this._model=b;
this._controller=a;
this._elements=c;
this._topics=this._model.getTopics();
var d=this;
this._model.categoryChanged.attach(function(){d.buildTopicsList()
});
this._model.displayModeChanged.attach(function(){var e=d._model.getDisplayMode();
d._elements.wrapperElem.removeClass(e.prev).addClass(e.current)
});
this._model.selectedTopicsChanged.attach(function(g,f){d.buildSuggestedList();
d.renderForm();
if(typeof f!="undefined"&&$(f.currentTarget).attr("id")=="topicsSuggested"){d.buildTopicsList()
}});
this._model.adultTopicSelected.attach(function(g,f){d.adultOptConfirm(f)
})
};
SUJS.View.TopicsList.prototype.init=function(){this.selectTopic();
this.submitHandler();
this._controller.updateDisplayMode(this._elements.mode);
this._controller.selectTopic(this._topics)
};
SUJS.View.TopicsList.prototype.adultOptConfirm=function(c){var a=this._model.getAdultRating(c);
var b=confirm("You have selected an "+a+"-rated topic.  Adding this topic will flag you as an "+a+"-rated stumbler, permanently preventing general users from viewing your profile. Are you absolutely sure you want to add this topic? (If you are unsure, click Cancel)");
if(b){this._controller.adultOptConfirm(a,c)
}else{$(c.target).attr("checked",false);
this._topics.ordering["Selected/Topics"].shift();
this._topics.topics[c.target.value].selected=false
}};
SUJS.View.TopicsList.prototype.buildTopicsList=function(){var o=this._elements.topicsList;
var l=this._elements.formElem;
var k=this._model.getTopics();
var p=this._model.getCategory();
var n=this._model.getSelectedTopics();
var s=this._model;
var m="checklist_";
var h='Warning: Selecting <span class="adultLabel">X</span>-rated topics will prevent G-rated stumblers from seeing your profile. It will also permanently restrict your ability to message or interact with these stumblers.';
var c='<ul class="col">';
var w="</ul>";
var j='</ul><ul class="col">';
var d=false;
if(typeof this._elements.numCols!="undefined"){d=this._elements.numCols
}var e=$.trim(p.filter);
e=e.replace(/ /gi,"|").replace(/[-[\]{}()*+?.,\\^$|#\s]/g,"\\$&");
function u(A){var z=0;
var y=A.length;
while(z<y&&s.getAdultRating(A[z])!="X"){z++
}return(z<y)?true:false
}function b(B,C){var z=Math.ceil(B/C);
var y=z;
for(var A=1;
A<C;
A++){a.splice(z,0,j);
z=z+y
}}function f(z){var y=z;
var i=a.length;
b(i,y);
o.attr("class","clearfix col"+y)
}var q=[];
var a=[];
o.html("");
if(p.category=="filter"){q=k.ordering.All
}else{q=k.ordering[p.category.replace(/_/,"/")]
}if(p.category=="Adult"&&u(q)){a.push('<p class="contentWarning">'+h+"</p>")
}a.push(c);
for(var r=0,g=q.length;
r<g;
r++){var v={};
v=k.topics[q[r]];
if(p.category==v.metatopics&&p.category!="Popular_Topics"){a.push(this.buildCheckList(v,m))
}else{if(v.popular&&p.category=="Popular_Topics"){a.push(this.buildCheckList(v,m))
}else{if(v.selected&&p.category=="Selected_Topics"){a.push(this.buildCheckList(v,m))
}else{if(p.category=="filter"){var x=v.name;
if(e!=""&&x.search(new RegExp(e,"i"))>=0){a.push(this.buildCheckList(v,m))
}}}}}}a.push(w);
f(d);
o.html(a.join(" "))
};
SUJS.View.TopicsList.prototype.buildSuggestedList=function(){var f=this,e=this._model.getSelectedTopics(),b={},c=e.length,a=this._model.getTopics();
if("undefined"!=typeof this._elements.suggestedElem){if(c>0){for(var d=0;
d<c;
d++){b[e[d].id]=e[d].id
}this.fetchSuggestedTopics(b,a.ftoken)
}else{this.hideSuggestedTopics()
}}};
SUJS.View.TopicsList.prototype.fetchSuggestedTopics=function(a,b){this._fetchSuggestedTopics("/ajax/topic/subscribe",{action:"suggest",topics:a,fauth:b})
};
SUJS.View.TopicsList.prototype._fetchSuggestedTopics=function(a,c){var b=this;
$.ajax({url:a,type:"POST",dataType:"json",data:c,beforeSend:function(){},success:function(d){if(d.success){b.renderSuggestedTopics(d.suggested)
}}})
};
SUJS.View.TopicsList.prototype.renderSuggestedTopics=function(b){var g=this._model.getTopics(),a=this._elements.suggestedElem,e=a.find("ul"),d={},c=[];
for(var f in b){d=g.topics[f];
c.push(this.buildCheckList(d,"suggested_"))
}if(c.length>0){a.addClass("left reloading").show().animate({opacity:1},300,function(){a.removeClass("reloading")
});
e.html(c.join(" "))
}else{this.hideSuggestedTopics()
}};
SUJS.View.TopicsList.prototype.hideSuggestedTopics=function(){if(typeof this._elements.suggestedElem=="undefined"||this._elements.suggestedElem==null){return
}var a=this._elements.suggestedElem,b=a.find("ul");
b.html("");
a.removeClass("left").hide()
};
SUJS.View.TopicsList.prototype.buildCheckList=function(f,i){var g=[];
var c=f.popular;
var j=f.metatopics;
var k=f.id;
var a=f.name;
var b=f.selected;
var l=f.size;
var h="";
var d=(f.adult)?f.adult:null;
if(typeof i!="undefined"){h=i
}g.push('<li class="topicItem ');
if(d){g.push("adultLevel"+d+" ")
}if(c){g.push("popular ")
}g.push(j);
g.push('"><label for="cbox_');
g.push(h);
g.push(j);
g.push("_");
g.push(k);
g.push('"><input name="categories_');
g.push(h);
g.push('[]" type="checkbox" id="cbox_');
g.push(h);
g.push(j);
g.push("_");
g.push(k);
g.push('" value="');
g.push(k);
g.push('" ');
if(b){g.push('checked="checked" ')
}g.push(" />");
g.push(a);
if(d){var e=(d==1)?"R":"X";
g.push(' <span class="adultLabel">'+e+"</span>")
}if(typeof l!="undefined"){g.push(" <span>(");
g.push(l);
g.push(")</span>")
}g.push("</label></li>");
return g.join("")
};
SUJS.View.TopicsList.prototype.buildSubmitList=function(d){var c=[];
var b=d.metatopics;
var a=d.id;
c.push('<input name="categories[]" type="checkbox" id="cbox_');
c.push(b);
c.push("_");
c.push(a);
c.push('" value="');
c.push(a);
c.push('" ');
c.push('checked="checked" />');
return c.join("")
};
SUJS.View.TopicsList.prototype.selectTopic=function(){var c=$(this._elements.suggestedElem);
var b=$(this._elements.topicsList);
var d=this;
function a(h,g){if($(g.target).is("input")){if(typeof d._elements.countCb=="function"){var f=d._elements.countCb(g);
if(f){d._controller.selectTopic(h,g)
}}else{d._controller.selectTopic(h,g)
}}}b.click(function(f){a(topics,f)
});
c.click(function(f){a(topics,f)
})
};
SUJS.View.TopicsList.prototype.renderForm=function(){var f=this._model.getSelectedTopics();
var c=this._elements.formElem.children("#cbToSubmit");
var e=this;
var b=[];
var a;
c.html("");
for(a in f){var d={};
d=f[a];
b.push(this.buildSubmitList(d))
}c.html(b.join(" "))
};
SUJS.View.TopicsList.prototype.submitHandler=function(){var a=this._elements.submitButton;
var b=this._elements.formElem;
var c=this;
a.click(function(){c._controller.submitHandler(b)
});
return false
};
SUJS.Controller.TopicsList=function(a){this._model=a
};
SUJS.Controller.TopicsList.prototype.adultOptConfirm=function(a,b){this._model.setAdultLevelOption(a,b)
};
SUJS.Controller.TopicsList.prototype.selectTopic=function(a,f){var d=a;
var n=[];
if(!(d.ordering["Selected/Topics"]==null)){n=d.ordering["Selected/Topics"]
}else{d.ordering["Selected/Topics"]=[]
}if(arguments.length>1){var j=$(f.target);
var b={key:j.attr("value"),bool:j.attr("checked")?true:false};
if(b!=undefined){function k(p,q){for(var e=0,o=p.length;
e<o;
e++){if(p[e]==q){return e
}}}d.topics[b.key].selected=b.bool;
if(b.bool){var h=[];
var g=d.topics[b.key].name;
for(var c=0,m=n.length;
c<m;
c++){h.push(d.topics[n[c]].name)
}h.push(g);
h.sort();
var l=k(h,g);
n.splice(l,0,parseInt(b.key))
}else{var l=k(n,b.key);
n.splice(l,1)
}}}this._model.setSelectedTopics(d.topics,f)
};
SUJS.Controller.TopicsList.prototype.updateDisplayMode=function(a){this._model.setDisplayMode(a)
};
SUJS.Controller.TopicsList.prototype.submitHandler=function(b){var c=this;
var a=b;
a.submit()
};
SUJS.View.TopicsNav=function(b,a,c){this._model=b;
this._controller=a;
this._elements=c
};
SUJS.View.TopicsNav.prototype.init=function(){var d=this._elements.nav;
var f=this._elements.selectedTopics;
var e=this;
var b=this._model.getDisplayMode();
var a;
var c=function(g){d.children("li").removeClass("active");
$("#"+g).parent("li").addClass("active ")
};
if(this._model.getSelectedTopics()&&$("#Selected_Topics").length>0){this.selectedCount()
}d.click(function(g){if(b.current!="modeNav"){e._controller.newDisplayMode("modeNav")
}if(($(g.target).closest("a")).is("a:not(#start_stumble_button, #hide)")){a=$(g.target).closest("a");
var h=a.attr("id")
}else{a=false
}if(a){e._controller.changeCategory(h)
}});
e._model.categoryChanged.attach(function(){var g=e._model.getCategory().category;
c(g)
});
if(typeof this._elements.default_category!="undefined"){e._controller.changeCategory(this._elements.default_category)
}};
SUJS.View.TopicsNav.prototype.selectedCount=function(){var a=this;
this._model.selectedTopicsChanged.attach(function(){var b=a._model.getSelectedTopicsCount();
$("#Selected_Topics span").html(b)
})
};
SUJS.Controller.TopicsNav=function(a){this._model=a
};
SUJS.Controller.TopicsNav.prototype.changeCategory=function(a){this._model.setCategory(a)
};
SUJS.Controller.TopicsNav.prototype.newDisplayMode=function(a){this._model.setDisplayMode(a)
};
SUJS.View.TopicsFilter=function(b,a,c){this._model=b;
this._controller=a;
this._elements=c
};
SUJS.View.TopicsFilter.prototype.init=function(){var e=this;
var c=this._elements.topicsFilter;
var a=c.find("input");
var b=c.find(".cancel");
function d(){a.val("").blur();
e._controller.cancel()
}a.focus(function(f){});
b.click(function(f){d()
});
a.keypress(function(f){if((f.keyCode&&f.keyCode=="13")||(f.which&&f.which==13)){return false
}else{return true
}});
a.keyup(function(f){e._controller.enable();
e._controller.setCategory("filter",a.val())
});
this._model.displayModeChanged.attach(function(){var f=e._model.getDisplayMode();
if(f.current!="modeFilter"){a.val("")
}})
};
SUJS.Controller.TopicsFilter=function(a){this._model=a
};
SUJS.Controller.TopicsFilter.prototype.enable=function(){this._model.setDisplayMode("modeFilter")
};
SUJS.Controller.TopicsFilter.prototype.cancel=function(){this._model.setDisplayMode("modeNav");
this._model.setCategory("Popular_Topics")
};
SUJS.Controller.TopicsFilter.prototype.setCategory=function(a,b){this._model.setCategory(a,b)
};
SUJS.Model.Message=function(){this.message=null;
this.subject=null;
this.image=null;
this.height=null;
this.setMessage=function(a){this.message=a;
return this
};
this.setSubject=function(a){this.subject=a;
return this
};
this.setImage=function(a){this.image=a;
return this
};
this.setHeight=function(a){this.height=a;
return this
}
};
SUJS.Model.Resource=function(){this.url="";
this.setUrl=function(a){this.url=a;
return this
}
};
SUJS.Model.InternalResource=function(){};
SUJS.Model.RemoteResource=function(){};
SUJS.Model.InternalResource.prototype=new SUJS.Model.Resource();
SUJS.Model.RemoteResource.prototype=new SUJS.Model.Resource();
SUJS.Model.RemoteResource.initFromUrl=function(a){var b=new SUJS.Model.RemoteResource();
b.setUrl(a);
return b
};
SUJS.Model.InternalResource.initFromUrl=function(a){var b=new SUJS.Model.InternalResource();
b.setUrl(a);
return b
};
SUJS.Model.Cookie=function(a,b){this.name=a;
this.value=b;
this.expires=null;
this.path="/";
this.domain="";
this.secure=false;
var c=this;
this.setExpirationSeconds=function(e){var d=new Date();
d.setTime(d.getTime()+(1000*e));
this.expires=d
};
this.setExpirationHours=function(d){this.setExpirationSeconds(d*3600)
};
this.setExpirationDays=function(d){this.setExpirationSeconds(d*86400)
};
this.setDomain=function(d){this.domain=d
};
this.save=function(){var d=this.name+"="+escape(this.value)+((this.expires)?"; expires="+this.expires.toGMTString():"")+((this.path)?"; path="+this.path:"")+((this.domain)?"; domain="+this.domain:"")+((this.secure)?"; secure":"");
document.cookie=d
};
this.read=function(){var f=this.name+"=";
var d=document.cookie.split(";");
for(var e=0;
e<d.length;
e++){var g=d[e];
while(g.charAt(0)==" "){g=g.substring(1,g.length)
}if(g.indexOf(f)==0){this.value=unescape(g.substring(f.length,g.length))
}}return this.value
};
this.setValue=function(d){this.value=d
};
this.kill=function(){this.setExpirationSeconds(-86400);
this.value=null;
this.save()
}
};
SUJS.View.Tooltip=function(a){this.target=null;
this._familyId=null;
this.persistent=true;
this.message=a;
this.tipNode=null;
this.leftPosition=false;
this.topPosition=false;
this.maxWidth=275;
this.overlap=0;
this.bubblePos="se";
this.showPointer=true;
this._showing=false;
this.customCallbacks={};
this.show=function(){if(!this.tipNode){this.createDomNode()
}this.reposition();
this.tipNode.comeBack();
this._showing=true;
return this
};
this.familyId=function(b){if(b){this._familyId=b;
return this
}else{return this._familyId
}};
this.hide=function(b){if(this.showTimer){clearTimeout(this.showTimer)
}if(!this.tipNode){return this
}if(b){this.tipNode.goAway()
}else{this.tipNode.goAway({fadeTime:0})
}this._showing=false;
if(typeof this.customCallbacks.goAway!="undefined"){this.customCallbacks.goAway()
}return this
};
this.showing=function(){return this._showing
};
this.setMaxWidth=function(b){this.maxWidth=b;
return this
};
this.setBubblePos=function(b){this.bubblePos=b;
return this
};
this.setPersistent=function(b){this.persistent=b;
return this
};
this.setLeftPosition=function(b){this.leftPosition=b;
return this
};
this.setTopPosition=function(b){this.topPosition=b;
return this
};
this.setShowPointer=function(b){this.showPointer=b;
return this
};
this.createDomNode=function(){this.target=$(this.message.subject);
var c=$('<a href="javascript:void(0);" class="suBubbleClose" style="display: block;">close</a>');
this.tipNode=$('<div class="wrapperBubble"><div class="tip"></div><div class="wrapperContent"><div class="content"></div></div></div>');
var d=this.tipNode.find(".content");
d.html(this.message.message);
if(this.persistent){d.append(c)
}if(this.message.image){var e=$("<img src='"+this.message.image+"' />");
d.addClass("hasImg");
d.prepend(e)
}var b=this;
this.tipNode.click(function(f){if($(f.target).hasClass("suBubbleClose")){b.hide(true)
}});
this.target.data("attached_tips",this);
this.target.click(function(){var f=$(this).data("attached_tips");
if(f){f.hide()
}});
$("body").append(this.tipNode)
};
this.reposition=function(){var f=this.target.offset();
var c=this.target.width();
var b=this.showPointer?Math.round(c/2):"-999em";
this.tipNode.children(".tip").css("left",b);
var g={left:0,top:0};
if(c<40){g.left=-1*Math.round(this.target.width()/2)
}f.left=f.left+g.left;
f.top=this.target.height()+f.top;
if(this.leftPosition){f.left=this.leftPosition
}if(this.topPosition){f.top=this.topPosition
}this.tipNode.css({"max-width":this.maxWidth});
var e=this.tipNode.width();
if(f.left>($(window).width()-e)){this.tipNode.find(".tip").css({left:"",right:"10px"});
var d=this.target.position();
f.left=d.left-e+25
}this.tipNode.css({left:f.left+"px",top:f.top+"px"})
};
this.delayedShow=function(c){var b=this;
this.showTimer=setTimeout(function(){b.show()
},c)
}
};
SUJS.Librarian=function(){this.listeners=[];
this.subscribe=function(a,b){if(typeof this.listeners[a]=="undefined"){this.listeners[a]=[]
}this.listeners[a].push(b)
};
this.publish=function(a,c,d){if(typeof this.listeners[a]!="undefined"){for(var b=0;
b<this.listeners[a].length;
b++){this.listeners[a][b](c)
}}if(d){d()
}}
};
SUJS.Librarian.getInstance=function(){try{if(typeof top.suGlobalMessagingLibrarian=="undefined"){top.suGlobalMessagingLibrarian=new SUJS.Librarian()
}if(typeof top.suGlobalMessagingLibrarian=="undefined"){return new SUJS.Librarian()
}return top.suGlobalMessagingLibrarian
}catch(a){}};
SUJS.View.ResourceViewPane=function(a,b){this.toolbarHeight=32;
this.styles={position:"absolute",display:"none",background:"#fff"};
this.resource=null;
this.handle=null;
this.name="";
if(a){this.name=a
}this.inflator=b||false;
this.showing=false;
this.customAnimator=null;
this.iframeOverflows=false;
this.listeners={};
this.closeButtonStyle="text";
this.modal=true;
this.loadingStyle="throbber";
this.reloadOnShow=false;
this.setDisplayStyle=function(d){for(var c in d){this.styles[c]=d[c]
}return this
};
this.setResource=function(c){this.resource=c;
return this
};
this.setName=function(c){this.name=c;
return this
};
this.setReloadOnShow=function(c){if(c==true){this.reloadOnShow=true
}else{this.reloadOnShow=false
}return this
};
this.setModal=function(c){if(c===false){this.modal=false
}else{this.modal=true
}return this
};
this.setPushDownMainPage=function(c){if(c===true){this.pushDownMainPage=true
}else{this.pushDownMainPage=false
}return this
};
this.setOverflowOkay=function(){this.iframeOverflows=true;
return this
};
this.setLoadingStyle=function(c){this.loadingStyle=c;
return this
};
this.setCloseButtonStyle=function(c){this.closeButtonStyle=c;
return this
};
this.render=function(){var f=$("<iframe></iframe>");
var d=$('<div class="panel" id="panelReplace"></div>');
var k=this.iframeOverflows?"scroll":"hidden";
f.attr("name",this.name).attr("src",this.resource.url).css({width:"100%",height:"100%",border:"none",overflow:k});
if(this.name=="sidepanel"){f.attr("id",this.name)
}d.css(this.styles);
if(this.closeButtonStyle){d.append(this.getCloseButton())
}if(this.loadingStyle=="throbber"){f.css("height","0px");
var j=$('<div><img src="/i/assets/loader_gray.gif" alt="Loading..." /></div>');
j.css({"text-align":"center","vertical-align":"middle","margin-top":"30px"});
d.append(j)
}d.append(f);
if(b){var g=$("#inflator");
$("#panelReplace").replaceWith(d);
d.animate({top:0},400);
g.slideDown(400)
}else{$("body").append(d)
}this.handle=d;
var i=SUJS.Librarian.getInstance();
var c=this;
i.subscribe("internalWindowLoaded",function(h){if(h==c.name){if(typeof j!="undefined"){j.hide()
}f.css("height","100%")
}});
if(this.pushDownMainPage&&this.styles.height){var e=parseInt(this.styles.height);
if(isNaN(e)==false&&e>0){$("#stumbleFrame").css("top",this.toolbarHeight+parseInt(this.styles.height))
}}};
this.hide=function(){if(b){$("#inflator").slideUp(200)
}this.fireGlobalEvent("viewpane_hide");
this.handle.hide();
this.showing=false;
if(this.pushDownMainPage){$("#stumbleFrame").css("top",this.toolbarHeight)
}};
this.show=function(){this.fireGlobalEvent("viewpane_show");
if(this.handle==null){this.render()
}if(this.customAnimator){this.customAnimator()
}else{this.handle.show()
}this.showing=true
};
this.toggleDisplay=function(c){if(c){if(c=="show"){if(this.reloadOnShow==true){this.render()
}this.show()
}else{if(c=="hide"){this.hide()
}}}else{if(this.showing){this.hide()
}else{if(this.reloadOnShow==true){this.render()
}this.show()
}}};
this.getCloseButton=function(){if(this.closeButtonStyle=="text"){var d=$('<div style="float: right;"><a class="hideText" style="color: #cccccc; font-weight: bold;" href="#">Hide</a></div>')
}else{if(this.closeButtonStyle=="x"){var d=$('<div style="float: right;"><a href="#"><img src="http://cdn.stumble-upon.com/i/toolbar/closeGray12x.png" border="0" /></a></div>')
}}d.css({position:"absolute",top:"5px",right:"20px",color:"#808080"});
var c=this;
d.click(function(){c.hide();
return false
});
return d
};
this.fireGlobalEvent=function(c){try{var f=SUJS.Librarian.getInstance(true);
f.publish(c,this)
}catch(d){}}
};
SUJS.View.ResourcePopup=function(a){this.name=a;
this.width=620;
this.height=550;
this.options={scrollbars:false};
this.setResource=function(b){this.resource=b;
return this
};
this.setWidth=function(b){this.width=b;
return this
};
this.setHeight=function(b){this.height=b;
return this
};
this.render=function(){var c=this.options.scrollbars?"1":"0";
var b=window.open(this.resource.url,this.name,"width="+this.width+"px,height="+this.height+"px,scrollbars="+scrollbars);
if(b&&!b.opener){b.opener=window
}if(window.focus){b.focus()
}}
};
SUJS.Util=function(){};
SUJS.Util.prototype.toggleShareUI=function(a,e){var b="su"+e;
var c="";
var d=window.open(c,b,"width=800px,height=500px");
var f=function(i,h){var g="/to/event/webtb_"+e+"share/?url="+encodeURIComponent(i);
if(h){g+="&extra="+encodeURIComponent(h+" ")
}d.location.href=g;
d.focus();
if(a.trk){a.trk(e+"ShareClick")
}if(a.currentStumble){a.currentStumble.autosharedToFacebook=true
}};
if(!a.currentStumble){if(a.getLocation){a.getLocation(f)
}}else{f(a.currentStumble.url,a.currentStumble.title)
}};
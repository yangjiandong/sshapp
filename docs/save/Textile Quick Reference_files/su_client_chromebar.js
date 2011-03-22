var SU_ClientChromebar=function(){this.device="chromebar";
this.source="litebar";
this.tokenRecheckInt=600;
this.viewPanes={};
this.stumblePending=false;
this.stumbleTimeout=null;
this.clientVersion="";
this.scriptVersion="";
this.currentReferralCount=0;
this.uiStateCookie=new SUJS.Model.Cookie("uiState");
this.messageTipOverlay=null;
this.messagePanelOverlay=null;
this.hoverTipOverlay=null;
this.tipTimer=null;
this.currentNodeId=null;
this.currentLabel="";
return true
};
var SU_ClientChromebar_prototype={init:function(){var b=this;
b.api.setResponseFilter(function(d,c){return b.responseFilter(d,c)
});
var a=function(){suExtensionApi.getDomain(function(c){b.server="http://"+c;
b.serverDomain=c;
b.setupLitebar()
})
};
if((typeof(suExtensionApi)=="undefined")||!suExtensionApi.isReady()){window.addEventListener("suScriptReadyExtensionApi",function(){a()
},false)
}else{a()
}},initUI:function(){$("#sponsored").hide()
},resetLocalPrefs:function(){},subscribeGlobalEvents:function(){},bindListeners:function(){var a=this;
this.userInterfaceEvents.favoritesLoggedOut.handler=function(e){a.gotoUrl("/signup/?pre=chromebar_tip");
return false
};
this.userInterfaceEvents.shareStumble={target:"#numReferrals",action:"click",handler:function(e){e.data.instance.performShareStumble();
return false
}};
var c=["login","favoritesLoggedOut","homeButton","closeButton"];
for(i=0;
i<c.length;
i++){this.userInterfaceEvents[c[i]].visitorOK=true
}var d;
for(var b in this.userInterfaceEvents){d=this.userInterfaceEvents[b];
$(d.target).bind(d.action,{instance:this},d.handler)
}},reloadCachedClientScript:function(){document.location.reload()
},syncServerLoginState:function(b){var a=this;
this.getAuthString(function(){if(a.keychain&&a.keychain.authString){a.getUserData(function(c){if(c&&c.info&&c.info.loggedIn){suExtensionApi.message.broadcastMessage("reinitUser",{user:c.info});
a.checkForShares(function(){a.setupLitebar()
});
if(b){b()
}}else{a.performLogout(false,function(){a.setupLitebar()
});
if(b){b()
}}})
}else{a.performLogout(false,function(){a.setupLitebar()
});
if(b){b()
}}},true)
},setupLitebar:function(){var a=this;
var c=new SUJS.Model.Cookie("chromebar_stumble","1");
c.setExpirationSeconds(3600);
c.save();
var b=new SUJS.Model.Cookie("litebar_recheck_auth");
b.domain=a.serverDomain.replace("www","");
b.read();
if(b.value=="1"){this.syncServerLoginState(function(){b.kill()
});
return false
}this.getData("performClientUpdate",function(d){if(d==1){a.setData("performClientUpdate",0);
a.reloadCachedClientScript()
}});
this.userInterfaceEvents.closeButton.handler=function(){if(a.viewPanes){if(a.stumblingMode&&a.stumblingMode.method=="video"&&a.currentStumble&&a.currentStumble.orig_url){a.gotoUrl(a.currentStumble.orig_url);
a.hideAllViewPanes(function(){suExtensionApi.toolbar.closeToolbar()
})
}else{a.hideAllViewPanes(function(){suExtensionApi.toolbar.closeToolbar()
})
}}};
suExtensionApi.message.addListener(function(d,f){switch(d){case"externalGetStumblingMode":suExtensionApi.message.broadcastMessage("currentStumblingMode",a.stumblingMode);
break;
case"externalRating":if(top.originatingExternalId&&f.originatingId==top.originatingExternalId){$("#like").click()
}break;
case"externalRatingBad":if(top.originatingExternalId&&f.originatingId==top.originatingExternalId){$("#notLike").show();
$("#notLike").click()
}break;
case"externalSetModeLabel":a.modeLabelExternallySet=1;
a.setModeLabel(f.label,function(){suExtensionApi.message.broadcastMessage("litebarReceivedExternalData","label")
});
break;
case"externalModeSelect":a.setStumblingMode(f.mode,function(){suExtensionApi.message.broadcastMessage("litebarReceivedExternalData","mode")
});
a.hideViewPane(a.viewPanes.topics);
break;
case"externalStumble":if(top.originatingExternalId&&f.originatingId==top.originatingExternalId){$("#stumble").click()
}break;
case"externalShare":if(top.originatingExternalId&&f.originatingId==top.originatingExternalId){$("#share").click()
}break;
case"externalVideoChannels":if(top.originatingExternalId&&f.originatingId==top.originatingExternalId){$("#topics").click()
}break;
case"stumbleLinkClicked":if(top.stumbleLinkClicked&&f.linkId==top.stumbleLinkClicked){if(!a.currentUser){a.displayRegistrationMessage("#stumble")
}else{if(f.mode.method&&f.mode.method=="topic"&&f.mode.topic=="Video"){f.mode.method="video";
f.mode.topic=0
}a.setStumblingMode(f.mode);
var g=function(){if(f.direct_url){a.gotoUrl(f.direct_url)
}else{a.performStumble()
}};
if(f.contextual){a.setModeLabel(f.contextual,function(){a.updateUI_CacheInterfaceState();
g()
})
}else{g()
}}}break;
case"litebarLogout":a.setActiveStumble(null);
a.setActiveUser(null);
a.updateUI();
a.unbindLoggedInHandlers();
a.hideShareMessage();
suExtensionApi.litebar.setHeight("32px");
a.resetStumblingMode();
a.setModeLabel("All Interests");
break;
case"localUpdateStumblingTopic":case"topicSelector_topicSelected":a.setStumblingTopic(f.id);
a.setModeLabel(f.name);
if(d=="topicSelector_topicSelected"){a.hideViewPane(a.viewPanes.topics)
}break;
case"localUpdateModeLabel":a.setModeLabel(f);
break;
case"localUpdateStumblingMode":break;
case"localUpdateReferralCount":$("#numReferrals a").html("<span></span>"+f);
if(f>0){$("#numReferrals").show()
}else{$("#numReferrals").hide()
}break;
case"localUpdateFavoritesCount":$("#favorites a").html(f+" favorites");
break;
case"loginboxLoginSuccess":if(typeof(f.user)=="undefined"){var e=new SUJS.Model.Cookie("litebar_recheck_auth");
e.domain=a.serverDomain.replace("www","");
e.read();
if(e.value=="1"){a.syncServerLoginState(function(){e.kill()
})
}break
}case"reinitUser":a.setActiveUser(f.user);
a.updateUI();
a.rebindLoggedInHandlers();
a.hideViewPane(a.viewPanes.login);
if(d=="loginboxLoginSuccess"){suExtensionApi.message.broadcastMessage("reinitUser",{user:f.user});
if(f.loggedin){var h=new SU_ClientRequestKeychain();
h.authString=f.auth;
a.setKeychain(h);
a.updateNumReferrals(f.numreferrals)
}}else{}break;
case"setModeLabel":a.modeLabelExternallySet=1;
a.setModeLabel(f,function(){suExtensionApi.message.broadcastMessage("litebarReceivedModeSettings",true)
});
break;
case"modeSelect":a.setStumblingMode(f,function(){suExtensionApi.message.broadcastMessage("litebarReceivedModeSelect",true)
});
break;
case"viewpane_hide":a.hideViewPane(a.viewPanes[f]);
break;
case"msgLogout":case"logoutConfirmClicked":a.litebarLogout();
break;
case"msgNextStumble":case"stumbleNow":if(a.currentUser){a.performStumble()
}else{a.displayRegistrationMessage("#stumble")
}break;
case"signupLinkClick":a.gotoUrl("/signup/?pre=chromebar_tip");
break;
case"loginLinkClick":a.messageTipOverlay.setPosition({width:0,height:0,left:0,top:0});
a.toggleLoginUI();
break;
case"topicSelector_partnerSelected":if(f.partnerid){a.resetStumblingMode();
a.setStumblingMode({method:"stumblethru",partner:f.partnerid});
a.setModeLabel(f.partnerid);
a.hideViewPane(a.viewPanes.topics)
}break;
case"closeSharePanel":if(a.viewPanes.share){a.hideViewPane(a.viewPanes.share)
}if(a.viewPanes.emailShare){a.hideViewPane(a.viewPanes.emailShare)
}break;
case"sendShareSuccess":setTimeout(function(){if(a.viewPanes.share){var j=function(l){a.viewPanes.share=undefined
};
a.hideViewPane(a.viewPanes.share,j)
}if(a.viewPanes.emailShare){var k=function(l){a.viewPanes.emailShare=undefined
};
a.hideViewPane(a.viewPanes.emailShare,k)
}},1500);
break;
case"directShare_reply":if(a.currentStumble&&a.currentStumble.referral_id){a.sendShare({referralId:a.currentStumble.referral_id,text:f,url:a.currentStumble.url},function(){});
a.hideShareMessage();
a.messagePanelOverlay.userHid=true;
suExtensionApi.litebar.setHeight("32px")
}break;
case"directShare_stopshare":if(a.currentStumble&&a.currentStumble.referral_id){$.ajax({type:"GET",dataType:"json",url:"/toolbar/profile_token.php",success:function(j,l,k){$.ajax({type:"POST",dataType:"json",url:"/ajax/user/subscribe",data:"act="+f.action+"&referral_id="+a.currentStumble.referral_id+"&fauth="+j,complete:function(m,n){suExtensionApi.message.postMessage(a.messagePanelOverlay,"directShare_stopshareResult",{action:f.action,result:n})
}})
}})
}break;
case"directShare_close":a.hideShareMessage();
a.messagePanelOverlay.userHid=true;
suExtensionApi.litebar.setHeight("32px");
break;
case"signupSuccess_litebar":if(f.extId==suExtensionApi.id){a.syncAuthString(f.authString,function(){a.rebindLoggedInHandlers();
a.getUserData(function(j){a.setupUser();
suExtensionApi.message.broadcastMessage("reinitUser",{user:j.info})
})
})
}break;
case"thumbDownMenu_subrate":a.performSubRating(f.subrating);
setTimeout(function(){a.hideViewPane(a.viewPanes.thumbDownMore)
},1000);
break;
case"thumbDownMenu_blocksite":a.performBlockSite(f.url,f.blocksite);
setTimeout(function(){a.hideViewPane(a.viewPanes.thumbDownMore)
},1000);
break;
case"showMeMore":a.showMeMore(f);
a.hideViewPane(a.viewPanes.showMeMore);
break;
case"updateFacebookPublishFlag":if(a.currentUser){a.currentUser.facebookPublish=(f===true);
a.setActiveUser(a.currentUser)
}break;
case"local_updateFacebookPublishFlag":if(a.currentUser){a.currentUser.facebookPublish=(f===true)
}break;
case"closeUrlPanel":a.hideViewPane(a.viewPanes.urlPanelOverlay);
break;
case"autoShareFacebook":a.autoShareToFacebook();
break
}});
if(typeof clientScriptVersion!="undefined"){this.scriptVersion=clientScriptVersion
}this.getData("@@currentVersion",function(d){a.clientVersion=d
});
this.getData("@@referralCount",function(d){if(d&&d>0){$("#numReferrals a").html("<span></span>"+d).show()
}});
suExtensionApi.message.broadcastMessage("litebarReadyForMessages",true);
a.getData("stumblingMode",function(d){a.stumblingMode=d;
suExtensionApi.litebar.getContentLocation(function(e){if(e==a.server+"/toolbar/client_redir.php"){if(a.currentUser){a.performStumble()
}else{a.gotoUrl("/signup/")
}return false
}a.getData("currentStumble",function(f){if(f){if(e==f.url){a.currentStumble=f;
if(f.is_referral){suExtensionApi.message.addListener(function(g,h){if(g=="messagePanelReady"){if(h.extId==suExtensionApi.id){a.displayShareMessage()
}}})
}}}a.initUI();
a.updateUI();
if(f&&(typeof f.numreferrals!="undefined")){a.updateNumReferrals(f.numreferrals)
}if(f&&f.message&&f.message.display=="tooltip"){if(typeof f.message.subject=="string"){f.message.subject=$(f.message.subject);
f.message.config={permanence:"close"}
}a.displayMessage(f.message)
}});
if(e==a.server+"/interests_after.php"){suExtensionApi.message.broadcastMessage("litebarLoadComplete",{extId:suExtensionApi.id})
}})
});
a.getData("stumblingModeLabel",function(d){if(typeof a.modeLabelExternallySet!="undefined"){return false
}if(d&&(typeof d=="string")&&(d!="All Interests")){a.setModeLabel(d)
}else{a.setModeLabel("All Interests")
}});
a.setupOverlays=function(){if(!a.hoverTipOverlay){a.hoverTipOverlay=true;
a.setupRegisteredViewPane({id:"hoverTipOverlay",url:a.server+"/toolbar/overlay.php?"+a.scriptVersion,dimensions:{left:0,top:35,width:200,height:100,zIndex:2}},function(d){a.hoverTipOverlay=d;
suExtensionApi.message.broadcastMessage("hoverTipOverlayReady",{extId:suExtensionApi.id})
})
}if(!a.messageTipOverlay){a.messageTipOverlay=true;
a.setupRegisteredViewPane({id:"messageTipOverlay",url:a.server+"/toolbar/overlay.php?"+a.scriptVersion,dimensions:{left:0,top:35,width:200,height:100,zIndex:1}},function(d){a.messageTipOverlay=d
})
}if(!a.messagePanelOverlay){a.messagePanelOverlay=true;
a.setupRegisteredViewPane({id:"messagePanelOverlay",url:a.server+"/toolbar/overlay.php?"+a.scriptVersion,dimensions:{left:0,top:32,width:$(window).width(),height:100}},function(d){a.messagePanelOverlay=d;
var e=function(f,h,g){if(g&&g.id==d.id){if(f=="readyToReceiveOverlayContents"){suExtensionApi.message.broadcastMessage("messagePanelReady",{extId:suExtensionApi.id})
}}};
suExtensionApi.message.addListener
})
}};
a.setupOverlays();
a.setupUser();
suExtensionApi.litebar.setHeight("32px");
a.bindListeners();
a.ensureKeychainExists()
},gotoUrl:function(a){if(this.timers){this.timers.addEvent("interrupted");
this.timers.saveTimersToDOMStorage()
}suExtensionApi.litebar.setContentLocation(a)
},setupUser:function(){var a=this;
this.getData("userInfo",function(b){if(b&&b.nickname){a.currentUser=b;
a.updateUI_User();
suExtensionApi.message.broadcastMessage("localUpdateFavoritesCount",a.currentUser.favorites);
suExtensionApi.message.broadcastMessage("reinitUser",{user:b})
}else{a.performLogout(false)
}})
},unbindLoggedInHandlers:function(){var a=this;
var c;
for(var b in this.userInterfaceEvents){c=this.userInterfaceEvents[b];
if(typeof c.visitorOK!="undefined"&&c.visitorOK){continue
}if(c.action=="click"&&b!="login"){$(c.target).unbind("click").bind("click",{element:c.target},function(d){a.displayRegistrationMessage(d.data.element);
return false
})
}}},unbindAllHandlers:function(){var c;
var a=this;
for(var b in this.userInterfaceEvents){c=this.userInterfaceEvents[b];
$(c.target).unbind(c.action)
}},rebindLoggedInHandlers:function(){this.unbindAllHandlers();
this.bindListeners()
},setActiveUser:function(a){this.currentUser=a;
this.setData("userInfo",a)
},ratingCallback:function(c){var b=this;
var a=function(d){suExtensionApi.message.broadcastMessage("localUpdateFavoritesCount",b.currentUser.favorites)
};
SU_ClientChromebar.superclass.ratingCallback.call(this,c,a);
if(c.msg){c.msg.config={permanence:"close"};
this.displayMessage(c.msg)
}if(c.noFacebookPublish&&b.currentUser){b.currentUser.facebookPublish=false;
b.setActiveUser(b.currentUser);
suExtensionApi.message.broadcastMessage("local_updateFacebookPublishFlag",false)
}},setModeLabel:function(a,b){this.currentLabel=a;
this.updateUI_CacheInterfaceState();
SU_ClientChromebar.superclass.setModeLabel.call(this,a,b)
},resetStumblingMode:function(a){SU_ClientChromebar.superclass.resetStumblingMode.call(this)
},checkForShares:function(b){var a=this;
SU_ClientChromebar.superclass.checkForShares.call(this,function(c){if(c&&c.referrals){a.updateNumReferrals(c.referrals)
}if(b){b()
}})
},clearKeychain:function(b){var a=this;
this.keychain=new SU_ClientRequestKeychain();
this.getData("keychain",function(c){if(c){a.setData("keychain",a.keychain)
}if(b){b()
}})
},responseFilter:function(b,a){if(a&&a.bad_token&&b&&b.action&&(b.action!="getAuth")){this.stumbleFinished();
this.syncServerLoginState();
return false
}return true
},ensureKeychainExists:function(b){var a=this;
if(this.keychain){if(typeof this.keychain.tokensExpired!="undefined"&&!this.keychain.tokensExpired()){if(b){b()
}return true
}}var a=this;
this.getData("keychain",function(d){if(d){a.keychain=new SU_ClientRequestKeychain();
for(var c in d){a.keychain[c]=d[c]
}if(b){b()
}}else{a.getAuthString(function(){if(b){b()
}},true)
}})
},getAuthString:function(c,b){var a=this;
this.setupApi(function(){if(b){a.api.keychain.authString=""
}a.api.getAuthString(function(d){if(typeof d=="string"&&d!=""){a.syncAuthString(d,function(){if(c){c()
}})
}else{if(c){c()
}}})
},b)
},syncAuthString:function(a,b){this.keychain=new SU_ClientRequestKeychain();
this.keychain.authString=a;
this.keychain.setTokenExpiration(600);
SU_ClientChromebar.superclass.setKeychain.call(this,this.keychain);
if(b){b()
}},setKeychain:function(a){SU_ClientChromebar.superclass.setKeychain.call(this,a)
},setActiveStumble:function(a){SU_ClientChromebar.superclass.setActiveStumble.call(this,a);
this.setData("currentStumble",a)
},performShareStumble:function(){return this.performStumble(true)
},performStumble:function(b){if(this.stumblePending){return false
}var a=this;
this.preupdateUI_Stumble();
this.stumblePending=true;
if(this.stumbleTimeout){window.clearTimeout(this.stumbleTimeout)
}this.stumbleTimeout=window.setTimeout(function(){a.stumbleFinished()
},15000);
if(b){SU_ClientChromebar.superclass.performShareStumble.call(this,function(c){a.stumbleFinished()
})
}else{SU_ClientChromebar.superclass.performStumble.call(this,function(c){a.stumbleFinished()
})
}},preupdateUI_Stumble:function(){this.hideShareMessage();
$(".litebar #stumble a").addClass("active")
},postupdateUI_Stumble:function(){$(".litebar #stumble a").removeClass("active")
},stumbleFinished:function(){this.stumblePending=false;
if(this.messagePanelOverlay){this.messagePanelOverlay.userHid=false
}if(this.stumbleTimeout){window.clearTimeout(this.stumbleTimeout);
this.stumbleTimeout=null
}this.postupdateUI_Stumble()
},updateUI_StumbleUrl:function(){var a=this;
suExtensionApi.litebar.getContentLocation(function(b){if(b&&b!=a.currentStumble.url){suExtensionApi.litebar.setContentLocation(a.currentStumble.url)
}else{$(".litebar #stumble a").removeClass("active");
if(a.currentStumble.is_referral){a.displayShareMessage()
}}})
},updateUI_StumbleMeta:function(){if(typeof this.currentStumble.rating!="undefined"){$("#like").find("a.bgImgLeft").removeClass("active");
$("#notLike").find("a.bgImgLeft").removeClass("active");
if(this.currentStumble.rating==1){$("#like").find("a.bgImgLeft").addClass("active");
$("#facebook").find("a").addClass("active");
$("#twitter").find("a").addClass("active");
$("#linkedin").find("a").addClass("active");
$("#email").find("a").addClass("active");
$("#notLike").hide();
$("#notLikeMore").hide();
if(this.currentUser){$("#showMeMore").show()
}}else{if(this.currentStumble.rating==0){$("#notLike").find("a.bgImgLeft").addClass("active");
$("#showMeMore").hide()
}else{if(this.currentUser){$("#notLike").show();
$("#notLikeMore").show();
$("#showMeMore").hide()
}}}}if(typeof this.currentStumble.publicid=="undefined"||this.currentStumble.publicid==""){$("#like, #showMeMore, #notLike, #notLikeMore, #share, #facebook, #twitter, #linkedin, #email, #reviews, #friends, #sponsored").hide()
}if(this.currentStumble.contextual){this.setModeLabel(this.currentStumble.contextual);
suExtensionApi.message.broadcastMessage("localUpdateModeLabel",this.currentStumble.contextual)
}else{if(this.currentStumble.method_used=="general"){this.setModeLabel("All Interests");
suExtensionApi.message.broadcastMessage("localUpdateModeLabel","All Interests")
}}if(this.currentStumble.discoverer_nick){$("#friends").show();
$("#friends a").show();
$("#friends a").html("<span></span>"+this.currentStumble.discoverer_nick);
$("#friends a").attr("href","/stumbler/"+this.currentStumble.discoverer_nick+"/");
$("#friends a").attr("target","_blank");
if(!this.currentStumble.is_subscription){$("#friends span").addClass("nonfriend")
}}var a=0;
if(this.currentStumble.numreferrals){a=this.currentStumble.numreferrals
}this.updateNumReferrals(a);
if(this.currentStumble.title){}if(typeof this.currentStumble.numreviews!="undefined"){if(this.currentStumble.numreviews>0){$("#reviews a").html(this.currentStumble.numreviews)
}}if(this.currentStumble.sponsored){$("#sponsored").show()
}},updateUI_User:function(){if(typeof initializedStateExt!="undefined"&&initializedStateExt){initializedStateExt=false;
return
}if(this.currentUser){if($("#SUwebTb").hasClass("loggedOut")){$("#SUwebTb").removeClass("loggedOut").addClass("loggedIn")
}$("#favorites a").html(this.currentUser.favorites+" favorites");
$("#favorites").css("background","none")
}else{if($("#SUwebTb").hasClass("loggedIn")){$("#SUwebTb").removeClass("loggedIn").addClass("loggedOut")
}}},performUrlLookup:function(b){var a=this;
suExtensionApi.litebar.getContentLocation(function(c){SU_ClientChromebar.superclass.getUrlInfo.call(a,c,function(d){if(d.info){b(d.info)
}else{b()
}})
})
},positionAndDisplayTooltip:function(a,d,c,g){if(typeof a=="boolean"){return false
}if(this.tipTimer){clearTimeout(this.tipTiper)
}var e=d.offset();
var b=d.width();
var f=function(){var j={contents:c.message,hidden:"no"};
if(c.config){j.config=c.config
}var h={left:e.left,top:33,width:300,height:150,zIndex:1};
if(e.left>$(window).width()-300){h.left=$(window).width()-300;
j.pointerPosition=300-($(window).width()-(e.left+(b/2)));
j.pointerPosition-=8
}else{h.left-=15;
j.pointerPosition=10+(b/2)
}suExtensionApi.message.postMessage(a,"litebarOverlayContents",{data:j,display:"tooltip"});
a.setPosition(h)
};
if(g){this.tipTimer=setTimeout(f,g)
}else{f()
}},positionAndDisplayPanel:function(b,d,c,f){var a=this;
var e={contents:c.message,hidden:"no"};
c.config.noPointer=true;
if(c.config){e.config=c.config
}suExtensionApi.message.postMessage(b,"litebarOverlayContents",{data:e,display:"panel"});
b.setPosition(b.originalPosition)
},updateNumReferrals:function(a){if(a==0){a=""
}suExtensionApi.message.broadcastMessage("msgNewReferralCount",a);
suExtensionApi.message.broadcastMessage("extNewReferralCount",a);
suExtensionApi.message.broadcastMessage("localUpdateReferralCount",a);
this.currentReferralCount=a
},displayMessage:function(e){if(e.display=="tooltip"){var d=new SUJS.Model.Message();
d.setMessage(e.message).setSubject(e.subject);
if(e.image){d.setImage(e.image)
}d.config=e.config;
var c=e.subject;
this.positionAndDisplayTooltip(this.messageTipOverlay,c,d);
this.messageTipShowing=true
}else{if(e.display=="panel"){if(e.url){var b=this;
var a=new SUJS.Model.Message();
a.setMessage(e);
a.config={};
b.spawnRegisteredViewPane({id:"urlPanelOverlay",url:e.url,dimensions:{left:0,top:32,width:$(window).width(),height:e.height},reloadOnShow:true},function(){})
}else{if(this.messagePanelOverlay.userHid){return false
}var d=new SUJS.Model.Message();
d.setMessage(e.message);
d.config=e.config;
var c=e.subject;
this.positionAndDisplayPanel(this.messagePanelOverlay,c,d)
}}}},hideShareMessage:function(){if(this.messagePanelOverlay&&(typeof this.messagePanelOverlay.setPosition!="undefined")){this.messagePanelOverlay.setPosition({width:0,height:0})
}},displayShareMessage:function(){var b=this.currentStumble;
if(b.referral_note){var a=b.referral_nick+" says: "+b.referral_note
}else{var a=b.referral_nick+" sent this to you."
}$("#friends").show();
$("#friends a").html("<span></span>"+b.referral_nick);
this.displayMessage({display:"panel",message:a,subject:$("#friends"),config:{permanence:"close",buttons:[{label:"Reply",action:"replyReferralNote",style:"plain"},{label:"Close",action:"closeReferralNote",style:"plain"}]}})
},displayRegistrationMessage:function(a){this.hideHoverTip();
this.displayMessage({display:"tooltip",message:"Please <a href=\"javascript:buttonAction('loginLinkClick');\">login</a> or <a href=\"javascript:buttonAction('signupLinkClick');\">sign up</a> to use this",subject:$(a),config:{permanence:"close"}})
},showHoverTip:function(b){if(this.currentNodeId==b.attr("id")){return
}this.currentNodeId=b.attr("id");
if(this.hoverTipOverlay){if(b.attr("title")){b.data("title",b.attr("title"));
b.attr("title","")
}if(this.messageTipShowing){return
}var a=new SUJS.Model.Message();
a.setMessage(b.data("title")).setSubject(b);
this.positionAndDisplayTooltip(this.hoverTipOverlay,b,a,800)
}},hideHoverTip:function(a){if(typeof this.hoverTipOverlay.setPosition=="undefined"){return false
}this.hoverTipOverlay.setPosition({width:0,height:0});
this.currentNodeId=null;
suExtensionApi.message.postMessage(this.hoverTipOverlay,"litebarOverlayContents",{data:{hidden:"yes"},display:"tooltip"});
if(this.tipTimer){clearTimeout(this.tipTimer)
}},showViewPane:function(d,c){if(!c){for(var b in this.viewPanes){if((typeof this.viewPanes[b]!="undefined")&&(typeof this.viewPanes[b].originalPosition!="undefined")){if(this.viewPanes[b].id!=d.id){this.hideViewPane(this.viewPanes[b])
}}}}if(typeof d.noDisplace=="undefined"||!d.noDisplace){var a=32+d.originalPosition.height;
suExtensionApi.litebar.setHeight(a+"px")
}if($(window).width()!=d.originalPosition.width&&d.noWidthResize!=true){d.originalPosition.width=$(window).width()
}d.visible=true;
d.setPosition(d.originalPosition)
},hideViewPane:function(c,b){if((typeof c=="undefined")||!c){return false
}var a={height:0,width:0};
c.getPosition(function(d){if(c.visible){c.originalPosition=d;
c.visible=false;
c.setPosition(a);
if(b){b(c)
}}});
suExtensionApi.litebar.setHeight("32px")
},hideAllViewPanes:function(d){for(var c in this.viewPanes){if(typeof this.viewPanes[c].originalPosition!="undefined"){this.hideViewPane(this.viewPanes[c])
}}this.hoverTipOverlay.setPosition({width:0,height:0,left:0,top:0});
this.messageTipOverlay.setPosition({width:0,height:0,left:0,top:0});
var a=this;
var b=function(){var f=0;
var g=false;
for(var e in a.viewPanes){if(a.viewPanes[e].visible){g=true
}f++
}if(g){setTimeout(b,1)
}else{d()
}};
b()
},setupRegisteredViewPane:function(b,c){var a=this;
suExtensionApi.overlay.create(b.url,function(d){d.originalPosition=b.dimensions;
d.setPosition({width:0,height:0});
if(b.noDisplace){d.noDisplace=true
}if(b.noWidthResize){d.noWidthResize=true
}a.viewPanes[b.id]=d;
if(c){c(d)
}})
},spawnRegisteredViewPane:function(b,c){if(!b.id){return false
}if(this.viewPanes[b.id]===true&&b.reloadOnShow!=true){return true
}if(this.viewPanes[b.id]&&!this.viewPanes[b.id].pending&&!this.viewPanes[b.id].visible&&b.reloadOnShow==true){this.viewPanes[b.id]=undefined
}var a=this;
if(typeof this.viewPanes[b.id]=="undefined"){this.viewPanes[b.id]=true;
this.setupRegisteredViewPane(b,function(d){a.showViewPane(d);
if(c){c(d)
}})
}else{if(this.viewPanes[b.id].pending){return true
}this.viewPanes[b.id].pending=true;
if(this.viewPanes[b.id].visible){this.hideViewPane(this.viewPanes[b.id])
}else{this.showViewPane(this.viewPanes[b.id])
}if(c){c(this.viewPanes[b.id])
}this.viewPanes[b.id].pending=false
}},toggleMailUI:function(){this.toggleShareUI("email")
},toggleShareUI:function(c){if(!c){c="share"
}if(this.currentStumble){var b=this.server+"/share/"+this.currentStumble.publicid+"/?src=chromebar";
var d="share";
if(c=="email"){b+="&email=1&mode=email";
d="emailShare"
}else{if(c=="facebook"){b+="&mode=facebook";
d="facebookShare"
}else{b+="&mode=toStumbler"
}}this.spawnRegisteredViewPane({id:d,url:b,reloadOnShow:true,dimensions:{left:0,top:32,width:$(window).width(),height:285}},function(e){e.emailMode=(c=="email")
})
}else{var a=this;
this.performUrlLookup(function(f){if(f&&f.known){var e=a.server+"/share/"+f.publicid+"/?src=chromebar";
var g="share";
if(c=="email"){e+="&email=1&mode=email";
g="emailShare"
}else{if(c=="facebook"){e+="&mode=facebook";
g="facebookShare"
}else{e+="&mode=toStumbler"
}}a.setActiveStumble(f);
a.spawnRegisteredViewPane({id:g,url:e,dimensions:{left:0,top:32,width:$(window).width(),height:285}},function(h){h.emailMode=(c=="email")
})
}else{if(f&&f.url){var e=a.server+"/share/0/?src=chromebar&url="+encodeURIComponent(f.url);
var g="share";
if(c=="email"){e+="&email=1&mode=email";
g="emailShare"
}else{if(c=="facebook"){e+="&mode=facebook";
g="facebookShare"
}}a.spawnRegisteredViewPane({id:g,url:e,dimensions:{left:0,top:32,width:$(window).width(),height:285}},function(h){h.emailMode=(c=="email")
})
}else{a.displayMessage({display:"tooltip",message:"At the moment, we don't allow sharing sites that are not in StumbleUpon, but we will be adding this in the near future",subject:$("#share"),config:{permanence:"fade10"}})
}}})
}},toggleTopicSelectorUI:function(){var a=this;
if(this.currentUser){var b=this.server+"/toolbar/topicselector.php?src="+this.source;
this.spawnRegisteredViewPane({id:"topics",url:b,dimensions:{left:0,top:32,width:$(window).width(),height:275}},function(c){})
}else{this.displayRegistrationMessage("#topics")
}},toggleShowMeMoreUI:function(){if(this.currentUser&&this.currentStumble){var a=this.server+"/toolbar/showmemoremenu.php?src="+this.source+"&pid="+encodeURIComponent(this.currentStumble.publicid)+"&discoverer="+encodeURIComponent(this.currentStumble.discoverer)+"&discoverer_nick="+encodeURIComponent(this.currentStumble.discoverer_nick);
if(this.currentStumble.sponsored){a+="&sponsored=1"
}var b=$("#showMeMore").offset();
this.spawnRegisteredViewPane({id:"showMeMore",url:a,dimensions:{left:b.left,top:32,width:300,height:110},noDisplace:true,noWidthResize:true},function(c){})
}},toggleThumbDownMoreUI:function(){var a=this;
this.setupApi(function(){a._toggleThumbDownMoreUI()
})
},_toggleThumbDownMoreUI:function(){if(this.currentUser&&this.currentStumble){var b=this.server+"/toolbar/thumbdownmenu.php?device="+encodeURIComponent(this.device)+"&auth="+encodeURIComponent(this.keychain.authString)+"&src="+this.source+"&url="+encodeURIComponent(this.currentStumble.url);
var c=$("#notLike").offset();
this.spawnRegisteredViewPane({id:"thumbDownMore",url:b,dimensions:{left:c.left,top:32,width:300,height:110},noDisplace:true,noWidthResize:true},function(d){})
}else{if(this.currentUser){var a=this;
this.performUrlLookup(function(e){if(e&&e.known){var d=a.server+"/toolbar/thumbdownmenu.php?device="+encodeURIComponent(this.device)+"&auth="+encodeURIComponent(this.keychain.authString)+"&src=chromebar&url="+encodeURIComponent(e.url);
a.setActiveStumble(e);
a.spawnRegisteredViewPane({id:"thumbDownMore",url:d,dimensions:{left:250,top:32,width:200,height:200},noDisplace:true,noWidthResize:true},function(f){})
}else{a.displayMessage({display:"tooltip",message:"At the moment, we don't allow rating sites that are not in StumbleUpon, but we will be adding this in the near future",subject:$("#notLikeMore"),config:{permanence:"fade10"}})
}})
}}},litebarLogout:function(){var a=this;
this.performLogout(true,function(){suExtensionApi.litebar.getContentLocation(function(b){if(b.indexOf(a.server)==0){top.location.reload()
}})
});
a.messageTipOverlay.setPosition({width:0,height:0,left:0,top:0});
return false
},performLogout:function(b,c){var a=this;
var e=null;
var d=function(){a.setActiveStumble(null);
suExtensionApi.message.broadcastMessage("litebarLogout",true);
a.resetStumblingMode();
a.setModeLabel("All Interests");
if(c){c()
}};
if(b){e=function(){a.logoutWebsite(function(){d()
})
}
}else{e=function(){d()
}
}this.clearKeychain(e);
this.updateNumReferrals(0);
this.setActiveUser(null);
this.unbindLoggedInHandlers();
this.hideShareMessage();
this.stumbleFinished();
suExtensionApi.litebar.setHeight("32px");
this.updateUI()
},toggleLoginUI:function(){this.primeLoginPanel(function(a){})
},primeLoginPanel:function(b){var a=this.server+"/toolbar/loginbox.php?src="+this.source;
this.spawnRegisteredViewPane({id:"login",url:a,noDisplace:true,dimensions:{left:0,top:32,width:450,height:175}},function(c){if(b){b(c)
}})
},setData:function(a,b,c){suExtensionApi.data.setValue(a,b,c)
},getData:function(a,b){suExtensionApi.data.getValue(a,function(c){if(c){if(typeof c=="string"){try{c=JSON.parse(c)
}catch(d){}}b(c)
}else{b()
}})
},getLocation:function(a){suExtensionApi.litebar.getContentLocation(function(b){if(a){a(b)
}})
},updateUI_CacheInterfaceState:function(){var a="";
if(this.currentUser){a+="1|"+this.currentUser.favorites+"|"+this.currentReferralCount+"|"+this.currentLabel
}else{a+="0|0|0"
}this.uiStateCookie.setValue(a);
this.uiStateCookie.save()
},lastthing:""};
SU_ClientChromebar.prototype=new SU_ClientLitebar();
SU_ClientChromebar.superclass=SU_ClientLitebar.prototype;
for(var prop in SU_ClientChromebar_prototype){SU_ClientChromebar.prototype[prop]=SU_ClientChromebar_prototype[prop]
};
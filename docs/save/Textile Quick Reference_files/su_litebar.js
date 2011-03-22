var SU_ClientLitebar=function(){this.api=new SU_Client();
this.apiInitialized=false;
this.currentStumble=null;
this.currentStaticUrl=null;
this.stumblingMode={};
this.currentUser=null;
this.sidecarData=null;
this.sidepanel=null;
this.errorState=null;
this.keychain=null;
this.credentialsFetchedTime=0;
this.data={};
this.timers=null;
return true
};
SU_ClientLitebar.prototype={init:function(){this.ensureKeychainExists();
this.bindListeners();
if(!this.timers){this.timers=new SU_Timers();
this.timers.init();
if(this.timers.enabled){var b=this.timers.getSavedTimers();
if(b){var a=this;
this.setupApi(function(){if(a.currentUser&&a.currentUser.uid&&a.currentUser.loggedIn&&a.currentUser.uid==b.uid&&a.currentUser.uid>15041000){a.api.saveTimers(b.pid,b.data)
}})
}var a=this;
$(window).unload(function(){a.timers.addEvent("interrupted");
a.timers.saveTimersToDOMStorage()
})
}}},onStumblePageLoaded:function(){if(this.timers){this.timers.addEvent("pageLoaded")
}},bindListeners:function(){throw"Must implement bindListeners in implementation class"
},setData:function(a,b,c){throw"Must implement setData in implementation class"
},getData:function(a,b){throw"Must implement getData in implementation class"
},getLocation:function(a){throw"Must implement getLocation in implementation class"
},setKeychain:function(a){this.keychain=a;
this.setData("keychain",a)
},ensureKeychainExists:function(){throw"Must implement ensureKeychainExists in implementation class"
},handleSidecarData:function(a){if(!a||typeof a.auxData=="undefined"){return false
}this.sidecarData=a.auxData;
if(typeof a.auxData.updateClient!="undefined"){this.setData("performClientUpdate",1)
}},setAuth:function(a){this.keychain.authString=a
},setupApi:function(d,c){var a=this;
var b=function(){a.api.setDevice(a.device);
a.api.setSource(a.source);
a.api.setKeychain(a.keychain);
if(typeof a.clientVersion!="undefined"){a.api.setClientVersion(a.clientVersion)
}a.api.setScriptVersion(a.scriptVersion);
a.apiInitialized=true;
if(d){d()
}};
if(c){this.keychain=new SU_ClientRequestKeychain();
b()
}else{this.api.setDevice(this.device);
this.api.setSource(this.source);
this.ensureKeychainExists(function(){b()
})
}},performShareStumble:function(b){var a=this;
this.setActiveStumble(null);
this.setActiveStaticUrl(null);
this.setupApi(function(){a.api.getShare(function(c){a.setActiveStumble(c);
a.handleSidecarData(c);
a.updateUI();
if(b){b(c)
}})
})
},performStumble:function(b){var a=this;
this.setActiveStumble(null);
this.setActiveStaticUrl(null);
this.setupApi(function(){a.api.getStumble(a.stumblingMode,function(c){a.setActiveStumble(c);
a.handleSidecarData(c);
a.updateUI();
if(b){b(c)
}})
})
},performStumbleDirect:function(c,b){var a=this;
this.setActiveStumble(null);
this.setActiveStaticUrl(null);
this.setupApi(function(){var f=a.stumblingMode;
if(c){for(var e in c){f[e]=c[e]
}}f.stumbleRequest=new Date().getTime();
if(a.timers){a.timers.addEvent("nextStumbleClick");
a.timers.saveTimersToDOMStorage()
}var d=a.api.getStumble(f);
b(d)
})
},performRating:function(b,c){var a=this;
this.setupApi(function(){if(a.currentStumble){if(typeof a.currentStumble.rating!="undefined"&&a.currentStumble.rating==b){b=-1
}a.api.rate(a.currentStumble.publicid,b,c,a.currentStumble.autosharedToFacebook);
a.currentStumble.rating=b;
a.updateUI()
}})
},performSubRating:function(b,c){var a=this;
this.setupApi(function(){if(a.currentStumble){if(typeof a.currentStumble.subrating!="undefined"&&a.currentStumble.subrating==b){b=0
}a.api.subrate(a.currentStumble.publicid,b,c);
a.currentStumble.subrating=b;
if(b<0){a.currentStumble.rating=0
}else{a.currentStumble.rating=-1
}a.updateUI()
}})
},performBlockSite:function(b,c,d){var a=this;
this.setupApi(function(){if(a.currentStumble){if(typeof a.currentStumble.blocksite!="undefined"&&a.currentStumble.blocksite==c){c=(c==1?0:1)
}a.api.blocksite(a.currentStumble.publicid,b,c,d);
a.currentStumble.blocksite=c;
a.updateUI()
}})
},getUserData:function(b){var a=this;
this.setupApi(function(){a.api.getUserData(function(c){if(c){a.setActiveUser(c.info);
a.updateUI();
if(b){b(c)
}}})
})
},getRequestToken:function(b){this.setupApi();
var a=this;
this.api.getRequestToken(function(c){if(b){b(c)
}})
},getUrlInfo:function(b,c){var a=this;
this.setupApi(function(){a.api.getUrlInfo(b,c)
})
},checkForShares:function(a){this.setupApi();
this.api.checkForShares(a)
},sendShare:function(a,b){this.setupApi();
this.api.sendShare(a,b)
},logoutWebsite:function(a){this.setupApi();
this.api.logoutWebsite(a)
},trk:function(a){this.setupApi();
this.api.trk(a,function(){})
},showSidePanel:function(c,a,b){},hideSidePanel:function(b,a){},resetStumblingMode:function(a){this.stumblingMode={};
this.setData("stumblingMode",this.stumblingMode,a)
},setStumblingMode:function(a,b){this.stumblingMode=a;
this.setData("stumblingMode",this.stumblingMode,function(){if(b){b(a)
}})
},setStumblingMethod:function(c,a){this.resetStumblingMode();
this.stumblingMode.method=c;
delete this.stumblingMode.extra;
if(a){this.stumblingMode.extra={};
for(var b in a){this.stumblingMode.extra[b]=a[b]
}}},setStumblingTopic:function(a,b){if(a==0){this.setStumblingMethod("general")
}else{this.setStumblingMethod("topic")
}this.stumblingMode.topic=a;
this.setData("stumblingMode",this.stumblingMode,b)
},getActiveStumble:function(){return this.currentStumble
},setActiveStumble:function(d){if(this.timers&&this.timers.enabled&&d&&d.publicid&&this.currentUser&&this.currentUser.uid&&this.currentUser.uid>0){this.timers.setPidAndUid(d.publicid,this.currentUser.uid);
if(this.timers.hasEvent("stumbleRequest")==false){var c=new SUJS.Model.Cookie("savedStumbleRequest","1");
c.read();
var a=c.value?c.value.split("|"):[];
if(a!=null&&a.length==2&&a[0]==d.publicid){this.timers.addEvent("stumbleRequest",a[1])
}}}this.currentStaticUrl=null;
this.currentStumble=d;
if(this.stumblingMode&&this.stumblingMode.method=="instumbler"&&this.stumblingMode.stumbler=="4777893"){var b="";
if(this.currentStumble&&this.currentStumble.url){b=this.currentStumble.url
}this.showSidePanel(this.stumblingMode.stumbler,b,false)
}},setActiveStaticUrl:function(a){this.currentStumble=null;
this.currentStaticUrl=a
},setActiveUser:function(a){this.currentUser=a
},setModeLabel:function(a,b){$("#topics a").html(a);
if(a=="All Interests"){$("#topics").removeClass("cancel").addClass("default")
}else{$("#topics").removeClass("default").addClass("cancel")
}this.setData("stumblingModeLabel",a,b)
},autoShareToFacebook:function(b){var a=this;
a.setupApi(function(){if(a.currentStumble&&a.currentStumble.url&&a.currentStumble.publicid&&!a.currentStumble.autosharedToFacebook){a.api.autoShareFacebook(a.currentStumble.publicid,a.currentStumble.url);
a.currentStumble.autosharedToFacebook=true
}})
},showMeMore:function(a){if(a.user_id){this.resetStumblingMode();
this.setStumblingMode({method:"instumbler",stumbler:a.user_id});
this.setModeLabel(a.user_name+"'s favorites")
}else{if(a.topic_id){this.setStumblingTopic(a.topic_id);
this.setModeLabel(a.topic_name)
}else{if(a.url_domain){this.resetStumblingMode();
this.setStumblingMode({method:"stumblethru",partner:a.url_domain});
this.setModeLabel(a.url_domain)
}}}},updateUI:function(){if(this.errorState){this.updateUI_Error()
}if(this.currentStumble){this.updateUI_StumbleUrl();
this.updateUI_StumbleMeta()
}this.updateUI_User();
if(this.sidecarData){for(var a=0;
a<this.sidecarData.length;
a++){var b=this.sidecarData[a];
if(b.type=="message"){this.displayMessage(b)
}}}this.updateUI_CacheInterfaceState()
},updateUI_StumbleUrl:function(){throw"Must implement updateUI_StumbleUrl"
},updateUI_StumbleMeta:function(){throw"Must implement updateUI_StumbleMeta"
},handleErrorState:function(a){return false
},debugText:function(a){if(typeof console!="undefined"){console.log(a)
}},gotoUrlPage:function(){if(this.currentStumble){this.gotoUrl(this.currentStumble.info_link)
}else{var a=this;
this.performUrlLookup(function(b){if(b){if(b.known){a.gotoUrl(b.info_link)
}else{a.displayMessage({display:"tooltip",message:"At the moment, we don't know about this site. Thumb it up to discover it!",subject:$("#reviews"),config:{permanence:"fade10"}})
}}})
}},ratingCallback:function(a,b){if(a.new_rating==-1){if(this.currentUser){this.currentUser.favorites--;
this.setData("userInfo",this.currentUser)
}}else{if(this.currentUser){this.currentUser.favorites++;
this.setData("userInfo",this.currentUser)
}else{this.setData("pendingRating",{publicid:this.currentStumble.publicid,rating:1});
this.toggleLoginUI()
}}this.updateUI_User();
if(b){b()
}},toggleTwitterShareUI:function(){new SUJS.Util().toggleShareUI(this,"twitter")
},toggleLinkedInShareUI:function(){new SUJS.Util().toggleShareUI(this,"linkedin")
},toggleFacebookShareUI:function(){new SUJS.Util().toggleShareUI(this,"facebook")
},userInterfaceEvents:{buttonTooltipClick:{target:".tips",action:"click.tips",handler:function(a){a.data.instance.hideHoverTip($(this))
}},stumble:{target:"#stumble",action:"click",handler:function(a){a.data.instance.performStumble();
return false
}},thumbUp:{target:"#like",action:"click",handler:function(c){try{if(typeof c.data.instance.viewPanes.thumbDownMore!="undefined"){if(typeof c.data.instance.hideViewPane!="undefined"){c.data.instance.hideViewPane(c.data.instance.viewPanes.thumbDownMore);
c.data.instance.viewPanes.thumbDownMore=undefined
}else{c.data.instance.viewPanes.thumbDownMore.hide()
}}if(typeof c.data.instance.viewPanes.showMeMore!="undefined"){if(typeof c.data.instance.hideViewPane!="undefined"){c.data.instance.hideViewPane(c.data.instance.viewPanes.showMeMore);
c.data.instance.viewPanes.showMeMore=undefined
}else{c.data.instance.viewPanes.showMeMore.hide()
}}}catch(b){}if(c.data.instance.currentStumble){if(typeof c.data.instance.currentStumble.known!="undefined"&&c.data.instance.currentStumble.known==false){c.data.instance.gotoUrl("/submit?url="+encodeURIComponent(c.data.instance.currentStumble.url))
}else{var a=c.data.instance;
c.data.instance.performRating(1,function(d){a.ratingCallback(d)
})
}}else{var a=c.data.instance;
c.data.instance.performUrlLookup(function(d){if(d.known){a.setActiveStumble(d);
a.performRating(1,function(e){a.ratingCallback(e)
})
}else{a.gotoUrl("/submit?url="+encodeURIComponent(d.url))
}})
}return false
}},showMeMore:{target:"#showMeMore",action:"click",handler:function(a){if(a.data.instance.currentStumble){a.data.instance.toggleShowMeMoreUI()
}}},thumbDown:{target:"#notLike",action:"click",handler:function(b){try{if(typeof b.data.instance.viewPanes.thumbDownMore!="undefined"){if(typeof b.data.instance.hideViewPane!="undefined"){b.data.instance.hideViewPane(b.data.instance.viewPanes.thumbDownMore);
b.data.instance.viewPanes.thumbDownMore=undefined
}else{b.data.instance.viewPanes.thumbDownMore.hide()
}}}catch(a){}if(b.data.instance.currentStumble){if(typeof b.data.instance.currentStumble.known!="undefined"&&b.data.instance.currentStumble.known==false){b.data.instance.displayMessage({display:"tooltip",message:"Nobody has discovered this page yet, so it cannot be thumbed down",subject:$("#notLike"),config:{permanence:"fade10"}})
}else{b.data.instance.performRating(0)
}}else{b.data.instance.performUrlLookup(function(c){if(c.known){b.data.instance.setActiveStumble(c);
b.data.instance.performRating(0)
}else{b.data.instance.displayMessage({display:"tooltip",message:"Nobody has discovered this page yet, so it cannot be thumbed down",subject:$("#notLike"),config:{permanence:"fade10"}})
}})
}return false
}},thumbDownMoreToggle:{target:"#notLikeMore",action:"click",handler:function(a){if(a.data.instance.currentStumble){if(typeof a.data.instance.currentStumble.known!="undefined"&&a.data.instance.currentStumble.known==false){a.data.instance.displayMessage({display:"tooltip",message:"Nobody has discovered this page yet, so it cannot be thumbed down",subject:$("#notLikeMore"),config:{permanence:"fade10"}})
}else{a.data.instance.toggleThumbDownMoreUI()
}}else{a.data.instance.performUrlLookup(function(b){if(b.known){a.data.instance.setActiveStumble(b);
a.data.instance.toggleThumbDownMoreUI()
}else{a.data.instance.displayMessage({display:"tooltip",message:"Nobody has discovered this page yet, so it cannot be thumbed down",subject:$("#notLikeMore"),config:{permanence:"fade10"}})
}})
}return false
}},shareMenuToggle:{target:"#share",action:"click",handler:function(a){a.data.instance.toggleShareUI();
a.data.instance.trk("normalShareClick");
return false
}},topicsMenuToggle:{target:"#topics",action:"click",handler:function(a){a.data.instance.toggleTopicSelectorUI();
return false
}},topicDeselection:{target:"#topics.cancel .bgImgRight",action:"click",handler:function(a){if($("#topics").hasClass("cancel")){a.data.instance.resetStumblingMode();
a.data.instance.setStumblingMode({});
a.data.instance.setModeLabel("All Interests");
if(a.data.instance.device=="chromebar"){suExtensionApi.message.broadcastMessage("localUpdateStumblingTopic",{id:0,name:"All Interests"})
}if(typeof a.data.instance.hideViewPane!="undefined"){a.data.instance.hideViewPane(a.data.instance.viewPanes.topics)
}a.data.instance.performStumble()
}else{a.data.instance.toggleTopicSelectorUI()
}return false
}},seeUrlInfo:{target:"#reviews",action:"click",handler:function(a){a.data.instance.gotoUrlPage();
return false
}},facebookShare:{target:"#facebook",action:"click",handler:function(a){a.data.instance.toggleFacebookShareUI();
return false
}},twitterShare:{target:"#twitter",action:"click",handler:function(a){a.data.instance.toggleTwitterShareUI();
return false
}},linkedinShare:{target:"#linkedin",action:"click",handler:function(a){a.data.instance.toggleLinkedInShareUI();
return false
}},emailShare:{target:"#email",action:"click",handler:function(a){a.data.instance.toggleMailUI();
a.data.instance.trk("emailShareClick");
return false
}},showUserInfo:{target:"#friends",action:"click",handler:function(a){}},sponsoredPage:{target:"#sponsored",action:"click",handler:function(a){}},login:{target:"#login",action:"click",handler:function(a){a.data.instance.toggleLoginUI();
return false
}},logout:{target:"#logout",action:"click",handler:function(a){a.data.instance.displayMessage({display:"tooltip",message:"Are you sure?",subject:$(this),config:{permanence:"close",buttons:[{label:"Yes, I know my password",action:"logoutConfirmClicked",style:"plain"}]}});
return false
}},favoritesLoggedOut:{target:"#favorites_l",action:"click",handler:function(a){a.data.instance.gotoUrl("/signup/?pre=wtb_savefavs");
return false
}},favoritesLoggedOutVideo:{target:"#favorites_l_video",action:"click",handler:function(a){a.data.instance.gotoUrl("/signup/?pre=video_wtb_savefavs");
return false
}},favoritesLoggedIn:{target:"#favorites",action:"click",handler:function(a){a.data.instance.gotoUrl("/favorites/");
return false
}},homeButton:{target:"#goHome",action:"click",handler:function(a){a.data.instance.gotoUrl("/");
return false
}},closeButton:{target:"#close",action:"click",handler:function(a){if(a.data.instance.currentStumble){a.data.instance.gotoUrl(a.data.instance.currentStumble.url)
}else{a.data.instance.gotoUrl("/")
}return false
}},buttonTooltipOn:{target:".tips",action:"mouseenter.tips",handler:function(a){a.data.instance.showHoverTip($(this))
}},buttonTooltipOff:{target:".tips",action:"mouseleave.tips",handler:function(a){a.data.instance.hideHoverTip($(this))
}}}};
var SU_Timers=function(){this.pid=null;
this.uid=null;
this.data=[];
this.dataHash={};
this.enabled=("localStorage" in window&&window.localStorage!==null);
this.noDuplicates={stumbleRequest:1,stumbleResponse:1,interrupted:1};
return true
};
SU_Timers.prototype={init:function(a){this.addEvent("stumbleResponse");
this.addEvent("startLoad")
},reset:function(){if(!this.enabled){return false
}this.pid=null;
this.data=[];
this.dataHash={};
return true
},setPidAndUid:function(a,b){if(!this.enabled){return
}this.pid=a;
this.uid=b
},addEvent:function(a,b){if(!this.enabled){return
}if(!b){b=new Date().getTime()
}if(a in this.noDuplicates&&this.hasEvent(a)){return
}if(a=="interrupted"&&this.hasEvent("nextStumbleClick")){return
}if(a=="stumbleRequest"){this.data.unshift({event:a,timestamp:b})
}else{this.data.push({event:a,timestamp:b})
}this.dataHash[a]=b
},hasEvents:function(){return this.data.length>0
},hasEvent:function(a){if(!this.enabled||(typeof this.dataHash[a]=="undefined")){return false
}return true
},toJSONString:function(){var b="[";
for(i in this.data){if(i>0){b+=", "
}b+="{";
var a=0;
for(p in this.data[i]){if(a++>0){b+=", "
}b+='"'+p+'":"'+this.data[i][p]+'"';
a++
}b+="}"
}b+="]";
return b
},saveTimersToDOMStorage:function(){if(!this.enabled||!this.pid||!this.uid||this.hasEvents()==0){return false
}window.localStorage.setItem("saved_data",this.toJSONString());
window.localStorage.setItem("saved_pid",this.pid);
window.localStorage.setItem("saved_uid",this.uid);
return true
},getSavedTimers:function(){if(!this.enabled){return null
}var a=window.localStorage.getItem("saved_pid");
var c=window.localStorage.getItem("saved_uid");
var b=window.localStorage.getItem("saved_data");
if(!a||!b){return null
}window.localStorage.removeItem("saved_pid");
window.localStorage.removeItem("saved_uid");
window.localStorage.removeItem("saved_data");
return{pid:a,uid:c,data:b}
}};
var SU_ClientRequestKeychain=function(){this.tokens={stumble:"",request:""};
this.tokenExpiration=0;
this.authString="";
this.OAuth={};
this.tokensExpired=function(){var a=Math.round(new Date().getTime()/1000);
if(a>this.tokenExpiration){return true
}return false
};
this.setTokenExpiration=function(a){var b=Math.round(new Date().getTime()/1000);
this.tokenExpiration=b+a
}
};
var SU_Client=function(){this.keychain=new SU_ClientRequestKeychain();
return true
};
SU_Client.prototype={version:1,device:"",source:"",clientVersion:"",scriptVersion:"",keychain:null,responseFilter:null,rate:function(c,a,d,b){if(!d){d=function(e){}
}this.makeRequest({action:"simple-rate",pid:c,rating:a,noFacebookAutoshare:b},d)
},subrate:function(b,a,c){if(!c){c=function(d){}
}this.makeRequest({action:"simple-subrate",pid:b,subrating:a},c)
},blocksite:function(c,a,b,d){if(!d){d=function(e){}
}this.makeRequest({action:"blocksite",pid:c,url:a,blocksite:b},d)
},askAutoShareFacebook:function(a){if(!a){return
}this.makeRequest({action:"askAutoShareFacebook"},a)
},autoShareFacebook:function(b,a,c){if(!c){c=function(d){}
}this.makeRequest({action:"autoShareFacebook",pid:b,url:a},c)
},saveTimers:function(b,a,c){if(!c){c=function(d){}
}this.makeRequest({action:"timers",pid:b,timers:a},c)
},getStumble:function(d,e){var c={};
for(var b in d){if(b=="extra"){for(var a in d[b]){c[a]=d[b][a]
}}else{c[b]=d[b]
}}c.action="stumble";
return this.makeRequest(c,e)
},getShare:function(b){var a={};
a.action="referral";
return this.makeRequest(a,b)
},getUserData:function(a){this.makeRequest({action:"userdata"},a)
},getUrlInfo:function(a,b){this.makeRequest({action:"urlInfo",url:a},b)
},getUserTopics:function(){this.makeRequest({action:"getTopics"},callback)
},checkForShares:function(a){this.makeRequest({action:"getFriendShares"},a)
},getRequestToken:function(a){this.makeRequest({action:"getState"},a)
},getAuthString:function(a){this.makeRequest({action:"getAuth"},a)
},logoutWebsite:function(a){this.makeRequest({action:"logoutWebsite",logout:1},a)
},trk:function(a){this.makeRequest({action:"rec",metric:a},function(){})
},sendShare:function(a,b){this.makeRequest({action:"send",in_reply_to:a.referralId,msg:a.text,url:a.url},b)
},sendEmailShare:function(a,b){throw"Not yet implemented"
},setDevice:function(a){this.device=a
},setSource:function(a){this.source=a
},setClientVersion:function(a){this.clientVersion=a
},setScriptVersion:function(a){this.scriptVersion=a
},setKeychain:function(a){this.keychain=a
},setParam:function(b,a){this.params[b]=a
},setResponseFilter:function(a){this.responseFilter=a
},setupRequestParams:function(){var a={src:this.source,device:this.device,v:this.clientVersion,sv:this.scriptVersion};
return a
},setAuth:function(a){this.keychain.authString=a
},makeRequest:function(f,h){var b=new SU_ClientApiConnector();
var e=new SU_ClientRequest();
if(f.action&&f.action=="getState"){}var d={logoutWebsite:true,send:true,timers:true};
if(f.action&&(f.action in d)){e.type="POST"
}e.params=this.setupRequestParams();
for(var c in f){e.params[c]=f[c]
}var g=h;
if(this.responseFilter){var a=this;
g=function(i){if(a.responseFilter(f,i)&&h){h(i)
}}
}return b.getResponse(e,this.keychain,g)
}};
var SU_ClientApiConnector=function(){this.endpoints={stumble:"/toolbar/next_stumble.php",referral:"/toolbar/next_stumble.php",logoutWebsite:"/toolbar/loginservices.php",send:"/toolbar/shareservices.php",def:"/toolbar/ajax_webtb.php"};
this.errorHandler=function(){};
this.getResponse=function(c,g,f){if(!g){throw"Must provide credentials for request"
}var e=this.endpoints.def;
if(typeof c.params.action!="undefined"){if(typeof this.endpoints[c.params.action]!="undefined"){e=this.endpoints[c.params.action]
}}if(g.authString){c.params.auth=g.authString
}if(typeof c.params.action!="undefined"&&c.params.action=="stumble"){c.params.t=g.tokens.stumble
}else{c.params.t=g.tokens.request
}if(f){c.params.output="json";
$.ajax({url:e,type:c.type,data:c.params,dataType:"json",async:c.async,success:f,error:this.errorHandler})
}else{var d=[];
for(var b in c.params){d.push(b+"="+c.params[b])
}var a=e+"?"+d.join("&");
return a
}}
};
var SU_ClientApiConnector_REST=function(){this.endpoints={stumble:"",rate:"",getTopics:""}
};
SU_ClientApiConnector_REST.prototype=new SU_ClientApiConnector();
var SU_ClientRequest=function(){this.type="GET";
this.params={};
this.async=true;
this.defaultParams={src:"litebar"}
};
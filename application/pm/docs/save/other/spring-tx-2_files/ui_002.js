(function(A){A.widget("ui.slider",A.extend({},A.ui.mouse,{_init:function(){var B=this,C=this.options;this._keySliding=false;this._handleIndex=null;this._detectOrientation();this._mouseInit();this.element.addClass("ui-slider ui-slider-"+this.orientation+" ui-widget ui-widget-content ui-corner-all");this.range=A([]);if(C.range){if(C.range===true){this.range=A("<div></div>");if(!C.values){C.values=[this._valueMin(),this._valueMin()]}if(C.values.length&&C.values.length!=2){C.values=[C.values[0],C.values[0]]
}}else{this.range=A("<div></div>")}this.range.appendTo(this.element).addClass("ui-slider-range");if(C.range=="min"||C.range=="max"){this.range.addClass("ui-slider-range-"+C.range)}this.range.addClass("ui-widget-header")}if(A(".ui-slider-handle",this.element).length==0){A('<a href="#"></a>').appendTo(this.element).addClass("ui-slider-handle")}if(C.values&&C.values.length){while(A(".ui-slider-handle",this.element).length<C.values.length){A('<a href="#"></a>').appendTo(this.element).addClass("ui-slider-handle")
}}this.handles=A(".ui-slider-handle",this.element).addClass("ui-state-default ui-corner-all");this.handle=this.handles.eq(0);this.handles.add(this.range).filter("a").click(function(D){D.preventDefault()}).hover(function(){A(this).addClass("ui-state-hover")},function(){A(this).removeClass("ui-state-hover")}).focus(function(){A(".ui-slider .ui-state-focus").removeClass("ui-state-focus");A(this).addClass("ui-state-focus")}).blur(function(){A(this).removeClass("ui-state-focus")});this.handles.each(function(D){A(this).data("index.ui-slider-handle",D)
});this.handles.keydown(function(I){var F=true;var E=A(this).data("index.ui-slider-handle");if(B.options.disabled){return }switch(I.keyCode){case A.ui.keyCode.HOME:case A.ui.keyCode.END:case A.ui.keyCode.UP:case A.ui.keyCode.RIGHT:case A.ui.keyCode.DOWN:case A.ui.keyCode.LEFT:F=false;if(!B._keySliding){B._keySliding=true;A(this).addClass("ui-state-active");B._start(I,E)}break}var G,D,H=B._step();if(B.options.values&&B.options.values.length){G=D=B.values(E)}else{G=D=B.value()}switch(I.keyCode){case A.ui.keyCode.HOME:D=B._valueMin();
break;case A.ui.keyCode.END:D=B._valueMax();break;case A.ui.keyCode.UP:case A.ui.keyCode.RIGHT:if(G==B._valueMax()){return }D=G+H;break;case A.ui.keyCode.DOWN:case A.ui.keyCode.LEFT:if(G==B._valueMin()){return }D=G-H;break}B._slide(I,E,D);return F}).keyup(function(E){var D=A(this).data("index.ui-slider-handle");if(B._keySliding){B._stop(E,D);B._change(E,D);B._keySliding=false;A(this).removeClass("ui-state-active")}});this._refreshValue()},destroy:function(){this.handles.remove();this.range.remove();
this.element.removeClass("ui-slider ui-slider-horizontal ui-slider-vertical ui-slider-disabled ui-widget ui-widget-content ui-corner-all").removeData("slider").unbind(".slider");this._mouseDestroy()},_mouseCapture:function(D){var E=this.options;if(E.disabled){return false}this.elementSize={width:this.element.outerWidth(),height:this.element.outerHeight()};this.elementOffset=this.element.offset();var H={x:D.pageX,y:D.pageY};var J=this._normValueFromMouse(H);var C=this._valueMax()-this._valueMin()+1,F;
var K=this,I;this.handles.each(function(L){var M=Math.abs(J-K.values(L));if(C>M){C=M;F=A(this);I=L}});if(E.range==true&&this.values(1)==E.min){F=A(this.handles[++I])}this._start(D,I);K._handleIndex=I;F.addClass("ui-state-active").focus();var G=F.offset();var B=!A(D.target).parents().andSelf().is(".ui-slider-handle");this._clickOffset=B?{left:0,top:0}:{left:D.pageX-G.left-(F.width()/2),top:D.pageY-G.top-(F.height()/2)-(parseInt(F.css("borderTopWidth"),10)||0)-(parseInt(F.css("borderBottomWidth"),10)||0)+(parseInt(F.css("marginTop"),10)||0)};
J=this._normValueFromMouse(H);this._slide(D,I,J);return true},_mouseStart:function(B){return true},_mouseDrag:function(D){var B={x:D.pageX,y:D.pageY};var C=this._normValueFromMouse(B);this._slide(D,this._handleIndex,C);return false},_mouseStop:function(B){this.handles.removeClass("ui-state-active");this._stop(B,this._handleIndex);this._change(B,this._handleIndex);this._handleIndex=null;this._clickOffset=null;return false},_detectOrientation:function(){this.orientation=this.options.orientation=="vertical"?"vertical":"horizontal"
},_normValueFromMouse:function(D){var C,H;if("horizontal"==this.orientation){C=this.elementSize.width;H=D.x-this.elementOffset.left-(this._clickOffset?this._clickOffset.left:0)}else{C=this.elementSize.height;H=D.y-this.elementOffset.top-(this._clickOffset?this._clickOffset.top:0)}var F=(H/C);if(F>1){F=1}if(F<0){F=0}if("vertical"==this.orientation){F=1-F}var E=this._valueMax()-this._valueMin(),I=F*E,B=I%this.options.step,G=this._valueMin()+I-B;if(B>(this.options.step/2)){G+=this.options.step}return parseFloat(G.toFixed(5))
},_start:function(D,C){var B={handle:this.handles[C],value:this.value()};if(this.options.values&&this.options.values.length){B.value=this.values(C);B.values=this.values()}this._trigger("start",D,B)},_slide:function(F,E,D){var G=this.handles[E];if(this.options.values&&this.options.values.length){var B=this.values(E?0:1);if((E==0&&D>=B)||(E==1&&D<=B)){D=B}if(D!=this.values(E)){var C=this.values();C[E]=D;var H=this._trigger("slide",F,{handle:this.handles[E],value:D,values:C});var B=this.values(E?0:1);
if(H!==false){this.values(E,D,(F.type=="mousedown"&&this.options.animate),true)}}}else{if(D!=this.value()){var H=this._trigger("slide",F,{handle:this.handles[E],value:D});if(H!==false){this._setData("value",D,(F.type=="mousedown"&&this.options.animate))}}}},_stop:function(D,C){var B={handle:this.handles[C],value:this.value()};if(this.options.values&&this.options.values.length){B.value=this.values(C);B.values=this.values()}this._trigger("stop",D,B)},_change:function(D,C){var B={handle:this.handles[C],value:this.value()};
if(this.options.values&&this.options.values.length){B.value=this.values(C);B.values=this.values()}this._trigger("change",D,B)},value:function(B){if(arguments.length){this._setData("value",B);this._change(null,0)}return this._value()},values:function(B,E,C,D){if(arguments.length>1){this.options.values[B]=E;this._refreshValue(C);if(!D){this._change(null,B)}}if(arguments.length){if(this.options.values&&this.options.values.length){return this._values(B)}else{return this.value()}}else{return this._values()
}},_setData:function(B,D,C){A.widget.prototype._setData.apply(this,arguments);switch(B){case"orientation":this._detectOrientation();this.element.removeClass("ui-slider-horizontal ui-slider-vertical").addClass("ui-slider-"+this.orientation);this._refreshValue(C);break;case"value":this._refreshValue(C);break}},_step:function(){var B=this.options.step;return B},_value:function(){var B=this.options.value;if(B<this._valueMin()){B=this._valueMin()}if(B>this._valueMax()){B=this._valueMax()}return B},_values:function(B){if(arguments.length){var C=this.options.values[B];
if(C<this._valueMin()){C=this._valueMin()}if(C>this._valueMax()){C=this._valueMax()}return C}else{return this.options.values}},_valueMin:function(){var B=this.options.min;return B},_valueMax:function(){var B=this.options.max;return B},_refreshValue:function(C){var F=this.options.range,D=this.options,L=this;if(this.options.values&&this.options.values.length){var I,H;this.handles.each(function(P,N){var O=(L.values(P)-L._valueMin())/(L._valueMax()-L._valueMin())*100;var M={};M[L.orientation=="horizontal"?"left":"bottom"]=O+"%";
A(this).stop(1,1)[C?"animate":"css"](M,D.animate);if(L.options.range===true){if(L.orientation=="horizontal"){(P==0)&&L.range.stop(1,1)[C?"animate":"css"]({left:O+"%"},D.animate);(P==1)&&L.range[C?"animate":"css"]({width:(O-lastValPercent)+"%"},{queue:false,duration:D.animate})}else{(P==0)&&L.range.stop(1,1)[C?"animate":"css"]({bottom:(O)+"%"},D.animate);(P==1)&&L.range[C?"animate":"css"]({height:(O-lastValPercent)+"%"},{queue:false,duration:D.animate})}}lastValPercent=O})}else{var J=this.value(),G=this._valueMin(),K=this._valueMax(),E=K!=G?(J-G)/(K-G)*100:0;
var B={};B[L.orientation=="horizontal"?"left":"bottom"]=E+"%";this.handle.stop(1,1)[C?"animate":"css"](B,D.animate);(F=="min")&&(this.orientation=="horizontal")&&this.range.stop(1,1)[C?"animate":"css"]({width:E+"%"},D.animate);(F=="max")&&(this.orientation=="horizontal")&&this.range[C?"animate":"css"]({width:(100-E)+"%"},{queue:false,duration:D.animate});(F=="min")&&(this.orientation=="vertical")&&this.range.stop(1,1)[C?"animate":"css"]({height:E+"%"},D.animate);(F=="max")&&(this.orientation=="vertical")&&this.range[C?"animate":"css"]({height:(100-E)+"%"},{queue:false,duration:D.animate})
}}}));A.extend(A.ui.slider,{getter:"value values",version:"1.7.1",eventPrefix:"slide",defaults:{animate:false,delay:0,distance:0,max:100,min:0,orientation:"horizontal",range:false,step:1,value:0,values:null}})})(jQuery);
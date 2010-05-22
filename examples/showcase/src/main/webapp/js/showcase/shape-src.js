dojo.provide("showcase.shape");

//父对象, 拥有_color属性与getColor()函数
dojo.declare("ShapeWidget", null, {
	_color : "",

	constructor : function(color) {
		this._color = color;
	},

	getColor : function() {
		return this._color;
	}
});

//子对象, 拥有半径属性与计算范围的函数, 同时继承父对象的Color属性与函数.
dojo.declare("CircleWidget", ShapeWidget, {
	_radius : 0,

	constructor : function(color, radius) {
		this._radius = radius;
	},

	getArea : function() {
		return Math.PI * this._radius * this._radius;
	},

	generateContent : function() {
		return "Shape is a circle with " + this.getColor() + " color," + this.getArea() + " area.";
	}
});

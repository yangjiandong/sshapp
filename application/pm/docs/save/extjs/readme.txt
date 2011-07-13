中文字号VS英文字号(磅)VS像素值的对应关系
----------------------------------------

八号＝5磅(5pt) ==(5/72)*96=6.67 =6px
七号＝5.5磅 ==(5.5/72)*96=7.3 =7px
小六＝6.5磅 ==(6.5/72)*96=8.67 =8px
六号＝7.5磅 ==(7.5/72)*96=10px
小五＝9磅 ==(9/72)*96=12px
五号＝10.5磅 ==(10.5/72)*96=14px
小四＝12磅 ==(12/72)*96=16px
四号＝14磅 ==(14/72)*96=18.67 =18px
小三＝15磅 ==(15/72)*96=20px
三号＝16磅 ==(16/72)*96=21.3 =21px
小二＝18磅 ==(18/72)*96=24px
二号＝22磅 ==(22/72)*96=29.3 =29px
小一＝24磅 ==(24/72)*96=32px
一号＝26磅 ==(26/72)*96=34.67 =34px
小初＝36磅 ==(36/72)*96=48px
初号＝42磅 ==(42/72)*96=56px

初号=42磅=14.82毫米
小初=36磅=12.70毫米
一号=26磅=9.17毫米
小一=24磅=8.47毫米
二号=22磅=7.76毫米
小二=18磅=6.35毫米
三号=16磅=5.64毫米
小三=15磅=5.29毫米
四号=14磅=4.94毫米
小四=12磅=4.23毫米
五号=10.5磅=3.70毫米
小五=9磅=3.18毫米
六号=7.5磅=2.56毫米
小六=6.5磅=2.29毫米
七号=5.5磅=1.94毫米
八号=5磅=1.76毫米

Ext.form.*使用技巧
------------------
http://liss.javaeye.com/blog/658086

设置表单控件为只读：
   1. setFieldReadOnly = function(f, bReadOnly) {
   2.     if (f instanceof Ext.form.ComboBox) {
   3.         f.setEditable(!bReadOnly);
   4.         if (bReadOnly) {
   5.             f.expandOrg = f.expand;
   6.             f.expand = function() {
   7.             };
   8.         } else {
   9.             f.expand = f.expandOrg;
  10.         }
  11.     } else {
  12.         f.getEl().dom.readOnly = bReadOnly;
  13.         if (bReadOnly)
  14.             f.getEl().addClass('x-form-readonly');
  15.         else
  16.             f.getEl().removeClass('x-form-readonly');
  17.     }
  18. };

抑制表单提交：
   1. disableEnter = function() {
   2.     Ext.select('input').on('keypress', onEnterkey);
   3.     Ext.select('select').on('keypress', onEnterkey);
   4. }
   5. onEnterkey = function(e) {
   6.     if (e.getKey() == e.ENTER && e.target.type != null) {
   7.         if (e.target.type != 'submit' && e.target.type != 'button'
   8.             && e.target.type != 'textarea') {
   9.             e.stopEvent();
  10.         }
  11.     }
  12. }

追加表单验证的规则：
   1. Ext.apply(Ext.form.VTypes, {
   2.     hankaku : function(v) {
   3.         var R = /^[ -~｡-ﾟ]*$/;
   4.         return R.test(v)
   5.     },
   6.     hankakuText : '只允许半角。',
   7.     hankakuMask : /[ -~｡-ﾟ]/i
   8. });

使用Ext.Element的mask方法，把fieldset的一部分做控件为不可输入。
   1. var fieldset = Ext.get('fieldset');
   2. fieldset.on('keydown', function(e) {
   3.     e.stopEvent();
   4. });
   5. fieldset.mask();
   6. // 解除
   7. fieldset.unmask();
   8. fieldset.un('keydown', function(e) {
   9.     e.stopEvent();
  10. });

ExtJs 中 xtype 与组件类的对应表
--------------------------------
http://liss.javaeye.com/blog/653163

image
--------------------------------
image.js

or

xtype : 'box',
    autoEl : {
      tag : 'div',
      html : '<div class="app-msg"><img src="img/magic-wand.png" class="app-img" />Log in to The Magic Forum</div>'
    }

<style type="text/css">
.app-img {
  vertical-align: middle;
  margin: 0px 20px 10px 0px;
}

.app-msg {
  font-size: large;
}
</style>

combo search style
------------------
combo 分页样式

combo-template-paging.html

form 一个不错的布局
-------------------

load.html

scroll.html -- 文件滚动效果
--------------------

this
--------------------

无论什么时候，JavaScript都会把this放到function内部。
它是基于一种非常简单的思想：如果函数直接是某个对象的成员，那么this的值就是这个对象。
如果函数不是某个对象的成员那么this的值便设为某种全局对象（常见有，浏览器中的window对象）。

除了内建的call/apply方法，Ext还为我们提供-- 辅助方法createDelegate，可以委派this。

   --END
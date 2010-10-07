
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

   --END
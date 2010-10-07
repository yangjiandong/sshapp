Ext.ns('MyApp.base');

MyApp.base.SubmitButton = Ext.extend(Ext.Button, {
     text:'确定'
    ,iconCls:'icon-submit'
    ,initComponent:function() {
        MyApp.base.SubmitButton.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent
}); // eo extend

Ext.reg('submitbutton', MyApp.base.SubmitButton);

MyApp.base.CancelButton = Ext.extend(Ext.Button, {
     text:'取消'
    ,iconCls:'icon-undo'
    ,initComponent:function() {
        MyApp.base.CancelButton.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent
}); // eo extend

Ext.reg('cancelbutton', MyApp.base.CancelButton);